package izradio.payghost.com.Delete;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import izradio.payghost.com.Config;
import izradio.payghost.com.MoreDetails;
import izradio.payghost.com.R;
import izradio.payghost.com.Tvshow.ShowItems;
import izradio.payghost.com.Tvshow.ShowviewHolder;


public class DelShowAdapter extends RecyclerView.Adapter<DelShowAdapter.ShowviewHolder>{
    public List<ShowItems> list ;
    private Context context;
    RequestQueue requestQueue;
    SharedPreferences prefs ;

    public DelShowAdapter(Context context, List<ShowItems> list) {
        this.list = list;
        this.context = context;

    }
    @Override
    public ShowviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.del_main_card_layout,null);
        ShowviewHolder viewHolder = new ShowviewHolder(v);

        return viewHolder;
    }
    @SuppressLint("Range")
    @Override
    public void onBindViewHolder(final ShowviewHolder holder, final int position) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        prefs = context.getSharedPreferences("Table", Context.MODE_PRIVATE);
        final String tbl = prefs.getString("table", null);

        try {

            Picasso.with(context).load(list.get(position).getImage()).resize(150, 150).centerCrop().into(holder.image);
            holder.message.setText(list.get(position).getMessage());
            holder.date.setText(list.get(position).getDate());
            holder.name.setText(list.get(position).getName());
           // holder.header.setAlpha(145);

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setMessage(Html.fromHtml("<font color='#1b5e20'>Proceed with deleting ?</font>"))
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    sendValues(tbl,list.get(position).getId());


                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

            Animation upAnim = AnimationUtils.loadAnimation(context, R.anim.slide_up_right);
            upAnim.reset();
            holder.itemView.clearAnimation();
            holder.itemView.setAnimation(upAnim);
            holder.itemView.setAnimation(upAnim);


        }catch (Exception e){
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public int getItemCount() {
        return this.list.size();
    }

    class ShowviewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        ImageView share;
        TextView name;
        TextView message;
        TextView date;
        FloatingActionButton delete;
        //RelativeLayout header;


        public ShowviewHolder(View v){
            super(v);
            image =(ImageView) v.findViewById(R.id.imgPost);
            share =(ImageView) v.findViewById(R.id.ads_share);
            name = (TextView) v.findViewById(R.id.tvShowName);
            message = (TextView) v.findViewById(R.id.tvMessage);
            date = (TextView) v.findViewById(R.id.tvDate);
            delete = (FloatingActionButton)itemView.findViewById(R.id.fab_delete);
            //  header = (RelativeLayout) v.findViewById(R.id.header);
        }
    }
    public void sendValues(final String table, final String id){

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_DELETE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response.toString());
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("table", table);
                parameters.put("id", id);

                return parameters;
            }
        };
        requestQueue.add(request);
        Toast.makeText(context, " deleted...", Toast.LENGTH_LONG).show();
    }
}



