package com.epam.esm.dao;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;

import java.util.Date;
import java.util.List;

public interface UserDao extends BaseDao<User> {

    boolean addCertificateToUser(GiftCertificate certificate, Long userId, Date purchaseTimestamp);

    Order findUserOrder(Long orderId, Long userId);

    List<Order> findUserOrders(Long userId);

    List<GiftCertificate> findCertificateByTags(String... tagNames);

    Tag findPopularTag(Long userId);

}
