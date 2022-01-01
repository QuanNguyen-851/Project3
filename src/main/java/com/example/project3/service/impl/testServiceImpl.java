package com.example.project3.service.impl;

import com.example.project3.model.entity.testEntity;
import com.example.project3.repository.itest;
import com.example.project3.service.testService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class testServiceImpl implements testService {
  @Autowired
  private itest repository;
  @Override
  public Long getbyid() {
    String str = repository.getAll().toString();
    List<testEntity> list = repository.getAll();
    testEntity entity = list.get(1);
    System.out.println(entity.getName());
    System.out.println("str" + repository.countAllByName("quan"));
    return 1L;
  }
}
