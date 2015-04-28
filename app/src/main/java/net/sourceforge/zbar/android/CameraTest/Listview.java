package net.sourceforge.zbar.android.CameraTest;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class Listview extends Activity {

    ArrayList<HashMap<String, String>> MyArrList;
    String[] Cmd = {"สแกน QR-Code","ดูรายละเอียด","แก้ไขกิจกรรม","ลบกิจกรรม"};

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewww);

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

        String url = "http://10.0.3.2/register/showAllData.php";

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

            registerForContextMenu(lisView1);


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

        menu.setHeaderIcon(android.R.drawable.btn_star_big_on);
        menu.setHeaderTitle("Command");
        String[] menuItems = Cmd;
        for (int i = 0; i<menuItems.length; i++) {
            menu.add(Menu.NONE, i, i, menuItems[i]);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = Cmd;
        String CmdName = menuItems[menuItemIndex];

        // Check Event Command
        if ("สแกน QR-Code".equals(CmdName)) {
            Toast.makeText(Listview.this, "สแกน QR-Code", Toast.LENGTH_LONG).show();
            final String sMemberID = MyArrList.get(info.position).get("MemberID").toString();
            String sActivityName = MyArrList.get(info.position).get("ActivityName").toString();
            String sDateStart = MyArrList.get(info.position).get("DateStart").toString();



            Intent newActivity = new Intent(Listview.this, LoginForQrCode.class);
            newActivity.putExtra("MemberID", sMemberID);
            startActivity(newActivity);
        } else if ("ดูรายละเอียด".equals(CmdName)) {
            Toast.makeText(Listview.this, "ดูรายละเอียด", Toast.LENGTH_LONG).show();

            final String sMemberID = MyArrList.get(info.position).get("MemberID").toString();
            String sActivityName = MyArrList.get(info.position).get("ActivityName").toString();
            String sDateStart = MyArrList.get(info.position).get("DateStart").toString();



            Intent newActivity = new Intent(Listview.this, DetailActivity.class);
            newActivity.putExtra("MemberID", sMemberID);
            startActivity(newActivity);
        } else if ("แก้ไขกิจกรรม".equals(CmdName)) {
            Toast.makeText(Listview.this, "Your Selected Update", Toast.LENGTH_LONG).show();

         final String sMemberID = MyArrList.get(info.position).get("MemberID").toString();
            String sActivityName = MyArrList.get(info.position).get("ActivityName").toString();
            String sDateStart = MyArrList.get(info.position).get("DateStart").toString();



            Intent newActivity = new Intent(Listview.this, LoginForUpdate.class);
                    newActivity.putExtra("MemberID", sMemberID);
                    startActivity(newActivity);








        } else if ("ลบกิจกรรม".equals(CmdName)) {
            Toast.makeText(Listview.this, "Your Selected Delete", Toast.LENGTH_LONG).show();
            /**
             * Call the mthod
             */

    } else if ("d".equals(CmdName)) {
        Toast.makeText(Listview.this, "Your Selected Delete", Toast.LENGTH_LONG).show();
        /**
         * Call the mthod
         */
    }
        return true;
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
                convertView = inflater.inflate(R.layout.activity_column, null);
            }

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
