package com.example.familyplanner.InProcess;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.familyplanner.ComDBHelper;
import com.example.familyplanner.R;

import java.util.ArrayList;

public class InProcessFragment extends Fragment {

    private ArrayList<InProcessTask> ipTasks;

    private ListView ipTaskListView;
    private InProcessAdapter inProcessAdapter;
    private Dialog ipInfoDialog;
    private TextView ipTopicTextView, ipContentTextView, ipDeadlineTextView, ipNumberTextView;
    private Button ipDoneButton, ipCloseButton;

    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;
    private ComDBHelper comDBHelper;


    public static final String DB_NAME = "sqlDB";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.in_process_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ipTasks = new ArrayList<>();

        ipTaskListView = getView().findViewById(R.id.in_process_list);
        inProcessAdapter = new InProcessAdapter(getContext(), ipTasks);
        ipTaskListView.setAdapter(inProcessAdapter);


        comDBHelper = new ComDBHelper(getContext(), DB_NAME, null, 1);

        ipInfoDialog = new Dialog(getContext());
        ipInfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ipInfoDialog.setContentView(R.layout.ip_info_dialog);

        ipTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                ipInfoDialog.show();

                ipTopicTextView = ipInfoDialog.findViewById(R.id.ip_topic_tv);
                ipContentTextView = ipInfoDialog.findViewById(R.id.ip_content_tv);
                ipDeadlineTextView = ipInfoDialog.findViewById(R.id.ip_deadline_tv);
                ipNumberTextView = ipInfoDialog.findViewById(R.id.ip_num_tv);

                if (inProcessAdapter.getItem(position).getIp_task_topic().equals("")) {
                    ipTopicTextView.setText("Без темы'");
                } else {
                    ipTopicTextView.setText(inProcessAdapter.getItem(position).getIp_task_topic());
                }
                if (inProcessAdapter.getItem(position).getIp_task_content().equals("")) {
                    ipContentTextView.setText("Без описания");
                } else {
                    ipContentTextView.setText(inProcessAdapter.getItem(position).getIp_task_content());
                }
                if (inProcessAdapter.getItem(position).getIp_deadline().equals("")) {
                    ipDeadlineTextView.setText("Без крайнего срока");
                } else {
                    ipDeadlineTextView.setText(inProcessAdapter.getItem(position).getIp_deadline());
                }

                ipNumberTextView.setText("#" + inProcessAdapter.getItem(position).getIp_num_task());

                ipDoneButton = ipInfoDialog.findViewById(R.id.ip_done_btn);
                ipDoneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sqLiteDatabase = comDBHelper.getWritableDatabase();

                        deleteTask(inProcessAdapter.getItem(position).getIp_num_task());
                        ipTasks.remove(position);
                        inProcessAdapter.notifyDataSetChanged();
                        comDBHelper.close();

                        Toast.makeText(getContext(), "Задача выпонена",
                                Toast.LENGTH_SHORT).show();
                        ipInfoDialog.cancel();
                    }
                });

                ipCloseButton = ipInfoDialog.findViewById(R.id.ip_close_btn);
                ipCloseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ipInfoDialog.cancel();
                    }
                });
            }
        });

    }


    public void deleteTask(String id) {
        sqLiteDatabase.execSQL("DELETE FROM " + ComDBHelper.IP_TABLE_NAME + " WHERE id = " + id);
    }

    public void update(){
        try {
            sqLiteDatabase = comDBHelper.getReadableDatabase();

            cursor = sqLiteDatabase.query(ComDBHelper.IP_TABLE_NAME, null,
                    null, null,
                    null, null, null);

            if (cursor.moveToFirst()) {
                    ipTasks.clear();
                do {

                    int idColIndex = cursor.getColumnIndex(ComDBHelper.IP_COLUMN_ID);
                    int topicColIndex = cursor.getColumnIndex(ComDBHelper.IP_COLUMN_TOPIC);
                    int contentColIndex = cursor.getColumnIndex(ComDBHelper.IP_COLUMN_CONTENT);
                    int deadlineColIndex = cursor.getColumnIndex(ComDBHelper.IP_COLUMN_DEADLINE);

                    ipTasks.add(new InProcessTask(cursor.getString(topicColIndex),
                            cursor.getString(contentColIndex),
                            cursor.getString(deadlineColIndex), cursor.getString(idColIndex)));

                    inProcessAdapter.notifyDataSetChanged();

                    if (idColIndex > 0) {

                        Toast.makeText(getContext(), "Ура, все хорошо!", Toast.LENGTH_SHORT).show();

                    } else if (idColIndex < 0) {

                        Toast.makeText(getContext(), "Что-то не так", Toast.LENGTH_SHORT).show();
                    }

                } while (cursor.moveToNext());
            } else
                cursor.close();
        } catch (Exception e) {

        }

    }
}


