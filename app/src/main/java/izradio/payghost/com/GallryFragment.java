package izradio.payghost.com;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import izradio.payghost.com.Gallery.GalleryAdapter;
import izradio.payghost.com.Gallery.GalleryItems;

public class GallryFragment extends Fragment {

    private String JSON_STRING;
    private String image;

    RecyclerView recyclerView;
    GalleryAdapter galleryAdapter;

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_gallry, container, false);

        recyclerView = (RecyclerView)root.findViewById(R.id.listGallerys);

        context = root.getContext();

        Display display  = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;
        int columns = Math.round(dpWidth/300);

        if(columns == 1){
            columns = 2;
        }else {
            columns = Math.round(dpWidth/300);
        }

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,columns);
        recyclerView.setLayoutManager(layoutManager);

        getGallery();

        return root;
    }

    private void showGallery(){

        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        List<GalleryItems> arrList = new ArrayList<GalleryItems>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                image = jo.getString(Config.TAG_IMAGE);

                HashMap<String,String> Tenants = new HashMap<>();

                Tenants.put(Config.TAG_IMAGE,image);

                list.add(Tenants);

                arrList.add(new GalleryItems(image));
            }
            galleryAdapter = new GalleryAdapter(getContext(),arrList);
            recyclerView.setAdapter(galleryAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getGallery(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
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
                showGallery();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_GALLERY);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
