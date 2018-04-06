package izradio.payghost.com;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import izradio.payghost.com.Tvshow.ShowAdapter;
import izradio.payghost.com.Tvshow.ShowItems;


public class NewsFragment extends Fragment {

    private String JSON_STRING;
    String name;
    String image;
    String message;
    String sender;
    String date;

    LinearLayoutManager linearlayout;
    RecyclerView recyclerView;
    ShowAdapter postviewAdapter;


    RelativeLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = (RecyclerView)root.findViewById(R.id.listMessage);
        linearlayout = new LinearLayoutManager(this.getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);

        getJSON();

        return  root;
    }

    private void showMessages(){

        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        List<ShowItems> arrList = new ArrayList<ShowItems>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                name = jo.getString(Config.TAG_POST_NAME);
                image = jo.getString(Config.TAG_POST_IMAGE);
                message = jo.getString(Config.TAG_POST_MESSAGE);
                date = jo.getString(Config.TAG_POST_DATE);
                sender = jo.getString(Config.TAG_POST_SENDER);

                arrList.add(new ShowItems(name,image,date,message,sender));
            }
            postviewAdapter = new ShowAdapter(getContext(),arrList);
            recyclerView.setAdapter(postviewAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = new ProgressDialog(getActivity());
                loading.setMessage("Please wait...");
                loading.setIndeterminate(false);
                loading.setCancelable(true);
                loading.show();
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
                String s = rh.sendGetRequest(Config.URL_GET_ALL_POST);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

}
