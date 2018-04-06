package izradio.payghost.com.Docs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import izradio.payghost.com.Config;
import izradio.payghost.com.MainActivity;
import izradio.payghost.com.R;
import izradio.payghost.com.RequestHandler;

public class Files extends AppCompatActivity {

    private String JSON_STRING;
    String filename;
    String fileurl;

    LinearLayoutManager linearlayout;
    RecyclerView recyclerView;
    FileAdapter fileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        recyclerView = (RecyclerView)findViewById(R.id.files_recyclerview);
        linearlayout = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);

        getJSON();
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
        if (id == R.id.action_back) {
            startActivity(new Intent(Files.this,MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMessages(){

        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        List<FileItems> arrList = new ArrayList<FileItems>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                filename = jo.getString(Config.TAG_FILE_NAME);
                fileurl = jo.getString(Config.TAG_FILE_URL);

                HashMap<String,String> Tenants = new HashMap<>();

                Tenants.put(Config.TAG_FILE_NAME,filename);
                Tenants.put(Config.TAG_FILE_URL,fileurl);

                list.add(Tenants);
                arrList.add(new FileItems(filename,fileurl));
            }
            fileAdapter = new FileAdapter(getApplicationContext(),arrList);
            recyclerView.setAdapter(fileAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                Files.this, list, R.layout.files_cardview,
                new String[]{Config.TAG_FILE_NAME,Config.TAG_FILE_URL},
                new int[]{R.id.txtfile,R.id.ivfile});
    }


    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Files.this,"Fetching Documents"," Please Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showMessages();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL_FILESs);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
