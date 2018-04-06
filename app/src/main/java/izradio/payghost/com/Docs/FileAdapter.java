package izradio.payghost.com.Docs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

import izradio.payghost.com.R;


public class FileAdapter extends RecyclerView.Adapter<FilesViewHolder>{

    public List<FileItems> list ;
    private Context context;


    public FileAdapter(Context context, List<FileItems> list) {
        this.list = list;
        this.context = context;

    }
    @Override
    public FilesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.files_cardview,null);
        FilesViewHolder viewHolder = new FilesViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FilesViewHolder holder, final int position) {
        holder.filename.setText(list.get(position).getFilename());

        String Str = new String(list.get(position).getFileurl());
        String SubStr1 = new String("." );
        int dot = Str.lastIndexOf(SubStr1);


        if(list.get(position).getFileurl().substring(dot).contains("pdf")){
            holder.fileurl.setBackgroundResource(R.drawable.pdf);
        }else if (list.get(position).getFileurl().substring(dot).contains("ppt") || list.get(position).getFileurl().substring(dot).contains("pptx")  ){
            holder.fileurl.setBackgroundResource(R.drawable.ppt);
        }else if(list.get(position).getFileurl().substring(dot).contains("docx")){
            holder.fileurl.setBackgroundResource(R.drawable.word);
        }else if(list.get(position).getFileurl().substring(dot).contains("doc")){
            holder.fileurl.setBackgroundResource(R.drawable.word);
        }else if(list.get(position).getFileurl().substring(dot).contains("xlsx") || list.get(position).getFileurl().substring(dot).contains("xlsm")  ){
            holder.fileurl.setBackgroundResource(R.drawable.excel);
        }else if(list.get(position).getFileurl().substring(dot).contains("png") || list.get(position).getFileurl().substring(dot).contains("jpg")  ){
            holder.fileurl.setBackgroundResource(R.drawable.icons8document);
        }


        holder.fileurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mydm.co.za/IZRadio/allfile/"+list.get(position).getFileurl()));
                context.startActivity(browserIntent);
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
}
