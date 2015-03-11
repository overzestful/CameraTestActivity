package net.sourceforge.zbar.android.CameraTest;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.Activity;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class Main extends Activity {

    private Toast toast;
    TextView tv_footer;


    private ImageButton ImgButtonAdd;
    private  ImageButton ImgButtonView;
    private  ImageButton ImgButtonCarlen;
  //  private StatusTracker mStatusTracker = StatusTracker.getInstance();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);



        /*tv_footer=(TextView)findViewById(R.id.tv_footer);
        //tv_footer.setText("ยินดีต้อนรับเข้าสู่ Activity Recording System with QR Code");
        tv_footer.setSelected(true);
*/


        ImgButtonAdd = (ImageButton)findViewById(R.id.addActivity);
        ImgButtonView = (ImageButton)findViewById(R.id.viewallactivity);
        ImgButtonCarlen = (ImageButton)findViewById(R.id.viewCalendar);

	}
    private long lastBackPressTime = 0;
    @Override
    public void onBackPressed(){
        if(this.lastBackPressTime < System.currentTimeMillis() - 2000){
            toast = Toast.makeText(this, "กดปุ่ม Back อีกครั้งเพื่อปิดแอพลิเคชั่น", 2000);
            toast.show();
            this.lastBackPressTime = System.currentTimeMillis();
        }else{
            if(toast != null){
                toast.cancel();
            }
            super.onBackPressed();
        }
    }




   /* @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }*/


    public void addAc(View v) {
        Intent intent = new Intent(Main.this, SQLiteDatabaseActivity.class);
      //  setContentView(R.layout.main_book);
        startActivity(intent);


    }

    public  void viewall(View v){
        Intent intent = new Intent(Main.this, ViewallActivity.class);
       // setContentView(R.layout.activity_viewall);
        startActivity(intent);


    }

    public  void view_calendar(View v){
        Intent intent = new Intent(Main.this,ViewCalendar.class);
        //setContentView(R.layout.activity_view_calendar);
        startActivity(intent);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent addFriend = new Intent(this, AddActivity.class);

            startActivity(addFriend);
            overridePendingTransition(android.R.anim.fade_in,
                    android.R.anim.fade_out);
        }
        return super.onOptionsItemSelected(item);
    }

}
