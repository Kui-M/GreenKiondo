package kui.com.greenkiondo;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Final extends AppCompatActivity {
    private TextView showDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        showDetails = findViewById(R.id.checkout);

        Bundle extras = getIntent().getExtras();
        //String latitude = extras.getString("latitude");
        //String longitude = extras.getString("longitude");
        String placename = extras.getString("placename");
        String address = extras.getString("address");

        StringBuilder stBuilder = new StringBuilder();
        /*stBuilder.append("Name: ");
        stBuilder.append("\n");
        stBuilder.append("Latitude: ");
        stBuilder.append(latitude);
        stBuilder.append("\n");
        stBuilder.append("Longitude: ");
        stBuilder.append(longitude);
        stBuilder.append("\n");
        */stBuilder.append("Your Kiondo will be sent to: ");
        stBuilder.append("\n");
        stBuilder.append(placename);
        stBuilder.append("\n");
        stBuilder.append(address);
        showDetails.setText(stBuilder.toString());


        Button done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Thankyou for using Green Kiondo.", Toast.LENGTH_LONG)
                        .show();

                Intent intent = new Intent(getApplicationContext(), NavActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), PickLocation.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
