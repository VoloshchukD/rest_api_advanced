package com.epam.esm.dao;

import com.epam.esm.dao.configuration.TestDataSourceConfiguration;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(TestDataSourceConfiguration.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    private static User user;

    @BeforeAll
    public static void initializeUser() {
        user = new User();
        user.setId(1L);
        user.setForename("John");
        user.setSurname("Wick");
    }

    @Test
    public void testFindUser() {
        Assertions.assertNotNull(userDao.find(user.getId()));
    }

    @Test
    public void testFindAllUsers() {
        Assertions.assertNotNull(userDao.findAll(3, 0));
    }

    @Test
    public void testFindUserOrder() {
        Assertions.assertNotNull(userDao.findUserOrder(1L, user.getId()));
    }

    @Test
    public void testFindUserOrders() {
        Assertions.assertNotNull(userDao.findUserOrders(1L, 3, 0));
    }

}
