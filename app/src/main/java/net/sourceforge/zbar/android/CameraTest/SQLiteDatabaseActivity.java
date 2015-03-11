package net.sourceforge.zbar.android.CameraTest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Overzestful-Fhon on 3/4/2015.
 */
public class SQLiteDatabaseActivity extends ListActivity {
    final static String TAG = "SQLite";
    private BookDataSource datasource;
    List<Book> values;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_book);
        Button add_book = (Button) findViewById(R.id.buttonAdd);
        add_book.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                addNewBook();
            }
        });
        datasource = new BookDataSource(this);
        datasource.open();

        showAllBook();// show all book.
    }

    public void showAllBook() {
        values = datasource.getAllBook();
        ArrayAdapter<Book> adapter = new ArrayAdapter<Book>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        showBookDetail(position); // View book by id
    }

    public void showBookDetail(final int id) {
        @SuppressWarnings("unchecked")
        final ArrayAdapter<Book> adapter = (ArrayAdapter<Book>) getListAdapter();
        final Book book = (Book) getListAdapter().getItem(id);

        final Dialog dialog = new Dialog(SQLiteDatabaseActivity.this);
        dialog.setContentView(R.layout.detail_book);
        dialog.setTitle("รายละเอียดกิจกรรม");
        dialog.setCancelable(true);



        /*Button button_password = (Button) dialog.findViewById(R.id.buttonEnter);
        button_password.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getListAdapter().getCount() > 0) {

                    password();
                    dialog.dismiss();
                }
            }
        });*/


        TextView txt_name = (TextView) dialog.findViewById(R.id.textBookNAME);
        TextView txt_staff = (TextView) dialog.findViewById(R.id.textBookSTAFF);
        final TextView txt_password = (TextView) dialog.findViewById(R.id.textBookPASSWORD);
        TextView txt_confirm_password = (TextView) dialog.findViewById(R.id.textBookCONFIRMPASSWORD);
        TextView txt_hour = (TextView) dialog.findViewById(R.id.textBookHOUR);

        txt_name.setText("ชื่อกิจกรรม :\t" + book.getName());
        txt_staff.setText("ผู้ดูแล :\t" + book.getStaff());
        txt_password.setText("รหัสผ่าน :\t" + book.getPassword());
        txt_confirm_password.setText("ยืนยันรหัสผ่าน :\t" + book.getConfirm_password());
        txt_hour.setText("จำนวนชั่วโมง :\t" + book.getHour());

        //enter

        Button button_edit = (Button) dialog.findViewById(R.id.buttonEdit);

        button_edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getListAdapter().getCount() > 0) {

                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(SQLiteDatabaseActivity.this);
                    LayoutInflater inflater = getLayoutInflater();

                    View view = inflater.inflate(R.layout.avtivity_enterpassword, null);
                    builder.setView(view);


                    final EditText password1 = (EditText) view.findViewById(R.id.password1);
                    final EditText password2 = (EditText) view.findViewById(R.id.password2);
                    builder.setTitle("ใส่รหัสกิจกรรม");
                    builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editBook(book);

               /* Intent goFirst = new Intent(getApplicationContext(),
                        QRcodeMain.class);
                startActivity(goFirst);*/
                            // Check username password
                 /*if (username.getText().equals("demo@example.com") &&
                         password.getText().equals("demo")) {
                     Toast.makeText(getApplicationContext(), "Login success!",
                             Toast.LENGTH_SHORT).show();
                 } else {
                     Toast.makeText(getApplicationContext(), "Login Failed!",
                             Toast.LENGTH_SHORT).show();
                 }*/
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.create();

                    builder.show();
                    dialog.dismiss();

                }
            }

        });


        //edit book


        Button button_enter = (Button) dialog.findViewById(R.id.buttonEnter);

        button_enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getListAdapter().getCount() > 0) {

                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(SQLiteDatabaseActivity.this);
                    LayoutInflater inflater = getLayoutInflater();

                    View view = inflater.inflate(R.layout.avtivity_enterpassword, null);
                    builder.setView(view);


                    final EditText password1 = (EditText) view.findViewById(R.id.password1);
                    final EditText password2 = (EditText) view.findViewById(R.id.password2);
                    builder.setTitle("ใส่รหัสกิจกรรม");
                    builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            Intent goFirst = new Intent(getApplicationContext(),
                                    QRcodeMain.class);
                            startActivity(goFirst);
                            // Check username password
                 /*if (username.getText().equals("demo@example.com") &&
                         password.getText().equals("demo")) {
                     Toast.makeText(getApplicationContext(), "Login success!",
                             Toast.LENGTH_SHORT).show();
                 } else {
                     Toast.makeText(getApplicationContext(), "Login Failed!",
                             Toast.LENGTH_SHORT).show();
                 }*/
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.create();

                    builder.show();
                    dialog.dismiss();

                }
            }

        });
        //delete book
        Button button_delete = (Button) dialog.findViewById(R.id.buttonDelete);
        button_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getListAdapter().getCount() > 0) {

                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(SQLiteDatabaseActivity.this);
                    LayoutInflater inflater = getLayoutInflater();

                    View view = inflater.inflate(R.layout.avtivity_enterpassword, null);
                    builder.setView(view);


                    final EditText password1 = (EditText) view.findViewById(R.id.password1);
                    final EditText password2 = (EditText) view.findViewById(R.id.password2);
                    builder.setTitle("ใส่รหัสกิจกรรม");
                    builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


               /* Intent goFirst = new Intent(getApplicationContext(),
                        QRcodeMain.class);
                startActivity(goFirst);*/
                            // Check username password
                 /*if (username.getText().equals("demo@example.com") &&
                         password.getText().equals("demo")) {
                     Toast.makeText(getApplicationContext(), "Login success!",
                             Toast.LENGTH_SHORT).show();
                 } else {
                     Toast.makeText(getApplicationContext(), "Login Failed!",
                             Toast.LENGTH_SHORT).show();
                 }*/
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.create();

                    builder.show();
                    datasource.deleteBook(book); // delete book
                    adapter.remove(book);
                    dialog.dismiss();
                    dialog.dismiss();
                    Toast.makeText(SQLiteDatabaseActivity.this, "Delete data succeed.", Toast.LENGTH_LONG).show();
                }
            }
        });


