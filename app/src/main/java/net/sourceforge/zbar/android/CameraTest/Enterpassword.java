package net.sourceforge.zbar.android.CameraTest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Overzestful-Fhon on 3/7/2015.
 */
public class Enterpassword extends Activity {



/*

    Button mDialogCustom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        mDialogCustom = (Button) findViewById(R.id.buttonEnter);

        mDialogCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(Enterpassword.this);
                LayoutInflater inflater = getLayoutInflater();

                View view = inflater.inflate(R.layout.avtivity_enterpassword, null);
                builder.setView(view);

                final EditText username = (EditText) view.findViewById(R.id.username);
                final EditText password = (EditText) view.findViewById(R.id.password);

                builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Check username password
                        if (username.getText().equals("demo@example.com") &&
                                password.getText().equals("demo")) {
                            Toast.makeText(getApplicationContext(), "Login success!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Login Failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create();

                builder.show();
            }
        });


    }
*/


}
