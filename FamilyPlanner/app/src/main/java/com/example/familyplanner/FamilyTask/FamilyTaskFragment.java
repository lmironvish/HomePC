package com.example.familyplanner.FamilyTask;

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

public class FamilyTaskFragment extends Fragment {


    private ListView famTaskListView;
    private ImageButton famAddButton;
    private Button famConfirmButton, famCancelButton, famAcceptButton,
            famDeleteCancelButton, famDeleteButton, famCloseButton;
    private Dialog famAddDialog, famInfoDialog, famDeleteDialog;
    private EditText famTopicEditText, famContentEditText, famDeadlineEditText;
    private TextView famTopicTextView, famContentTextView, famDeadlineTextView, famNumberTextView;
    private ArrayList famTask;
    private FamilyTaskAdapter familyTaskAdapter;

    private ComDBHelper comDBHelper;
    private SQLiteDatabase sqLiteDatabase;
    private ContentValues contentValues;
    private Cursor cursor;


    private String id;

    public static final String DB_NAME = "sqlDB";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fam_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        famTask = new ArrayList<Object>();
        famTaskListView = getView().findViewById(R.id.fam_task_list);
        familyTaskAdapter = new FamilyTaskAdapter(getContext(), famTask);
        famTaskListView.setAdapter(familyTaskAdapter);

        contentValues = new ContentValues();
        comDBHelper = new ComDBHelper(getContext(), DB_NAME, null, 1);

        famAddDialog = new Dialog(getContext());
        famInfoDialog = new Dialog(getContext());
        famDeleteDialog = new Dialog(getContext());


        famDeleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        famInfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        famAddDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        famAddDialog.setContentView(R.layout.fam_task_add_dialog);
        famInfoDialog.setContentView(R.layout.fam_info_dialog);
        famDeleteDialog.setContentView(R.layout.fam_delete_dialog);


        famAddButton = getView().findViewById(R.id.addB);


        famAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sqLiteDatabase = comDBHelper.getWritableDatabase();


                famTopicEditText = famAddDialog.findViewById(R.id.fam_topic_et);
                famContentEditText = famAddDialog.findViewById(R.id.fam_content_et);
                famDeadlineEditText = famAddDialog.findViewById(R.id.fam_deadline);


                famAddDialog.show();

                famConfirmButton = famAddDialog.findViewById(R.id.fam_confirm_btn);
                famConfirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sqLiteDatabase = comDBHelper.getWritableDatabase();

                        contentValues = new ContentValues();

                        contentValues.put(ComDBHelper.FAM_COLUMN_TOPIC,
                                famTopicEditText.getText().toString());
                        contentValues.put(ComDBHelper.FAM_COLUMN_CONTENT,
                                famContentEditText.getText().toString());
                        contentValues.put(ComDBHelper.FAM_COLUMN_DEADLINE,
                                famDeadlineEditText.getText().toString());


                        id = String.valueOf(sqLiteDatabase.insert(ComDBHelper.FAM_TABLE_NAME,
                                null, contentValues));

                        if(Integer.parseInt(id) > 0){

                            Toast.makeText(getContext(), "Успешно",
                                    Toast.LENGTH_LONG).show();

                        }else if(Integer.parseInt(id) == -1){

                            Toast.makeText(getContext(), "Ошибка записи в базу данных",
                                    Toast.LENGTH_SHORT).show();
                        }



                        comDBHelper.close();

                        famTask.add(new FamilyTask(famTopicEditText.getText().toString(),
                                famContentEditText.getText().toString(),
                                famDeadlineEditText.getText().toString(), id));

                        familyTaskAdapter.notifyDataSetChanged();

                        famTopicEditText.setText(null);
                        famContentEditText.setText(null);
                        famDeadlineEditText.setText(null);

