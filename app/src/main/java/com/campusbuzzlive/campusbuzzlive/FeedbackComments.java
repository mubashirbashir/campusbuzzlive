package com.campusbuzzlive.campusbuzzlive;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.campusbuzzlive.campusbuzzlive.R.id.etQuery;
import static com.campusbuzzlive.campusbuzzlive.R.id.linearLayout;

public class FeedbackComments extends AppCompatActivity {
    JSONArray answerArray = null;
    ArrayList<HashMap<String, String>> answerList;
    HashMap<String,String> answerMap = new HashMap<String,String>();
    SwipeRefreshLayout mSwipeRefreshLayout;


    private LinearLayout linearLayout = null;
    private LinearLayout linearLayout2 = null;
    Button bComment;
    EditText etAnswer;
    int i=0;
    String query,etc,userName,enroll,questionId;

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_comments);
        refreshItems();
       // mSwipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        //mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
          //  @Override
           // public void onRefresh() {
                // Refresh items
             //   refreshItems();
            //}
        //});


        query=getIntent().getStringExtra("query");
        etc=getIntent().getStringExtra("etc");
        questionId=getIntent().getStringExtra("questionid");
        userName=getIntent().getStringExtra("name");
        enroll=getIntent().getStringExtra("enrollmentid");
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        TextView tvDynamicAnswer = new TextView(context);
        TextView tvDynamicEtc = new TextView(context);

        TextView tvDynamicName = new TextView(context);
        TextView tvDynamicEnroll = new TextView(context);
        tvDynamicName.setText(userName);
        tvDynamicEnroll.setText(enroll);

        View vDynamicLine = new View(context);
        tvDynamicAnswer.setText(query);
        tvDynamicAnswer.setTypeface(Typeface.DEFAULT_BOLD);
        tvDynamicAnswer.setTextSize(20);
       // tvDynamicAnswer.setGravity(Gravity.CENTER);
        tvDynamicAnswer.setTextColor(getResources().getColor(R.color.buzzcolor));
        tvDynamicEtc.setTextSize(12);
        tvDynamicEtc.setGravity(Gravity.RIGHT);
        //tvDynamicEtc.setId(i++);
        vDynamicLine.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        vDynamicLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 8));
        tvDynamicAnswer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tvDynamicEtc.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vDynamicLine.getLayoutParams();
        params.setMargins(0, 30, 0, 40); //substitute parameters for left, top, right, bottom
        vDynamicLine.setLayoutParams(params);
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) tvDynamicAnswer.getLayoutParams();
        params1.setMargins(0, 0, 0, 20); //substitute parameters for left, top, right, bottom
        tvDynamicAnswer.setLayoutParams(params1);
       // String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        tvDynamicEtc.setText(etc);
        linearLayout2.addView(tvDynamicName,0);
        linearLayout2.addView(tvDynamicEnroll,1);
        linearLayout2.addView(tvDynamicAnswer, 2);
        linearLayout2.addView(tvDynamicEtc, 3);
        linearLayout2.addView(vDynamicLine, 4);







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



                        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout);

                        final ProgressDialog progressDialog=new ProgressDialog(context);
                        progressDialog.setTitle("Adding Answer");
                        progressDialog.setMessage("please wait!");
                        progressDialog.show();
                        dialog.dismiss();

                        progressDialog.setCanceledOnTouchOutside(false);
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean error = jsonResponse.getBoolean("error");
                                    if (!error) {
                                        progressDialog.dismiss();
                                        // Toast.makeText(getApplicationContext(),"Registration Successfull.Please Log In to continue.",Toast.LENGTH_LONG).show();
                                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                                        builder.setMessage(" Answer Added Successfully!")
                                                .setNegativeButton("ok", null)
                                                .create()
                                                .show();



                                    //    refreshItems();
                                        //   Intent intent = new Intent(SignupActivity.this, LoginActivity.class);


                                        //  intent.putExtra("enrollmentid",enrollmentid);
                                        //    SignupActivity.this.startActivity(intent);
                                    } else {
                                        String msg= jsonResponse.getString("error_msg");
                                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
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

                        AddAnswerRequest addAnswerRequest = new AddAnswerRequest(questionId,java.text.DateFormat.getDateTimeInstance().format((new Date())),etAnswer.getText().toString(),"14045110011",  responseListener);
                        RequestQueue queue = Volley.newRequestQueue(context);
                        queue.add(addAnswerRequest);




                    }
                });

            }
        });



    }

    private void refreshItems() {
        linearLayout2.removeAllViews();

        answerList = new ArrayList<HashMap<String,String>>();
        linearLayout=(LinearLayout)findViewById(R.id.linearLayout);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    //  boolean error = jsonResponse.getBoolean("error");

                    answerArray= jsonResponse.getJSONArray("answers");




                    for(int i=0;i<answerArray.length();i++) {
                        JSONObject c = answerArray.getJSONObject(i);
                        String userName = c.getString("name");
                       // String questionId=c.getString("questionid");

                        String answerText = c.getString("answer");

                        String datetime = c.getString("datetime");



                        String enrollmentid = c.getString("enrollmentid");
                        HashMap<String,String> answerMap = new HashMap<String,String>();

                        answerMap.put("name",userName);
                        answerMap.put("answer",answerText);
                        answerMap.put("datetime",datetime);

                        answerMap.put("enrollmentid",enrollmentid);





                        answerList.add(answerMap);


                    }
                    mSwipeRefreshLayout.setRefreshing(false);

                    display();








                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GetAnswersRequest getAnswersRequest = new GetAnswersRequest( questionId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getAnswersRequest);

        // refresf from db



        Toast.makeText(this,"Refreshing...",Toast.LENGTH_LONG).show();
    }

    private void display() {

        for (int i = 0; i < answerList.size(); i++) {


            //display items


            //
            final String nameText = (answerList.get(i).get("name"));
            String answerNameText = (answerList.get(i).get("answer"));
            String dateTimeText = (answerList.get(i).get("datetime"));

            final String enrollmentText = (answerList.get(i).get("enrollmentid"));
           // final String questionid=(questionList.get(i).get("questionid"));
            final TextView tvDynamicName = new TextView(this);
            final TextView tvDynamicEnroll = new TextView(this);

            final TextView tvDynamicQuery = new TextView(this);
            final TextView tvDynamicEtc = new TextView(this);
            View vDynamicLine = new View(this);

            tvDynamicEnroll.setText(enrollmentText);
            tvDynamicName.setTextSize(18);
            tvDynamicEnroll.setTextSize(16);
            tvDynamicName.setText(nameText);
            tvDynamicName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));




            tvDynamicQuery.setText(answerNameText);
            tvDynamicQuery.setTextSize(24);
            tvDynamicQuery.setGravity(Gravity.LEFT);
            tvDynamicQuery.setTextColor(getResources().getColor(R.color.buzzcolor));
            tvDynamicEtc.setTextSize(12);
            tvDynamicEtc.setGravity(Gravity.RIGHT);
            //  tvDynamicEtc.setId(i++);
            vDynamicLine.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            vDynamicLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
            tvDynamicQuery.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            tvDynamicEtc.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vDynamicLine.getLayoutParams();
            params.setMargins(40, 40, 40, 40); //substitute parameters for left, top, right, bottom
            vDynamicLine.setLayoutParams(params);
            String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
            tvDynamicEtc.setText(dateTimeText);
            final String query = tvDynamicQuery.getText().toString();
            linearLayout.addView(tvDynamicName);
            linearLayout.addView(tvDynamicEnroll);
            linearLayout.addView(tvDynamicQuery);
            linearLayout.addView(tvDynamicEtc );
            linearLayout.addView(vDynamicLine);
           mSwipeRefreshLayout.setRefreshing(false);

            //Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();

            //dialog.dismiss();
        }
    }
}
