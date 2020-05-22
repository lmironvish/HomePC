package com.example.fragmenttest.InProcess;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.fragmenttest.ComDBHelper;
import com.example.fragmenttest.R;

public class InProcessFragment extends Fragment {

    private ComDBHelper ipDBHelper;
    private SQLiteDatabase SQLDB;
    private ContentValues cv;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.in_process_fragment, container, false);

        /*SQLDB = ipDBHelper.getWritableDatabase();

        Cursor c = SQLDB.query( ipDBHelper.IP_TABLE_NAME, null,
                null, null,
                null, null, null);

        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int topicColIndex = c.getColumnIndex(ipDBHelper.IP_COLUMN_TOPIC);
            int contentColIndex = c.getColumnIndex(ipDBHelper.IP_COLUMN_CONTENT);
            int deadlnColIndex = c.getColumnIndex(ipDBHelper.IP_COLUMN_DEADLINE);

            do {
                // получаем значения по номерам столбцов и пишем все в лог


                *//*Log.d(LOG_TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", name = " + c.getString(nameColIndex) +
                                ", email = " + c.getString(emailColIndex));*//*
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
//            Log.d(LOG_TAG, "0 rows");
        c.close();Log.d("LOG_TAG", "--- Rows in mytable: ---");
*/
    }
}
