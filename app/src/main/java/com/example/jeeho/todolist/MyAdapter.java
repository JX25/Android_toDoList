package com.example.jeeho.todolist;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.jeeho.todolist.model.task.Task;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Task> taskList;
    private OnItemClickListener onItemClickListener;
    private RecyclerView mRecycleView;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onLongItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener= listener;
    }



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView taskTitle;
        public TextView taskDate;
        public RadioButton deleteTask;
        public ViewHolder(View v, final OnItemClickListener onItemClickListener) {
            super(v);
            taskTitle =(TextView) v.findViewById(R.id.taskTitle);
            taskDate = (TextView) v.findViewById(R.id.taskDate);
            deleteTask = (RadioButton) v.findViewById(R.id.deleteTask);

            deleteTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener!=null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            onItemClickListener.onDeleteClick(position);
                        }
                    }
                }
            });

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener!=null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(onItemClickListener!=null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            onItemClickListener.onLongItemClick(position);
                        }
                    }
                    return true;
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Task> taskList, RecyclerView mRecycleView) {
        if(taskList.isEmpty()) this.taskList = new ArrayList<>();
        else this.taskList = taskList;
        this.mRecycleView = mRecycleView;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(view,onItemClickListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.taskTitle.setText(taskList.get(position).getLabel());
        holder.taskDate.setText(taskList.get(position).getExpireDate());

        String currentDate = getTodayDate();
        String [] currentDateElements = currentDate.split("-");
        String [] taskDateElements = taskList.get(position).getExpireDate().split("-");

        if(Integer.valueOf(currentDateElements[2]) > Integer.valueOf(taskDateElements[2]))
        {
            holder.taskTitle.setTextColor(Color.RED);
            holder.taskDate.setTextColor(Color.GRAY);
        }
        else if( Integer.valueOf(currentDateElements[2]) == Integer.valueOf(taskDateElements[2])
                && Integer.valueOf(currentDateElements[1]) > Integer.valueOf(taskDateElements[1]))
        {
            holder.taskTitle.setTextColor(Color.RED);
            holder.taskDate.setTextColor(Color.GRAY);
        }
        else if( Integer.valueOf(currentDateElements[2]) == Integer.valueOf(taskDateElements[2])
                && Integer.valueOf(currentDateElements[1]) == Integer.valueOf(taskDateElements[1])
                && Integer.valueOf(currentDateElements[0]) > Integer.valueOf(taskDateElements[0])
                ){
            holder.taskTitle.setTextColor(Color.RED);
            holder.taskDate.setTextColor(Color.GRAY);
        }
        else{
            holder.taskTitle.setTextColor(Color.BLACK);
            holder.taskDate.setTextColor(Color.BLACK);
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    private String getTodayDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }
}