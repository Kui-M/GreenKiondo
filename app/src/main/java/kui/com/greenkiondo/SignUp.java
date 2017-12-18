package kui.com.greenkiondo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.Types.NULL;

public class SignUp extends AppCompatActivity {

    EditText sn_fname,sn_lname,sn_phone,sn_email,sn_phyAdd,sn_pass;
    private static final String TAG = SignUp.class.getSimpleName();
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sn_fname = findViewById(R.id.sn_fName);
        sn_lname = findViewById(R.id.sn_lName);
        sn_phone = findViewById(R.id.sn_pNo);
        sn_email = findViewById(R.id.sn_email);
        sn_phyAdd = findViewById(R.id.sn_pAddress);
        sn_pass = findViewById(R.id.sn_password);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(SignUp.this,
                    NavActivity.class);
            startActivity(intent);
            finish();
        }

        Button btn_signUp = findViewById(R.id.btn_sign_up);
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = sn_fname.getText().toString().trim();
                String lname = sn_lname.getText().toString().trim();
                String phone = sn_phone.getText().toString().trim();
                String email = sn_email.getText().toString().trim();
                String py_add = sn_phyAdd.getText().toString().trim();
                String password = sn_pass.getText().toString().trim();
/*
                Log.i("FIRST NAME :",fname);
                Log.i("LAST NAME: ",lname);
                Log.i("PHONE : ",phone);
                Log.i("EMAIL: ",email);
               // Log.i("PASSWORD : ",password);

                Log.d("hello","hi");*/

                if (!fname.isEmpty() && !lname.isEmpty()&& !phone.isEmpty()&& !email.isEmpty() && !py_add.isEmpty()&& !password.isEmpty()) {

                    registerUser(fname,lname,phone,email,py_add,password);

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String fname, final String lname, final String phone, final String email, final String py_add,
                              final String pass) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String fname = user.getString("fname");
                        String lname = user.getString("lname");
                        String phone = user.getString("phone");
                        String email = user.getString("email");
                        String py_add = user.getString("py_add");
                       // String pass = user.getString("pass");
                       // String created_at = user.getString("created_at");

                        // Inserting row in users table
                        db.addUser(fname,lname,phone,email,py_add,uid);
                        db.getUserDetails();

                        Toast.makeText(getApplicationContext(), " IT WORKSSSSSSSSSSSS", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                SignUp.this,
                                Login.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message

                        db.getUserDetails();
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("fname", fname);
                params.put("lname", lname);
                params.put("phone", phone);
                params.put("email", email);
                params.put("py_add", py_add);
                params.put("pass", pass);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    /* Log.d("Reading: ", "Reading all User Details..");
    List<Users> userses = db.getAllUsers();

        Log.d("Table 1:", "Table Users Details ");
        for (Users u :userses){
        String log = "Id: "+u.getId()+"  ,Name: " + u.getName() + "  ,Job: " + u.getJob();

        Log.d("Users: ", log);
    }*/

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), Login.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