                        famAddDialog.cancel();
                    }
                });


                famCancelButton = famAddDialog.findViewById(R.id.fam_cancel_btn);
                famCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        comDBHelper.close();
                        famAddDialog.cancel();
                    }
                });
            }
        });


        famTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                famInfoDialog.show();

                famAcceptButton = famInfoDialog.findViewById(R.id.fam_accept_btn);
                famCloseButton = famInfoDialog.findViewById(R.id.fam_close_btn);

                famContentTextView = famInfoDialog.findViewById(R.id.fam_content_tv);
                famTopicTextView = famInfoDialog.findViewById(R.id.fam_topic_tv);
                famDeadlineTextView = famInfoDialog.findViewById(R.id.fam_deadline_tv);
                famNumberTextView = famInfoDialog.findViewById(R.id.fam_num_tv);

                if (familyTaskAdapter.getItem(position).getFam_task_topic().equals("")) {
                    famTopicTextView.setText("Без темы'");
                } else {
                    famTopicTextView.setText(familyTaskAdapter.getItem(position).getFam_task_topic());
                }
                if (familyTaskAdapter.getItem(position). getFam_task_content().equals("")) {
                    famContentTextView.setText("Без описания");
                } else {
                    famContentTextView.setText(familyTaskAdapter.getItem(position).getFam_task_content());
                }
                if (familyTaskAdapter.getItem(position).getFam_deadline().equals("")) {
                    famDeadlineTextView.setText("Без крайнего срока");
                } else {
                    famDeadlineTextView.setText(familyTaskAdapter.getItem(position).getFam_deadline());
                }

                famNumberTextView.setText("#" + familyTaskAdapter.getItem(position).getFam_task_num());


                famAcceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        sqLiteDatabase = comDBHelper.getWritableDatabase();


                        String topic = familyTaskAdapter.getItem(position).getFam_task_topic();
                        String content = familyTaskAdapter.getItem(position).getFam_task_content();
                        String deadline = familyTaskAdapter.getItem(position).getFam_deadline();

                        deleteTask(familyTaskAdapter.getItem(position).getFam_task_num());
                        famTask.remove(position);
                        familyTaskAdapter.notifyDataSetChanged();

                        contentValues.put(ComDBHelper.MY_COLUMN_TOPIC, topic);
                        contentValues.put(ComDBHelper.MY_COLUMN_CONTENT, content);
                        contentValues.put(ComDBHelper.MY_COLUMN_DEADLINE, deadline);

                        sqLiteDatabase.insert(ComDBHelper.MY_TABLE_NAME,
                                null, contentValues);

                        comDBHelper.close();

                        sqLiteDatabase = comDBHelper.getWritableDatabase();


                        contentValues.put(ComDBHelper.IP_COLUMN_TOPIC, topic);
                        contentValues.put(ComDBHelper.IP_COLUMN_CONTENT, content);
                        contentValues.put(ComDBHelper.IP_COLUMN_DEADLINE, deadline);

                        sqLiteDatabase.insert(ComDBHelper.IP_TABLE_NAME,
                                null, contentValues);

                        comDBHelper.close();
                        famInfoDialog.cancel();
                    }
                });
                famCloseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        famInfoDialog.cancel();
                    }
                });
            }
        });


        famTaskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                famDeleteDialog.show();

                famDeleteCancelButton = famDeleteDialog.findViewById(R.id.fam_del_close_btn);
                famDeleteButton = famDeleteDialog.findViewById(R.id.fam_delete_btn);

                famDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            sqLiteDatabase = comDBHelper.getWritableDatabase();
                            deleteTask(familyTaskAdapter.getItem(position).getFam_task_num());
                            Toast.makeText(getContext(), "Задача удалена",
                                    Toast.LENGTH_SHORT).show();

                            comDBHelper.close();
                        } catch (Exception e) {
                        }

                        familyTaskAdapter.remove((FamilyTask)
                                famTaskListView.getItemAtPosition(position));
                        familyTaskAdapter.notifyDataSetChanged();

                        famDeleteDialog.cancel();

                    }
                });
                famDeleteCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        famDeleteDialog.cancel();
                    }
                });
                return true;
            }
        });
    }


    public void deleteTask(String id) {

        sqLiteDatabase.execSQL("DELETE FROM " + ComDBHelper.FAM_TABLE_NAME + " WHERE id = " + id);
    }

    public void update() {
        try {

            sqLiteDatabase = comDBHelper.getReadableDatabase();

            Cursor c = sqLiteDatabase.query(ComDBHelper.FAM_TABLE_NAME, null,
                    null, null,
                    null, null, null);
            if (c.moveToFirst()) {

                famTask.clear();
                do {

                    int idColIndex = c.getColumnIndex(ComDBHelper.FAM_COLUMN_ID);
                    int topicColIndex = c.getColumnIndex(ComDBHelper.FAM_COLUMN_TOPIC);
                    int contentColIndex = c.getColumnIndex(ComDBHelper.FAM_COLUMN_CONTENT);
                    int deadlineColIndex = c.getColumnIndex(ComDBHelper.FAM_COLUMN_DEADLINE);

                    famTask.add(new FamilyTask(c.getString(topicColIndex),
                            c.getString(contentColIndex),
                            c.getString(deadlineColIndex), c.getString(idColIndex)));

                    familyTaskAdapter.notifyDataSetChanged();
                } while (c.moveToNext());
            } else

                c.close();
            comDBHelper.close();
        } catch (Exception e) {

        }
    }

}