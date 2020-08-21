package com.es.phoneshop.security;

import java.util.Date;

public class DosUserInfo {
    private Date date;
    private long counter;
    private boolean isBlocked;

    public DosUserInfo(Date date, long counter) {
        this.date = date;
        this.counter = counter;
        this.isBlocked = false;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public void incrementCounter() {
        counter++;
    }
}
