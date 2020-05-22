package com.example.fragmenttest.FamilyTask;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

public class FamilyTaskFragment extends Fragment {

    //Назначаем кнопки, вью'шки, диалоги, arraylist и адаптер
    //Assign buttons, views, dialogs, arraylist and adapter

    private ListView fam_task_lv;
    private Button fam_add_btn_b, fam_confirm_btn, fam_cancel_btn, fam_accept_btn,
            fam_del_cancel_btn, fam_del_btn, fam_close_btn;
    private Dialog fam_add_dialog, fam_info_dialog, fam_delete_dialog;
    private EditText fam_topic_et, fam_content_et, fam_deadline_et;
    private TextView fam_topic_tv, fam_content_tv, fam_deadline_tv, fam_num_tv;
    private ArrayList famTask;
    private FamilyTaskAdapter familyTaskAdapter;

    //Для работы с БД: хэлпер и сама БД
    //For work with DB: helper & and database itself

    ComDBHelper famDBHelper;
    SQLiteDatabase sqlDB;
    ContentValues cv;

    String id;

    //Название таблицы в базе данных записываем в константу
    //The name of the table in the database is written in a constant

    public static final String FAM_TABLE_NAME = "FamilyTask";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fam_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Определяем arraylist, адаптеры и вьюшки
        //Define arraylist, adapters and views

        famTask = new ArrayList<Object>();
        fam_task_lv = getView().findViewById(R.id.fam_task_list);
        familyTaskAdapter = new FamilyTaskAdapter(getContext(), famTask);
        fam_task_lv.setAdapter(familyTaskAdapter);

        //Определяем хелпер
        //Define helper

        famDBHelper = new ComDBHelper(getContext(), FAM_TABLE_NAME, null, 1);

        //Начинаем считывание и запись в адаптер тех задач, которые уже есть в базе
        //Start reading and writing to the adapter those tasks that are already in the database



        try {

            //Открываем базу данных для записи
            //Opening the database for writing

            sqlDB = famDBHelper.getReadableDatabase();

            //Назначим курсор для перемещения по записям БД
            //Assign the cursor to move through the database records

            Cursor c = sqlDB.query(famDBHelper.FAM_TABLE_NAME, null,
                    null, null,
                    null, null, null);

            if (c.moveToFirst()) {

                do {

                    //Получаем значения по номерам столбцов и добавляем в задачу
                    //Get values by column numbers and add them to the task

                    int idColIndex = c.getColumnIndex(famDBHelper.FAM_COLUMN_ID);
                    int topicColIndex = c.getColumnIndex(famDBHelper.FAM_COLUMN_TOPIC);
                    int contentColIndex = c.getColumnIndex(famDBHelper.FAM_COLUMN_CONTENT);
                    int deadlineColIndex = c.getColumnIndex(famDBHelper.FAM_COLUMN_DEADLINE);


                    famTask.add(new FamilyTask(c.getString(topicColIndex),
                            c.getString(contentColIndex),
                            c.getString(deadlineColIndex), c.getString(idColIndex)));


                    //Сохраняем изменения
                    //Save changes

                    familyTaskAdapter.notifyDataSetChanged();

                    //Для уверенности добавим тост
                    //To make sure we add a toast

                    Toast.makeText(getContext(), "Ура, все хорошо!", Toast.LENGTH_SHORT).show();

                } while (c.moveToNext());

            } else
                //Закрываем курсор и БД
                //Closing the cursor and database

                c.close();
            famDBHelper.close();
        }catch (Exception e){

        }
                //Определяем диалоговые окна
                //Define a dialog box

        fam_add_dialog = new Dialog(getContext());
        fam_info_dialog = new Dialog(getContext());
        fam_delete_dialog = new Dialog(getContext());

        //Обозначаем разметку диалоговых окон
        //Denoting the layout of dialog boxes

        fam_add_dialog.setContentView(R.layout.fam_task_add_dialog);
        fam_info_dialog.setContentView(R.layout.fam_info_dialog);
        fam_delete_dialog.setContentView(R.layout.fam_delete_dialog);

        //Добавляем заголовоки для диалоговых окон
        //Adding headers for dialog boxes

        fam_add_dialog.setTitle("Добавить задачу");
        fam_info_dialog.setTitle("Информация о задании");
        fam_delete_dialog.setTitle("Удалить задачу?");

        //Определяем кнопку добавления задачи и ставим листенер
        //Define the add task button and set the listener

        fam_add_btn_b = getView().findViewById(R.id.addB);

        fam_add_btn_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Открываем базу данных для записи
                //Opening the database for writing

                sqlDB = famDBHelper.getWritableDatabase();

                //Ищем и определяем в разметке диалогового окна edittext и кнопки
                //Search for and define buttons and edittext in the markup of the dialog box

                fam_topic_et = fam_add_dialog.findViewById(R.id.fam_topic_et);
                fam_content_et = fam_add_dialog.findViewById(R.id.fam_content_et);
                fam_deadline_et = fam_add_dialog.findViewById(R.id.fam_deadline);

                //Запускаем окно
                //Launch the window

                fam_add_dialog.show();

                //Определяем и ставим на кнопку добавления листенер
                //Define the add button and set the listener

                fam_confirm_btn = fam_add_dialog.findViewById(R.id.fam_confirm_btn);

                fam_confirm_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Открываем базу данных для записи
                        //Opening the database for writing

                        sqlDB = famDBHelper.getWritableDatabase();

                        //Объект, помогающий получить записи из базы данных
                        //Object that helps you get records from the database

