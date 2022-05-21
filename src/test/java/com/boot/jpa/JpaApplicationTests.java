package com.boot.jpa;

import com.boot.jpa.dao.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.lang.Long;
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
