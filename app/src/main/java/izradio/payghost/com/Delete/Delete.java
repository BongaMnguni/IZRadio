package izradio.payghost.com.Delete;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import izradio.payghost.com.R;

public class Delete extends Fragment {
    SharedPreferences sharedpreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        final View roortview = inflater.inflate(R.layout.fragment_delete, container, false);

        LinearLayout linearLayout = (LinearLayout) roortview.findViewById(R.id.del_layout);
        linearLayout.getBackground().setAlpha(230);

        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

       final DisplayDelete mainfragment = new DisplayDelete();
        sharedpreferences = getActivity().getSharedPreferences("Packages", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();

        roortview.findViewById(R.id.del_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.clear();
                editor.apply();

                Bundle bundle = new Bundle();
                bundle.putString("fragment","Gallery");
                bundle.putString("table","gallery");
                bundle.putString("url","http://mydm.co.za/IZRadio/Galleryretrieve.php");

                mainfragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.del_content, mainfragment).commit();
                roortview.findViewById(R.id.del_layout).setVisibility(View.GONE);

            }
        });

        roortview.findViewById(R.id.del_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.clear();
                editor.apply();

                Bundle bundle = new Bundle();
                bundle.putString("fragment","Videos");
                bundle.putString("table","videoUpload");
                bundle.putString("url","http://mydm.co.za/IZRadio/Videoretrieve.php");

                mainfragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.del_content, mainfragment).commit();
                roortview.findViewById(R.id.del_layout).setVisibility(View.GONE);

            }
        });roortview.findViewById(R.id.del_news).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               editor.clear();
                editor.apply();

                Bundle bundle = new Bundle();
                bundle.putString("fragment","News");
                bundle.putString("table","TVShow");
                bundle.putString("url","http://mydm.co.za/IZRadio/postretrieve.php");

                mainfragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.del_content, mainfragment).commit();
                roortview.findViewById(R.id.del_layout).setVisibility(View.GONE);

            }
        });roortview.findViewById(R.id.del_members).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.clear();
                editor.apply();

                Bundle bundle = new Bundle();
                bundle.putString("fragment","Members");
                bundle.putString("table","Signup");
                bundle.putString("url","http://mydm.co.za/IZRadio/RetrieveSignup.php");

                mainfragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.del_content, mainfragment).commit();
                roortview.findViewById(R.id.del_layout).setVisibility(View.GONE);

            }
        });
        return roortview;
    }
}
