package com.example.familyplanner.MyTask;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.familyplanner.ComDBHelper;
import com.example.familyplanner.R;

import java.util.ArrayList;

public class MyTaskFragment extends Fragment {

    private ListView myTaskListView;
    private ImageButton myAddButton;
    private Button myConfirmButton,
            myCancelButton, myCloseButton,
            myDelCloseButton, myDeleteButton, myDoneButton;
    private Dialog myAddDialog, myInfoDialog, myDeleteDialog;
    private EditText myTopicEditText, myContentEditText, myDeadlineEditText;
    private TextView myTopicTextView, myContentTextView, myDeadlineTextView, myNumberTextView;
    private ArrayList myTasks;
    private MyTasksAdapter myTasksAdapter;

    private ComDBHelper comDBHelper;
    private SQLiteDatabase sqLiteDatabase;
    private ContentValues contentValues;

    private String id;


    public static final String DB_NAME = "sqlDB";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myTasks = new ArrayList<>();

        myTaskListView = getView().findViewById(R.id.my_task_list);
        myTasksAdapter = new MyTasksAdapter(getContext(), myTasks);
        myTaskListView.setAdapter(myTasksAdapter);

        comDBHelper = new ComDBHelper(getContext(), DB_NAME, null, 1);

        myAddDialog = new Dialog(getContext());
        myDeleteDialog = new Dialog(getContext());
        myInfoDialog = new Dialog(getContext());

        myAddDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myInfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDeleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        myAddDialog.setContentView(R.layout.my_task_add_dialog);
        myDeleteDialog.setContentView(R.layout.my_delete_dialog);
        myInfoDialog.setContentView(R.layout.my_info_dialog);


        myAddButton = getView().findViewById(R.id.addA);

        myAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myAddDialog.show();

                myTopicEditText = myAddDialog.findViewById(R.id.my_topic_et);
                myContentEditText = myAddDialog.findViewById(R.id.my_content_et);
                myDeadlineEditText = myAddDialog.findViewById(R.id.my_deadline);
                myConfirmButton = myAddDialog.findViewById(R.id.my_confirm_btn);
                myCancelButton = myAddDialog.findViewById(R.id.my_cancel_btn);


                myConfirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        sqLiteDatabase = comDBHelper.getWritableDatabase();

                        contentValues = new ContentValues();

                        contentValues.put(ComDBHelper.MY_COLUMN_TOPIC, myTopicEditText.getText().toString());
                        contentValues.put(ComDBHelper.MY_COLUMN_CONTENT, myContentEditText.getText().toString());
                        contentValues.put(ComDBHelper.MY_COLUMN_DEADLINE, myDeadlineEditText.getText().toString());

                        id = String.valueOf(sqLiteDatabase.insert(ComDBHelper.MY_TABLE_NAME, null, contentValues));

                        if (Integer.parseInt(id) > 0) {

                            Toast.makeText(getContext(), "Успешно",
                                    Toast.LENGTH_SHORT).show();

                        } else if (Integer.parseInt(id) == -1) {

                            Toast.makeText(getContext(), "Ошибка записи в базу данных",
                                    Toast.LENGTH_SHORT).show();

                        }

                        comDBHelper.close();

                        myTasks.add(new MyTask(myTopicEditText.getText().toString(),

                                myContentEditText.getText().toString(),
                                myDeadlineEditText.getText().toString(), id));

                        myTasksAdapter.notifyDataSetChanged();

                        id = null;

                        myTopicEditText.setText(null);
                        myContentEditText.setText(null);
                        myDeadlineEditText.setText(null);

