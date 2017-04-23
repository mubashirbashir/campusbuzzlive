package com.campusbuzzlive.campusbuzzlive;

/**
 * Created by mubas on 3/6/2017.
 */


import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.TextView;

import java.io.IOException;


public class HomeFragClass extends Fragment {
    private final static String stream = "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio2_mf_p";
   ImageView ivControl,ivCircle;
    TextView tvTextDisplay;
    MediaPlayer mediaPlayer;
    boolean started = false;
    boolean prepared = false;

    ProgressDialog pg;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        View rootView = inflater.inflate(
                R.layout.home_frag_layout, container, false);
        ivControl = (ImageView) rootView.findViewById(R.id.ivControl);
        ivCircle = (ImageView) rootView.findViewById(R.id.ivCircle);
        tvTextDisplay = (TextView) rootView.findViewById(R.id.tvTextDisplay);
        // ivControl.setImageResource(R.drawable.ic_event_black_24dp);
    //    mediaPlayer.start();
        final Animation animation =AnimationUtils.loadAnimation(getActivity(),R.anim.zoom);
        ivCircle.startAnimation(animation);
        ivControl.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        tvTextDisplay.setText("Tune in to Live stream by pressing the Play button");



        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        ivControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if (started) {
                    mediaPlayer.stop();
                    started = false;
                    tvTextDisplay.setText("Tune in to Live stream by pressing the Play button.");

                    ivControl.setImageResource(R.drawable.ic_play_arrow_black_24dp);

                   // ivControl.setText("Play");
                } else {
                    tvTextDisplay.setText("Hold on, while the stream is loading...");


                   ivControl.setImageResource(R.drawable.ic_hourglass_empty_black_24dp);

                    ivControl.setEnabled(false);

                    new PlayTask().execute(stream);
                    mediaPlayer.start();

               //     ivControl.setImageResource(R.drawable.ic_power_settings_new_black_24dp);

                 //   ivControl.setText("Pause");
                }

            }
        });







        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Campus Buzz Live!");
    }


    private class PlayTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            try {
                //mediaPlayer.
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.prepare();
                prepared = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
          //  play.setEnabled(true);
         ivControl.setEnabled(true);
            mediaPlayer.start();
            started = true;
           ivControl.setImageResource(R.drawable.ic_power_settings_new_black_24dp);
            tvTextDisplay.setText("Don't like what you're listening, press the Stop Button.");

        }
    }
}