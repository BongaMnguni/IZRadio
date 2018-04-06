package izradio.payghost.com.signup;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
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
import java.util.List;

import izradio.payghost.com.Config;
import izradio.payghost.com.R;
import izradio.payghost.com.RequestHandler;

public class Members extends Fragment {

    private String JSON_STRING;
    String name,school,number;

    LinearLayoutManager linearlayout;
    RecyclerView recyclerView;
    SignupAdapter signupAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_members, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.listMembers);

        linearlayout = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);

        RelativeLayout linearLayout = (RelativeLayout)view.findViewById(R.id.membersLayout);
        linearLayout.getBackground().setAlpha(230);

        getJSON();

        return view;
    }

    private void showMembers(){

        JSONObject jsonObject = null;
        List<SignupItems> arrList = new ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                name = jo.getString(Config.TAG_POST_NAMES);
                school = jo.getString(Config.TAG_POST_SCHOOL_NAME);
                number = jo.getString(Config.TAG_POST_NUMBER);


                arrList.add(new SignupItems(name,number,school));


            }
            try {
                signupAdapter = new SignupAdapter(getActivity().getApplicationContext(),arrList);
                recyclerView.setAdapter(signupAdapter);
            }catch (Exception e){

            }


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
                showMembers();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_MEMBERS);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

}
