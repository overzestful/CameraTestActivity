package net.sourceforge.zbar.android.CameraTest;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class ShowData extends Activity{

	private TextView output;
	private Button del;
	private Button back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showdata);
		output = (TextView)findViewById(R.id.textView1);
		del = (Button)findViewById(R.id.delete);
		back = (Button)findViewById(R.id.back);
		
		CameraTestActivity.db = openOrCreateDatabase( "test1.db", SQLiteDatabase.CREATE_IF_NECESSARY, null); //
		CameraTestActivity.db.setVersion(1);
		CameraTestActivity.db.setLocale(Locale.getDefault());
		CameraTestActivity.db.setLockingEnabled(true);

		try {
            final String CREATE_TABLE_CONTAIN = "CREATE TABLE IF NOT EXISTS person ("  // �������� table person ���ӡ�����ҧ
                    + "_id INTEGER primary key AUTOINCREMENT,"   // ��Ŵ� _id ����� PK
                    + "name TEXT);";                  // ��Ŵ� name ��Դ Text

            CameraTestActivity.db.execSQL(CREATE_TABLE_CONTAIN);
          //  Toast.makeText(getBaseContext(), "Table person is created.",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
        	Toast.makeText(getBaseContext(), "Error,"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
		
		
		showdata();
		
		
		del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				delete_clicked();
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ShowData.this,CameraTestActivity.class);
				startActivity(intent);
			}
		});
		
		
	}
	
	public void delete_clicked(){
    	String sql = "delete from person ";
    	CameraTestActivity.db.execSQL(sql);
   	  	showdata();
    }

	
	private void showdata() {
    	output.setText("");
    	SQLiteCursor cur = (SQLiteCursor)CameraTestActivity.db.rawQuery("select * from person",null);
    	 cur.moveToFirst();
         while (cur.isAfterLast() == false) {
         	output.append(" Data : "+cur.getString(1)+"\n");
        	cur.moveToNext();
         }
         cur.close();
		
	}
	
	
}
