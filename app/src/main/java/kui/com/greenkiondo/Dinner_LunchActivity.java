package kui.com.greenkiondo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Dinner_LunchActivity extends AppCompatActivity {
    // Log tag
    private static final String TAG = BreakfastActivity.class.getSimpleName();

    // Movies json url
    private static final String url = "http://food2fork.com/api/search?key=965c095439caf056aa29b3f87c0f71ae&q=dinner";
    private ProgressDialog pDialog;
    private List<Recipe> recipeList = new ArrayList<>();
    private ListView listView;
    private RecipeListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner__lunch);
        listView = findViewById(R.id.list_dinlin);
        adapter = new RecipeListAdapter(this, recipeList);
        listView.setAdapter(adapter);

        final Context context = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String passRecipe_id = String.valueOf(recipeList.get(position).getRecipe_id());
                String passRecipe_title = String.valueOf(recipeList.get(position).getRecipe_title());
                String passRecipe_publisher = String.valueOf(recipeList.get(position).getPublisher());
                String passRecipe_image_url = String.valueOf(recipeList.get(position).getThumbnailUrl());
                String passRecipe_source_url = String.valueOf(recipeList.get(position).getSource_url());

                Intent rdetail = new Intent( Dinner_LunchActivity.this,RecipeDetailActivity.class);

                rdetail.putExtra("recipe_id",passRecipe_id);
                rdetail.putExtra("recipe_title", passRecipe_title);
                rdetail.putExtra("publisher",passRecipe_publisher);
                rdetail.putExtra("image_url",passRecipe_image_url);
                rdetail.putExtra("source_url",passRecipe_source_url);
                // detailIntent.putExtra("recipe_id",selectedRecipie.get(KEY_RECIPIE_ID));

                startActivity(rdetail);



            }
        });


       /* pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();*/
        final RelativeLayout progressBar = findViewById(R.id.progressBar);
        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        // Creating volley request obj
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        progressBar.setVisibility(View.INVISIBLE);
                       // pDialog.hide();

                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());

                            // gettiing Json Array Node
                            JSONArray recipeArray = jsonObject.getJSONArray("recipes");
                            for (int i = 0; i < recipeArray.length(); i++) {

                                JSONObject obj = recipeArray.getJSONObject(i);
                                Recipe recipe = new Recipe();
                                recipe.setRecipe_title(obj.getString("title"));
                                recipe.setThumbnailUrl(obj.getString("image_url"));
                                recipe.setSource_url(obj.getString("source_url"));
                                recipe.setPublisher(obj.getString("publisher"));
                                recipe.setRecipe_id(obj.getString("recipe_id"));

                                recipeList.add(recipe);
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
                pDialog.hide();
            }
        });


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), NavActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

}
