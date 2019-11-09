## springboot + 原生dbunit做单元测试Demo

### 单元测试

    1、使用TestRestTemplate测试controller层
    2、使用Mokito模拟测试Service层
    3、使用dbunit作为测试时数据库隔离的工具配合mapper测试
    4、断言加入了hamcrest表达式
    5、分别对MVC各层都进行了单元测试
    6、并没有使用db-unit-maven插件->此插件"有毒"

### dbunit很古老的一个东西

    dbunit主要用来隔离测试数据，防止我们写的断言由于其他数据库操作导致出错，用来测试mapper
    backup.xml是数据库备份文件；
    department.xml是测试数据；
    
    
    使用dbunit原生API的主要原因：
    我觉得没必要用别人开源的东西，我觉得自己手写也可以解决，而且代码量是真的不多(核心代码不超过10行)；
    最主要的原因是用别人的东西还得去学习，增加成本，
    关键是别人写的东西有BUG的话更新也很慢毕竟不是大公司出品的没有保障，
    就像dbunit maven插件一样;
### Department sql脚本
    
    CREATE TABLE `department` ( 
    `id` INT ( 11 ) NOT NULL AUTO_INCREMENT, 
    `departmentName` VARCHAR ( 255 ) DEFAULT NULL, 
    PRIMARY KEY ( `id` ) ) 
    ENGINE = INNODB AUTO_INCREMENT = 6 DEFAULT CHARSET = utf8;

## QQ:550185890
