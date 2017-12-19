package kui.com.greenkiondo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
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

public class SearchActivity extends AppCompatActivity {

   Button btn_search ;
   EditText search_recipe;

    private static final String TAG = SearchActivity.class.getSimpleName();

    // Movies json url

    private ProgressDialog pDialog;
    private List<Recipe> recipeList = new ArrayList<Recipe>();
    private ListView listView;
    private RecipeListAdapter adapter;

    String recpie_search_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        search_recipe = (EditText)findViewById(R.id.ed_search);
        btn_search = (Button)findViewById(R.id.btn_search_recipe);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "it can be clicked", Toast.LENGTH_LONG)
                        .show();
                String s_recipe = search_recipe.getText().toString().trim();

                /*http://food2fork.com/api/get?key=965c095439caf056aa29b3f87c0f71ae&rId=47025*/
               /* http://food2fork.com/api/search?key=965c095439caf056aa29b3f87c0f71ae&q=shredded%20chicken*/

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("food2fork.com")
                        .appendPath("api")
                        .appendPath("search")
                        .appendQueryParameter("key","965c095439caf056aa29b3f87c0f71ae")
                        .appendQueryParameter("q",s_recipe);
                recpie_search_url = builder.build().toString();



                final RelativeLayout progressBar = (RelativeLayout) findViewById(R.id.progressBar);
                //making the progressbar visible
                progressBar.setVisibility(View.VISIBLE);

                // Creating volley request obj
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        recpie_search_url, null,
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
                        progressBar.setVisibility(View.INVISIBLE);
                        // pDialog.hide();
                    }
                });


                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(jsonObjReq);

            }
        });


        listView = (ListView) findViewById(R.id.searchListView);
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

                Intent rdetail = new Intent( SearchActivity.this,RecipeDetailActivity.class);

                rdetail.putExtra("recipe_id",passRecipe_id);
                rdetail.putExtra("recipe_title", passRecipe_title);
                rdetail.putExtra("publisher",passRecipe_publisher);
                rdetail.putExtra("image_url",passRecipe_image_url);
                rdetail.putExtra("source_url",passRecipe_source_url);
                // detailIntent.putExtra("recipe_id",selectedRecipie.get(KEY_RECIPIE_ID));

                startActivity(rdetail);




            }
        });

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
