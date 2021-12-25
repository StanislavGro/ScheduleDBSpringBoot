package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "day_table")
public class Day implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String day;

//    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    List<Schedule> scheduleList;

    public Day(){}

    public Day(String day){
        this.id = findId(day);
        this.day = day;
//        scheduleList = new ArrayList<>();
    }

    public Day(long id,String day){
        this.id = id;
        this.day = day;
//        scheduleList = new ArrayList<>();
    }

    public long findId(String day){
        switch (day){
            case "Понедельник":
                return 1l;
            case "Вторник":
                return 2l;
            case "Среда":
                return 3l;
            case "Четверг":
                return 4l;
            case "Пятница":
                return 5l;
            case "Суббота":
                return 6l;
            default:
                System.out.println("Ошибка! Такого дня нет!");
                return -1l;
        }
    }

    public void addSchedule(Schedule schedule){
        schedule.setDay(this);
//        scheduleList.add(schedule);
    }

//    public void removeSchedule(Schedule schedule){
//        scheduleList.remove(schedule);
//    }

    public long getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "Day{" +
                "id=" + id +
                ", day='" + day + '\'' +
                '}';
    }

}
