package com.cloud;

import com.cloud.myspring.PropertiesPro;
import com.cloud.myspring.ValueBean;
import com.cloud.myspring.vo.BeanDefBean;
import com.cloud.myspring.vo.CycleRefA;
import com.cloud.myspring.vo.Student;
import junit.framework.TestCase;
import org.assertj.core.util.Strings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppSession.class)
public class springTest extends TestCase {


    @Autowired
    private BeanDefBean beanDefBean;
    @Autowired
    private Student student;
    @Autowired
    private ApplicationContext applicationContext;


    @Test
    public void testPostProcessBeanDefinitionRegistry() {
        System.out.println(">>>>>>" + beanDefBean.getName());
    }

    @Test
    public void testMyService() {
        student.print();
    }

    @Test
    public void testCycleRef() {
        CycleRefA bean = applicationContext.getBean(CycleRefA.class);
        bean.print();
    }

    @Test
    public void test() {
        ValueBean bean = applicationContext.getBean(ValueBean.class);
    }
}