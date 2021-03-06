package kui.com.greenkiondo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {
    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private List<Ingredient> ingredientList = new ArrayList<>();
    private ListView iglistView;
    private IngredientListAdapter adapter;
    Button viewDir;
    private SQLiteHandler db;
    Context context = this;

    String recipe_id_detail,recipe_title_detail,recipe_source_url_detail,recipe_image_url_detail,recipe_publisher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        iglistView = findViewById(R.id.ingredientList);
        adapter = new IngredientListAdapter(this,ingredientList);
        iglistView.setAdapter(adapter);


        iglistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String passIgName = String.valueOf(ingredientList.get(position).getIngredient());
                Toast.makeText(getApplicationContext(),
                        passIgName +"recipe name:"+ recipe_title_detail, Toast.LENGTH_LONG).show();
                db = new SQLiteHandler(getApplicationContext());
               db.addIngredient(passIgName,recipe_title_detail);
            }
        });

        ImageView recipeImage = findViewById(R.id.recipe_image_detail);

        Intent intent = getIntent();

        recipe_id_detail = this.getIntent().getExtras().getString("recipe_id");
        recipe_title_detail = this.getIntent().getExtras().getString("recipe_title");
        recipe_publisher = this.getIntent().getExtras().getString("publisher");
        recipe_source_url_detail = this.getIntent().getExtras().getString("source_url");
        recipe_image_url_detail = this.getIntent().getExtras().getString("image_url");

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("food2fork.com")
                .appendPath("api")
                .appendPath("get")
                .appendQueryParameter("key","965c095439caf056aa29b3f87c0f71ae")
                .appendQueryParameter("rId",recipe_id_detail);
        String ingredient_url = builder.build().toString();


        Picasso.with(context).load(recipe_image_url_detail).into(recipeImage);

        setTitle(recipe_title_detail);

        viewDir = findViewById(R.id.view_directions_detail);
        viewDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webview = new Intent(RecipeDetailActivity.this,WebViewActivity.class);
                webview.putExtra("source_url",recipe_source_url_detail);
                startActivity(webview);

            }
        });




        /* VOLLEY FOR LIST VIEW */




        final RelativeLayout progressBar = findViewById(R.id.progressBar);
        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);
        // Creating volley request obj
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                ingredient_url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        progressBar.setVisibility(View.INVISIBLE);
                        //pDialog.hide();

                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());

                           JSONObject jpObject = jsonObject.getJSONObject("recipe");

                            // gettiing Json Array Node
                            JSONArray ingredientArray = jpObject.getJSONArray("ingredients");

                            for (int i = 0; i < ingredientArray.length(); i++) {
                                String ingred = (String) ingredientArray.get(i);
                                Ingredient ig = new Ingredient();
                                ig.setIngredient(ingred);

                                ingredientList.add(ig);
                            }

                            // adding movie to movies array

                        }catch(JSONException e){e.printStackTrace();}

                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                progressBar.setVisibility(View.INVISIBLE);
            }
        });


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_buttons, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_my_kiondo:
                Intent mk = new Intent(RecipeDetailActivity.this,MyKiondoActivity.class);
                startActivity(mk);
                return true;
             default:
                 finish();
                 return true;
        }

    }
}
