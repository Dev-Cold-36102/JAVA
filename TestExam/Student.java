package TestExam;

import java.io.Serializable;

public class Student implements Serializable {
    private String id;
    private String email;
    private String fullName;
    private int score;

    public Student(String id, String fullName, String email, int score) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.score = score;
    }

    public void setid(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getid() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getfullName() {
        return this.fullName;
    }

    public int getScore() {
        return this.score;
    }

    public void displayStudent() {
        System.out.printf("%-15s%-30s%-35s%-10s\n", this.id, this.fullName, this.email, this.score);
    }
}

