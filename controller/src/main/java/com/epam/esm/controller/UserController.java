package com.epam.esm.controller;

import com.epam.esm.controller.util.CustomLinkBuilder;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findUser(@PathVariable("id") Long id)
            throws ParameterNotPresentException, DataNotFoundException {
        User user = userService.find(id);
        user.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).findUser(user.getId()))
                .withSelfRel());
        user.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .findUsers(CustomLinkBuilder.limit, CustomLinkBuilder.offset))
                .withRel(CustomLinkBuilder.USER_RELATION_NAME));
        user.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .findUserOrders(user.getId(), CustomLinkBuilder.limit, CustomLinkBuilder.offset)).withRel(
                CustomLinkBuilder.USER_RELATION_NAME));
        user.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class)
                .findPopularTag(user.getId())).withRel(CustomLinkBuilder.USER_RELATION_NAME));
        return user;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findUsers(@RequestParam Integer limit, @RequestParam Integer offset)
            throws ParameterNotPresentException {
        List<User> users = userService.findAll(limit, offset);
        for (User user : users) {
            user.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                    .findUserOrders(user.getId(), CustomLinkBuilder.limit, CustomLinkBuilder.offset)).withRel(
                    CustomLinkBuilder.USER_RELATION_NAME));
            user.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TagController.class)
                    .findPopularTag(user.getId())).withRel(CustomLinkBuilder.USER_RELATION_NAME));
        }
        return users;
    }

    @GetMapping(value = "/orders/{orderId}", params = {"userId"})
    @ResponseStatus(HttpStatus.OK)
    public Order findUserOrder(@PathVariable("orderId") Long orderId, @RequestParam("userId") Long userId)
            throws ParameterNotPresentException {
        Order order = userService.findUserOrder(orderId, userId);
        order.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .findUserOrders(userId, CustomLinkBuilder.limit, CustomLinkBuilder.offset))
                .withRel(CustomLinkBuilder.USER_RELATION_NAME));
        return order;
    }

    @GetMapping(value = "/orders", params = {"userId"})
    @ResponseStatus(HttpStatus.OK)
    public List<Order> findUserOrders(@RequestParam("userId") Long userId, @RequestParam Integer limit,
                                      @RequestParam Integer offset) throws ParameterNotPresentException {
        return userService.findUserOrders(userId, limit, offset);
    }

}
