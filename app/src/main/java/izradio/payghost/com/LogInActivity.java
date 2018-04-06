package izradio.payghost.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        final EditText username = (EditText)findViewById(R.id.edUsername);
        final EditText password = (EditText)findViewById(R.id.edPassword);

        Button login = (Button)findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(username.getText().toString().equalsIgnoreCase("izradio") && password.getText().toString().equalsIgnoreCase("admin@izradio$#")  ){
                    startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                }else {
                    username.setError("wrong username");
                    password.setError("wrong password");
                }

            }
        });


    }
}
