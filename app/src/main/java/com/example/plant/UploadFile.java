package com.example.plant;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import am.appwise.components.ni.NoInternetDialog;

public class UploadFile extends AppCompatActivity {

    Dialog epicDialog;
    TextView titleTv,messageTv;
    ImageView closePopupHealthyplant,closePopupNotplant;
    Button btnAcpt,btnRetry;
    private static final String ROOT_URL = "http://eead3423a309.ngrok.io";
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST =1 ;
    private static final int CAMERA_REQUEST=100;
    public static String Disease_Name;
    private int flag=0;
    ImageView imageView;
    Button gallery,camera;
    private String filePath;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(this).build();
        imageView=findViewById(R.id.imageView2);
        gallery=findViewById(R.id.upload);
        epicDialog=new Dialog(this);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(UploadFile.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(UploadFile.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(UploadFile.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);
                    }
                } else {
                    Log.e("Else", "Else");
                    showFileChooser();
                }
            }
        });
    camera=findViewById(R.id.camera);
    camera.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            flag=1;
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    });
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_REQUEST){
            bitmap=(Bitmap) data.getExtras().get("data");
            uploadBitmap(bitmap);
            imageView.setImageBitmap(bitmap);
            progress_dialog();
            if(flag==1){
                camera.setText("Retake");
            }
        }
        else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picUri = data.getData();
            filePath = getPath(picUri);
            if (filePath != null) {
                try {
                    Log.d("filePath", String.valueOf(filePath));
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);

                        Log.d("bitmap", String.valueOf(bitmap));
                    int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                    bitmap = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    uploadBitmap(bitmap);
                    imageView.setImageBitmap(bitmap);
                    progress_dialog();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Toast.makeText(
                        UploadFile.this,"no image selected",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void progress_dialog() {
        final Loading loading=new Loading(UploadFile.this);
        loading.startloadingDialog();
        loading.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.dismissDialog();
            }
        },8000);
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        String path = null;

        if(cursor!=null && cursor.getCount()>0)
        {
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        }
            cursor.close();

        return path;
    }
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private void uploadBitmap(final Bitmap bitmap) {
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, ROOT_URL,
                new Response.Listener<NetworkResponse>()
                {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        try {
                            Loading.dismiss_dialog();
                            JSONObject obj = new JSONObject(new String(response.data));
                            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                            Toast.makeText(getApplicationContext(), obj.getString("disease"), Toast.LENGTH_SHORT).show();
                            Disease_Name=obj.getString("disease");
                            if(Disease_Name.equals("Healthy")==true)
                            {
                                epicDialog.setContentView(R.layout.epic_popup_healthyplant);
                                closePopupHealthyplant=(ImageView) epicDialog.findViewById(R.id.closePopupHealthyplant);
                                btnAcpt=(Button) epicDialog.findViewById(R.id.btnAcpt);
                                titleTv=(TextView) epicDialog.findViewById(R.id.titleTv);
                                messageTv=(TextView) epicDialog.findViewById(R.id.messageTv);

                                closePopupHealthyplant.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        epicDialog.dismiss();
                                    }
                                });
                                epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                epicDialog.show();
                                btnAcpt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i=new Intent(getApplicationContext(),UploadFile.class);
                                        startActivity(i);
                                    }
                                });
                            }
                            else if(Disease_Name.equals("Other")==true)
                            {
                                epicDialog.setContentView(R.layout.epic_popup_notplant);
                                closePopupNotplant=(ImageView) epicDialog.findViewById(R.id.closePopupNotplant);
                                btnRetry=(Button) epicDialog.findViewById(R.id.btnRetry);
                                titleTv=(TextView) epicDialog.findViewById(R.id.titleTv);
                                messageTv=(TextView) epicDialog.findViewById(R.id.messageTv);
                                closePopupNotplant.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        epicDialog.dismiss();
                                    }
                                });
                                epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                epicDialog.show();
                                btnRetry.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent in=new Intent(getApplicationContext(),UploadFile.class);
                                        startActivity(in);
                                    }
                                });

                            }else
                            {
                                Bundle b=new Bundle();
                                b.putString("disease",Disease_Name);
                                Intent i=new Intent(UploadFile.this,disease_details.class);
                                i.putExtras(b);
                                startActivity(i);
                            }
                        } catch (JSONException | UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError",""+error.getMessage());
                    }
                }) {

            //            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("tags", tags);
//                return params;
//            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.list,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.signout){
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(getApplicationContext(),login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
            return true;
        }if (id==R.id.homeButton){
            Intent intent=new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
        }
        return true;
    }

//    public void openDialog()
//    {
//        ExampleDialog exampleDialog=new ExampleDialog();
//        exampleDialog.show(getSupportFragmentManager(),"example dialog");
//    }
}
