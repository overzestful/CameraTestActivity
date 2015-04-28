package net.sourceforge.zbar.android.CameraTest;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends Activity {


    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        showInfo();

        // btnBack
        final Button btnBack = (Button) findViewById(R.id.btnBack);
        // Perform action on click
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newActivity = new Intent(DetailActivity.this,Listview.class);
                startActivity(newActivity);
            }
        });

    }

    public void showInfo()
    {
        // txtMemberID,txtMemberID,txtUsername,txtPassword,txtName,txtEmail,txtTel
        final TextView tMemberID = (TextView)findViewById(R.id.txtMemberID);
        final TextView tUsername = (TextView)findViewById(R.id.txtUsername);
        final TextView tPassword = (TextView)findViewById(R.id.txtPassword);
        final TextView tName = (TextView)findViewById(R.id.txtName);
        final TextView tEmail = (TextView)findViewById(R.id.txtEmail);
        final TextView tTel = (TextView)findViewById(R.id.txtTel);

        String url = "http://10.0.3.2/login/getByMemberID.php";

        Intent intent= getIntent();
        final String MemberID = intent.getStringExtra("MemberID");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sMemberID", MemberID));

        /** Get result from Server (Return the JSON Code)
         *
         * {"MemberID":"2","Username":"adisorn","Password":"adisorn@2","Name":"Adisorn Bunsong","Tel":"021978032","Email":"adisorn@thaicreate.com"}
         */

        String resultServer  = getHttpPost(url,params);

        String strMemberID = "";
        String strUsername = "";
        String strPassword = "";
        String strName = "";
        String strEmail = "";
        String strTel = "";

        JSONObject c;
        try {
            c = new JSONObject(resultServer);
            strMemberID = c.getString("MemberID");
            strUsername = c.getString("Username");
            strPassword = c.getString("Password");
            strName = c.getString("Name");
            strEmail = c.getString("Email");
            strTel = c.getString("Tel");

            if(!strMemberID.equals(""))
            {
                tMemberID.setText(strMemberID);
                tUsername.setText(strUsername);
                tPassword.setText(strPassword);
                tName.setText(strName);
                tEmail.setText(strEmail);
                tTel.setText(strTel);
            }
            else
            {
                tMemberID.setText("-");
                tUsername.setText("-");
                tPassword.setText("-");
                tName.setText("-");
                tEmail.setText("-");
                tTel.setText("-");
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public String getHttpPost(String url,List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Status OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
