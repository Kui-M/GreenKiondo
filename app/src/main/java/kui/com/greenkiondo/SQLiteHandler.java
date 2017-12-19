package kui.com.greenkiondo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kui on 11/27/2017.
 */

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "greenkiondo";

    // Login table name
    private static final String TABLE_USER = "user";

    //ingredient table
    private static final String TABLE_INGREDIENTS = "ingredients";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PY_ADD = "py_add";
    //private static final String KEY_PASS = "pass";
    private static final String KEY_UID = "uid";
   // private static final String KEY_CREATED_AT = "created_at";

    //Ingredients table column names
    private static final String KEY_INGREDIENT_ID = "ig_id";
    private static final String KEY_INGREDIENT = "ingredient";
    private static final String KEY_RECIPE_NAME = "recipe_name";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FNAME + " TEXT,"
                + KEY_LNAME + " TEXT,"
                + KEY_PHONE + " INT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_PY_ADD + " TEXT,"
               // + KEY_PASS + " TEXT,"
                + KEY_UID + " TEXT"+ ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        String CREATE_INGREDIENTS_TABLE = "CREATE TABLE " + TABLE_INGREDIENTS + "("
                + KEY_INGREDIENT_ID + " INTEGER PRIMARY KEY,"
                + KEY_INGREDIENT + " TEXT,"
                + KEY_RECIPE_NAME + " TEXT" + ")";
        db.execSQL(CREATE_INGREDIENTS_TABLE);


        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_INGREDIENTS);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String fname, String lname, String phone, String email, String py_add, String uid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, fname); // Name
        values.put(KEY_LNAME, lname);
        values.put(KEY_PHONE, phone);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PY_ADD, py_add);
        //values.put(KEY_PASS, pass);// Email
        values.put(KEY_UID, uid); // Email
       // values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("fname", cursor.getString(1));
            user.put("lname", cursor.getString(2));
            user.put("phone", cursor.getString(3));
            user.put("email", cursor.getString(4));
            user.put("py_add", cursor.getString(5));
           // user.put("pass", cursor.getString(6));
            user.put("uid", cursor.getString(6));
           // user.put("created_at", cursor.getString(8));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    /* TABLE INGREDIENTS  CRUD OPERATIONS*/

    public void  addIngredient(String ingredient,String recipe_name){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_INGREDIENT,ingredient);
        values.put(KEY_RECIPE_NAME,recipe_name);

        long id = db.insert(TABLE_INGREDIENTS,null,values);
        db.close();

        Log.d(TAG, "New user Ingredient into sqlite: " + id);

    }

   /* public HashMap<String,String> getIngredients(){
        HashMap<String, String> ingredient = new HashMap<>();
        String selectQuery = "SELECT  * FROM " + TABLE_INGREDIENTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            ingredient.put("ingredient", cursor.getString(1));
            ingredient.put("recipe_name", cursor.getString(2));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching Ingredient from Sqlite: " + ingredient.toString());

        return ingredient;
    }*/

   public ArrayList<Ingredient> getIngredients(){
       SQLiteDatabase db = this.getReadableDatabase();
       ArrayList<Ingredient> ig= new ArrayList<Ingredient>();
       String selectQuery = "SELECT  * FROM " + TABLE_INGREDIENTS;

       Cursor result = db.rawQuery(selectQuery , null);
       while(result.moveToNext()){
           ig.add( new Ingredient(result.getString(result.getColumnIndex(KEY_INGREDIENT)),
                   result.getString(result.getColumnIndex(KEY_RECIPE_NAME))));

       }
       return ig;
   }

    public void  deleteIngredient(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_INGREDIENTS, null, null);
        db.close();

        Log.d(TAG, "Deleted all ingredients info from sqlite");
    }



}

