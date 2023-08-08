import java.sql.Date;
import java.time.LocalDate;

public class Course {  //pojoClass
  private int id;
  private String course_name;
  private int credit;
  private double course_fee;
  private LocalDate start_date;
  private LocalDate finish_date;

    public Course() {
    }

    public Course(String course_name, int credit, double course_fee, LocalDate start_date, LocalDate finish_date) {
        this.course_name = course_name;
        this.credit = credit;
        this.course_fee = course_fee;
        this.start_date = start_date;
        this.finish_date = finish_date;
    }

    public Course(int id, String course_name, int credit, double course_fee, LocalDate start_date, LocalDate finish_date) {
        //bu constructor sadece planned courses için gerekli. id serial,unıque bir değer manuel işlem yapılmamalı
        this.id=id;
        this.course_name = course_name;
        this.credit = credit;
        this.course_fee = course_fee;
        this.start_date = start_date;
        this.finish_date = finish_date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public double getCourse_fee() {
        return course_fee;
    }

    public void setCourse_fee(double course_fee) {
        this.course_fee = course_fee;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getFinish_date() {
        return finish_date;
    }

    public void setFinish_date(LocalDate finish_date) {
        this.finish_date = finish_date;
    }


    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", course_name='" + course_name + '\'' +
                ", credit=" + credit +
                ", course_fee=" + course_fee +
                ", start_date=" + start_date +
                ", finish_date=" + finish_date +
                '}';
    }
}
