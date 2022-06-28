package com.gfa.users.controllers;

import com.gfa.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController extends UserController{

    @Autowired
    public MemberController(UserService userService) {
        super(userService);
    }
}
