package kui.com.greenkiondo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by Kui on 12/11/2017.
 */

public class IngredientListAdapter extends BaseAdapter {


    private Activity activity;
    private LayoutInflater inflater;
    private List<Ingredient> ingredientItems;


    public IngredientListAdapter(Activity activity, List<Ingredient> ingredientItems) {
        this.activity = activity;
        this.ingredientItems = ingredientItems;
    }

    @Override
    public int getCount() {
        return ingredientItems.size();
    }

    @Override
    public Object getItem(int location) {
        return ingredientItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_recipe_ingredient, null);


        TextView recipe_ingredient = convertView.findViewById(R.id.recipe_ingredient);


        // getting movie data for the row

        Ingredient i = ingredientItems.get(position);


        // title

        recipe_ingredient.setText(i.getIngredient());


        return convertView;
    }
}