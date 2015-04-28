/*
package net.sourceforge.zbar.android.CameraTest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

public class ListItemView extends Activity {


    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final Button btn1 = (Button) findViewById(R.id.button1);
        // Perform action on click
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SearchData();
            }
        });

    }


    public void SearchData()
    {
        // listView1
        final ListView lisView1 = (ListView)findViewById(R.id.listView1);

        // editText1
        final EditText inputText = (EditText)findViewById(R.id.editText1);

        */
/**
         * [{"CustomerID":"C001","Name":"Win Weerachai","Email":"win.weerachai@thaicreate.com" ,"CountryCode":"TH","Budget":"1000000","Used":"600000"},
         * {"CustomerID":"C002","Name":"John Smith","Email":"john.smith@thaicreate.com" ,"CountryCode":"EN","Budget":"2000000","Used":"800000"},
         * {"CustomerID":"C003","Name":"Jame Born","Email":"jame.born@thaicreate.com" ,"CountryCode":"US","Budget":"3000000","Used":"600000"},
         * {"CustomerID":"C004","Name":"Chalee Angel","Email":"chalee.angel@thaicreate.com" ,"CountryCode":"US","Budget":"4000000","Used":"100000"}]
         *//*


        String url = "http://10.0.3.2/register/getJSON.php";

        // Paste Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("txtKeyword", inputText.getText().toString()));

        try {
            JSONArray data = new JSONArray(getJSONUrl(url,params));

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
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


            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(ListItemView.this, MyArrList, R.layout.activity_column,
                    new String[] {"ActivityName", "SpinnerType", "DateStart"}, new int[] {R.id.ColCustomerID, R.id.ColName, R.id.ColEmail});
            lisView1.setAdapter(sAdap);

            final AlertDialog.Builder viewDetail = new AlertDialog.Builder(this);
            // OnClick Item
            lisView1.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView,
                                        int position, long mylng) {

                    String strActivityName = MyArrList.get(position).get("ActivityName")
                            .toString();
                    String strActivityStaff = MyArrList.get(position).get("ActivityStaff")
                            .toString();
                    String strSpinnerType = MyArrList.get(position).get("SpinnerType")
                            .toString();
                    String strHour = MyArrList.get(position).get("Hour")
                            .toString();
                    String strDateStart = MyArrList.get(position).get("DateStart")
                            .toString();


                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(ListItemView.this);
                    builder.setTitle("รายละเอียดกิจกรรม");
                    builder.setMessage("ชื่อกิจกรรม : " + strActivityName + "\n"
                            + "ผู้ดูแลกิจกรรม : " + strActivityStaff + "\n"
                            + "ประเภทกิจกรรม : " + strSpinnerType + "\n"
                            + "จำนวนชั่วโมงที่ได้รับ : " + strHour + "\n"
                            + "วันที่เริ่มทำกิจกรรม : " + strDateStart);

                    builder.setPositiveButton("เริ่มสแกน", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getApplicationContext(),
                                    "ขอบคุณครับ", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //dialog.dismiss();
                        }
                    });
                    builder.show();
                   */
/* String strActivityName = MyArrList.get(position).get("ActivityName")
                            .toString();
                    String strActivityStaff = MyArrList.get(position).get("ActivityStaff")
                            .toString();
                    String strSpinnerType = MyArrList.get(position).get("SpinnerType")
                            .toString();
                    String strHour = MyArrList.get(position).get("Hour")
                            .toString();
                    String strDateStart = MyArrList.get(position).get("DateStart")
                            .toString();


                    viewDetail.setIcon(android.R.drawable.btn_star_big_on);
                    viewDetail.setTitle("รายละเอียดกิจกรรม");
                    viewDetail.setMessage("ชื่อกิจกรรม : " + strActivityName + "\n"
                            + "ผู้ดูแลกิจกรรม : " + strActivityStaff + "\n"
                            + "ประเภทกิจกรรม : " + strSpinnerType + "\n"
                            + "จำนวนชั่วโมงที่ได้รับ : " + strHour + "\n"
                            + "วันที่เริ่มทำกิจกรรม : " + strDateStart);


                    viewDetail.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                }
                            });
                    viewDetail.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                }
                            });

                    viewDetail.show();*//*


                }
            });


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
                Log.e("Log", "Failed to download file..");
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
*/
