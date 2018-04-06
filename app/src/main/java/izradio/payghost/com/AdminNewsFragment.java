package izradio.payghost.com;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class AdminNewsFragment extends Fragment implements View.OnClickListener {

    private Button buttonUpload;
    EditText EdMessage,EdName,EdSender;
    private ImageView browse;

    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private String UPLOAD_URL ="http://mydm.co.za/IZRadio/postupload.php";
    private String KEY_IMAGE = "Image";
    private String KEY_NAME = "Name";
    private String KEY_SENDER = "Sender";
    private String KEY_MESSAGE = "Message";

    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_admin_news, container, false);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        buttonUpload = (Button)root.findViewById(R.id.btnPost);
        browse = (ImageView)root.findViewById(R.id.browse);
        EdName = (EditText)root.findViewById(R.id.input_name);
        EdMessage = (EditText)root.findViewById(R.id.input_message);
        EdSender = (EditText)root.findViewById(R.id.input_sender);

        LinearLayout linearLayout = (LinearLayout)root.findViewById(R.id.layout_news);
        linearLayout.getBackground().setAlpha(230);


        browse.setOnClickListener(this);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        return root;
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){

        try {

            //Showing the progress dialog
            // final ProgressDialog loading = ProgressDialog.show(getActivity().getApplicationContext(), "Uploading...", "Please wait...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            //Disimissing the progress dialog
                            //Showing toast message of the response
                            Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            //Dismissing the progress dialog

                            //Showing toast
                            Toast.makeText(getActivity(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    //Converting Bitmap to String
                    String image = getStringImage(bitmap);

                    //Getting Image Name
                    //Creating parameters
                    Map<String, String> params = new Hashtable<String, String>();

                    //Adding parameters
                    params.put(KEY_IMAGE,image);
                    params.put(KEY_NAME,EdName.getText().toString().trim());
                    params.put(KEY_SENDER,EdSender.getText().toString().trim());
                    params.put(KEY_MESSAGE,EdMessage.getText().toString().trim());
                    //returning parameters
                    return params;
                }
            };

            //Creating a Request Queue
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

            //Adding request to the queue
            requestQueue.add(stringRequest);

        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                browse.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {

        if(v == browse){
            showFileChooser();
        }

        if(v == buttonUpload){

        }
    }
}
