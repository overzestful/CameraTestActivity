package net.sourceforge.zbar.android.CameraTest;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

public class UpdateActivity extends Activity {

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        showInfo();

        // btnSave
        Button btnSave = (Button) findViewById(R.id.btnSave);
        // Perform action on click
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(SaveData())
                {
                    // When Save Complete
                    Intent newActivity = new Intent(UpdateActivity.this,Listview.class);
                    startActivity(newActivity);
                }
            }
        });


       /* // btnCancel
        final Button btnCancel = (Button) findViewById(R.id.btnCancel);
        // Perform action on click
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newActivity = new Intent(UpdateActivity.this,MainActivity.class);
                startActivity(newActivity);
            }
        });*/

    }

    public void showInfo()
    {
        // txtMemberID,txtUsername,txtPassword,txtConPassword,txtName,txtEmail,txtTel
        final EditText txtActivityName = (EditText) findViewById(R.id.txtActivityName);
        final EditText txtActivityStaff = (EditText) findViewById(R.id.txtActivityStaff);
        final EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
        final EditText txtConPassword = (EditText) findViewById(R.id.txtConPassword);
        final Spinner spinner_type = (Spinner)findViewById(R.id.spinner_type);
        final EditText txtHour = (EditText) findViewById(R.id.txtHour);
        final EditText txtDateStart = (EditText)findViewById(R.id.txtDateStart);
        final EditText txtTimeStart = (EditText)findViewById(R.id.TimeStart);
        final EditText txtDateEnd = (EditText)findViewById(R.id.txtDateEnd);
        final EditText txtTimeEnd = (EditText)findViewById(R.id.TimeStart);

        Button btnSave = (Button) findViewById(R.id.btnSave);
       // Button btnCancel = (Button) findViewById(R.id.btnCancel);

        String url = "http://10.0.3.2/register/getByMemberID.php";

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
        String strActivityName = "";
        String strActivityStaff = "";
        String strPassword = "";
        String strConPassword = "";
        String strSpinnerType = "";
        String strHour = "";
        String strDateStart = "";
        String strTimeStart = "";
        String strDateEnd = "";
        String strTimeEnd = "";

        JSONObject c;
        try {
            c = new JSONObject(resultServer);
            strMemberID = c.getString("MemberID");
            strActivityName= c.getString("ActivityName");
            strActivityStaff = c.getString("ActivityStaff");
            strPassword = c.getString("Password");
            strConPassword = c.getString("ConPassword");
            strSpinnerType = c.getString("SpinnerType");
            strHour = c.getString("Hour");
            strDateStart = c.getString("DateStart");
            strTimeStart = c.getString("TimeStart");
            strDateEnd = c.getString("DateEnd");
            strTimeEnd = c.getString("TimeEnd");



            if(!strMemberID.equals(""))
            {
                //txtMemberID.setText(strMemberID);
                txtActivityName.setText(strActivityName);
                txtActivityStaff.setText(strActivityStaff);
                txtPassword.setText(strPassword);
                txtConPassword.setText(strConPassword);
                spinner_type.setSelected(true);
                txtHour.setText(strHour);
                txtDateStart.setText(strDateStart);
                txtTimeStart.setText(strTimeStart);
                txtDateEnd.setText(strDateEnd);
                txtTimeEnd.setText(strTimeEnd);

            }
            else
            {
                txtActivityName.setText("-");
                txtActivityStaff.setText("-");
                txtPassword.setText("-");
                txtConPassword.setText("-");
                //spinner_type.setSelected("-");
                txtHour.setText("-");
                txtDateStart.setText("-");
                txtTimeStart.setText("-");
                txtDateStart.setText("-");
                txtDateEnd.setText("-");


                btnSave.setEnabled(false);
               // btnCancel.requestFocus();
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public boolean SaveData()
    {

        // txtMemberID,txtPassword,txtName,txtEmail,txtTel
        final EditText txtActivityName = (EditText) findViewById(R.id.txtActivityName);
        final EditText txtActivityStaff = (EditText) findViewById(R.id.txtActivityStaff);
        final EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
        final EditText txtConPassword = (EditText) findViewById(R.id.txtConPassword);
        final Spinner spinner_type = (Spinner)findViewById(R.id.spinner_type);
        final EditText txtHour = (EditText) findViewById(R.id.txtHour);
        final EditText txtDateStart = (EditText)findViewById(R.id.txtDateStart);
        final EditText txtTimeStart = (EditText)findViewById(R.id.TimeStart);
        final EditText txtDateEnd = (EditText)findViewById(R.id.txtDateEnd);
        final EditText txtTimeEnd = (EditText)findViewById(R.id.TimeStart);



        // Dialog
        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        ad.setTitle("Error! ");
        ad.setIcon(android.R.drawable.btn_star_big_on);
        ad.setPositiveButton("Close", null);

        /*// Check Password
        if(txtPassword.getText().length() == 0 || txtConPassword.getText().length() == 0 )
        {
            ad.setMessage("Please input [Password/Confirm Password] ");
            ad.show();
            txtPassword.requestFocus();
            return false;
        }
        // Check Password and Confirm Password (Match)
        if(!txtPassword.getText().toString().equals(txtConPassword.getText().toString()))
        {
            ad.setMessage("Password and Confirm Password Not Match! ");
            ad.show();
            txtConPassword.requestFocus();
            return false;
        }
        // Check Name
        if(txtName.getText().length() == 0)
        {
            ad.setMessage("Please input [Name] ");
            ad.show();
            txtName.requestFocus();
            return false;
        }
        // Check Email
        if(txtEmail.getText().length() == 0)
        {
            ad.setMessage("Please input [Email] ");
            ad.show();
            txtEmail.requestFocus();
            return false;
        }
        // Check Tel
        if(txtTel.getText().length() == 0)
        {
            ad.setMessage("Please input [Tel] ");
            ad.show();
            txtTel.requestFocus();
            return false;
        }
*/

        String url = "http://10.0.3.2/register/updateData.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sActivityName", txtActivityName.getText().toString()));
        params.add(new BasicNameValuePair("sActivityStaff", txtActivityStaff.getText().toString()));
        params.add(new BasicNameValuePair("sPassword", txtPassword.getText().toString()));
        params.add(new BasicNameValuePair("sConPassword", txtConPassword.getText().toString()));
        params.add(new BasicNameValuePair("sSpinnerType", spinner_type.getSelectedItem().toString()));
        params.add(new BasicNameValuePair("sHour", txtHour.getText().toString()));
        params.add(new BasicNameValuePair("sDateStart", txtDateStart.getText().toString()));
        params.add(new BasicNameValuePair("sTimeStart", txtTimeStart.getText().toString()));
        params.add(new BasicNameValuePair("sDateEnd", txtDateEnd.getText().toString()));
        params.add(new BasicNameValuePair("sTimeEnd", txtTimeEnd.getText().toString()));
        /** Get result from Server (Return the JSON Code)
         * StatusID = ? [0=Failed,1=Complete]
         * Error	= ?	[On case error return custom error message]
         *
         * Eg Save Failed = {"StatusID":"0","Error":"Email Exists!"}
         * Eg Save Complete = {"StatusID":"1","Error":""}
         */

        String resultServer  = getHttpPost(url,params);

        /*** Default Value ***/
        String strStatusID = "0";
        String strError = "Unknow Status!";

        JSONObject c;
        try {
            c = new JSONObject(resultServer);
            strStatusID = c.getString("StatusID");
            strError = c.getString("Error");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Prepare Save Data
        if(strStatusID.equals("0"))
        {
            ad.setMessage(strError);
            ad.show();
            return false;
        }
        else
        {
            Toast.makeText(UpdateActivity.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
        }


        return true;
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
