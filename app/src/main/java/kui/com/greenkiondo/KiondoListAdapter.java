package kui.com.greenkiondo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alvin on 19-Dec-17.
 */

public class KiondoListAdapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<Ingredient> kiondoItems;


    public KiondoListAdapter(Activity activity, List<Ingredient> kiondoItems) {
        this.activity = activity;
        this.kiondoItems = kiondoItems;
    }

    @Override
    public int getCount() {
        return kiondoItems.size();
    }

    @Override
    public Object getItem(int location) {
        return kiondoItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_kiondo, null);


        TextView kiondo_ingredient = convertView.findViewById(R.id.kiondo_ingredient);


        // getting movie data for the row

        Ingredient i = kiondoItems.get(position);


        // title

        kiondo_ingredient.setText(i.getIngredient());


        return convertView;
    }
}
