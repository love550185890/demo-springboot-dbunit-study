package com.example.study.mapper;

import com.example.study.TestBase;
import com.example.study.TestDbunitMainConfig;
import com.example.study.entity.Department;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;
import org.xml.sax.InputSource;
import java.io.*;
import java.sql.SQLException;

@SpringBootTest(classes = TestDbunitMainConfig.class)
public class TestDeptMapper extends TestBase {

    @Autowired
    WebApplicationContext context;


    @Autowired
    IDatabaseConnection dbunitConnection;

    /**
     * dbunit备份数据库
     * @throws Exception
     */
    //@Test
    void testBackup() {
        IDatabaseConnection connection = dbunitConnection;
        IDataSet dataSet = null;
        try {
            dataSet = connection.createDataSet(new String[]{"department"});
            FlatXmlDataSet.write(dataSet,new FileWriter(new File("src/main/resources/backup.xml")));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataSetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }

    /**
     * dbunit导入测试数据
     */
    //@Test
    void testImportTestData() {
        IDatabaseConnection connection = dbunitConnection;
        try {
            IDataSet dataSet = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream("src/main/resources/department.xml"))));
            DatabaseOperation.CLEAN_INSERT.execute(dbunitConnection,dataSet);
        } catch (DataSetException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DatabaseUnitException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

        }
    }

    /**
     * dbunit从备份文件中恢复数据库数据
     * @throws SQLException
     */
    //@Test
    void testResumeDB() {
        IDatabaseConnection connection = dbunitConnection;
        try {
            IDataSet dataSet = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream("src/main/resources/backup.xml"))));
            DatabaseOperation.CLEAN_INSERT.execute(dbunitConnection,dataSet);
        } catch (DataSetException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DatabaseUnitException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

        }
    }

    /**
     * 所有操作数据库的地方都需要: 先备份数据库，在导入测试数据
     */
    @BeforeEach
    void setup(){
        System.out.println("init ....备份数据...导入测试数据");
        testBackup();
        testImportTestData();
    }

    /**
     * 操作完数据库一定要从备份文件中恢复数据
     */
    @AfterEach
    void tearDown(){
        System.out.println("down.....恢复数据库数据");
        testResumeDB();
    }

    @Autowired
    DepartmentMapper departmentMapper;

    /**
     * 测试load方法
     */
    @Test
    void testMapperLoad(){
        //MockitoAnnotations.initMocks(this);
        Department department = departmentMapper.selectByPrimaryKey(3);
        Assertions.assertEquals(3,department.getId());
        Assertions.assertEquals("运营部",department.getDepartmentname());
    }

    /**
     * 测试添加方法
     */
    @Test
    void testMapperAdd(){
        Department department = new Department();
        department.setId(5);
        department.setDepartmentname("测试数据部");
        departmentMapper.insert(department);
        Department dept = departmentMapper.selectByPrimaryKey(5);
        MatcherAssert.assertThat("数据并没有插入",department.getId(),Matchers.is(dept.getId()));
    }

    @Test
    void testMapperDelete(){
        departmentMapper.deleteByPrimaryKey(3);
        Department department = departmentMapper.selectByPrimaryKey(3);
        Assert.assertNull("删除有问题",department);
    }

    @Test
    void testMapperUpdate(){
        Department department = new Department();
        department.setId(3);
        department.setDepartmentname("数据测试部");
        departmentMapper.updateByPrimaryKey(department);
        Department dept = departmentMapper.selectByPrimaryKey(3);
        MatcherAssert.assertThat("更新方法有问题","数据测试部",Matchers.equalTo(dept.getDepartmentname()));
    }
}
