package com.example.demo.entity.request;

import com.example.demo.entity.response.Auditory;
import com.example.demo.entity.response.Day;
import com.example.demo.entity.response.Group;
import com.example.demo.entity.response.Time;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequest {

    private int week;
    private DayRequest day;
    private TimeRequest time;
    private GroupRequest group;
    private AuditoryRequest auditory;

}
