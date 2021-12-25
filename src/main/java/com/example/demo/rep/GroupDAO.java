package com.example.demo.rep;

import com.example.demo.entity.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupDAO extends CrudRepository<Group, Integer> {}