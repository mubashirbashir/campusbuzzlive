package com.campusbuzzlive.campusbuzzlive;

/**
 * Created by mubas on 3/6/2017.
 */

import android.content.Intent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;



import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;


public class ProfileFragClass extends Fragment {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    Context context;
    ImageView ivDP;
    ImageButton imageButton4;
    Button bChangeDP;

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //Inflate the layout for this fragment

        View rootView = inflater.inflate(
                R.layout.profile_frag_layout, container, false);

        context = rootView.getContext();
        imageButton4 = (ImageButton) rootView.findViewById(R.id.ibCamera);
        ivDP = (ImageView) rootView.findViewById(R.id.ivDP);
        Button bChangePW = (Button)rootView.findViewById(R.id.bChangePW) ;
        Button bPhone= (Button) rootView.findViewById(R.id.bPhone);
        Button bEmail= (Button) rootView.findViewById(R.id.bEmail);
        bChangeDP= (Button) rootView.findViewById(R.id.bChangeDP);

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
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // convert byte array to Bitmap

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);


                ivDP.setImageBitmap(bitmap);
                bChangeDP.setEnabled(true);
            }
        }
    }

        @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Profile Settings");



    }


}
