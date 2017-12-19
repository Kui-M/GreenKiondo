package kui.com.greenkiondo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyKiondoActivity extends AppCompatActivity {


    private SQLiteHandler db = null;
    KiondoListAdapter kadapter = null;
    ListView kiondoListView = null;
    ArrayList<Ingredient> kiondoItems = null;



    /*Variables to hold the records*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_kiondo);


        db = new SQLiteHandler(this);
        kiondoItems = db.getIngredients();
        kadapter = new KiondoListAdapter(this,R.layout.list_kiondo,kiondoItems);

        kiondoListView = (ListView)findViewById(R.id.kiondoList);
        kiondoListView.setAdapter(kadapter);



        Button checkout = findViewById(R.id.view_directions_detail);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PickLocation.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), NavActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

}
