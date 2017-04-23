package com.campusbuzzlive.campusbuzzlive;

/**
 * Created by mubas on 3/6/2017.
 */


import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;


public class HomeFragClass extends Fragment {
    double lat = 34.063751;

    double lng = 74.809215;
    ImageView ivControl,ivCircle ;

    String format = "geo:0,0?q=" + lat + "," + lng + "(Convocation Complex)";
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        View rootView = inflater.inflate(
                R.layout.home_frag_layout, container, false);
        ivControl = (ImageView) rootView.findViewById(R.id.ivControl);
        ivCircle = (ImageView) rootView.findViewById(R.id.ivCircle);

       // ivControl.setImageResource(R.drawable.ic_event_black_24dp);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom);

      //  animation.setRepeatMode();
        ivCircle.startAnimation(animation);
        animation.setRepeatCount(Animation.INFINITE);


        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Radio");
    }
}