package com.example.sardorbek.ptototypes.Model.new_requests;

/**
 * Created by sardorbek on 4/24/18.
 */

public class Sender {
    public String to;
    public Notification notification;



    public Sender(String to, Notification notification) {
        this.to= to;
        this.notification = notification;
    }

}
