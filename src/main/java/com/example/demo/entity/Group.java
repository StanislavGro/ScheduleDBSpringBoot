package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groupp_table")
public class Group implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "groupp")
    private String group;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    List<Schedule> scheduleList;

    public Group(){}

    public Group(long id, String group){
        this.id = id;
        this.group = group;
        scheduleList = new ArrayList<>();
    }

    public Group(String group){
        this.group = group;

        scheduleList = new ArrayList<>();
    }

    public void addSchedule(Schedule schedule){
        schedule.setGroup(this);
        scheduleList.add(schedule);
    }

    public void removeSchedule(Schedule schedule){
        scheduleList.remove(schedule);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", group='" + group + '\'' +
                '}';
    }
}
