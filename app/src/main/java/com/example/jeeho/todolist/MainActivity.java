package com.example.jeeho.todolist;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.example.jeeho.todolist.model.task.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Task> taskList;
    private Button addNewTaskButton;
    private SharedPreferences mPrefs;

    //private AdapterView.OnItemClickListener onItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecycleView);
        addNewTaskButton = (Button) findViewById(R.id.addButton);

        loadTaskList();
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(taskList,mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent startIntent = new Intent(getApplicationContext(), DetailTaskActivity.class);
                startIntent.putExtra("taskLabel",taskList.get(position).getLabel());
                startIntent.putExtra("expireDate",taskList.get(position).getExpireDate());
                startIntent.putExtra("taskDescription",taskList.get(position).getDescription());
                startActivityForResult(startIntent,1);
            }

            @Override
            public void onDeleteClick(int position) {
                taskList.remove(position);
                //mAdapter.onBindViewHolder(null,position);
                mAdapter.notifyItemChanged(position);
                mAdapter.notifyDataSetChanged();
                saveTaskList();
            }

            @Override
            public void onLongItemClick(int position) {
                Intent startIntent = new Intent(getApplicationContext(), AddTaskActivity.class);
                startIntent.putExtra("taskLabel",taskList.get(position).getLabel());
                startIntent.putExtra("taskDate",taskList.get(position).getExpireDate());
                startIntent.putExtra("taskDescription",taskList.get(position).getDescription());
                startIntent.putExtra("isNew",false);
                startIntent.putExtra("position",position);
                startActivityForResult(startIntent,1);
            }
        });


        addNewTaskButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),AddTaskActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }


    public void loadPreperedTasks()
    {
        taskList = new ArrayList<>();
        taskList.add(new Task("Zadanie 1.","zadanie 1\n zadanie31531 1","11-5-2016"));
        taskList.add(new Task("Zadanie 2.","zadanie 22\n zadanie 351311","16-12-2019"));
        taskList.add(new Task("Zadanie 3.","zadanie 311\n zadan315ie 1","4-1-2020"));
        taskList.add(new Task("Zadanie 4.","zadanie 1153\n zadani135e 1","10-12-2012"));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
        startActivityForResult(intent,1);
    }

    private void saveTaskList() {
        mPrefs = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadTaskList(){
        mPrefs = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("task list", null);
        Type type = new TypeToken<ArrayList<Task>>(){}.getType();
        taskList = gson.fromJson(json,type  );

        if( taskList == null ){
            loadPreperedTasks();
        }
    }


    //dodawanie/edycja
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if(!data.hasExtra("isNew")){
                    int position = Integer.parseInt(data.getStringExtra("position"));
                    taskList.get(position).setLabel(data.getStringExtra("taskLabel"));
                    taskList.get(position).setExpireDate(data.getStringExtra("taskDate"));
                    taskList.get(position).setDescription(data.getStringExtra("taskDescription"));
                    //mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    saveTaskList();
                }
                else{
                    Task task = new Task();
                    task.setLabel(data.getStringExtra("taskLabel"));
                    task.setDescription(data.getStringExtra("taskDescription"));
                    task.setExpireDate(data.getStringExtra("taskDate"));
                    taskList.add(task);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mAdapter.notifyItemInserted(taskList.size() - 1);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mAdapter);
                    saveTaskList();

                }

            }
        }
    }
}