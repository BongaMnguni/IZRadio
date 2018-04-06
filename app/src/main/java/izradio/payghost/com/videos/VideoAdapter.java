package izradio.payghost.com.videos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;
import izradio.payghost.com.R;

/**
 * Created by Payghost on 3/26/2018.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.videoviewHolder>{

    public List<videoItems> list ;
    private Context context;


    public VideoAdapter(Context context, List<videoItems> list) {
        this.list = list;
        this.context = context;

    }
    @Override
    public videoviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_video_layout,null);
        videoviewHolder viewHolder = new videoviewHolder(v);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final videoviewHolder holder, final int position) {

        holder.timeLayout.getBackground().setAlpha(150);
        holder.time.setText(list.get(position).getTime());

      holder.image.setImageBitmap(list.get(position).getVideoURL());

        Animation upAnim = AnimationUtils.loadAnimation(context,R.anim.simple_grow);
        upAnim.reset();
        holder.itemView.clearAnimation();
        holder.itemView.setAnimation(upAnim);

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getUrl()));
                context.startActivity(browserIntent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return this.list.size();
    }


    class videoviewHolder extends RecyclerView.ViewHolder {

        public LinearLayout timeLayout;
        public TextView time;
        public ImageView image;
        public ImageView play;

        public videoviewHolder(View itemView) {
            super(itemView);

            timeLayout = (LinearLayout) itemView.findViewById(R.id.timeLayout);
            time = (TextView) itemView.findViewById(R.id.vid_time);
            image = (ImageView) itemView.findViewById(R.id.gallery_image);
            play = (ImageView) itemView.findViewById(R.id.videoplay);

        }
    }

}