package com.epam.esm.controller;

import com.epam.esm.controller.util.assembler.OrderModelAssembler;
import com.epam.esm.controller.util.assembler.UserModelAssembler;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private UserService userService;

    private UserModelAssembler userModelAssembler;

    private OrderModelAssembler orderModelAssembler;

    public UserController(UserService userService, UserModelAssembler userModelAssembler,
                          OrderModelAssembler orderModelAssembler) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
        this.orderModelAssembler = orderModelAssembler;
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<User> findUser(@PathVariable("id") Long id)
            throws ParameterNotPresentException, DataNotFoundException {
        User user = userService.find(id);
        return userModelAssembler.toModel(user);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EntityModel<User>> findUsers(@RequestParam Integer page) throws IllegalPageNumberException {
        List<User> users = userService.findAll(page);
        return userModelAssembler.toCollectionModel(users);
    }

    @GetMapping(value = "/orders/{orderId}", params = {"userId"})
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Order> findUserOrder(@PathVariable("orderId") Long orderId, @RequestParam("userId") Long userId)
            throws ParameterNotPresentException {
        Order order = userService.findUserOrder(orderId, userId);
        return orderModelAssembler.toModel(order);
    }

    @GetMapping(value = "/orders", params = {"userId", "page"})
    @ResponseStatus(HttpStatus.OK)
    public List<EntityModel<Order>> findUserOrders(@RequestParam("userId") Long userId,
                                                   @RequestParam("page") Integer page)
            throws ParameterNotPresentException, IllegalPageNumberException {
        return orderModelAssembler.toCollectionModel(userService.findUserOrders(userId, page));
    }

}
