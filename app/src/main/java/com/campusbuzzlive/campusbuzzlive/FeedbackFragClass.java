package com.campusbuzzlive.campusbuzzlive;

/**
 * Created by mubas on 3/6/2017.
 */

import android.app.Dialog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class FeedbackFragClass extends Fragment {
    private LinearLayout linearLayout = null;
    Button bPost;
    EditText etQuery;
    int i=0;
    JSONArray questionArray = null;
    ArrayList<HashMap<String, String>> questionList;
    HashMap<String,String> questiontMap = new HashMap<String,String>();
    SwipeRefreshLayout   mSwipeRefreshLayout;

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
                      final  ProgressDialog progressDialog=new ProgressDialog(getContext());
                        progressDialog.setTitle("Adding Question");
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
                                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                                        builder.setMessage(" Question Added Successfully!")
                                                .setNegativeButton("ok", null)
                                                .create()
                                                .show();

                                        refreshItems();
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

                        AddQuestionRequest addQuestionRequest = new AddQuestionRequest(etQuery.getText().toString(), java.text.DateFormat.getDateTimeInstance().format((new Date())), "14045110028",  responseListener);
                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        queue.add(addQuestionRequest);




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

        mSwipeRefreshLayout=(SwipeRefreshLayout) getActivity().findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);
        refreshItems();

    }

    private void refreshItems() {
        linearLayout.removeAllViews();

        questionList = new ArrayList<HashMap<String,String>>();
        linearLayout=(LinearLayout)getActivity().findViewById(R.id.linearLayout);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    //  boolean error = jsonResponse.getBoolean("error");

                    questionArray = jsonResponse.getJSONArray("questions");


                    if (questionArray.length() > 0){

                        for (int i = 0; i < questionArray.length(); i++) {
                            JSONObject c = questionArray.getJSONObject(i);
                            String userName = c.getString("name");
                            String questionId = c.getString("questionid");

                            String questionText = c.getString("question");

                            String datetime = c.getString("datetime");


                            String enrollmentid = c.getString("enrollmentid");
                            HashMap<String, String> questionMap = new HashMap<String, String>();

                            questionMap.put("name", userName);
                            questionMap.put("question", questionText);
                            questionMap.put("datetime", datetime);

                            questionMap.put("enrollmentid", enrollmentid);
                            questionMap.put("questionid", questionId);


                            questionList.add(questionMap);


                        }
                    mSwipeRefreshLayout.setRefreshing(false);

                    display();


                }
                else {
                        Toast.makeText(getContext(),"Nothing to Show",Toast.LENGTH_LONG).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GetQuestionsRequest getQuestionRequest = new GetQuestionsRequest(  responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getQuestionRequest);

        // refresf from db



        Toast.makeText(getContext(),"Refreshing...",Toast.LENGTH_LONG).show();
    }

    private void display() {

        for (int i = 0; i < questionList.size(); i++) {


            //display items


            //
            final String nameText = (questionList.get(i).get("name"));
            String questionNameText = (questionList.get(i).get("question"));
            String dateTimeText = (questionList.get(i).get("datetime"));

            final String enrollmentText = (questionList.get(i).get("enrollmentid"));
            final String questionid=(questionList.get(i).get("questionid"));
            final TextView tvDynamicName = new TextView(getActivity());
            final TextView tvDynamicEnroll = new TextView(getActivity());

            final TextView tvDynamicQuery = new TextView(getActivity());
            final TextView tvDynamicEtc = new TextView(getActivity());
            View vDynamicLine = new View(getActivity());

            tvDynamicEnroll.setText(enrollmentText);
            tvDynamicName.setTextSize(18);
            tvDynamicEnroll.setTextSize(16);
            tvDynamicName.setText(nameText);
            tvDynamicName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));




            tvDynamicQuery.setText(questionNameText);
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
           // String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
            tvDynamicEtc.setText(dateTimeText);
            final String query = tvDynamicQuery.getText().toString();
            linearLayout.addView(tvDynamicName);
            linearLayout.addView(tvDynamicEnroll);
            linearLayout.addView(tvDynamicQuery);
            linearLayout.addView(tvDynamicEtc );
            linearLayout.addView(vDynamicLine);
            tvDynamicQuery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent showfullquery = new Intent(getActivity(), FeedbackComments.class);
                    showfullquery.putExtra("query", query);
                    showfullquery.putExtra("questionid",questionid);
                    showfullquery.putExtra("name", nameText);
                    showfullquery.putExtra("enrollmentid",enrollmentText);
                    showfullquery.putExtra("etc", tvDynamicEtc.getText().toString());
                    startActivity(showfullquery);
                }
            });

            //Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();

            //dialog.dismiss();
        }
    }
}