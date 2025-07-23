package com.coursera.utils;

public class Course {
    private String title;
    private String rating;
    private String duration;

    public Course() {
        this.title = "";
        this.rating = "";
        this.duration = "";
    }

    public Course(String title, String rating, String duration) {
        this.title = title;
        this.rating = rating;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", rating='" + rating + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
