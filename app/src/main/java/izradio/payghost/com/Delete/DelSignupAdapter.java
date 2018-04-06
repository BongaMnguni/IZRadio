package izradio.payghost.com.Delete;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import izradio.payghost.com.Config;
import izradio.payghost.com.R;
import izradio.payghost.com.signup.SignupItems;

/**
 * Created by Payghost on 3/14/2018.
 */

public class DelSignupAdapter extends RecyclerView.Adapter<DelSignupAdapter.signupViewHolder>{

    public List<SignupItems> list ;
    private Context context;
    RequestQueue requestQueue;
    SharedPreferences prefs ;


    public DelSignupAdapter(Context context, List<SignupItems> list) {
        this.list = list;
        this.context = context;

    }
    @Override
    public signupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.del_list_row_members,null);
        signupViewHolder viewHolder = new signupViewHolder(v);
        context = parent.getContext();
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final signupViewHolder holder, final int position) {

        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        prefs = context.getSharedPreferences("Table", Context.MODE_PRIVATE);
        final String tbl = prefs.getString("table", null);
      /*  if(position %2 == 0){
            holder.recyclerView.setBackgroundColor(R.color.gray);
        }*/

        holder.textViewName.setText(list.get(position).getName());
        holder.textViewschool.setText(list.get(position).getSchool());
        holder.textViewnumber.setText(list.get(position).getNumber());

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

    class signupViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName,textViewschool,textViewnumber;
        CardView recyclerView;
        FloatingActionButton delete;

        public signupViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.tvname);
            textViewschool = (TextView)itemView.findViewById(R.id.tvschool);
            textViewnumber = (TextView)itemView.findViewById(R.id.tvnumber);
            recyclerView = (CardView) itemView.findViewById(R.id.card);
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
