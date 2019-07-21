package com.topica.zyot.shyn.model;

public class Student {
    private String id;
    private String fullname;
    private String className;
    private String countryside;
    private double cpa;

    public Student(String id, String fullname, String className, String countryside, double cpa) {
        this.id = id;
        this.fullname = fullname;
        this.className = className;
        this.countryside = countryside;
        this.cpa = cpa;
    }

    public Student(double cpa) {
        this.id = "12345678";
        this.fullname = "ahihi";
        this.className = "ICT";
        this.countryside = "Ha Noi";
        this.cpa = cpa;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCountryside() {
        return countryside;
    }

    public void setCountryside(String countryside) {
        this.countryside = countryside;
    }

    public double getCpa() {
        return cpa;
    }

    public void setCpa(double cpa) {
        this.cpa = cpa;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Student{")
                .append("id='")
                .append(id)
                .append('\'')
                .append(", fullname='")
                .append(fullname)
                .append('\'')
                .append(", cpa=")
                .append(cpa)
                .append('}')
                .toString();
    }
}
