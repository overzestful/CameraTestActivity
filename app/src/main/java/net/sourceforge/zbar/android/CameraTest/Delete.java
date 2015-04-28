package net.sourceforge.zbar.android.CameraTest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Delete extends Activity {

    ArrayList<HashMap<String, String>> MyArrList;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ShowData();


        // btnSearch
        final Button btnSearch = (Button) findViewById(R.id.btnSearch);
        //btnSearch.setBackgroundColor(Color.TRANSPARENT);
        // Perform action on click
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShowData();
            }
        });

    }

    public void ShowData()
    {
        // listView1
        final ListView lisView1 = (ListView)findViewById(R.id.listView1);

        // keySearch
        EditText strKeySearch = (EditText)findViewById(R.id.txtKeySearch);

        // Disbled Keyboard auto focus
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(strKeySearch.getWindowToken(), 0);

        /**
         * [{"MemberID":"1","Username":"weerachai","Password":"weerachai@1","Name":"Weerachai Nukitram","Tel":"0819876107","Email":"weerachai@thaicreate.com"},
         * {"MemberID":"2","Username":"adisorn","Password":"adisorn@2","Name":"Adisorn Bunsong","Tel":"021978032","Email":"adisorn@thaicreate.com"},
         * {"MemberID":"3","Username":"surachai","Password":"surachai@3","Name":"Surachai Sirisart","Tel":"0876543210","Email":"surachai@thaicreate.com"}]
         */

        String url = "http://10.0.3.2/delete/showAllData.php";

        // Paste Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("txtKeyword", strKeySearch.getText().toString()));

        try {

            JSONArray data = new JSONArray(getJSONUrl(url,params));

            MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("MemberID", c.getString("MemberID"));
                map.put("ActivityName", c.getString("ActivityName"));
                map.put("ActivityStaff", c.getString("ActivityStaff"));
                map.put("SpinnerType", c.getString("SpinnerType"));
                map.put("Hour", c.getString("Hour"));
                map.put("DateStart", c.getString("DateStart"));
                map.put("TimeStart", c.getString("TimeStart"));
                map.put("DateEnd", c.getString("DateEnd"));
                map.put("TimeEnd", c.getString("TimeEnd"));
                MyArrList.add(map);

            }

            lisView1.setAdapter(new ImageAdapter(this));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public class ImageAdapter extends BaseAdapter
    {
        private Context context;

        public ImageAdapter(Context c)
        {
            // TODO Auto-generated method stub
            context = c;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return MyArrList.size();
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.activity_column_delete, null);
            }

            // imgCmdDelete
            ImageButton cmdDelete = (ImageButton) convertView.findViewById(R.id.btnDelete);
            cmdDelete.setBackgroundColor(Color.TRANSPARENT);

            final AlertDialog.Builder adb1 = new AlertDialog.Builder(Delete.this);
            final AlertDialog.Builder adb2 = new AlertDialog.Builder(Delete.this);

            cmdDelete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent goFirst = new Intent(getApplicationContext(),
                            LoginForDelete.class);
                    startActivity(goFirst);
                    adb1.setTitle("Delete?");
                    adb1.setMessage("Are you sure delete [" + MyArrList.get(position).get("ActivityName") +"]");
                    adb1.setNegativeButton("Cancel", null);
                    adb1.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {








                                // Request to Delete data.
                                String url = "http://10.0.3.2/delete/deleteData.php";
                                List<NameValuePair> params = new ArrayList<NameValuePair>();
                                params.add(new BasicNameValuePair("sMemberID", MyArrList.get(position).get("MemberID")));

                                String resultServer = getJSONUrl(url, params);

                                /** Get result delete data from Server (Return the JSON Code)
                                 * StatusID = ? [0=Failed,1=Complete]
                                 * Error	= ?	[On case error return custom error message]
                                 *
                                 * Eg Login Failed = {"StatusID":"0","Error":"Cannot delete data!"}
                                 * Eg Login Complete = {"StatusID":"1","Error":""}
                                 */
                                String strStatusID = "0";
                                String strError = "Unknow Status";

                                try {
                                    JSONObject c = new JSONObject(resultServer);
                                    strStatusID = c.getString("StatusID");
                                    strError = c.getString("Error");
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                // Prepare Delete
                                if (strStatusID.equals("0")) {
                                    // Dialog
                                    adb2.setTitle("Error! ");
                                    adb2.setIcon(android.R.drawable.btn_star_big_on);
                                    adb2.setPositiveButton("Close", null);
                                    adb2.setMessage(strError);
                                    adb2.show();
                                } else {
                                    Toast.makeText(Delete.this, "Delete data successfully.", Toast.LENGTH_SHORT).show();
                                    ShowData(); // reload data again
                                }

                        }});
                    adb1.show();
                }
            });

            // ColMemberID
            TextView txtMemberID = (TextView) convertView.findViewById(R.id.ColMemberID);
            txtMemberID.setPadding(10, 0, 0, 0);
            txtMemberID.setText(MyArrList.get(position).get("MemberID") +".");

            // R.id.ColName
            TextView txtName = (TextView) convertView.findViewById(R.id.ColName);
            txtName.setPadding(5, 0, 0, 0);
            txtName.setText(MyArrList.get(position).get("ActivityName"));

            // R.id.ColTel
            TextView txtTel = (TextView) convertView.findViewById(R.id.ColTel);
            txtTel.setPadding(5, 0, 0, 0);
            txtTel.setText(MyArrList.get(position).get("DateStart"));


            return convertView;

        }

    }


    public String getJSONUrl(String url,List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Download OK
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
