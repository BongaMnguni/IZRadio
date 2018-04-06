package izradio.payghost.com.Tvshow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import java.util.List;

import izradio.payghost.com.MoreDetails;
import izradio.payghost.com.R;


public class ShowAdapter extends RecyclerView.Adapter<ShowviewHolder>{
    public List<ShowItems> list ;
    private Context context;

    public ShowAdapter(Context context, List<ShowItems> list) {
        this.list = list;
        this.context = context;

    }
    @Override
    public ShowviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_card_layout,null);
        ShowviewHolder viewHolder = new ShowviewHolder(v);

        return viewHolder;
    }
    @SuppressLint("Range")
    @Override
    public void onBindViewHolder(final ShowviewHolder holder, final int position) {



        try {

            Picasso.with(context).load(list.get(position).getImage()).resize(150, 150).centerCrop().into(holder.image);
            holder.message.setText(list.get(position).getMessage());
            holder.date.setText(list.get(position).getDate());
            holder.name.setText(list.get(position).getName());
           // holder.header.setAlpha(145);

            Animation upAnim = AnimationUtils.loadAnimation(context, R.anim.slide_up_right);
            upAnim.reset();
            holder.itemView.clearAnimation();
            holder.itemView.setAnimation(upAnim);
            holder.itemView.setAnimation(upAnim);

            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String message = "Show" + "\n" +
                            "Title: " + list.get(position).getName().toString() +"\n" +
                            "Description " + list.get(position).getMessage().toString()  +"\n" +"\n" +
                            "Download the IZ Radio App : https://play.google.com/store/apps/details?id=izradio.payghost.com";

                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.putExtra(Intent.EXTRA_TEXT, message);
                    context.startActivity(Intent.createChooser(share, "Share Vie"));
                }
            });

            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context,MoreDetails.class);
                    intent.putExtra("image",list.get(position).getImage());
                    intent.putExtra("date",list.get(position).getDate());
                    intent.putExtra("message",list.get(position).getMessage());
                    intent.putExtra("sender",list.get(position).getSender());
                    intent.putExtra("name",list.get(position).getName());
                    context.startActivity(intent);
                }
            });


        }catch (Exception e){
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public int getItemCount() {
        return this.list.size();
    }
}

