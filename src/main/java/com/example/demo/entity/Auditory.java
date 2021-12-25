package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auditory_table")
public class Auditory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String auditory;

    @OneToMany(mappedBy = "auditory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Schedule> scheduleList;

    public Auditory(){}

    public Auditory(int id, String auditory){
        this.id = id;
        this.auditory = auditory;
        scheduleList = new ArrayList<>();
    }

    public Auditory(String auditory){
        this.auditory = auditory;
        scheduleList = new ArrayList<>();
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getAuditory() {
        return auditory;
    }

    public void setAuditory(String auditory) {
        this.auditory = auditory;
    }

    public void addSchedule(Schedule schedule){
        schedule.setAuditory(this);
        scheduleList.add(schedule);
    }

    public void removeSchedule(Schedule schedule){
        scheduleList.remove(schedule);
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @Override
    public String toString() {
        return auditory;
    }

}