                        cv = new ContentValues();

                        //Берем значения полей из edittext и вставляем в базу данных
                        //Taking the field values from the edittext and inserting them into the database

                        cv.put(ComDBHelper.FAM_COLUMN_TOPIC, fam_topic_et.getText().toString());
                        cv.put(ComDBHelper.FAM_COLUMN_CONTENT, fam_content_et.getText().toString());
                        cv.put(ComDBHelper.FAM_COLUMN_DEADLINE, fam_deadline_et.getText().toString());

                        //Для уверенности будем брать ID поля, в который помещена
                        // задача и поместим в переменную, а затем выведем тост с информацией

                        //To be sure, we will take the ID of the field in which it is placed
                        // task and put it in a variable, and then output a toast with information

                        id = String.valueOf(sqlDB.insert(famDBHelper.FAM_TABLE_NAME, null, cv));
                        Toast.makeText(getContext(), "Успешно! rowID: " + id,
                                Toast.LENGTH_LONG).show();

                        //Закрываем базу данных
                        //Closing database

                        famDBHelper.close();

                        //Добавляем задачу
                        //Adding a task

                        famTask.add(new FamilyTask(fam_topic_et.getText().toString(),
                                fam_content_et.getText().toString(),
                                fam_deadline_et.getText().toString(), id));

                        //Сохраняем изменения
                        //Save changes

                        familyTaskAdapter.notifyDataSetChanged();

                        //Очищаем edittext от текста задачи
                        //Clearing edittext from the task text

                        fam_topic_et.setText("");
                        fam_content_et.setText("");
                        fam_deadline_et.setText("");

                        //Закрывем диалоговое окно
                        //Closing the dialog box

                        fam_add_dialog.cancel();
                    }
                });

                //Определяем и ставим на кнопку отмены листенер
                //Define the cancel button and set the listener

                fam_cancel_btn = fam_add_dialog.findViewById(R.id.fam_cancel_btn);

                fam_cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Закрываем БД и диалоговое окно
                        //Closing the database and the dialog box

                        famDBHelper.close();
                        fam_add_dialog.cancel();

                    }
                });
            }
        });

        //Устанавливаем листенер на листвью,
        // чтобы открывалось диалоговое окно, в котором представлена информация по заданию

        //Installing the listener on the listview to open
        //a dialog box that provides information about the task

        fam_task_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Запускаем диалоговое окно, определяем все textview и кнопки,
                // на textview помещаем все данные из задачи

                //Launch the dialog box and define all textview and buttons,
                // putting all data from the issue on textview

                fam_info_dialog.show();

                fam_accept_btn = fam_info_dialog.findViewById(R.id.fam_accept_btn);
                fam_close_btn = fam_info_dialog.findViewById(R.id.fam_close_btn);

                fam_content_tv = fam_info_dialog.findViewById(R.id.fam_content_tv);
                fam_topic_tv = fam_info_dialog.findViewById(R.id.fam_topic_tv);
                fam_deadline_tv = fam_info_dialog.findViewById(R.id.fam_deadline_tv);
                fam_num_tv = fam_info_dialog.findViewById(R.id.fam_num_tv);

                fam_content_tv.setText(familyTaskAdapter.getItem(position).getFam_task_content());
                fam_topic_tv.setText(familyTaskAdapter.getItem(position).getFam_task_topic());
                fam_deadline_tv.setText(familyTaskAdapter.getItem(position).getFam_deadline());
                fam_num_tv.setText("#" + familyTaskAdapter.getItem(position).getFam_task_num());

                //Определим кнопку для того, чтобы забрать задачу на выполнение
                //Define a button to pick up the task for completion

                fam_accept_btn.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

                     }
                 });
                fam_close_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fam_info_dialog.cancel();
                    }
                });
            }
        });

        //На listview устанавливаем листенер для долгого нажатия
        //On listview set the listener for a long click

        fam_task_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                //Устанавиливаем диалоговое окно для удаления задачи
                //Setting up a dialog box for deleting a task

                fam_delete_dialog.show();

                //Две кнопки: "закрыть диалоговое окно" и "удалить задачу"
                //Two buttons: "close dialog box" and " delete task"
                fam_del_cancel_btn = fam_delete_dialog.findViewById(R.id.fam_del_close_btn);
                fam_del_btn = fam_delete_dialog.findViewById(R.id.fam_delete_btn);

                //Листенер для кнопки удаления
                //Listener for the delete button

                fam_del_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        try {
                            //Открываем базу

                            sqlDB = famDBHelper.getWritableDatabase();

                            //Удаляем задачу из базы данных

                            deleteTask(familyTaskAdapter.getItem(position).getFam_task_num());
                            Toast.makeText(getContext(), "Задача удалена", Toast.LENGTH_SHORT).show();

                            //Закрываем базу данных

                            famDBHelper.close();

                        }catch (Exception e) {

                        }

                        //Удаляем задачу из listview и сохраняем изменения

                        familyTaskAdapter.remove((FamilyTask) fam_task_lv.getItemAtPosition(position));
                        familyTaskAdapter.notifyDataSetChanged();

                        //Закрываем диалог
                        fam_delete_dialog.cancel();

                    }
                });
                fam_del_cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fam_delete_dialog.cancel();
                    }
                });
                return true;
            }
        });
    }
    public void deleteTask(String id){
        sqlDB.execSQL("DELETE FROM " + FAM_TABLE_NAME + " WHERE id = " + id);
    }
}