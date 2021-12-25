package com.example.demo.rep;

import com.example.demo.entity.Schedule;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleDAO extends CrudRepository<Schedule, Integer> {}