package com.sharma.tushar.attendencesimple.Data;

import java.io.Serializable;

public class SubjectPercent implements Serializable{

    private String subject;
    private int notAttended;
    private int total;
    private double percentage;

    public SubjectPercent(String subject, int notAttended, int total) {
        this.subject = subject;
        this.notAttended = notAttended;
        this.total = total;
        this.percentage = setPercentage();
    }

    private double setPercentage() {
        return ((total - notAttended)/(double)total) * 100;
    }

    public String getSubject() {
        return subject;
    }

    public double getPercentage() {
        return percentage;
    }

    public int getTotal() {
        return total;
    }

    public int getNotAttended() {
        return notAttended;
    }
}
