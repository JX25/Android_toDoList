package com.example.jeeho.todolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jeeho.todolist.model.task.Task;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    private List<Task> taskList;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private boolean edit =true;
    private int position;
    private Button displayDate;
    private EditText label;
    private TextView expDate;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        label = (EditText) findViewById(R.id.createTitle);
        expDate = (TextView) findViewById(R.id.createDate);
        description = (EditText) findViewById(R.id.createDescription);
        displayDate = (Button) findViewById(R.id.button);
        //expDate.setText(getTodayDate());
        if(getIntent().hasExtra("taskLabel")){
            label.setText(getIntent().getExtras().getString("taskLabel"));
        }
        if (getIntent().hasExtra("taskDate")) {
            expDate.setText(getIntent().getExtras().getString("taskDate"));
        }
        if (getIntent().hasExtra("taskDescription")) {
            description.setText(getIntent().getExtras().getString("taskDescription"));
        }
        if(getIntent().hasExtra("isNew")){
            edit=getIntent().getExtras().getBoolean("isNew");
        }
        if(getIntent().hasExtra("position")){
            position=getIntent().getExtras().getInt("position");
        }


        displayDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar cal =Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddTaskActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month=month+1;
                String date = day +"-" + month +"-" +year;
                expDate.setText(date);
            }
        };

        expDate.setText("5-6-2018");
        if(edit==true)
        {
            Button addTask = (Button) findViewById(R.id.taskButton);
            addTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("taskLabel", label.getText().toString());
                    intent.putExtra("taskDescription", description.getText().toString());
                    intent.putExtra("taskDate", expDate.getText().toString());
                    intent.putExtra("isNew",true);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            });
        }
        else{
            Button addTask = (Button) findViewById(R.id.taskButton);
            TextView infoBox = (TextView) findViewById(R.id.infoBox);
            infoBox.setText("Edit Task");
            addTask.setText("Edit");
            addTask.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view){
                    Intent intent = new Intent();
                    intent.putExtra("taskLabel", label.getText().toString());
                    intent.putExtra("taskDescription", description.getText().toString());
                    intent.putExtra("taskDate", expDate.getText().toString());
                    intent.putExtra("position",Integer.toString(position));
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            });
        }
    }


    private String getTodayDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }

}

