package com.example.study.service;

import com.example.study.entity.Department;

public interface DepartmentService {

    public void add(Department department);

    public void deleteById(Integer id);

    public void update(Department department);

    public Department getById(Integer id);
}
