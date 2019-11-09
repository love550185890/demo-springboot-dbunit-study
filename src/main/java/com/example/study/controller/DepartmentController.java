package com.example.study.controller;

import com.example.study.entity.Department;
import com.example.study.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/dept/{id}")
    public Department getById(@PathVariable Integer id){
        Assert.notNull(id,"访问的url:/dept/id ---> id不能为空");
        return departmentService.getById(id);
    }

    @PostMapping("/dept")
    public ResponseEntity add(@RequestBody Department department){
        departmentService.add(department);
        ResponseEntity responseEntity = new ResponseEntity("插入成功",HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("/dept/{id}")
    public ResponseEntity testUpdate(@PathVariable Integer id){
        departmentService.deleteById(id);
        ResponseEntity responseEntity = new ResponseEntity("delete成功",HttpStatus.OK);
        return responseEntity;
    }

    @PutMapping("/dept")
    public ResponseEntity testUpdate(@RequestBody Department department){
        departmentService.update(department);
        ResponseEntity responseEntity = new ResponseEntity("update成功",HttpStatus.OK);
        return responseEntity;
    }
}
