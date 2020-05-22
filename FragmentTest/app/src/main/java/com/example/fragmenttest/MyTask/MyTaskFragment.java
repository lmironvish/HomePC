package com.example.fragmenttest.MyTask;

    import android.app.Dialog;
    import android.content.ContentValues;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ListView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import com.example.fragmenttest.ComDBHelper;
    import com.example.fragmenttest.R;

    import java.util.ArrayList;

public class MyTaskFragment extends Fragment {

    //Назначаем кнопки, вью'шки, диалоги, arraylist и адаптер
    //Assign buttons, views, dialogs, arraylist and adapter

    private ListView my_task_lv;
    private Button my_add_btn_a, my_confirm_btn,
            my_cancel_btn, my_close_btn,
            my_del_close_btn, my_delete_btn, my_done_btn;
    private Dialog my_add_dialog, my_info_dialog, my_delete_dialog;
    private EditText my_topic_et, my_content_et, my_deadline_et;
    private TextView my_topic_tv, my_content_tv, my_deadline_tv, my_num_tv;
    private ArrayList myTasks;
    private MyTasksAdapter myTasksAdapter;

    ComDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    ContentValues cv;

    private String id;


    public static final String MY_TABLE_NAME = "MyTask";


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myTasks = new ArrayList<>();

        my_task_lv = getView().findViewById(R.id.my_task_list);
        myTasksAdapter = new MyTasksAdapter(getContext(), myTasks);
        my_task_lv.setAdapter(myTasksAdapter);


        myDBHelper = new ComDBHelper(getContext(), MY_TABLE_NAME, null, 1);


        Log.d("LOG_TAG", "--- Insert in mytable: ---");

        try {
            sqlDB = myDBHelper.getReadableDatabase();

            Cursor c = sqlDB.query(myDBHelper.MY_TABLE_NAME, null,
                    null, null,
                    null, null, null);

            if (c.moveToFirst()) {

                // определяем номера столбцов по имени в выборке

                do {
                    int idColIndex = c.getColumnIndex(myDBHelper.MY_COLUMN_ID);
                    int topicColIndex = c.getColumnIndex(myDBHelper.MY_COLUMN_TOPIC);
                    int contentColIndex = c.getColumnIndex(myDBHelper.MY_COLUMN_CONTENT);
                    int deadlineColIndex = c.getColumnIndex(myDBHelper.MY_COLUMN_DEADLINE);

                    // получаем значения по номерам столбцов и пишем все в лог
                    myTasks.add(new MyTask(c.getString(topicColIndex),
                            c.getString(contentColIndex),
                            c.getString(deadlineColIndex), c.getString(idColIndex)));

                    myTasksAdapter.notifyDataSetChanged();

                    if(idColIndex > 0){

                        Toast.makeText(getContext(), "Ура, все хорошо!", Toast.LENGTH_SHORT).show();

                    }else if(idColIndex < 0){

                        Toast.makeText(getContext(), "Что-то не так", Toast.LENGTH_SHORT).show();
                    }

                } while (c.moveToNext());
            } else
                c.close();
        } catch (Exception e) {

        }

        my_add_dialog = new Dialog(getContext());
        my_delete_dialog = new Dialog(getContext());
        my_info_dialog = new Dialog(getContext());

        my_add_dialog.setContentView(R.layout.my_task_add_dialog);
        my_delete_dialog.setContentView(R.layout.my_delete_dialog);
        my_info_dialog.setContentView(R.layout.my_info_dialog);

        my_info_dialog.setTitle("Информация");
        my_delete_dialog.setTitle("Удалить задачу?");
        my_add_dialog.setTitle("Добавить задачу");

        my_add_btn_a = getView().findViewById(R.id.addA);

        my_add_btn_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                my_add_dialog.show();

