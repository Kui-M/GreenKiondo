package kui.com.greenkiondo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {

    EditText sn_fname,sn_lname,sn_phone,sn_email,sn_phyAdd,sn_pass,sn_cPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sn_fname = findViewById(R.id.sn_fName);
        sn_lname = findViewById(R.id.sn_lName);
        sn_phone = findViewById(R.id.sn_pNo);
        sn_email = findViewById(R.id.sn_email);
        sn_phyAdd = findViewById(R.id.sn_pAddress);
        sn_pass = findViewById(R.id.sn_password);
        sn_cPass = findViewById(R.id.sn_cPassword);

        Button btn_signUp = findViewById(R.id.btn_sign_up);
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sn_login = new Intent(SignUp.this,Login.class);
                startActivity(sn_login);
            }
        });

    }
}
