package com.campusbuzzlive.campusbuzzlive;

/**
 * Created by mubas on 3/6/2017.
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;



import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


public class ProfileFragClass extends Fragment {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final int PICK_IMAGE_REQUEST=1999;
    Context context;
    ImageView ivDP;
    ImageButton imageButton4;

    Uri outputFileUri;
    Session session;

    String stringImage;
    host h = new host();

    String selectedImagePath = "";
    RequestQueue queue;




    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //Inflate the layout for this fragment

        View rootView = inflater.inflate(
                R.layout.profile_frag_layout, container, false);
        session=new Session();

        context = rootView.getContext();
        session=new Session();

        ivDP = (ImageView) rootView.findViewById(R.id.ivDP);
     final    Button bChangePW = (Button)rootView.findViewById(R.id.bChangePW) ;
      final   Button bPhone= (Button) rootView.findViewById(R.id.bPhone);
        final Button bEmail= (Button) rootView.findViewById(R.id.bEmail);
     final    Button bName = (Button)rootView.findViewById(R.id.bName) ;
       final Button bEnroll= (Button) rootView.findViewById(R.id.benroll);
        final Button bDepartment= (Button) rootView.findViewById(R.id.bDepartment);
        final Button bGender= (Button) rootView.findViewById(R.id.bGender);
        final String photoURL = (h.address+"/uploads/"+session.getEnrollSession()+".jpeg");

        Picasso.with(getContext())
                .load(photoURL)
                .skipMemoryCache()
                .placeholder(R.mipmap.userdummy)   // optional
                .error(R.mipmap.userdummy)      // optional
                .resize(150,150)                        // optional
                .into(ivDP);

          /*
          imageButton4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //Pass the context and the Activity class you need to open from the Fragment Class, to the Intent
                if (v.getId() == R.id.ibCamera) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,
                            CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
            }
           }); */
          final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    // boolean error = jsonResponse.getBoolean("error");
                    String name = jsonResponse.getString("name");


                        String gender = jsonResponse.getString("gender");


                        String department = jsonResponse.getString("department");
                        String phone = jsonResponse.getString("phone");

                        String email = jsonResponse.getString("email");





                    bName.setText(name);
                    bGender.setText(gender);
                    bDepartment.setText(department);
                    bPhone.setText(phone);

                    bEmail.setText(email);
                    bEnroll.setText(session.getEnrollSession());







