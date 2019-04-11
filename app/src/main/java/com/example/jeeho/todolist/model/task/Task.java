package com.example.jeeho.todolist.model.task;

import java.util.Date;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Task {

    @SerializedName("label")
    private String label;

    @SerializedName("description")
    private String description;

    @SerializedName("expire")
    private String expireDate;


    public Task(){};

    public Task(String label, String description, String expireDate)
    {
        this.label = label;
        this.description=description;
        this.expireDate=expireDate;

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

}
