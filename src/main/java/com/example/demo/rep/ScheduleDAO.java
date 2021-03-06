package com.example.demo.rep;

import com.example.demo.entity.response.Schedule;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleDAO extends CrudRepository<Schedule, Integer> {
    void deleteById(Long scheduleID);
}