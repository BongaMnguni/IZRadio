package izradio.payghost.com.Delete;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import izradio.payghost.com.Config;
import izradio.payghost.com.Gallery.GalleryItems;
import izradio.payghost.com.R;
import izradio.payghost.com.RequestHandler;
import izradio.payghost.com.Tvshow.ShowAdapter;
import izradio.payghost.com.Tvshow.ShowItems;
import izradio.payghost.com.signup.SignupAdapter;
import izradio.payghost.com.signup.SignupItems;
import izradio.payghost.com.videos.VideoAdapter;
import izradio.payghost.com.videos.videoItems;

public class DisplayDelete extends Fragment {

    private String JSON_STRING,GET_URL;
    private String image,id,table,name,school,number,message,date,sender;
    private Bitmap video;

    public static String video_duration,url;

    LinearLayoutManager linearlayout;
    RecyclerView recyclerView;
    DelGalleryAdapter galleryAdapter;
    DelSignupAdapter signupAdapter;
    DelShowAdapter postviewAdapter;
    DelVideoAdapter videoAdapter;

    SharedPreferences sharedpreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootview = inflater.inflate(R.layout.fragment_display_delete, container, false);

        RelativeLayout linearLayout = (RelativeLayout) rootview.findViewById(R.id.del_displayLayout);
        linearLayout.getBackground().setAlpha(230);

        sharedpreferences = getActivity().getSharedPreferences("Table", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        table = getArguments().getString("table");
        editor.putString("table",table);
        editor.commit();

        recyclerView = (RecyclerView)rootview.findViewById(R.id.listGallerys);
        GET_URL = getArguments().getString("url");

        String frag = getArguments().getString("fragment");

        switch (frag) {
            case "Gallery":
                getJSON(GET_URL);
                break;
            case "Videos":
               getVideos(GET_URL);
                break;
            case "News":
               getNewsJSON(GET_URL);
                break;
            case "Members":
                getMembersJSON(GET_URL);
                break;

        }
        return rootview;
    }

    private void showGallery(){

        Display display = getActivity().getWindowManager().getDefaultDisplay();
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

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),columns);
        recyclerView.setLayoutManager(layoutManager);


        JSONObject jsonObject = null;
        List<GalleryItems> arrList = new ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                id = jo.getString(Config.TAG_ID);
                image = jo.getString(Config.TAG_IMAGE);

                arrList.add(new GalleryItems(image,id));
            }
            galleryAdapter = new DelGalleryAdapter(getActivity(),arrList);
            recyclerView.setAdapter(galleryAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void getJSON(final String urlz){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = new ProgressDialog(getActivity());
                loading.setMessage("Fetching Images...");
                loading.setIndeterminate(false);
                loading.setCancelable(true);
                loading.show(); }

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
                String s = rh.sendGetRequest(urlz);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }


    ///////////////////////////// MEMBERS   //////////////////////////////////////////

    private void showMembers(){

        linearlayout = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);

        JSONObject jsonObject = null;
        List<SignupItems> arrList = new ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                id = jo.getString(Config.TAG_ID);
                name = jo.getString(Config.TAG_POST_NAMES);
                school = jo.getString(Config.TAG_POST_SCHOOL_NAME);
                number = jo.getString(Config.TAG_POST_NUMBER);

                arrList.add(new SignupItems(id,name,number,school));

            }
            try {
                signupAdapter = new DelSignupAdapter(getActivity().getApplicationContext(),arrList);
                recyclerView.setAdapter(signupAdapter);
            }catch (Exception e){

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void getMembersJSON(final String urlz){

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
                String s = rh.sendGetRequest(urlz);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
//////////////////////////////////////////////////// NEWS //////////////////////////////////////////////////////////////////////

    private void showNews(){

        linearlayout = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);

        JSONObject jsonObject = null;
        List<ShowItems> arrList = new ArrayList<ShowItems>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                id = jo.getString(Config.TAG_ID);
                name = jo.getString(Config.TAG_POST_NAME);
                image = jo.getString(Config.TAG_POST_IMAGE);
                message = jo.getString(Config.TAG_POST_MESSAGE);
                date = jo.getString(Config.TAG_POST_DATE);
                sender = jo.getString(Config.TAG_POST_SENDER);

                arrList.add(new ShowItems(id,name,image,date,message,sender));
            }
            try{
                postviewAdapter = new DelShowAdapter(getContext(),arrList);
                recyclerView.setAdapter(postviewAdapter);
            }catch (Exception e){

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getNewsJSON(final String urlz){
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
                showNews();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(urlz);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    /////////////////////////////////////////////////   VIDEOS   //////////////////////////////////////////////////
    private void showVideos(){

        Display display = getActivity().getWindowManager().getDefaultDisplay();
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

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),columns);
        recyclerView.setLayoutManager(layoutManager);


        JSONObject jsonObject = null;
        List<videoItems> arrList = new ArrayList<videoItems>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                video = getVideoThumbnail(jo.getString(Config.TAG_VIDEO));
                id = jo.getString(Config.TAG_ID);
                url = jo.getString(Config.TAG_VIDEO);

                arrList.add(new videoItems(video_duration,video,url,id));

            }
            videoAdapter = new DelVideoAdapter(getContext(),arrList);
            recyclerView.setAdapter(videoAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getVideos(final String urlz){
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
                showVideos();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(urlz);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    public static Bitmap getVideoThumbnail(String videoPath)
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14) {
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
                String time = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                int duration = Integer.parseInt(time);
                String minutes = TimeUnit.MILLISECONDS.toMinutes(duration)+"";
                String seconds = (TimeUnit.MILLISECONDS.toSeconds(duration)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)))+"";
                if(TimeUnit.MILLISECONDS.toMinutes(duration)<10)
                {
                    minutes = "0"+TimeUnit.MILLISECONDS.toMinutes(duration);
                }
                if((TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)))<10)
                {
                    seconds = "0"+(TimeUnit.MILLISECONDS.toSeconds(duration) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
                }
                video_duration = minutes+":"+seconds;

            }
            else {
                mediaMetadataRetriever.setDataSource(videoPath);
                //   mediaMetadataRetriever.setDataSource(videoPath);
                String time = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                int duration = Integer.parseInt(time);

                String minutes = TimeUnit.MILLISECONDS.toMinutes(duration)+"";
                String seconds = (TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)))+"";
                if(TimeUnit.MILLISECONDS.toMinutes(duration)<10)
                {
                    minutes = "0"+TimeUnit.MILLISECONDS.toMinutes(duration);
                }
                if((TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)))<10)
                {
                    seconds = "0"+(TimeUnit.MILLISECONDS.toSeconds(duration) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
                }
                video_duration = minutes+":"+seconds;
             /*   video_duration = String.format("0%d:0%d",
                        TimeUnit.MILLISECONDS.toMinutes(duration),
                        TimeUnit.MILLISECONDS.toSeconds(duration) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
                );*/
            }
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // Toast.makeText(,e.getMessage(),Toast.LENGTH_LONG).show();

        }
        finally
        {
            if (mediaMetadataRetriever != null)
            {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }
}
