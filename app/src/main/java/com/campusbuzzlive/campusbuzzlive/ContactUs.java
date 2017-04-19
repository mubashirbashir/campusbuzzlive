package com.campusbuzzlive.campusbuzzlive;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity implements View.OnClickListener {
    EditText etSub, etBody;
    ImageButton ibSend, ibCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        ibSend= (ImageButton) findViewById(R.id.ibSend);
        etBody=(EditText) findViewById(R.id.etBody);
        etSub=(EditText) findViewById(R.id.etSub);
        ibCall=(ImageButton) findViewById(R.id.ibCall);
        ibCall.setOnClickListener(this);
        ibSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ibSend) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            //intent.setType("message/rfc822");
            intent.setData(Uri.parse("mailto:campusbuzz@gmail.com"));
            // intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"campusbuzz@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, etSub.getText().toString());
            intent.putExtra(Intent.EXTRA_TEXT, etBody.getText().toString());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(Intent.createChooser(intent, "Send Mail"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(ContactUs.this, "No Email Client", Toast.LENGTH_SHORT).show();

            }
        }
        if(v.getId()==R.id.ibCall){
            Intent intent= new Intent((Intent.ACTION_DIAL));
            intent.setData(Uri.parse("tel:07006502829"));
            startActivity(intent);
        }
    }


}
