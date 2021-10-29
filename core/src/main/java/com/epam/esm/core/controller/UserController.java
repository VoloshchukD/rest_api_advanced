package com.epam.esm.core.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        user.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).findUser(id)).withSelfRel());
        user.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).findUsers()).withRel(
                "actions"));
        user.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).findUserOrders(id)).withRel(
                "actions"));
        return user;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findUsers() {
        return userService.findAll();
    }

    @PostMapping(params = {"certificateId", "userId"})
    public ResponseEntity<Boolean> addCertificateToUser(@RequestParam("certificateId") Long certificateId,
                                                        @RequestParam("userId") Long userId) {
        boolean result = userService.addCertificateToUser(certificateId, userId);
        HttpStatus httpStatus = result ? HttpStatus.CREATED : HttpStatus.NOT_MODIFIED;
        return new ResponseEntity(result, httpStatus);
    }

    @GetMapping(value = "/orders/{orderId}", params = {"userId"})
    @ResponseStatus(HttpStatus.OK)
    public Order findUserOrder(@PathVariable("orderId") Long orderId, @RequestParam("userId") Long userId) {
        return userService.findUserOrder(orderId, userId);
    }

    @GetMapping(value = "/orders", params = {"userId"})
    @ResponseStatus(HttpStatus.OK)
    public List<Order> findUserOrders(@RequestParam("userId") Long userId) {
        return userService.findUserOrders(userId);
    }

    @GetMapping(value = "/certificates")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificate> findCertificateByTags(@RequestParam List<String> tagNames) {
        String[] tagNamesArray = new String[tagNames.size()];
        return userService.findCertificateByTags(tagNames.toArray(tagNamesArray));
    }

    @GetMapping(value = "/popular-tag", params = {"userId"})
    @ResponseStatus(HttpStatus.OK)
    public Tag findPopularTag(@RequestParam("userId") Long userId) {
        return userService.findPopularTag(userId);
    }

}
