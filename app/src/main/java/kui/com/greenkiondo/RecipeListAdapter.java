package kui.com.greenkiondo;

/**
 * Created by Kui on 12/9/2017.
 */

import kui.com.greenkiondo.R;
import kui.com.greenkiondo.AppController;
import kui.com.greenkiondo.Recipe;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class RecipeListAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Recipe> recipeItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public RecipeListAdapter(Activity activity, List<Recipe> recipeItems) {
        this.activity = activity;
        this.recipeItems = recipeItems;
    }

    @Override
    public int getCount() {
        return recipeItems.size();
    }

    @Override
    public Object getItem(int location) {
        return recipeItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_recipe, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = convertView
                .findViewById(R.id.thumbnail);
        TextView recipe_title = convertView.findViewById(R.id.recipe_title);
        TextView publisher = convertView.findViewById(R.id.publisher);

        // getting movie data for the row
        Recipe r = recipeItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(r.getThumbnailUrl(), imageLoader);

        // title
        recipe_title.setText(r.getRecipe_title());

        // website name
        publisher.setText(r.getPublisher());


        return convertView;
    }



}
