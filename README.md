# MixQuery
当前的存储引擎可谓百花齐放，层出不穷，不同的存储有其适用的场景和优势劣势。因此，在实际的业务场景中，不同特点的数据会存储到不同的存储引擎里。
然而异构的存储和数据源，却给分析查询带来了挑战，比如跨源查询，以及跨集群查询将变得困难，此外各种语法和查询方式，也让用户使用的成本较高。

因此，需要一款支持混合查询的引擎。当前，已经有很多开源或者未开源的框架，比如Presto, Drill, SparkSQL, QuickSQL, SuperSQL等，感兴趣的可以取了解下。我这里并不期望做成一个媲美
前面开源框架的一个框架，而是希望更多的以学习和探索的视角，去开发一个混合查询引擎，当然肯定会参考这个框架的实现。

那么这个混合查询引擎需要哪些能力了？作为一款混合查询引擎，首先应该对外提供统一的查询语言，也就是SQL，屏蔽掉底层数据源细节。
此外，对于数据计算，也需要做到灵活支持多种计算引擎。并且，要有SQL优化的能力。
## 整体架构
![整体架构](https://github.com/xianfengyi/photos/blob/main/mixquery/MixQuery%E6%80%BB%E4%BD%93%E6%9E%B6%E6%9E%84.png)
PS:这是预期的架构，现在只实现了非常简单的功能
## 快速开始
初始化元数据数据库:
```sql
CREATE TABLE t_meta_calcite_schema (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `name` varchar(255) NOT NULL COMMENT 'schema名',
   `type` varchar(255) NOT NULL DEFAULT '' COMMENT 'schema类型',
   `factory` varchar(255) NOT NULL COMMENT 'jdbc factory',
   `operand` varchar(255) NOT NULL COMMENT 'schema operand',
   `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `mixquery`.`t_meta_calcite_schema` (`id`, `name`, `type`, `factory`, `operand`, `update_time`) VALUES (1, 'db1', 'custom', 'org.apache.calcite.adapter.jdbc.JdbcSchema$Factory', '{\"jdbcDriver\": \"com.mysql.cj.jdbc.Driver\",\"jdbcUrl\": \"jdbc:mysql://localhost:3306/test\",\"jdbcUser\": \"root\",\"jdbcPassword\": \"123456\"}', '2022-09-03 22:17:34');
INSERT INTO `mixquery`.`t_meta_calcite_schema` (`id`, `name`, `type`, `factory`, `operand`, `update_time`) VALUES (2, 'db2', 'custom', 'org.apache.calcite.adapter.jdbc.JdbcSchema$Factory', '{\"jdbcDriver\": \"org.postgresql.Driver\",\"jdbcUrl\": \"jdbc:postgresql://localhost:5432/test\",\"jdbcUser\": \"postgres\",\"jdbcPassword\": \"123456\"}', '2022-09-03 22:51:12');
```
准备测试数据：学生信息，以及成绩，学生信息放到Mysql中，成绩信息放到PostgreSQL中。
Mysql：
```sql
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `sex` varchar(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

INSERT INTO `test`.`student` (`id`, `name`, `sex`) VALUES (1, '小明', '男');
INSERT INTO `test`.`student` (`id`, `name`, `sex`) VALUES (2, '小红', '女');
```
PostgreSQL:
```sql
CREATE TABLE IF NOT EXISTS score
(
    id integer NOT NULL,
    student_id integer NOT NULL,
    grade integer NOT NULL,
    PRIMARY KEY (id)
)

insert into score(id,student_id,grade)values(1,1,80);
insert into score(id,student_id,grade)values(2,2,90);
```

测试异构数据源查询SQL:
```sql
select student.*,score.grade 
from db1.student as student 
    join db2.score as score on student.id=score.student_id
```

通过自定义JDBC驱动，进行MYSQL中的student表与PostgreSQL中score表join查询：
```java
@Test
public void testMixQuery_join() throws ClassNotFoundException {
    Class.forName("com.pioneeryi.mixquery.jdbc.Driver");

    try (Connection conn = DriverManager.getConnection("jdbc:mixquery:localhost:9093/mixquery")) {
        // 查询数据
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select student.*,score.grade from db1.student as student join db2.score as score on student.id=score.student_id");
        printRs(rs);
    } catch (SQLException exception) {
        exception.printStackTrace();
    }
}
```
查询结果：
```shell
id:1 , name:小明 , sex:男 , grade:80
id:2 , name:小红 , sex:女 , grade:90
```