progressDialog.dismiss();




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GetProfileRequest getProfileRequest = new GetProfileRequest( session.getEnrollSession(), responseListener);
        queue = Volley.newRequestQueue(getContext());
        queue.add(getProfileRequest);





          ivDP.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(final View view) {
                                          final Dialog dialog = new Dialog(getContext());

                                          dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                                          dialog.setContentView(R.layout.camera_dialog_layout);

                                          dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                          Button mCamerabtn = (Button) dialog.findViewById(R.id.cameradialogbtn);

                                          Button mGallerybtn = (Button) dialog.findViewById(R.id.gallerydialogbtn);

                                          Button btnCancel = (Button) dialog.findViewById(R.id.canceldialogbtn);

                                          dialog.getWindow().setLayout(DrawerLayout.LayoutParams.FILL_PARENT, DrawerLayout.LayoutParams.FILL_PARENT);
                                          dialog.show();

                                          mCamerabtn.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                  startActivityForResult(intent,
                                                          CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                                                  dialog.cancel();

                                              }
                                          });

                                          mGallerybtn.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {

                                                  Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                                  startActivityForResult(i, PICK_IMAGE_REQUEST);
                                                  dialog.cancel();
                                              }
                                          });

                                          btnCancel.setOnClickListener(new View.OnClickListener() {

                                              @Override

                                              public void onClick(View v) {

                                                  dialog.cancel(); // dismissing the popup

                                              }

                                          });
                                      }
                                  });


        bChangePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass the context and the Activity class you need to open from the Fragment Class, to the Intent
                if (v.getId() == R.id.bChangePW) {
                    Intent intent = new Intent(getActivity(),ChangePassword.class);
                    startActivity(intent);
                }
            }
        });
        bEmail.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                //Pass the context and the Activity class you need to open from the Fragment Class, to the Intent
                if (v.getId() == R.id.bEmail) {
                    Intent intent = new Intent(getActivity(),UpdateEmail.class);
                    startActivity(intent);
                }
            }
        });
        bPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pass the context and the Activity class you need to open from the Fragment Class, to the Intent
                if (v.getId() == R.id.bPhone) {
                    Intent intent = new Intent(getActivity(),UpdatePhoneNo.class);
                    startActivity(intent);
                }
            }
        });

    return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {







            if (resultCode == RESULT_OK&&requestCode==CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

                Bitmap bmp = (Bitmap) data.getExtras().get("data");
               stringImage= getStringImage(bmp);
                upload();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // convert byte array to Bitmap

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);


                ivDP.setImageBitmap(bitmap);

            }


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {

            Uri targetUri = data.getData();

            Log.d("TAG", "datae" + targetUri);

            Bitmap bitmap;

            try {
                bitmap = decodeSampledBitmapFromUri(targetUri, ivDP.getWidth(), ivDP.getHeight());
                if (bitmap == null) {

                    Toast.makeText(

                            getContext(),

                            "the image data could not be decoded"

                                    + targetUri.getPath(), Toast.LENGTH_LONG)

                            .show();


                } else {


                    selectedImagePath = getPath(targetUri);// targetUri.getPath();

                    Toast.makeText(

                            getContext(),

                            "Image selected: " + bitmap.getWidth() + " x "

                                    + bitmap.getHeight() + targetUri.getPath(),

                            Toast.LENGTH_LONG).show();

                    ivDP.setImageBitmap(bitmap);


                    stringImage= getStringImage(bitmap);
                    upload();

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }



    }

    private void upload() {


        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("Upoading...");
        progressDialog.setMessage("Please wait!");
        progressDialog.show();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean error = jsonResponse.getBoolean("error");
                    if (!error) {
                        progressDialog.dismiss();
                        // Toast.makeText(getApplicationContext(),"Registration Successfull.Please Log In to continue.",Toast.LENGTH_LONG).show();
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                        builder.setMessage("   Success!")
                                .setNegativeButton("ok", null)
                                .create()
                                .show();


                        //   Intent intent = new Intent(SignupActivity.this, LoginActivity.class);


                        //  intent.putExtra("enrollmentid",enrollmentid);
                        //    SignupActivity.this.startActivity(intent);
                    } else {
                        String msg= jsonResponse.getString("error_msg");
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                        builder.setMessage(msg)
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        UploadImageRequest uploadImageRequest = new UploadImageRequest(session.getEnrollSession(),stringImage,  responseListener);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(uploadImageRequest);






    }

    public String getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        int column_index = cursor

                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);

    }

    public String getRealPathFromURI(ProfileFragClass context, Uri contentUri) {

        Cursor cursor = null;

        try {



            if("content".equals(contentUri.getScheme())) {

                String[] proj = {MediaStore.Images.Media.DATA};

                cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);

                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                return cursor.getString(column_index);

            }

            else{

                return contentUri.getPath();

            }





        } finally {

            if (cursor != null) {

                cursor.close();

            }

        }

    }

    public Bitmap decodeSampledBitmapFromUri(Uri uri, int reqWidth,

                                             int reqHeight) throws FileNotFoundException {



        Bitmap bm = null;



        try {

// First decode with inJustDecodeBounds=true to check dimensions

            final BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(context.getContentResolver()

                    .openInputStream(uri), null, options);



// Calculate inSampleSize

            options.inSampleSize = calculateInSampleSize(options, reqWidth,

                    reqHeight);



// Decode bitmap with inSampleSize set

            options.inJustDecodeBounds = false;

            bm = BitmapFactory.decodeStream(context.getContentResolver()

                    .openInputStream(uri), null, options);

        } catch (FileNotFoundException e) {

            e.printStackTrace();

            Toast.makeText(getContext(), e.toString(),

                    Toast.LENGTH_LONG).show();

        }



        return bm;

    }

    public int calculateInSampleSize(BitmapFactory.Options options,

                                     int reqWidth, int reqHeight) {

// Raw height and width of image

        final int height = options.outHeight;

        final int width = options.outWidth;

        int inSampleSize = 1;



        if (height > reqHeight || width > reqWidth) {

            if (width > height) {

                inSampleSize = Math.round((float) height / (float) reqHeight);

            } else {

                inSampleSize = Math.round((float) width / (float) reqWidth);

            }

        }

        return inSampleSize;

    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Profile Settings");



    }
    @Override
    public void onStop() {
        queue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
        super.onStop();
    }


}
