package izradio.payghost.com.Gallery;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import izradio.payghost.com.R;


public class GalleryviewHolder extends RecyclerView.ViewHolder{
    ImageView image;

    public  GalleryviewHolder(View v){
        super(v);
        image =(ImageView) v.findViewById(R.id.gallery_image);
    }
}
