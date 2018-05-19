package com.example.sardorbek.ptototypes.Model.new_requests;

import java.util.List;

/**
 * Created by sardorbek on 4/1/18.
 */

public class Requests {
    private String userName;
    private String floor;
    private String total;
    private String status;



    public Requests() {}

    public Requests(String userName, String floor, String total, String status) {
        this.userName = userName;
        this.floor = floor;
        this.total = total;
        this.status = status;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
