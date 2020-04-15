package com.design.prototype;

/**
 * 实现Product接口的类
 *
 * @Date 2019/7/1 11:29
 */
public class MessageBox implements Product {

    private char decoChar;

    public MessageBox(char decoChar) {
        this.decoChar = decoChar;
    }

    @Override
    public void use(String s) {
        int length = s.getBytes().length;
        for (int i = 0; i < length; i++) {
            System.out.print(decoChar);
        }
        System.out.println("");
        System.out.println(decoChar+" "+s+" "+decoChar);
        for(int i=0;i<length+2;i++){
            System.out.print(decoChar);
        }
        System.out.println("");
    }

    @Override
    public Product createClone() {
        Product p=null;
        try {
            p= (Product) clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return p;
    }

    public static void main(String[] args) throws Exception{
        Manager manager=new Manager();
        MessageBox messageBox=new MessageBox('*');
        messageBox.use("实例本身");
        MessageBox messageBox1=new MessageBox('/');
        manager.register("a",messageBox);
        manager.register("b",messageBox1);

        Product p1=manager.create("a");
        p1.use("原型模式");
        Product p2=manager.create("b");
        p2.use("prototype");
        Student student=new Student();
        student.setId(1L);
        student.setAge(45);
        student.setName("testOne");
        System.out.println(student.toString());
        manager.register("student1",student);
        Student std= (Student) manager.create("student1");
        System.out.println("==="+std.toString());
        Object obj=new Object();

    }
}
