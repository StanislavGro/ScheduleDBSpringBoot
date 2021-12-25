package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "schedule")
public class Schedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int week;

    @ManyToOne
    @JoinColumn(name = "day")
    @JsonManagedReference
    private Day day;

    @ManyToOne
    @JoinColumn(name = "time")
    @JsonManagedReference
    private Time time;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "groupp")
    @JsonManagedReference
    private Group group;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "auditory")
    @JsonManagedReference
    private Auditory auditory;

    public Schedule() {
    }

    public Schedule(long id, int week, Day day, Time time, Group group, Auditory auditory){
        this.id = id;
        this.week = week;
        this.day = day;
        this.time = time;
        this.group = group;
        this.auditory = auditory;
    }

    public Schedule(int week, Day day, Time time, Group group, Auditory auditory){
        this.week = week;
        this.day = day;
        this.time = time;
        this.group = group;
        this.auditory = auditory;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Auditory getAuditory() {
        return auditory;
    }

    public void setAuditory(Auditory auditory) {
        this.auditory = auditory;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", week=" + week +
                ", day=" + day +
                ", time=" + time +
                ", group=" + group +
                ", auditory=" + auditory +
                '}';
    }
}
