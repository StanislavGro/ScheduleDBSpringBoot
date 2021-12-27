package com.example.demo.rep;

import com.example.demo.entity.response.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupDAO extends CrudRepository<Group, Integer> {
    void deleteById(Long groupID);
}