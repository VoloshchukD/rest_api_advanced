package com.epam.esm.dao.impl;

import com.epam.esm.dao.ConstantQuery;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean add(User user) {
        return false;
    }

    @Override
    public User find(Long id) {
        return jdbcTemplate.query(ConstantQuery.FIND_USER_QUERY, new BeanPropertyRowMapper<>(User.class),
                new Object[]{id})
                .stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public List<User> findAll(Integer limit, Integer offset) {
        return jdbcTemplate.query(ConstantQuery.FIND_ALL_USERS_QUERY,
                new BeanPropertyRowMapper<>(User.class), limit, offset);
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Order findUserOrder(Long orderId, Long userId) {
        return jdbcTemplate.query(ConstantQuery.FIND_USER_ORDER_QUERY, new BeanPropertyRowMapper<>(Order.class),
                orderId, userId)
                .stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Order> findUserOrders(Long userId, Integer limit, Integer offset) {
        return jdbcTemplate.query(ConstantQuery.FIND_ALL_USER_ORDERS_QUERY, new BeanPropertyRowMapper<>(Order.class),
                userId, limit, offset);
    }

}
