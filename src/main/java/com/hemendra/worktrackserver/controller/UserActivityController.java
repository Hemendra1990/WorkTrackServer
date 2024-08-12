package com.hemendra.worktrackserver.controller;

import com.hemendra.worktrackserver.UserActivityDto;
import com.hemendra.worktrackserver.service.UserActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-activity")
@RequiredArgsConstructor
public class UserActivityController {

    private final UserActivityService userActivityService;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping
    public List<UserActivityDto> findAll() {
        return userActivityService.findAll();
    }

    @GetMapping("/{id}")
    public UserActivityDto findById(@PathVariable("id") Long id) {
        return userActivityService.findById(id);
    }

    @PostMapping
    public void saveUserActivity(@RequestBody UserActivityDto userActivityDto) {
        userActivityService.saveUserActivity(userActivityDto);
    }
}
