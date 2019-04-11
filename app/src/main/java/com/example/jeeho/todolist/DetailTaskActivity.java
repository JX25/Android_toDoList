package com.example.jeeho.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);

        TextView taskTitle = (TextView) findViewById(R.id.titleBox);
        TextView taskDate = (TextView) findViewById(R.id.expDateBox);
        TextView taskDescription=(TextView) findViewById(R.id.descriptionBox);

        //przyjęcie danych z mainActivity
        if(getIntent().hasExtra("taskLabel")){
            taskTitle.setText(getIntent().getExtras().getString("taskLabel"));
        }
        if (getIntent().hasExtra("expireDate")) {
            taskDate.setText(getIntent().getExtras().getString("expireDate"));
        }
        if (getIntent().hasExtra("taskDescription")) {
            taskDescription.setText(getIntent().getExtras().getString("taskDescription"));
        }

    }
}
