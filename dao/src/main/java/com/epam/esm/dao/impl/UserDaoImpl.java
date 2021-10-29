package com.epam.esm.dao.impl;

import com.epam.esm.dao.ConstantQuery;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
    public List<User> findAll() {
        Integer limit = 2;
        Integer offset = 2;
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
    public boolean addCertificateToUser(GiftCertificate certificate, Long userId, Date purchaseTimestamp) {
        int affectedRows = jdbcTemplate.update(ConstantQuery.ADD_CERTIFICATE_TO_USER_QUERY, certificate.getId(),
                purchaseTimestamp, certificate.getPrice(), userId);
        return (affectedRows == 1);
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
    public List<Order> findUserOrders(Long userId) {
        return jdbcTemplate.query(ConstantQuery.FIND_ALL_USER_ORDERS_QUERY, new BeanPropertyRowMapper<>(Order.class),
                userId);
    }

    @Override
    public List<GiftCertificate> findCertificateByTags(String... tagNames) {
        return jdbcTemplate.query(ConstantQuery.FIND_CERTIFICATE_BY_TAGS_QUERY,
                new BeanPropertyRowMapper<>(GiftCertificate.class), tagNames);
    }

    @Override
    public Tag findPopularTag(Long userId) {
        return jdbcTemplate.query(ConstantQuery.FIND_POPULAR_TAG_QUERY,
                new BeanPropertyRowMapper<>(Tag.class), userId)
                .stream()
                .findAny()
                .orElse(null);
    }

}
