package net.sourceforge.zbar.android.CameraTest;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddActivityWebserv extends Activity {

    private EditText editT1;
    private Button btn1;
    private EditText editT2;
    private Button btn2;

    private int mYear;
    private int mMonth;
    private int mDay;

    private EditText TxtTime;
    private Button BtnTime;
    private EditText TxtTime2;
    private Button BtnTime2;

    private int mHour;
    private int mMinute;

    static final int TIME_DIALOG_ID = 1;
    static final int DATE_DIALOG_ID = 2;
    int cur = 0;

    @SuppressLint("NewApi")

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addactivitywebserv);



       // final Spinner spin1 = (Spinner) findViewById(R.id.spinner_type);
        // final Button btn1 = (Button) findViewById(R.id.button1);
        // Perform action on click
       /* btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(AddActivityWebserv.this,
                        "Your Selected : " + String.valueOf(spin1.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();

            }
        });*/

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


//Date
        // editText1
        editT1 = (EditText) findViewById(R.id.txtDateStart);
        editT2 = (EditText) findViewById(R.id.txtDateEnd);
        // button1
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);

        // add a click listener to the button
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
                updateCurrentDate();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateCurrentDate2();
                showDialog(DATE_DIALOG_ID);
            }
        });

        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // display the current date

//Time
        // editText1
        TxtTime = (EditText) findViewById(R.id.TimeStart);
        TxtTime2 = (EditText) findViewById(R.id.TimeEnd);

        // button1
        BtnTime = (Button) findViewById(R.id.selectTimeStart);

        // button1
        BtnTime2 = (Button) findViewById(R.id.selectTimeEnd);


        // add a click listener to the button
        BtnTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateCurrentTime();
                showDialog(TIME_DIALOG_ID);
            }
        });
        BtnTime2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateCurrentTime2();
                showDialog(TIME_DIALOG_ID);
            }
        });

        // get the current time
        final Calendar c1 = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR);
        mMinute = c.get(Calendar.MINUTE);

        // display the current time
        updateCurrentTime();


        // btnSave
        final Button btnSave = (Button) findViewById(R.id.btnSave);
        // Perform action on click
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (SaveData()) {
                    // When Save Complete
                }
            }
        });


    }

   //Date
   @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                System.out.println("onCreateDialog  : " + id);
                cur = DATE_DIALOG_ID;
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);


            case TIME_DIALOG_ID:
                cur = TIME_DIALOG_ID;
                System.out.println("onCreateDialog2  : " + id);
                return new TimePickerDialog(this,
                        mTimeSetListener,
                        mHour, mMinute, false);
        }
        return null;
    }




    public boolean SaveData() {

        // txtUsername,txtPassword,txtName,txtEmail,txtTel
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

        // Check Username
        if (txtActivityName.getText().length() == 0) {
            ad.setMessage("Please input [Username] ");
            ad.show();
            txtActivityName.requestFocus();
            return false;
        }
        // Check Password
        if (txtPassword.getText().length() == 0 || txtConPassword.getText().length() == 0) {
            ad.setMessage("Please input [Password/Confirm Password] ");
            ad.show();
            txtPassword.requestFocus();
            return false;
        }
        // Check Password and Confirm Password (Match)
        if (!txtPassword.getText().toString().equals(txtConPassword.getText().toString())) {
            ad.setMessage("Password and Confirm Password Not Match! ");
            ad.show();
            txtConPassword.requestFocus();
            return false;
        }
        // Check Name
        if (txtActivityStaff.getText().length() == 0) {
            ad.setMessage("Please input [Name] ");
            ad.show();
            txtActivityStaff.requestFocus();
            return false;
        }

        // Check Tel
        if (txtHour.getText().length() == 0) {
            ad.setMessage("Please input [Tel] ");
            ad.show();
            txtHour.requestFocus();
            return false;
        }
       /* // Check Date
        if(txtDate.getText().length() == 0)
        {
            ad.setMessage("Please input [Tel] ");
            ad.show();
            txtDate.requestFocus();
            return false;
        }*/


        String url = "http://172.19.192.26/register/saveADDData.php";

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

        String resultServer = getHttpPost(url, params);

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
        if (strStatusID.equals("0")) {
            ad.setMessage(strError);
            ad.show();
        } else {
            Toast.makeText(AddActivityWebserv.this, "Save Data Successfully", Toast.LENGTH_SHORT).show();
            txtActivityName.setText("");
            txtActivityStaff.setText("");
            txtPassword.setText("");
            txtConPassword.setText("");
            spinner_type.setSelected(true);
            txtHour.setText("");
            txtDateStart.setText("");
            txtTimeStart.setText("");
            txtDateStart.setText("");
            txtDateEnd.setText("");

        }


        return true;
    }


    public String getHttpPost(String url, List<NameValuePair> params) {
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


    //Date
    // updates the date we display in the editText
    private void updateCurrentDate() {
        editT1.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear).append(" "));


    }

    private void updateCurrentDate2() {
        editT2.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear).append(" "));


    }

    // updates the time we display in the editText
    private void updateCurrentTime() {
        TxtTime.setText(
                new StringBuilder()
                        .append(mHour).append(":")
                        .append(mMinute));
    }
    private void updateCurrentTime2() {
        TxtTime2.setText(
                new StringBuilder()
                        .append(mHour).append(":")
                        .append(mMinute));
    }

    //Date
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    // updateCurrentDate();
                    updateCurrentDate2();
                }
            };

    //Time
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {

                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    // TODO Auto-generated method stub
                    mHour = hourOfDay;
                    mMinute = minute;
                    updateCurrentTime2();
                }
            };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
