package izradio.payghost.com;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import java.util.concurrent.TimeUnit;

import izradio.payghost.com.Gallery.GalleryAdapter;
import izradio.payghost.com.Gallery.GalleryItems;
import izradio.payghost.com.videos.VideoAdapter;
import izradio.payghost.com.videos.videoItems;

public class videoFragment extends Fragment {

    private String JSON_STRING;
    private Bitmap video;
    public static String video_duration,url;

    RecyclerView recyclerView;
    VideoAdapter videoAdapter;

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



        getVideos();

        return root;
    }

    private void showVideos(){

        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        List<videoItems> arrList = new ArrayList<videoItems>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                video = getVideoThumbnail(jo.getString(Config.TAG_VIDEO));
                url = jo.getString(Config.TAG_VIDEO);

                arrList.add(new videoItems(video_duration,video,url));
            }
            videoAdapter = new VideoAdapter(getContext(),arrList);
            recyclerView.setAdapter(videoAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getVideos(){
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
                String s = rh.sendGetRequest(Config.URL_GET_VIDEOS);
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
