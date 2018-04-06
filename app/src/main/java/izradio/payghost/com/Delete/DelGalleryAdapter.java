package izradio.payghost.com.Delete;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import izradio.payghost.com.Gallery.GalleryItems;
import izradio.payghost.com.R;

/**
 * Created by 21428 on 10/18/2017.
 */
public class DelGalleryAdapter extends RecyclerView.Adapter<DelGalleryAdapter.GalleryviewHolder>{

    public List<GalleryItems> list ;
    private Context context;
    RequestQueue requestQueue;
    SharedPreferences prefs ;

    public DelGalleryAdapter(Context context, List<GalleryItems> list) {
        this.list = list;
        this.context = context;

    }
    @Override
    public GalleryviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.del_gallery_cardview,null);
        GalleryviewHolder viewHolder = new GalleryviewHolder(v);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final GalleryviewHolder holder, final int position) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        prefs = context.getSharedPreferences("Table", Context.MODE_PRIVATE);
        final String tbl = prefs.getString("table", null);

        Picasso.with(context).load(list.get(position).getImage()).into(holder.image);

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

        Animation upAnim = AnimationUtils.loadAnimation(context,R.anim.translate);
        upAnim.reset();
        holder.itemView.clearAnimation();
        holder.itemView.setAnimation(upAnim);

    }
    @Override
    public int getItemCount() {
        return this.list.size();
    }
    class GalleryviewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        FloatingActionButton delete;


        public GalleryviewHolder(View itemView) {
            super(itemView);

            image = (ImageView)itemView.findViewById(R.id.gallery_image);
            delete = (FloatingActionButton)itemView.findViewById(R.id.fab_delete);

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
