package izradio.payghost.com.Gallery;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import izradio.payghost.com.R;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryviewHolder>{

    public List<GalleryItems> list ;
    private Context context;

    public GalleryAdapter(Context context, List<GalleryItems> list) {
        this.list = list;
        this.context = context;

    }
    @Override
    public GalleryviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_cardview,null);
        GalleryviewHolder viewHolder = new GalleryviewHolder(v);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final GalleryviewHolder holder, final int position) {

        Picasso.with(context).load(list.get(position).getImage()).into(holder.image);

        Animation upAnim = AnimationUtils.loadAnimation(context,R.anim.simple_grow);
        upAnim.reset();
        holder.itemView.clearAnimation();
        holder.itemView.setAnimation(upAnim);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_image);
                dialog.setCancelable(true);

                ImageView image = (ImageView) dialog.findViewById(R.id.img_full);
                Picasso.with(context).load(list.get(position).getImage()).into(image);

                dialog.show();

            }
        });

    }
    @Override
    public int getItemCount() {
        return this.list.size();
    }
}
