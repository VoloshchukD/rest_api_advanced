package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;

import java.util.List;

public interface UserService extends BaseService<User> {

    Order findUserOrder(Long orderId, Long userId) throws ParameterNotPresentException;

    List<Order> findUserOrders(Long userId, Integer page)
            throws ParameterNotPresentException, IllegalPageNumberException;

}
