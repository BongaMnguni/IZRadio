package izradio.payghost.com.signup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import java.util.List;

import izradio.payghost.com.R;


/**
 * Created by Payghost on 3/14/2018.
 */

public class SignupAdapter extends RecyclerView.Adapter<SignupAdapter.signupViewHolder>{

    public List<SignupItems> list ;
    private Context context;


    public SignupAdapter(Context context, List<SignupItems> list) {
        this.list = list;
        this.context = context;

    }
    @Override
    public signupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_members,null);
        signupViewHolder viewHolder = new signupViewHolder(v);

        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final signupViewHolder holder, int position) {

      /*  if(position %2 == 0){
            holder.recyclerView.setBackgroundColor(R.color.gray);
        }*/

        holder.textViewName.setText(list.get(position).getName());
        holder.textViewschool.setText(list.get(position).getSchool());
        holder.textViewnumber.setText(list.get(position).getNumber());

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

        public signupViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.tvname);
            textViewschool = (TextView)itemView.findViewById(R.id.tvschool);
            textViewnumber = (TextView)itemView.findViewById(R.id.tvnumber);
            recyclerView = (CardView) itemView.findViewById(R.id.card);


        }
    }
}
