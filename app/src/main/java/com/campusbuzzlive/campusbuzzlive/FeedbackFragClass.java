package com.campusbuzzlive.campusbuzzlive;

/**
 * Created by mubas on 3/6/2017.
 */

import android.app.Dialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class FeedbackFragClass extends Fragment {
    private LinearLayout linearLayout = null;
    Session session;
    Button bPost;
    EditText etQuery;
    int i = 0;
    JSONArray questionArray = null;
    ArrayList<HashMap<String, String>> questionList;
    HashMap<String, String> questiontMap = new HashMap<String, String>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    RequestQueue queue;
    host h=new host();


    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        View rt = inflater.inflate(
                R.layout.feedback_frag_layout, container, false);
        getActivity().setTitle("KU Forum");
        linearLayout = (LinearLayout) rt.findViewById(R.id.linearLayout);

        session = new Session();
        FloatingActionButton fabFF = (FloatingActionButton) rt.findViewById(R.id.fabFF);

        fabFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.feedback_dialog_layout);
                bPost = (Button) dialog.findViewById(R.id.bPost);
                etQuery = (EditText) dialog.findViewById(R.id.etQuery);
                //dialog.setTitle("Post a Query");
                dialog.show();

                etQuery.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(etQuery.getText().toString().length()==0) {

                            etQuery.setError("Enter a query");
                            bPost.setEnabled(false);

                        }
                            else{

                            bPost.setEnabled(true);

                        }
                    }
                });


                bPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        final ProgressDialog progressDialog = new ProgressDialog(getContext());
                        // progressDialog.setTitle(session.getEnrollSession());

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
                                        refreshItems();
                                        progressDialog.dismiss();
                                        // Toast.makeText(getApplicationContext(),"Registration Successfull.Please Log In to continue.",Toast.LENGTH_LONG).show();
                                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                                        builder.setMessage(" Question Added Successfully!")
                                                .setNegativeButton("ok", null)
                                                .create()
                                                .show();


                                        //   Intent intent = new Intent(SignupActivity.this, LoginActivity.class);


                                        //  intent.putExtra("enrollmentid",enrollmentid);
                                        //    SignupActivity.this.startActivity(intent);
                                    } else {
                                        String msg = jsonResponse.getString("error_msg");
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
                        Response.ErrorListener errorListener = new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(final VolleyError error) {





                                progressDialog.dismiss();

                                final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());

                                alert.setTitle("No response");
                                alert.setMessage("Please check your internet connection");

                                alert.setNegativeButton("Ok",null);
                                alert.show();


                            }
                        };


                        AddQuestionRequest addQuestionRequest = new AddQuestionRequest(etQuery.getText().toString(), java.text.DateFormat.getDateTimeInstance().format((new Date())), session.getEnrollSession(), responseListener,errorListener);
                         queue = Volley.newRequestQueue(getContext());
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

        mSwipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeRefreshLayout);
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


        questionList = new ArrayList<HashMap<String, String>>();
        linearLayout = (LinearLayout) getActivity().findViewById(R.id.linearLayout);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    //  boolean error = jsonResponse.getBoolean("error");

                    questionArray = jsonResponse.getJSONArray("questions");


                    if (questionArray.length() > 0) {

                        for (int i = 0; i < questionArray.length(); i++) {
                            JSONObject c = questionArray.getJSONObject(i);
                            String userName = c.getString("name");
                            String photo=c.getString("photo");
                            String questionId = c.getString("questionid");

                            String questionText = c.getString("question");

                            String datetime = c.getString("datetime");


                            String enrollmentid = c.getString("enrollmentid");
                            HashMap<String, String> questionMap = new HashMap<String, String>();

                            questionMap.put("name", userName);
                            questionMap.put("photo",photo);
                            questionMap.put("question", questionText);
                            questionMap.put("datetime", datetime);

                            questionMap.put("enrollmentid", enrollmentid);
                            questionMap.put("questionid", questionId);


                            questionList.add(questionMap);



                        }
                        mSwipeRefreshLayout.setRefreshing(false);

                        display();


                    } else {
                        Toast.makeText(getContext(), "Nothing to Show", Toast.LENGTH_LONG).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(final VolleyError error) {
                mSwipeRefreshLayout.setRefreshing(false);
                ImageView errorshow =new ImageView(getActivity());
                errorshow.setImageResource(R.drawable.ic_sentiment_dissatisfied_black_24dp);
                linearLayout.removeAllViews();




                linearLayout.addView(errorshow);

                final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());

                alert.setTitle("No response");
                alert.setMessage("Please check your internet connection");
                alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSwipeRefreshLayout.setRefreshing(true);

                        refreshItems();

                        linearLayout.removeAllViews();

                    }
                });
                alert.setNegativeButton("Cancel",null);
                alert.show();


            }
        };


        GetQuestionsRequest getQuestionRequest = new GetQuestionsRequest(responseListener,errorListener);
         queue = Volley.newRequestQueue(getContext());
        queue.add(getQuestionRequest);

        // refresf from db


        Toast.makeText(getContext(), "Refreshing...", Toast.LENGTH_SHORT).show();
    }

    private void display() {
        linearLayout.removeAllViews();
        for (int i = 0; i < questionList.size(); i++) {


            //display items
            LinearLayout ll =new LinearLayout(getActivity());
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout ln =new LinearLayout(getActivity());
            ln.setOrientation(LinearLayout.VERTICAL);
            ln.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));


            final ImageView dynamicMenu = new ImageView(getActivity());

            final ImageView imageViewdp= new ImageView(getActivity());
            LinearLayout.LayoutParams paramx =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramx.setMargins(0, 0, 10, 0); //substitute parameters for left, top, right, bottom
            imageViewdp.setLayoutParams(paramx);

            final String photoURL = (h.address+"/uploads/"+questionList.get(i).get("photo"));
           // Toast.makeText(getContext(),photoURL,Toast.LENGTH_LONG).show();

            Picasso.with(getContext())
                    .load(photoURL)

                    .placeholder(R.mipmap.userdummy)   // optional
                    .error(R.mipmap.userdummy)      // optional
                    .resize(150,150)                        // optional
                    .into(imageViewdp);

            //
            final String nameText = (questionList.get(i).get("name"));
            String questionNameText = (questionList.get(i).get("question"));
            String dateTimeText = (questionList.get(i).get("datetime"));


            final String enrollmentText = (questionList.get(i).get("enrollmentid"));
            final String questionid = (questionList.get(i).get("questionid"));
            final TextView tvDynamicName = new TextView(getActivity());
            final TextView tvDynamicEnroll = new TextView(getActivity());

          //  final ImageView dynamicDelete = new ImageView(getActivity());

            final TextView tvDynamicQuery = new TextView(getActivity());
            final TextView tvDynamicEtc = new TextView(getActivity());
            View vDynamicLine = new View(getActivity());

            tvDynamicEnroll.setText(enrollmentText);
            tvDynamicName.setTextSize(16);
            tvDynamicEnroll.setTextSize(14);
            tvDynamicName.setText(nameText);
            tvDynamicName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvDynamicName.setTextColor(getResources().getColor(R.color.black));


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
            params.setMargins(0, 5, 0, 30); //substitute parameters for left, top, right, bottom
            vDynamicLine.setLayoutParams(params);
            // String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
            tvDynamicEtc.setText(dateTimeText);
            final String query = tvDynamicQuery.getText().toString();

            if (session.getEnrollSession().toString().equals(tvDynamicEnroll.getText().toString())) {
                // Toast.makeText(this,session.getEnrollSession(),Toast.LENGTH_LONG).show();
                dynamicMenu.setImageResource(R.drawable.ic_more_vert_black_24dp);

                dynamicMenu.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) dynamicMenu.getLayoutParams();
                params1.gravity = (Gravity.RIGHT);
                dynamicMenu.setLayoutParams(params1);
                linearLayout.addView(dynamicMenu);
            }


            linearLayout.addView(ll);
            ll.addView(imageViewdp);
            ll.addView(ln);



            ln.addView(tvDynamicName);
            ln.addView(tvDynamicEnroll);


            linearLayout.addView(tvDynamicQuery);
            linearLayout.addView(tvDynamicEtc);
            linearLayout.addView(vDynamicLine);
            tvDynamicQuery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent showfullquery = new Intent(getActivity(), FeedbackComments.class);
                    showfullquery.putExtra("query", query);
                    showfullquery.putExtra("questionid", questionid);
                    showfullquery.putExtra("name", nameText);
                    showfullquery.putExtra("enrollmentid", enrollmentText);
                    showfullquery.putExtra("photo", photoURL);
                    showfullquery.putExtra("etc", tvDynamicEtc.getText().toString());
                    startActivity(showfullquery);
                }
            });

            //Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();

            //dialog.dismiss();
            dynamicMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    PopupMenu menu = new PopupMenu(getContext(), v);

                    menu.getMenu().add(Menu.NONE, 1, 1, "Delete");

                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage("Are you sure you want to delete this item? " )
                                    .setIcon(R.drawable.ic_delete_black_24dp)

                                    .setNegativeButton("Cancel", null)
                                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            deleteQuestion(questionid);
                                        }
                                    })
                                    .show();


                            return true;
                        }
                    });
                    menu.show();

                }
            });


        }

    }

    private void deleteQuestion(String questionid) {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean error = jsonResponse.getBoolean("error");
                    if (!error) {
                        refreshItems();
                        progressDialog.dismiss();
                        // Toast.makeText(getApplicationContext(),"Registration Successfull.Please Log In to continue.",Toast.LENGTH_LONG).show();
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                        builder.setMessage(" Successfully Deleted!")
                                .setNegativeButton("ok", null)
                                .create()
                                .show();


                        //   Intent intent = new Intent(SignupActivity.this, LoginActivity.class);


                        //  intent.putExtra("enrollmentid",enrollmentid);
                        //    SignupActivity.this.startActivity(intent);
                    } else {
                        String msg = jsonResponse.getString("error_msg");
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


        DeleteQuestionRequest deleteQuestionRequest = new DeleteQuestionRequest(questionid, responseListener);
         queue = Volley.newRequestQueue(getContext());
        queue.add(deleteQuestionRequest);


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