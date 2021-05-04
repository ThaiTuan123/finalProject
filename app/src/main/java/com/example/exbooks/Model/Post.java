package com.example.exbooks.Model;

import com.google.firebase.database.ServerValue;

import java.util.Date;


public class Post {

    private String description;
    private String imageurl;
    private String postid;
    private String publisher;

    private String category;



    private String title;
    private String author;

    private String  date;
    private String time;



    public Post() {

    }


    public Post(String description, String imageurl, String postid, String publisher, String date, String time,String category,String title, String author) {
        this.description = description;
        this.imageurl = imageurl;
        this.postid = postid;
        this.publisher = publisher;
        this.category = category;

        this.title = title;
        this.author = author;

        this.date = date;
        this.time = time;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

//    public Long getTimeStamp() {
////        return timeStamp;
////    }
////
////    public void setTimeStamp(Long timeStamp) {
////        this.timeStamp = timeStamp;
////    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}