package kui.com.greenkiondo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alvin on 19-Dec-17.
 */

public class KiondoListAdapter extends ArrayAdapter {

    private Context context;
    //private ArrayList<Ingredient>ingredient_kiondo;
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<Ingredient> kiondoItems;


    public KiondoListAdapter(Context context,int textViewResourceId, ArrayList objects) {
        super(context,textViewResourceId, objects);

        this.context= context;

        kiondoItems = objects;
        /*this.activity = activity;
        this.kiondoItems = kiondoItems;*/
    }

    private class ViewHolder{
        TextView ingredientKiondo;
        TextView recipeKiondo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder=null;
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_kiondo, null);

            holder = new ViewHolder();
            holder.ingredientKiondo = (TextView) convertView.findViewById(R.id.kiondo_ingredient);
            holder.recipeKiondo = (TextView) convertView.findViewById(R.id.recipe_title_kiondo);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Ingredient individualIngredient= kiondoItems.get(position);
        holder.ingredientKiondo.setText(individualIngredient.getIngredient() + "");
        holder.recipeKiondo.setText(individualIngredient.getRecipe_title_ingredient()+"");

        return convertView;

   }
}
