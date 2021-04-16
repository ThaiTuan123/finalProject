package com.example.exbooks.Model;

public class Comment {

    private String id;
    private String comment;
    private String publisher;
    private String date;



    public Comment() {
    }

    public Comment(String id, String comment, String publisher,String date) {
        this.id = id;
        this.comment = comment;
        this.publisher = publisher;
        this.date = date;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}