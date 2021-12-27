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
    @GetMapping("/schedules/get")
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
    @PostMapping("/schedules/post")
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
                group.setId(gr.getId());
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
                auditory.setId(au.getId());
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
    public ResponseEntity deleteGroup(@RequestParam Long groupID) {
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
    public ResponseEntity deleteAuditory(@RequestParam Long auditoryID) {
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
    @DeleteMapping("/schedules/{scheduleID}")
    @ApiOperation("Removing schedule")
    public ResponseEntity deleteSchedule(@RequestParam Long scheduleID) {
        try {
            scheduleDAO.deleteById(scheduleID);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @CrossOrigin
    @PutMapping("/auditories")
    @ApiOperation("Updating auditory")
    public void updateAuditory(long id, String auditoryStr) throws Exception {
        List<Auditory> auditories = new ArrayList<>();
        auditoryDAO.findAll().forEach(auditories::add);
        Auditory auditory = null;

        for (Auditory au : auditories) {
            if (au.getId() == id) {
                auditory = new Auditory(id, auditoryStr);
                auditoryDAO.save(auditory);
                return;
            }
        }

        if (auditory == null)
            throw new Exception("Didn't find such auditory");

    }

    @CrossOrigin
    @PutMapping("/groups")
    @ApiOperation("Updating group")
    public void updateGroup(long id, String groupStr) throws Exception {
        List<Group> groups = new ArrayList<>();
        groupDAO.findAll().forEach(groups::add);
        Group group = null;

        for (Group gr : groups) {
            if (gr.getId() == id) {
                group = new Group(id, groupStr);
                groupDAO.save(group);
                return;
            }
        }

        if (group == null)
            throw new Exception("Didn't find such group");

    }

    @CrossOrigin
    @PutMapping("/schedules/update")
    @ApiOperation("Updating schedule")
    public void updateSchedule(long id, int week, String day, String timeStart, String timeEnd, String group, String auditory) throws Exception {
        List<Schedule> schedules = new ArrayList<>();
        scheduleDAO.findAll().forEach(schedules::add);
        Schedule schedule = null;

        for (Schedule sc : schedules) {
            if (sc.getId() == id) {
                Day day1 = new Day(day);
                Time time = new Time(timeStart, timeEnd);
                Group group1 = new Group(group);
                List<Group> groups = new ArrayList<>();
                groupDAO.findAll().forEach(groups::add);
                for (Group gr : groups) {
                    if (gr.getGroup().equals(group1.getGroup()))
                        group1.setId(gr.getId());
                }
                List<Auditory> auditories = new ArrayList<>();
                auditoryDAO.findAll().forEach(auditories::add);
                Auditory auditory1 = new Auditory(auditory);
                for (Auditory au : auditories) {
                    if (au.getAuditory().equals(auditory1.getAuditory()))
                        auditory1.setId(au.getId());
                }
                schedule = new Schedule(id, week, day1, time, group1, auditory1);
                scheduleDAO.save(schedule);
            }
        }

        if (schedule == null)
            throw new Exception("Didn't find such schedule");

    }

}