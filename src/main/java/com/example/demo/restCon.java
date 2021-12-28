package com.example.demo;

import com.example.demo.entity.request.AuditoryRequest;
import com.example.demo.entity.request.GroupRequest;
import com.example.demo.entity.request.ScheduleRequest;
import com.example.demo.entity.response.*;
import com.example.demo.rep.AuditoryDAO;
import com.example.demo.rep.GroupDAO;
import com.example.demo.rep.ScheduleDAO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    @GetMapping
    @ApiOperation("Getting schedules")
    public List<Schedule> getSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        scheduleDAO.findAll().forEach(schedules::add);

        return schedules;
    }

    @CrossOrigin
    @GetMapping("/auditories")
    @ApiOperation("Getting auditories")
    public List<Auditory> getAuditories() {
        List<Auditory> auditories = new ArrayList<>();
        auditoryDAO.findAll().forEach(auditories::add);

        return auditories;
    }

    @CrossOrigin
    @GetMapping("/groups")
    @ApiOperation("Getting groups")
    public List<Group> getGroups() {
        List<Group> groups = new ArrayList<>();
        groupDAO.findAll().forEach(groups::add);

        return groups;
    }

    @CrossOrigin
    @PostMapping("/auditories")
    @ApiOperation("Adding a new auditory")
    public Auditory addAuditory(@RequestBody AuditoryRequest auditoryReq) {

        return auditoryDAO.save(new Auditory(auditoryReq.getAuditory()));
    }

    @CrossOrigin
    @PostMapping("/groups")
    @ApiOperation("Adding a new group")
    public Group addGroup(@RequestBody GroupRequest groupReq) {
        return groupDAO.save(new Group(groupReq.getGroup()));
    }

    @CrossOrigin
    @PostMapping
    @ApiOperation("Adding a new schedule")
    public Schedule addSchedule(@RequestBody ScheduleRequest scheduleReq) {
        boolean isGroup = false, isAudit = false;
        Day day = new Day(scheduleReq.getDay().getDay());
        Time time = new Time(scheduleReq.getTime().getTimeStart(), scheduleReq.getTime().getTimeEnd());
        List<Group> groups = new ArrayList<>();
        Group group = new Group(scheduleReq.getGroup().getGroup());
        groupDAO.findAll().forEach(groups::add);
        for (Group gr : groups) {
            if (gr.getGroup().equals(group.getGroup())) {
                group = gr;
                isGroup = true;
            }
        }
        if(!isGroup){
            groupDAO.save(group);
        }
        Auditory auditory = new Auditory(scheduleReq.getAuditory().getAuditory());
        List<Auditory> auditories = new ArrayList<>();
        auditoryDAO.findAll().forEach(auditories::add);
        for (Auditory au : auditories) {
            if (au.getAuditory().equals(auditory.getAuditory())) {
                auditory = au;
                isAudit = true;
            }
        }
        if(!isAudit){
            auditoryDAO.save(auditory);
        }
        return scheduleDAO.save(new Schedule(scheduleReq.getWeek(), day, time,group,auditory));
    }

    @Transactional
    @CrossOrigin
    @DeleteMapping("/groups/{groupID}")
    @ApiOperation("Removing group")
    public ResponseEntity deleteGroup(@PathVariable Long groupID) {
        try {
            groupDAO.deleteById(groupID);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Transactional
    @CrossOrigin
    @DeleteMapping("/auditories/{auditoryID}")
    @ApiOperation("Removing auditory")
    public ResponseEntity deleteAuditory(@PathVariable Long auditoryID) {
        try {
            auditoryDAO.deleteById(auditoryID);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Transactional
    @CrossOrigin
    @DeleteMapping("/{scheduleID}")
    @ApiOperation("Removing schedule")
    public ResponseEntity deleteSchedule(@PathVariable Long scheduleID) {
        try {
            scheduleDAO.deleteById(scheduleID);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @CrossOrigin
    @PutMapping("/auditories/{auditoryID}")
    @ApiOperation("Updating auditory")
    public ResponseEntity updateAuditory(@PathVariable Long auditoryID, @RequestBody AuditoryRequest auditoryRequest) {

        try {
            List<Auditory> auditories = new ArrayList<>();
            auditoryDAO.findAll().forEach(auditories::add);
            Auditory newAuditory = null;

            for (Auditory au : auditories) {
                if (au.getId() == auditoryID) {
                    newAuditory = au;
                    newAuditory.setAuditory(auditoryRequest.getAuditory());
//                    newAuditory = new Auditory(id, auditoryRequest.getAuditory());
                    auditoryDAO.save(newAuditory);
                    return ResponseEntity.status(HttpStatus.OK).build();
                }
            }

            throw new Exception("Didn't find such auditory");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @CrossOrigin
    @PutMapping("/groups/{groupID}")
    @ApiOperation("Updating group")
    public ResponseEntity updateGroup(@PathVariable Long groupID, @RequestBody GroupRequest groupRequest){

        try {
            List<Group> groups = new ArrayList<>();
            groupDAO.findAll().forEach(groups::add);
            Group newGroup = null;

            for (Group gr : groups) {
                if (gr.getId() == groupID) {
                    newGroup = gr;
                    newGroup.setGroup(groupRequest.getGroup());
//                    newGroup = new Group(id, groupRequest.getGroup());
                    groupDAO.save(newGroup);
                    return ResponseEntity.status(HttpStatus.OK).build();
                }
            }

            throw new Exception("Didn't find such group");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }

    }

    @CrossOrigin
    @PutMapping("/{scheduleID}")
    @ApiOperation("Updating schedule")
    public ResponseEntity updateSchedule(@PathVariable Long scheduleID, @RequestBody ScheduleRequest scheduleReq){

        try {

            List<Schedule> schedules = new ArrayList<>();
            scheduleDAO.findAll().forEach(schedules::add);
            Schedule schedule = null;

            for (Schedule sc : schedules) {
                if (sc.getId() == scheduleID) {

                    boolean isGroup = false, isAudit = false;
                    Day day = new Day(scheduleReq.getDay().getDay());
                    Time time = new Time(scheduleReq.getTime().getTimeStart(), scheduleReq.getTime().getTimeEnd());
                    List<Group> groups = new ArrayList<>();
                    Group group = new Group(scheduleReq.getGroup().getGroup());
                    groupDAO.findAll().forEach(groups::add);
                    for (Group gr : groups) {
                        if (gr.getGroup().equals(group.getGroup())) {
                            group = gr;
                            group.setGroup(gr.getGroup());
//                            group.setId(gr.getId());
                            isGroup = true;
                        }
                    }
                    if (!isGroup) {
                        groupDAO.save(group);
                    }
                    Auditory auditory = new Auditory(scheduleReq.getAuditory().getAuditory());
                    List<Auditory> auditories = new ArrayList<>();
                    auditoryDAO.findAll().forEach(auditories::add);
                    for (Auditory au : auditories) {
                        if (au.getAuditory().equals(auditory.getAuditory())) {
                            auditory = au;
                            auditory.setAuditory(au.getAuditory());
//                            auditory.setId(au.getId());
                            isAudit = true;
                        }
                    }
                    if (!isAudit) {
                        auditoryDAO.save(auditory);
                    }

                    scheduleDAO.save(new Schedule(scheduleID, scheduleReq.getWeek(), day, time,group,auditory));
                    return ResponseEntity.status(HttpStatus.OK).build();

                }
            }

            throw new Exception("Didn't find such schedule");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }

    }

}