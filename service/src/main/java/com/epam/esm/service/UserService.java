package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;

import java.util.List;

public interface UserService extends BaseService<User> {

    boolean addCertificateToUser(Long certificateId, Long userId);

    Order findUserOrder(Long orderId, Long userId);

    List<Order> findUserOrders(Long userId);

    List<GiftCertificate> findCertificateByTags(String... tagNames);

    Tag findPopularTag(Long userId);

}
