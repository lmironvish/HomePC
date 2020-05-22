package com.example.fragmenttest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ComDBHelper extends SQLiteOpenHelper {

    public static final String FAM_TABLE_NAME = "FamilyTask";
    public static final String FAM_COLUMN_ID = "id";
    public static final String FAM_COLUMN_TOPIC = "topic";
    public static final String FAM_COLUMN_CONTENT = "content";
    public static final String FAM_COLUMN_DEADLINE = "deadline";

    public static final String MY_TABLE_NAME = "MyTask";
    public static final String MY_COLUMN_ID = "id";
    public static final String MY_COLUMN_TOPIC = "topic";
    public static final String MY_COLUMN_CONTENT = "content";
    public static final String MY_COLUMN_DEADLINE = "deadline";

    public static final String IP_TABLE_NAME = "InProcess";
    public static final String IP_COLUMN_ID = "id";
    public static final String IP_COLUMN_TOPIC = "topic";
    public static final String IP_COLUMN_CONTENT = "content";
    public static final String IP_COLUMN_DEADLINE = "deadline";

    public ComDBHelper(@Nullable Context context,
                       @Nullable String name,
                       @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + FAM_TABLE_NAME +" ("
                + FAM_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FAM_COLUMN_TOPIC + " TEXT,"
                + FAM_COLUMN_CONTENT + " TEXT,"
                + FAM_COLUMN_DEADLINE + " TEXT "
                + ");");

        sqLiteDatabase.execSQL("CREATE TABLE " + MY_TABLE_NAME +" ("
                + MY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MY_COLUMN_TOPIC + " TEXT, "
                + MY_COLUMN_CONTENT + " TEXT, "
                + MY_COLUMN_DEADLINE + " TEXT "
                + ");");

        sqLiteDatabase.execSQL("CREATE TABLE " + IP_TABLE_NAME +" ("
                + IP_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + IP_COLUMN_TOPIC + " TEXT, "
                + IP_COLUMN_CONTENT + " TEXT, "
                + IP_COLUMN_DEADLINE + " TEXT "
                + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
