package com.example.study.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.study.TestBase;
import com.example.study.entity.Department;
import com.example.study.service.DepartmentService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


public class TestController extends TestBase {
    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    DepartmentService departmentService;

    @Test
    void testLoad(){
        Department dept = new Department();
        dept.setId(1);
        dept.setDepartmentname("mock 部门");
        Mockito.when(departmentService.getById(ArgumentMatchers.anyInt())).thenReturn(dept);
        Department department = testRestTemplate.getForObject("/dept/1", Department.class);
        Mockito.verify(departmentService,Mockito.times(1)).getById(ArgumentMatchers.anyInt());
        System.out.println("department = " + department);
        MatcherAssert.assertThat("controller有问题",dept.getId(),Matchers.is(department.getId()));
        MatcherAssert.assertThat("controller有问题",dept.getDepartmentname(),Matchers.equalTo(department.getDepartmentname()));
    }

    @Test
    void testAdd(){
        Department department = new Department();
        department.setId(1);
        department.setDepartmentname("mock 部门");
        //void 方法的测试有所不同
        Mockito.doAnswer(new Answer<Department>() {
            @Override
            public Department answer(InvocationOnMock invocation) throws Throwable {
                Department dept = (Department) invocation.getArguments()[0];
                System.err.println(dept);
                MatcherAssert.assertThat("Controller有问题",1,Matchers.is(dept.getId()));
                MatcherAssert.assertThat("controller有问题","mock 部门",Matchers.equalTo(dept.getDepartmentname()));
                return null;
            }
        }).when(departmentService).add(ArgumentMatchers.any(Department.class));
        //设置Http请求头使用spring包装的实现
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Object toJSON = JSONObject.toJSON(department);
        System.out.println("wrap = "+ toJSON.toString());
        //设置spring包装的HttpEntity,把HttpHeaders和数据一起放进来
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(toJSON,headers);
        //发送Post请求
        ResponseEntity<String> mapResponseEntity = testRestTemplate.postForEntity("/dept", httpEntity,String.class);
        Assert.assertTrue(mapResponseEntity.getStatusCode().is2xxSuccessful());
        System.out.println("mapResponseEntity = " + mapResponseEntity);
    }

    @Test
    void testDelete(){
        Mockito.doAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Integer arg = (Integer) invocation.getArguments()[0];
                Assert.assertNotNull(arg);
                Assert.assertThat(arg,Matchers.is(1));
                return null;
            }
        }).when(departmentService).deleteById(ArgumentMatchers.anyInt());
        testRestTemplate.delete("/dept/1");
        Mockito.verify(departmentService,Mockito.times(1)).deleteById(ArgumentMatchers.anyInt());
    }

    @Test
    public void testUpdate(){
        Department department = new Department();
        department.setId(1);
        department.setDepartmentname("测试部");
        //打桩departmentService
        Mockito.doAnswer(new Answer<Department>() {
            @Override
            public Department answer(InvocationOnMock invocation) throws Throwable {
                Department dept = (Department)invocation.getArguments()[0];
                Assert.assertThat(dept.getId(),Matchers.is(1));
                Assert.assertThat(dept.getDepartmentname(),Matchers.equalTo("测试部"));
                return null;
            }
        }).when(departmentService).update(ArgumentMatchers.any(Department.class));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Object jsonDepartment = JSONObject.toJSON(department);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(jsonDepartment, headers);
        testRestTemplate.put("/dept",httpEntity);
        Mockito.verify(departmentService,Mockito.times(1)).update(ArgumentMatchers.any(Department.class));
    }
}
