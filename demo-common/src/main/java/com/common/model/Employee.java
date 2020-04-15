package com.common.model;

import com.design.CloneDemo;
import lombok.ToString;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Date 2019/8/31 14:13
 */
@Component
public class Employee implements Serializable ,Cloneable{

    private Long empno;
    private String name;
    private Integer salary;
    private Integer deptno;

    public Employee(long empno, String name, int salary, int deptno) {
        this.empno=empno;
        this.name=name;
        this.salary=salary;
        this.deptno=deptno;
    }

    public Employee(){

    }

    public Long getEmpno() {
        return empno;
    }

    public void setEmpno(Long empno) {
        this.empno = empno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }

    @Override
    public String toString() {
       return "new Instance";
    }

    @Override
    public Object clone() {
        Employee stu = null;
        try{
            stu = (Employee)super.clone();   //浅复制
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return stu;
    }
}
