package com.example.finalproject.models;

import java.util.Objects;

public class Assignment {
    private String id;
    private String studentId;
    private String subject;
    private String desc;
    private long dueDate;
    private boolean finished;

    public Assignment(String studentId, String subject, String desc, long dueDate, boolean finished) {
        this.studentId = studentId;
        this.subject = subject;
        this.desc = desc;
        this.dueDate = dueDate;
        this.finished = finished;
    }
    public Assignment(String id, String studentId, String subject, String desc, long dueDate, boolean finished) {
        this.id = id;
        this.studentId = studentId;
        this.subject = subject;
        this.desc = desc;
        this.dueDate = dueDate;
        this.finished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Assignment() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
