package com.example.study.service;


import com.example.study.entity.Department;
import com.example.study.mapper.DepartmentMapper;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


public class TestDeptService  {

    @InjectMocks
    DepartmentService deptService = new DepartmentServiceImpl();

    @Mock
    DepartmentMapper departmentMapper;

    @BeforeEach
     void init(){
        MockitoAnnotations.initMocks(this);
        System.err.println("init() ......");
    }

    @Test
    void testDeptServiceLoad(){
        Department department = new Department();
        department.setId(4);
        department.setDepartmentname("策划部");
        BDDMockito.given(departmentMapper.selectByPrimaryKey(ArgumentMatchers.anyInt()))
                .willReturn(department);
        Department dept = deptService.getById(4);
        MatcherAssert.assertThat(4,Matchers.is(dept.getId()));
    }

    @Test
    void testDeptAdd(){
        Department department = new Department();
        department.setId(4);
        department.setDepartmentname("测试部");
        //测试void方法
        BDDMockito.doAnswer(new Answer<Department>() {
            @Override
            public Department answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                System.out.println("arguments = " + Arrays.asList(arguments));
                //这里可以拿到Service传过来的参数,拿到参数之后我们可以加自己的断言
                Department department2 = (Department) invocation.getArguments()[0];
                System.err.println(department2.getId());
                MatcherAssert.assertThat("Service有问题",4,Matchers.equalTo(department2.getId()));
                Assertions.assertThat(Matchers.notNullValue(arguments.getClass()));
                return null;
            }
        }).when(departmentMapper).insert(ArgumentMatchers.any(Department.class));
        deptService.add(department);
        Mockito.verify(departmentMapper,Mockito.times(1)).insert(ArgumentMatchers.any(Department.class));
    }

    @Test
    public void testUpdate(){
        Department department = new Department();
        department.setId(4);
        department.setDepartmentname("测试部");
        deptService.update(department);
        Mockito.verify(departmentMapper,Mockito.times(1)).updateByPrimaryKey(department);
    }

    @Test
    public void testDelete() {
        deptService.deleteById(1);
        Mockito.verify(departmentMapper, Mockito.times(1)).deleteByPrimaryKey(ArgumentMatchers.anyInt());
    }
}
