package com.sharma.tushar.attendencesimple.Data;

import java.io.Serializable;

public class SubjectPercent implements Serializable{

    private String subject;
    private int attended;
    private int total;
    private double percentage;

    public SubjectPercent(String subject, int notAttended, int total) {
        this.subject = subject;
        this.total = total;
        this.attended = total - notAttended;
        this.percentage = setPercentage();
    }

    private double setPercentage() {
        return Math.round (((attended/(double)total) * 100)*100.0)/100.0;
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

    public int getAttended() {
        return attended;
    }
}
