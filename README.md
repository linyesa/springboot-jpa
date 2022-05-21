# springboot-jpa简单使用

添加依赖，可在springboot项目创建时勾选jpa自动添加jpa起步依赖，mysql-connector-java需自己添加，里面包含**spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver**及其他连接mysql的重要属性

```xml
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
</dependency>
```

## 添加数据源配置

application.properties

连接到数据库，库名：springboot_mybatis

```properties
spring.datasource.password=123456
spring.datasource.url=jdbc:mysql://localhost:3306/springboot_mybatis
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
```

创建实体类（需要与数据库表映射，如数据库没有该表，会自动创建）

创建一个数据库，数据库的名字为前边在配置文件中配置的，然后在启动类启动，即可生成对应的数据表。

无参构造不能少，因为加了有参构造

```java
@Entity
public class Student {
    @Id
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="age")
    private Integer age;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Student() {
    }

    public Student(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
```

具体注解作用：

@Entity	声明类为实体或表。
@Table	声明表名。
@Basic	指定非约束明确的各个字段。
@Embedded	指定类或它的值是一个可嵌入的类的实例的实体的属性。
@Id	指定的类的属性，用于识别（一个表中的主键）。
@GeneratedValue	指定如何标识属性可以被初始化，例如自动、手动、或从序列表中获得的值。
@Transient	指定的属性，它是不持久的，即：该值永远不会存储在数据库中。
@Column	指定持久属性栏属性。
@SequenceGenerator	指定在@GeneratedValue注解中指定的属性的值。它创建了一个序列。
@TableGenerator	指定在@GeneratedValue批注指定属性的值发生器。它创造了的值生成的表。
@AccessType	这种类型的注释用于设置访问类型。如果设置@AccessType（FIELD），则可以直接访问变量并且不需要getter和setter，但必须为public。如果设置@AccessType（PROPERTY），通过getter和setter方法访问Entity的变量。
@JoinColumn	指定一个实体组织或实体的集合。这是用在多对一和一对多关联。
@UniqueConstraint	指定的字段和用于主要或辅助表的唯一约束。
@ColumnResult	参考使用select子句的SQL查询中的列名。
@ManyToMany	定义了连接表之间的多对多一对多的关系。
@ManyToOne	定义了连接表之间的多对一的关系。
@OneToMany	定义了连接表之间存在一个一对多的关系。
@OneToOne	定义了连接表之间有一个一对一的关系。
@NamedQueries	指定命名查询的列表。
@NamedQuery	指定使用静态名称的查询。



## 创建StudentRepository接口，该接口只需要继承JpaRepository接口即可

<Student,Long>为泛型，Student为操作的实体对象，Long为主键类型

下为JpaRepository的接口声明

```java
public interface JpaRepository<T, ID> extends PagingAndSortingRepository<T, ID>, QueryByExampleExecutor<T>
```

```java
public interface StudentRepository extends JpaRepository<Student,Long> {

}
```

## 测试查询

**直接注入接口就可以使用**

```java
@SpringBootTest
class JpaApplicationTests {
    @Autowired
    StudentRepository studentRepository;
    @Test
    void findAll() {
        System.out.println("----------------findAll---------------");
        System.out.println(studentRepository.findAll());
    }
    @Test
    void findById(){
        Long x=new Long(1);
        System.out.println("---------------findById--------------");
        System.out.println(studentRepository.findById(x));
    }
}
```

**结果如下**

----------------findAll---------------
[Student{id=1, name='sss', age=234}, Student{id=2, name='sy', age=111}, Student{id=3, name='tqn', age=18}, Student{id=4, name='tqn', age=18}, Student{id=5, name='tqn', age=18}, Student{id=6, name='tqn', age=18}, Student{id=7, name='tqn', age=18}, Student{id=8, name='tqn', age=18}, Student{id=9, name='tqn', age=18}, Student{id=10, name='tqn', age=18}, Student{id=11, name='tqn', age=18}]
---------------findById--------------
Optional[Student{id=1, name='sss', age=234}]

