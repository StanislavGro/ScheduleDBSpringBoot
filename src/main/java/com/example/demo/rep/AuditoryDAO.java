package com.example.demo.rep;

import com.example.demo.entity.response.Auditory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoryDAO extends CrudRepository<Auditory, Integer> {
    void deleteById(Long auditoryID);
}