                my_topic_et = my_add_dialog.findViewById(R.id.my_topic_et);
                my_content_et = my_add_dialog.findViewById(R.id.my_content_et);
                my_deadline_et = my_add_dialog.findViewById(R.id.my_deadline);
                my_confirm_btn = my_add_dialog.findViewById(R.id.my_confirm_btn);
                my_cancel_btn = my_add_dialog.findViewById(R.id.my_cancel_btn);


                my_confirm_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sqlDB = myDBHelper.getWritableDatabase();

                        cv = new ContentValues();

                        cv.put(ComDBHelper.MY_COLUMN_TOPIC, my_topic_et.getText().toString());
                        cv.put(ComDBHelper.MY_COLUMN_CONTENT, my_content_et.getText().toString());
                        cv.put(ComDBHelper.MY_COLUMN_DEADLINE, my_deadline_et.getText().toString());

                        id = String.valueOf(sqlDB.insert(myDBHelper.MY_TABLE_NAME, null, cv));

                        if(Integer.parseInt(id) > 0){

                            Toast.makeText(getContext(), "Успешно",
                                    Toast.LENGTH_SHORT).show();

                        }else if(Integer.parseInt(id) == -1){

                            Toast.makeText(getContext(), "Ошибка записи в базу данных",
                                    Toast.LENGTH_SHORT).show();

                        }

                        myDBHelper.close();

                        myTasks.add(new MyTask(my_topic_et.getText().toString(),
                                my_content_et.getText().toString(),
                                my_deadline_et.getText().toString(), id));

                        myTasksAdapter.notifyDataSetChanged();

                        id = null;

                        my_topic_et.setText(null);
                        my_content_et.setText(null);
                        my_deadline_et.setText(null);

                        my_add_dialog.cancel();
                    }
                });
                my_cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        my_add_dialog.cancel();

                    }
                });

            }
        });


        my_task_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                my_info_dialog.show();

                //TextViews
                my_topic_tv = my_info_dialog.findViewById(R.id.my_topic_tv);
                my_content_tv = my_info_dialog.findViewById(R.id.my_content_tv);
                my_deadline_tv = my_info_dialog.findViewById(R.id.my_deadline_tv);
                my_num_tv = my_info_dialog.findViewById(R.id.my_num_tv);

                //Buttons
                my_close_btn = my_info_dialog.findViewById(R.id.close_btn);
                my_done_btn = my_info_dialog.findViewById(R.id.my_accept_btn);


                my_topic_tv.setText(myTasksAdapter.getItem(position).getTask_topic());
                my_content_tv.setText(myTasksAdapter.getItem(position).getTask_content());
                my_deadline_tv.setText(myTasksAdapter.getItem(position).getDeadline());
                my_num_tv.setText(myTasksAdapter.getItem(position).getTask_id());

                my_done_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        my_task_lv.getItemAtPosition(position);
                        my_info_dialog.cancel();
                    }
                });

                my_close_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        my_info_dialog.cancel();
                    }
                });

            }
        });

        my_task_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                my_delete_dialog.show();
                my_del_close_btn = my_delete_dialog.findViewById(R.id.my_del_close_btn);
                my_delete_btn = my_delete_dialog.findViewById(R.id.my_delete_btn);

                my_delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            sqlDB = myDBHelper.getWritableDatabase();

                            //Удаляем задачу из базы данных

                            deleteTask(myTasksAdapter.getItem(position).getTask_id());
                            Toast.makeText(getContext(), "Задача удалена", Toast.LENGTH_SHORT).show();

                            //Закрываем базу данных

                            myDBHelper.close();
                        }catch (Exception e){

                        }

                        myTasksAdapter.remove((MyTask) my_task_lv.getItemAtPosition(position));
                        myTasksAdapter.notifyDataSetChanged();


                        my_delete_dialog.cancel();

                    }
                });
                my_del_close_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        my_delete_dialog.cancel();

                    }
                });
                return true;
            }
        });
    }

    public void deleteTask(String id) {
        sqlDB.execSQL("DELETE FROM " + MY_TABLE_NAME + " WHERE id = " + id);
    }
}

