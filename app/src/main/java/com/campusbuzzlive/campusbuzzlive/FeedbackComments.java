package com.campusbuzzlive.campusbuzzlive;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import static com.campusbuzzlive.campusbuzzlive.R.id.etQuery;

public class FeedbackComments extends AppCompatActivity {


    private LinearLayout linearLayout = null;
    Button bComment;
    EditText etAnswer;
    int i=0;
    String query,etc;

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_comments);
        query=getIntent().getStringExtra("query");
        etc=getIntent().getStringExtra("etc");
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        TextView tvDynamicAnswer = new TextView(context);
        TextView tvDynamicEtc = new TextView(context);
        View vDynamicLine = new View(context);
        tvDynamicAnswer.setText(query);
        tvDynamicAnswer.setTextSize(20);
        tvDynamicAnswer.setGravity(Gravity.CENTER);
        tvDynamicAnswer.setTextColor(getResources().getColor(R.color.buzzcolor));
        tvDynamicEtc.setTextSize(16);
        tvDynamicEtc.setGravity(Gravity.RIGHT);
        tvDynamicEtc.setId(i++);
        vDynamicLine.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        vDynamicLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
        tvDynamicAnswer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tvDynamicEtc.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vDynamicLine.getLayoutParams();
        params.setMargins(40, 40, 40, 40); //substitute parameters for left, top, right, bottom
        vDynamicLine.setLayoutParams(params);
       // String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        tvDynamicEtc.setText(etc);
        linearLayout.addView(tvDynamicAnswer, 0);
        linearLayout.addView(tvDynamicEtc, 1);
        linearLayout.addView(vDynamicLine, 2);







        FloatingActionButton fabFC = (FloatingActionButton) findViewById(R.id.fabFC);
        fabFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.comments_dialog_layout);
                bComment = (Button) dialog.findViewById(R.id.bComment);
                etAnswer= (EditText) dialog.findViewById(R.id.etAnswer);
                dialog.setTitle("Post an Answer");
                dialog.show();
                bComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TextView tvDynamicAnswer = new TextView(context);
                        TextView tvDynamicEtc = new TextView(context);
                        View vDynamicLine = new View(context);
                        tvDynamicAnswer.setText(etAnswer.getText());
                        tvDynamicAnswer.setTextSize(20);
                        tvDynamicAnswer.setGravity(Gravity.LEFT);
                        tvDynamicAnswer.setTextColor(getResources().getColor(R.color.buzzcolor));
                        tvDynamicEtc.setTextSize(16);
                        tvDynamicEtc.setGravity(Gravity.RIGHT);
                        tvDynamicEtc.setId(i++);
                        vDynamicLine.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        vDynamicLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                        tvDynamicAnswer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        tvDynamicEtc.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vDynamicLine.getLayoutParams();
                        params.setMargins(40, 40, 40, 40); //substitute parameters for left, top, right, bottom
                        vDynamicLine.setLayoutParams(params);
                        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                        tvDynamicEtc.setText(currentDateTimeString);
                        linearLayout.addView(tvDynamicAnswer, 3);
                        linearLayout.addView(tvDynamicEtc, 4);
                       linearLayout.addView(vDynamicLine, 5);


                        Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

            }
        });



    }
}
