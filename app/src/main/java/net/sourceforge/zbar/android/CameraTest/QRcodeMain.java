package net.sourceforge.zbar.android.CameraTest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class QRcodeMain extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_main);


    }

    public void recScan(View v) {
        Intent intent = new Intent(QRcodeMain.this, CameraTestActivity.class);
        //  setContentView(R.layout.main_book);
        startActivity(intent);


    }

}
