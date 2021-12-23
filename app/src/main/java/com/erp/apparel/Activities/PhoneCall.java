package com.erp.apparel.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.erp.apparel.R;

public class PhoneCall extends AppCompatActivity {
    TextView m_phone;
    Button m_click;
    public static final int RESULT_PICK_CONTACT=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_call);
        m_phone=findViewById(R.id.phoneno);
        m_click=findViewById(R.id.click);

        m_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(i,RESULT_PICK_CONTACT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Toast.makeText(PhoneCall.this, "Failed To Pick Contact", Toast.LENGTH_SHORT).show();
        }
        //  super.onActivityResult(requestCode, resultCode, data);
    }

    private void contactPicked(Intent data) {

        Cursor cursor =null;

        try {
            String phoneNo= null;
            Uri uri=data.getData();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                cursor=getContentResolver().query(uri,null,null,null);
            }

            cursor.moveToFirst();


            int phoneIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            phoneNo=cursor.getString(phoneIndex);
            m_phone.setText(phoneNo);
            Log.e("mylog","number is : " +phoneNo);

        }catch (Exception e){e.printStackTrace();}
    }
}