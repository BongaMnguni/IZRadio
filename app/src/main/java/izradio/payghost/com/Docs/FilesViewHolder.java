package izradio.payghost.com.Docs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import izradio.payghost.com.R;

public class FilesViewHolder extends RecyclerView.ViewHolder{

    public TextView filename;
    public ImageView fileurl;


    public  FilesViewHolder(View v){
        super(v);

        filename =(TextView)v.findViewById(R.id.txtfile);
        fileurl = (ImageView)v.findViewById(R.id.ivfile);

    }

}
