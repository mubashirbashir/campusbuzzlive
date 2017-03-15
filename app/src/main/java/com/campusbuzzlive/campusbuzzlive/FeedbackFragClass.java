package com.campusbuzzlive.campusbuzzlive;

/**
 * Created by mubas on 3/6/2017.
 */

import android.app.Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.Date;


public class FeedbackFragClass extends Fragment {
    private LinearLayout linearLayout = null;
    Button bPost;
    EditText etQuery;
    int i=0;

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        View rt =inflater.inflate(
                R.layout.feedback_frag_layout  , container, false);
        getActivity().setTitle("Feedback Forum");
        linearLayout = (LinearLayout) rt.findViewById(R.id.linearLayout);


        FloatingActionButton fabFF = (FloatingActionButton) rt.findViewById(R.id.fabFF);

        fabFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.feedback_dialog_layout);
                bPost = (Button) dialog.findViewById(R.id.bPost);
                etQuery= (EditText) dialog.findViewById(R.id.etQuery);
                dialog.setTitle("Post a Query");
                dialog.show();
                bPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView tvDynamicQuery = new TextView(getActivity());
                        TextView tvDynamicEtc = new TextView(getActivity());
                        View vDynamicLine = new View(getActivity());
                        tvDynamicQuery.setText(etQuery.getText());
                        tvDynamicQuery.setTextSize(24);
                        tvDynamicQuery.setGravity(Gravity.LEFT);
                        tvDynamicQuery.setTextColor(getResources().getColor(R.color.buzzcolor));
                        tvDynamicEtc.setTextSize(16);
                        tvDynamicEtc.setGravity(Gravity.RIGHT);
                        tvDynamicEtc.setId(i++);
                        vDynamicLine.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        vDynamicLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                        tvDynamicQuery.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        tvDynamicEtc.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vDynamicLine.getLayoutParams();
                        params.setMargins(40, 40, 40, 40); //substitute parameters for left, top, right, bottom
                        vDynamicLine.setLayoutParams(params);
                        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                        tvDynamicEtc.setText(currentDateTimeString);
                        linearLayout.addView(tvDynamicQuery, 0);
                        linearLayout.addView(tvDynamicEtc, 1);
                        linearLayout.addView(vDynamicLine, 2);

                        Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });
        return rt;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles

    }
}