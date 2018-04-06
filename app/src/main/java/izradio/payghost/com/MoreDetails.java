package izradio.payghost.com;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MoreDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details);

        final TextView name = (TextView)findViewById(R.id.more_name);
        final TextView message = (TextView)findViewById(R.id.more_message);
        final ImageView image = (ImageView)findViewById(R.id.more_image);
      //  final TextView back = (TextView)findViewById(R.id.more_back);

      //  final TextView sender_textview = (TextView)findViewById(R.id.more_sender);
        final TextView date_textview = (TextView)findViewById(R.id.more_date);

        Bundle bundle = getIntent().getExtras();
        String img = bundle.getString("image");
        name.setText(bundle.getString("name"));
        message.setText(bundle.getString("message"));
       /// sender_textview.setText("By: "+bundle.getString("sender"));
        date_textview.setText(bundle.getString("date"));

        Picasso.with(getApplicationContext()).load(img).into(image);
/*
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id == R.id.action_back){
            startActivity(new Intent(MoreDetails.this,MainActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }

}
