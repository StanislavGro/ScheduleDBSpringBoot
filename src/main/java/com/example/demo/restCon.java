package com.example.demo;

import com.example.demo.entity.*;
import com.example.demo.rep.AuditoryDAO;
import com.example.demo.rep.GroupDAO;
import com.example.demo.rep.ScheduleDAO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("schedules")
public class restCon {
    @Autowired
    private ScheduleDAO scheduleDAO;

    @Autowired
    private AuditoryDAO auditoryDAO;

    @Autowired
    private GroupDAO groupDAO;

    @CrossOrigin
    @GetMapping("/schedules/get")
    @ApiOperation("Getting schedules")
    public List<Schedule> getSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        scheduleDAO.findAll().forEach(schedules::add);

        return schedules;
    }

    @CrossOrigin
    @GetMapping("/auditories/get")
    @ApiOperation("Getting auditories")
    public List<Auditory> getAuditories() {
        List<Auditory> auditories = new ArrayList<>();
        auditoryDAO.findAll().forEach(auditories::add);

        return auditories;
    }

    @CrossOrigin
    @GetMapping("/groups/get")
    @ApiOperation("Getting groups")
    public List<Group> getGroups() {
        List<Group> groups = new ArrayList<>();
        groupDAO.findAll().forEach(groups::add);

        return groups;
    }

    @CrossOrigin
    @PostMapping("/auditories/post")
    @ApiOperation("Adding a new auditory")
    public Auditory addAuditory(String auditoryStr) {
        Auditory auditory = new Auditory(auditoryStr);
        auditoryDAO.save(auditory);
        return auditory;
    }

    @CrossOrigin
    @PostMapping("/groups/post")
    @ApiOperation("Adding a new group")
    public Group addGroup(String groupStr) {
        Group group = new Group(groupStr);
        groupDAO.save(group);
        return group;
    }

    @CrossOrigin
    @PostMapping("/schedules/post")
    @ApiOperation("Adding a new schedule")
    public Schedule addSchedule(int week, String day, String timeStart, String timeEnd, String group, String auditory) {
        Day day1 = new Day(day);
        Time time = new Time(timeStart,timeEnd);
        Group group1 = new Group(group);
        List<Group> groups = new ArrayList<>();
        groupDAO.findAll().forEach(groups::add);
        for(Group gr: groups){
            if(gr.getGroup().equals(group1.getGroup()))
                group1.setId(gr.getId());
        }
        List<Auditory> auditories = new ArrayList<>();
        auditoryDAO.findAll().forEach(auditories::add);
        Auditory auditory1 = new Auditory(auditory);
        for(Auditory au : auditories){
            if(au.getAuditory().equals(auditory1.getAuditory()))
                auditory1.setId(au.getId());
        }
        Schedule schedule = new Schedule(week,day1,time, group1, auditory1);
        scheduleDAO.save(schedule);
        return schedule;
    }

}