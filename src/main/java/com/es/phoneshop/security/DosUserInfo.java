package com.es.phoneshop.security;

import java.util.Date;

public class DosUserInfo {
    private Date dateOfLastVisit;
    private long visitsCounter;
    private boolean isBlocked;

    public DosUserInfo(Date date, long counter) {
        this.dateOfLastVisit = date;
        this.visitsCounter = counter;
        this.isBlocked = false;
    }

    public Date getDateOfLastVisit() {
        return dateOfLastVisit;
    }

    public void setDateOfLastVisit(Date dateOfLastVisit) {
        this.dateOfLastVisit = dateOfLastVisit;
    }

    public long getVisitsCounter() {
        return visitsCounter;
    }

    public void setVisitsCounter(long visitsCounter) {
        this.visitsCounter = visitsCounter;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public void incrementVisitsCounter() {
        visitsCounter++;
    }
}
