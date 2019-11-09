package com.example.study.service;

import com.example.study.entity.Department;
import com.example.study.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentMapper deptMapper;

    @Override
    public void add(Department department) {
        deptMapper.insert(department);
    }

    @Override
    public void deleteById(Integer id) {
        deptMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Department department) {
        deptMapper.updateByPrimaryKey(department);
    }

    @Override
    public Department getById(Integer id) {
        return deptMapper.selectByPrimaryKey(id);
    }
}
