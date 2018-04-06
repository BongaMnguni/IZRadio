package izradio.payghost.com;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    EditText name,school,number;
    AppCompatButton signup;
    SharedPreferences sharedpreferences;
    RelativeLayout welcomelayout,signuplayout;
    CardView contin;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("Sign Up");

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        contin = (CardView) findViewById(R.id.contin);
        welcomelayout = (RelativeLayout)findViewById(R.id.welcomelayout);
        signuplayout = (RelativeLayout)findViewById(R.id.signuplayout);

        sharedpreferences = getSharedPreferences("SignUp", Context.MODE_PRIVATE);
        name = (EditText)findViewById(R.id.signup_name);
        school = (EditText)findViewById(R.id.signup_skul_name);
        number = (EditText)findViewById(R.id.signup_number);

        contin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        signup = (AppCompatButton)findViewById(R.id.btnSignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num = number.getText().length();
                if (!(num == 10)){
                    number.setError("Enter A valid Number");
                    Toast.makeText(getApplicationContext(), "Enter A valid Number", Toast.LENGTH_LONG).show();
                }else {
                    signuplayout.setVisibility(View.GONE);
                    welcomelayout.setVisibility(View.VISIBLE);

                    Animation upAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_right);
                    upAnim.reset();
                    welcomelayout.clearAnimation();
                    welcomelayout.setAnimation(upAnim);
                    welcomelayout.setAnimation(upAnim);
                }

                check ();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        signuplayout.setVisibility(View.VISIBLE);
                        welcomelayout.setVisibility(View.GONE);


                        signup();

                    }
                }, 3000);


            }
        });
    }

    private void check (){

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("check",true);
        editor.commit();
    }

    public void signup(){


        if(!name.getText().toString().equals(" ") || !school.getText().toString().equals(" ") || !number.getText().toString().equals(" ") ){
            int num = number.getText().length();
            if (!(num == 10)){
                number.setError("Enter A valid Number");
            }else {
                StringRequest request = new StringRequest(Request.Method.POST, Config.URL_INSERT_MEMBERS, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response.toString());
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> parameters = new HashMap<String, String>();

                        parameters.put("name", name.getText().toString());
                        parameters.put("school", school.getText().toString());
                        parameters.put("number", number.getText().toString());


                        return parameters;
                    }
                };
                requestQueue.add(request);
                Toast.makeText(getApplicationContext(), " Registered Successful", Toast.LENGTH_LONG).show();
                contin.setVisibility(View.VISIBLE);
            }

        }else {
            Toast.makeText(getApplicationContext(), "All Field Are Required !!!", Toast.LENGTH_LONG).show();
        }

    }
}
