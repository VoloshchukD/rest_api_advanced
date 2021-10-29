package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.util.ExceptionMessageHandler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    private GiftCertificateDao certificateDao;

    public UserServiceImpl(UserDao userDao, GiftCertificateDao certificateDao) {
        this.userDao = userDao;
        this.certificateDao = certificateDao;
    }

    @Override
    public boolean add(User user) {
        return userDao.add(user);
    }

    @Override
    public User find(Long id) throws ParameterNotPresentException, DataNotFoundException {
        if (id == null) {
            throw new ParameterNotPresentException(ExceptionMessageHandler.USER_CODE,
                    ExceptionMessageHandler.USER_ID_NOT_PRESENT_MESSAGE_NAME);
        }
        User user = userDao.find(id);
        if (user == null) {
            throw new DataNotFoundException(ExceptionMessageHandler.USER_CODE,
                    ExceptionMessageHandler.USER_NOT_FOUND_MESSAGE_NAME);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }

    @Override
    public boolean addCertificateToUser(Long certificateId, Long userId) {
        GiftCertificate certificate = certificateDao.find(certificateId);
        Date purchaseTimestamp = new Date();
        return userDao.addCertificateToUser(certificate, userId, purchaseTimestamp);
    }

    @Override
    public Order findUserOrder(Long orderId, Long userId) {
        return userDao.findUserOrder(orderId, userId);
    }

    @Override
    public List<Order> findUserOrders(Long userId) {
        return userDao.findUserOrders(userId);
    }

    @Override
    public List<GiftCertificate> findCertificateByTags(String... tagNames) {
        return userDao.findCertificateByTags(tagNames);
    }

    @Override
    public Tag findPopularTag(Long userId) {
        return userDao.findPopularTag(userId);
    }

}