                        myAddDialog.cancel();
                    }
                });
                myCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        myAddDialog.cancel();

                    }
                });

            }
        });


        myTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                myInfoDialog.show();

                //TextViews
                myTopicTextView = myInfoDialog.findViewById(R.id.my_topic_tv);
                myContentTextView = myInfoDialog.findViewById(R.id.my_content_tv);
                myDeadlineTextView = myInfoDialog.findViewById(R.id.my_deadline_tv);
                myNumberTextView = myInfoDialog.findViewById(R.id.my_num_tv);

                //Buttons
                myCloseButton = myInfoDialog.findViewById(R.id.close_btn);
                myDoneButton = myInfoDialog.findViewById(R.id.my_done_btn);

                if (myTasksAdapter.getItem(position).getTask_topic().equals("")) {
                    myTopicTextView.setText("Без темы'");
                } else {
                    myTopicTextView.setText(myTasksAdapter.getItem(position).getTask_topic());
                }
                if (myTasksAdapter.getItem(position).getTask_content().equals("")) {
                    myContentTextView.setText("Без описания");
                } else {
                    myContentTextView.setText(myTasksAdapter.getItem(position).getTask_content());
                }
                if (myTasksAdapter.getItem(position).getDeadline().equals("")) {
                    myDeadlineTextView.setText("Без крайнего срока");
                } else {
                    myDeadlineTextView.setText(myTasksAdapter.getItem(position).getDeadline());
                }

                myNumberTextView.setText("#" + myTasksAdapter.getItem(position).getTask_id());

                myDoneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            sqLiteDatabase = comDBHelper.getWritableDatabase();

                            deleteTask(myTasksAdapter.getItem(position).getTask_id());
                            myTasks.remove(position);
                            myTasksAdapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Задача выполнена",
                                    Toast.LENGTH_SHORT).show();

                            comDBHelper.close();
                            myInfoDialog.cancel();

                        } catch (Exception e) {

                        }

                    }
                });

                myCloseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myInfoDialog.cancel();
                    }
                });

            }
        });

        myTaskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                myDeleteDialog.show();
                myDelCloseButton = myDeleteDialog.findViewById(R.id.my_del_close_btn);
                myDeleteButton = myDeleteDialog.findViewById(R.id.my_delete_btn);

                myDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            sqLiteDatabase = comDBHelper.getWritableDatabase();

                            deleteTask(myTasksAdapter.getItem(position).getTask_id());
                            Toast.makeText(getContext(), "Задача удалена",
                                    Toast.LENGTH_SHORT).show();

                            comDBHelper.close();
                        } catch (Exception e) {

                        }

                        myTasksAdapter.remove((MyTask) myTaskListView.getItemAtPosition(position));
                        myTasksAdapter.notifyDataSetChanged();

                        myDeleteDialog.cancel();

                    }
                });
                myDelCloseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        myDeleteDialog.cancel();

                    }
                });
                return true;
            }
        });
    }

    public void deleteTask(String id) {
        sqLiteDatabase.execSQL("DELETE FROM " + ComDBHelper.MY_TABLE_NAME + " WHERE id = " + id);
    }

    public void update(){
        try {
            sqLiteDatabase = comDBHelper.getReadableDatabase();

            Cursor c = sqLiteDatabase.query(ComDBHelper.MY_TABLE_NAME, null,
                    null, null,
                    null, null, null);

            if (c.moveToFirst()) {

                myTasks.clear();

                do {
                    int idColIndex = c.getColumnIndex(ComDBHelper.MY_COLUMN_ID);
                    int topicColIndex = c.getColumnIndex(ComDBHelper.MY_COLUMN_TOPIC);
                    int contentColIndex = c.getColumnIndex(ComDBHelper.MY_COLUMN_CONTENT);
                    int deadlineColIndex = c.getColumnIndex(ComDBHelper.MY_COLUMN_DEADLINE);

                    myTasks.add(new MyTask(c.getString(topicColIndex),
                            c.getString(contentColIndex),
                            c.getString(deadlineColIndex), c.getString(idColIndex)));

                    myTasksAdapter.notifyDataSetChanged();

                    if (idColIndex > 0) {

                        Toast.makeText(getContext(), "Ура, все хорошо!", Toast.LENGTH_SHORT).show();

                    } else if (idColIndex < 0) {

                        Toast.makeText(getContext(), "Что-то не так", Toast.LENGTH_SHORT).show();
                    }

                } while (c.moveToNext());
            } else
                c.close();
        } catch (Exception e) {

        }
    }
}

