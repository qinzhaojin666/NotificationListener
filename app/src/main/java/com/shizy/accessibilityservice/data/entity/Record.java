package com.shizy.accessibilityservice.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "record")
public class Record {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String text;
    private Date createAt;

    public Record(String title, String text) {
        this.title = title;
        this.text = text;
        this.createAt = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