// close dialog
        Button button_cancel = (Button) dialog.findViewById(R.id.buttonClose);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

/*

    public void password() {



        AlertDialog.Builder builder =
                new AlertDialog.Builder(SQLiteDatabaseActivity.this);
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.avtivity_enterpassword, null);
        builder.setView(view);


        final EditText password1 = (EditText) view.findViewById(R.id.password1);
        final EditText password2 = (EditText) view.findViewById(R.id.password2);
        builder.setTitle("ใส่รหัสกิจกรรม");
        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {







               */
/* Intent goFirst = new Intent(getApplicationContext(),
                        QRcodeMain.class);
                startActivity(goFirst);*//*

                // Check username password
                 */
/*if (username.getText().equals("demo@example.com") &&
                         password.getText().equals("demo")) {
                     Toast.makeText(getApplicationContext(), "Login success!",
                             Toast.LENGTH_SHORT).show();
                 } else {
                     Toast.makeText(getApplicationContext(), "Login Failed!",
                             Toast.LENGTH_SHORT).show();
                 }*//*

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
*/


    public void showBookDetail1(final int id) {
        @SuppressWarnings("unchecked")
        final ArrayAdapter<Book> adapter = (ArrayAdapter<Book>) getListAdapter();
        final Book book = (Book) getListAdapter().getItem(id);

        final Dialog dialog = new Dialog(SQLiteDatabaseActivity.this);
        dialog.setContentView(R.layout.detail_book);
        dialog.setTitle("รายละเอียดกิจกรรม");
        dialog.setCancelable(true);



        /*Button button_password = (Button) dialog.findViewById(R.id.buttonEnter);
        button_password.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getListAdapter().getCount() > 0) {

                    password();
                    dialog.dismiss();
                }
            }
        });*/


        TextView txt_name = (TextView) dialog.findViewById(R.id.textBookNAME);
        TextView txt_staff = (TextView) dialog.findViewById(R.id.textBookSTAFF);
        TextView txt_password = (TextView) dialog.findViewById(R.id.textBookPASSWORD);
        TextView txt_confirm_password = (TextView) dialog.findViewById(R.id.textBookCONFIRMPASSWORD);
        TextView txt_hour = (TextView) dialog.findViewById(R.id.textBookHOUR);

        txt_name.setText("ชื่อกิจกรรม :\t" + book.getName());
        txt_staff.setText("ผู้ดูแล :\t" + book.getStaff());
        txt_password.setText("รหัสผ่าน :\t" + book.getPassword());
        txt_confirm_password.setText("ยืนยันรหัสผ่าน :\t" + book.getConfirm_password());
        txt_hour.setText("จำนวนชั่วโมง :\t" + book.getHour());


        //edit book
        Button button_edit = (Button) dialog.findViewById(R.id.buttonEdit);
        button_edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getListAdapter().getCount() > 0) {

                    editBook(book);

                    dialog.dismiss();
                }
            }
        });
        //delete book
        Button button_delete = (Button) dialog.findViewById(R.id.buttonDelete);
        button_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getListAdapter().getCount() > 0) {
                    datasource.deleteBook(book); // delete book
                    adapter.remove(book);
                    dialog.dismiss();
                    dialog.dismiss();
                    Toast.makeText(SQLiteDatabaseActivity.this, "Delete data succeed.", Toast.LENGTH_LONG).show();
                }
            }
        });


