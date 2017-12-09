package kui.com.greenkiondo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
    private List<Recipe> recipeList = new ArrayList<Recipe>();
    private ListView listView;
    private RecipeListAdapter adapter;
    TextView recipe_urlTV, recipe_idTV, recipe_titleTV;
    Button viewDir;
    Context context = this;

    String recipe_id_detail,recipe_title_detail,recipe_source_url_detail,recipe_image_url_detail,recipe_publisher;
    static final String KEY_RECIPIE_INGREDIENT = "recipe_ingredient";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);


        recipe_idTV=(TextView) findViewById(R.id.recipe_id_detail);
        recipe_titleTV=(TextView)findViewById(R.id.recipe_title_detail);
        recipe_urlTV=(TextView)findViewById(R.id.recipe_url_detail);
        ImageView recipeImage = (ImageView) findViewById(R.id.recipe_image_detail);

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

        recipe_idTV.setText(recipe_id_detail);
        recipe_titleTV.setText(recipe_title_detail);
        recipe_urlTV.setText(recipe_source_url_detail);
        Picasso.with(context).load(recipe_image_url_detail).into(recipeImage);

        setTitle(recipe_title_detail);

        viewDir = (Button)findViewById(R.id.view_directions_detail);
        viewDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webview = new Intent(RecipeDetailActivity.this,WebViewActivity.class);
                webview.putExtra("source_url",recipe_source_url_detail);
                startActivity(webview);

            }
        });




}
}
