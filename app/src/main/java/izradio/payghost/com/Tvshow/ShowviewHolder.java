package izradio.payghost.com.Tvshow;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import izradio.payghost.com.R;


public class ShowviewHolder extends RecyclerView.ViewHolder{
    ImageView image;
    ImageView share;
    TextView name;
    TextView message;
    TextView date;
   //RelativeLayout header;


    public ShowviewHolder(View v){
        super(v);
        image =(ImageView) v.findViewById(R.id.imgPost);
        share =(ImageView) v.findViewById(R.id.ads_share);
        name = (TextView) v.findViewById(R.id.tvShowName);
        message = (TextView) v.findViewById(R.id.tvMessage);
        date = (TextView) v.findViewById(R.id.tvDate);
      //  header = (RelativeLayout) v.findViewById(R.id.header);
    }
}