package com.design;

import com.common.model.Employee;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *对现象clone  浅拷贝和深拷贝
 * @Date 2020/3/30 14:10
 * @name CloneDemo
 */

@Getter
@Setter
public class CloneDemo implements Cloneable {

    private String id;
    private String name;

    private Employee employee;

    @Override
    public Object clone() {
        CloneDemo stu = null;
        try{
            stu = (CloneDemo)super.clone();   //浅复制
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        //stu.employee = (Employee)employee.clone();   //深度复制
        return stu;
    }

    public static void main(String[] args) {
        CloneDemo demo=new CloneDemo();
        demo.setId("01");
        demo.setName("张三丰");
        Employee employee= new Employee(11,"xiaozhang",50000,1);
        demo.setEmployee(employee);
        System.out.println("demo源地址:"+demo);
        CloneDemo demoNew= (CloneDemo) demo.clone();
        CloneDemo demoCopy=demo; //潜copy
        System.out.println("demoClone :"+demoNew+  demoNew.getEmployee());
        System.out.println("demoCopy:  "+demoCopy+  demoCopy.getEmployee());
    }
}
