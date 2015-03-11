/*
 * Basic no frills app which integrates the ZBar barcode scanner with
 * the camera.
 * 
 * Created by lisah0 on 2012-02-24
 */
package net.sourceforge.zbar.android.CameraTest;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Button;
import android.widget.Toast;

import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Size;

import android.widget.TextView;

/* Import ZBar Class files */
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import net.sourceforge.zbar.Config;

public class CameraTestActivity extends Activity
{
    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;
    private EditText et;
    public static SQLiteDatabase db;
    TextView output;
    
    EditText res ;
    TextView scanText;
    Button scanButton;
    Button view;
    Button del;
    Button back;
    Button exit;

    ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;

    static {
        System.loadLibrary("iconv");
    } 

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_scanner);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();
        res = (EditText)findViewById(R.id.editText1);
        
        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        FrameLayout preview = (FrameLayout)findViewById(R.id.cameraPreview);
        
        
        et = (EditText)findViewById(R.id.editText1);
        Button save = (Button)findViewById(R.id.button2);
        view = (Button)findViewById(R.id.button1);
        exit = (Button)findViewById(R.id.exit);
       
        save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = et.getText().toString();   // �Ѻ��Ҩҡ txt_name

		    	if(name.equals("")){
		    		return;
		    	}
		    	
		    	String sql ="insert into person values(null,'"+name+"')";
		    	try{
		    		db.execSQL(sql);
		    		Toast.makeText(getBaseContext(), "Inset to Database is success.",Toast.LENGTH_SHORT).show();
		    	}catch(Exception e){
		    		Toast.makeText(getBaseContext(), "Error,"+e.getMessage(),Toast.LENGTH_SHORT).show();
		    	}finally{
		    		et.setText("");
		    	}
				
			}
		});
        
        
        view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
						Intent intent = new Intent(CameraTestActivity.this,ShowData.class);
						startActivity(intent);
			}
		});
        
        
        
        exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	            System.exit(0);
			}
		});
        
        
        
        
        preview.addView(mPreview);

        scanText = (TextView)findViewById(R.id.scanText);

        scanButton = (Button)findViewById(R.id.ScanButton);

        scanButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (barcodeScanned) {
                        barcodeScanned = false;
                        scanText.setText("Scanning...");
                        mCamera.setPreviewCallback(previewCb);
                        mCamera.startPreview();
                        previewing = true;
                        mCamera.autoFocus(autoFocusCB);
                    }
                }
            });
        
    	db = openOrCreateDatabase( "test1.db", SQLiteDatabase.CREATE_IF_NECESSARY, null); // ���ҧ Database ���� test1.db
		db.setVersion(1);
		db.setLocale(Locale.getDefault());
		db.setLockingEnabled(true);

		try {
            final String CREATE_TABLE_CONTAIN = "CREATE TABLE IF NOT EXISTS person ("  // �������� table person ���ӡ�����ҧ
                    + "_id INTEGER primary key AUTOINCREMENT,"   // ��Ŵ� _id ����� PK
                    + "name TEXT);";                  // ��Ŵ� name ��Դ Text

            db.execSQL(CREATE_TABLE_CONTAIN);
          //  Toast.makeText(getBaseContext(), "Table person is created.",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
        	Toast.makeText(getBaseContext(), "Error,"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
		
    }

   
   
    
    @Override
    protected void onDestroy(){
       super.onDestroy();
       db.close();  // Close Database Connection
    }
    

	public void onPause() {
        super.onPause();
        releaseCamera();
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e){
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable() {
            public void run() {
                if (previewing)
                    mCamera.autoFocus(autoFocusCB);
            }
        };

    PreviewCallback previewCb = new PreviewCallback() {
            public void onPreviewFrame(byte[] data, Camera camera) {
                Camera.Parameters parameters = camera.getParameters();
                Size size = parameters.getPreviewSize();

                Image barcode = new Image(size.width, size.height, "Y800");
                barcode.setData(data);

                int result = scanner.scanImage(barcode);
                
                if (result != 0) {
                    previewing = false;
                    mCamera.setPreviewCallback(null);
                    mCamera.stopPreview();
                    
                    SymbolSet syms = scanner.getResults();
                    for (Symbol sym : syms) {
                        scanText.setText("barcode result " + sym.getData());
                        res.setText(sym.getData().toString());
                        barcodeScanned = true;
                    }
                }
            }
        };

    // Mimic continuous auto-focusing
    AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
                autoFocusHandler.postDelayed(doAutoFocus, 1000);
            }
        };
}