// close dialog
        Button button_cancel = (Button) dialog.findViewById(R.id.buttonClose);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void editBook(final Book book) {
        final Dialog dialog = new Dialog(SQLiteDatabaseActivity.this);
        dialog.setContentView(R.layout.add_book);
        dialog.setTitle("แก้ไขกิจกรรม");
        dialog.setCancelable(true);

        final EditText name = (EditText) dialog.findViewById(R.id.editTextNAME);
        final EditText staff = (EditText) dialog
                .findViewById(R.id.editTextSTAFF);
        final EditText password = (EditText) dialog
                .findViewById(R.id.editTextPASSWORD);
        final EditText confirm_password = (EditText) dialog
                .findViewById(R.id.editTextCONFIRMPASSWORD);
        final EditText hour = (EditText) dialog
                .findViewById(R.id.editTextHOUR);

        name.setText(book.getName());
        staff.setText(book.getStaff());
        password.setText(book.getPassword());
        confirm_password.setText(book.getConfirm_password());
        hour.setText(book.getHour());
        // setup button
        Button button_save = (Button) dialog.findViewById(R.id.buttonSave);
        button_save.setText("Update");
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get string
                String value_name = name.getText().toString();
                String value_staff = staff.getText().toString();
                String value_password = password.getText().toString();
                String value_confirm_password = confirm_password.getText().toString();
                // String value_type = type.getText().toString();
                String value_hour = hour.getText().toString();

                book.setName(value_name);
                book.setStaff(value_staff);
                book.setPassword(value_password);
                book.setConfirm_password(value_confirm_password);
                book.setHour(value_hour);
                datasource.updateBook(book);
                showAllBook();
                dialog.cancel();
            }
        });
        Button button_cancel = (Button) dialog.findViewById(R.id.buttonCancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }


    public void addNewBook() {
        final Dialog dialog = new Dialog(SQLiteDatabaseActivity.this);
        dialog.setContentView(R.layout.add_book);
        dialog.setTitle("เพิ่มกิจกรรม");
        dialog.setCancelable(true);
        // setup button
        Button button_save = (Button) dialog.findViewById(R.id.buttonSave);
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get string
                EditText name = (EditText) dialog
                        .findViewById(R.id.editTextNAME);
                EditText staff = (EditText) dialog
                        .findViewById(R.id.editTextSTAFF);
                EditText password = (EditText) dialog
                        .findViewById(R.id.editTextPASSWORD);
                EditText confirm_password = (EditText) dialog
                        .findViewById(R.id.editTextCONFIRMPASSWORD);
                EditText hour = (EditText) dialog
                        .findViewById((R.id.editTextHOUR));

                String value_name = name.getText().toString();
                String value_staff = staff.getText().toString();
                String value_password = password.getText().toString();
                String value_confirm_password = confirm_password.getText().toString();
                String value_hour = hour.getText().toString();


                @SuppressWarnings("unchecked")
                ArrayAdapter<Book> adapter = (ArrayAdapter<Book>) getListAdapter();
                Book book = new Book();
                book.setName(value_name);
                book.setStaff(value_staff);
                book.setPassword(value_password);
                book.setConfirm_password(value_confirm_password);
                book.setHour(value_hour);
                book = datasource.insertBook(book);
                adapter.add(book);
                dialog.cancel();
            }
        });
        Button button_cancel = (Button) dialog.findViewById(R.id.buttonCancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}
