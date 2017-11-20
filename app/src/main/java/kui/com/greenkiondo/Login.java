package kui.com.greenkiondo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
     EditText lg_email,lg_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lg_email = findViewById(R.id.lgEmail);
        lg_password = findViewById(R.id.lgPassword);

        Button lg_login = findViewById(R.id.btn_login);
        lg_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lg_home = new Intent(Login.this,Home.class);
                startActivity(lg_home);
            }
        });

        Button sn = findViewById(R.id.btn_login_signup);
        sn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sn_screen = new Intent(Login.this,SignUp.class);
                startActivity(sn_screen);
            }
        });
    }
}
