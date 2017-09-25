package com.studentinformationproj.adapter;

/**
 * Created by Srisht on 13-09-2017.
 * This class has the information of student
 * like ID, name, roll, class, subject and marks
 */

public class StudentInfoAdapter {

    private String stID,stName,stRoll,stClass,stSubject,stMarks;

    public StudentInfoAdapter(String studentID, String studentName,String studentRoll,String studentClass, String studentSubject,String studentMarks){
        this.stID = studentID;
        this.stName = studentName;
        this.stRoll = studentRoll;
        this.stClass = studentClass;
        this.stSubject = studentSubject;
        this.stMarks = studentMarks;
    }
    public StudentInfoAdapter(String studentName,String studentRoll,String studentClass, String studentSubject,String studentMarks){
        this.stName = studentName;
        this.stRoll = studentRoll;
        this.stClass = studentClass;
        this.stSubject = studentSubject;
        this.stMarks = studentMarks;
    }
    public StudentInfoAdapter(){
    }

    public String getStID() {
        return stID;
    }

    public void setStID(String stID) {
        this.stID = stID;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getStRoll() {
        return stRoll;
    }

    public void setStRoll(String stRoll) {
        this.stRoll = stRoll;
    }

    public String getStClass() {
        return stClass;
    }

    public void setStClass(String stClass) {
        this.stClass = stClass;
    }

    public String getStSubject() {
        return stSubject;
    }

    public void setStSubject(String stSubject) {
        this.stSubject = stSubject;
    }

    public String getStMarks() {
        return stMarks;
    }

    public void setStMarks(String stMarks) {
        this.stMarks = stMarks;
    }
}
