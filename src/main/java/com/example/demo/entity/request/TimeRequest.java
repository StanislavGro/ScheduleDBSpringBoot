package com.example.demo.entity.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class TimeRequest {
    private String timeStart;
    private String timeEnd;
}
