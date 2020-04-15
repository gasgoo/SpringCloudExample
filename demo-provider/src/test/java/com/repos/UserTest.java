package com.repos;

import com.client.ClientDemoApplication;
import com.client.domain.User;
import com.client.repository.UserResp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Date 2019/5/14 10:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes= ClientDemoApplication.class)
public class UserTest {

    @Autowired
    private UserResp userResp;

    @Test
    @Transactional
    @Rollback
    public void testSave(){
        User user=new User();
        user.setPassword("testPWD");
        user.setUsername("user31");
        User save = userResp.save(user);
        System.out.println("==="+save.getId());
    }
    @Test
    public void testOptimisticLock(){
        Optional<User> byId = userResp.findById(1);
        Optional<User> byId1 = userResp.findById(1);
        if (byId.isPresent()){
            User u=byId.get();
            System.out.println("find:==="+u.getVersion());
            u.setUsername("updatePWDBy");
            User save = userResp.save(u);
            System.out.println("==="+save.getVersion());
        }
        byId1.ifPresent(user -> {user.setUsername("updateHappyLock");
            userResp.save(user);
        });

    }
}
