package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public static final String DB_NAME = "meDB";
    public static final String LOG_TAG = "myLogs";

    DBHelper dbHelper;
    SQLiteDatabase db;
    ContentValues cv;

    private TextView tv;

    private Button signIn, show;
    private EditText login, password;
    private String pass, log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signIn = (Button) findViewById(R.id.signIn);

        dbHelper = new DBHelper(this, DB_NAME, null, 1);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login = (EditText) findViewById(R.id.login);
                password = (EditText) findViewById(R.id.pass);

                pass = password.getText().toString();
                log = login.getText().toString();

                db = dbHelper.getWritableDatabase();

                cv = new ContentValues();
                cv.put(DBHelper.COLUMN_NAME, log);
                cv.put(DBHelper.COLUMN_PASSWORD, pass);

                db.insert(DBHelper.TABLE_NAME, null, cv);

                dbHelper.close();

            }
        });

        show = (Button) findViewById(R.id.show);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "--- Rows in mytable: ---");

                db = dbHelper.getWritableDatabase();

                Cursor c = db.query( DBHelper.TABLE_NAME, null,
                        null, null,
                        null, null, null);

                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex(DBHelper.COLUMN_ID);
                    int nameColIndex = c.getColumnIndex(DBHelper.COLUMN_NAME);
                    int emailColIndex = c.getColumnIndex(DBHelper.COLUMN_PASSWORD);

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                                        ", name = " + c.getString(nameColIndex) +
                                        ", email = " + c.getString(emailColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();

                dbHelper.close();

            }
        });
    }
}