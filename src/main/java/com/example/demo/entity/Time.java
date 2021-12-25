package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "time_table")
public class Time implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "time_start")
    private String timeStart;

    @Column(name = "time_end")
    private String timeEnd;

//    @OneToMany(mappedBy = "time", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    List<Schedule> scheduleList;

    public Time(){}

    public Time(String timeStart, String timeEnd){
        this.id = findId(timeStart, timeEnd);
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
//        scheduleList = new ArrayList<>();

    }

    public Time(long id, String timeStart, String timeEnd){
        this.id = id;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
//        scheduleList = new ArrayList<>();

    }

    public long findId(String timeStart, String timeEnd){
        String commonStr = timeStart+"-"+timeEnd;
        switch (commonStr){
            case "8:30-10:00":
                return 1l;
            case "10:15-11:45":
                return 2l;
            case "12:00-13:30":
                return 3l;
            case "14:00-15:30":
                return 4l;
            case "15:45-17:15":
                return 5l;
            case "17:30-19:00":
                return 6l;
            case "19:15-20:45":
                return 7l;
            default:
                System.out.println("Ошибка! Такого промежутка времени нет!!");
                return -1l;
        }

    }

    public void addSchedule(Schedule schedule){
        schedule.setTime(this);
//        scheduleList.add(schedule);
    }

//    public void removeSchedule(Schedule schedule){
//        scheduleList.remove(schedule);
//    }

    public long getId() {
        return id;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public String toString() {
        return "Time{" +
                "id=" + id +
                ", timeStart='" + timeStart + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                '}';
    }
}
