package com.hemendra.worktrackserver.controller;

import com.hemendra.worktrackserver.dto.AppActivityDto;
import com.hemendra.worktrackserver.dto.UserActivityDto;
import com.hemendra.worktrackserver.dto.UserWebsiteActivityDto;
import com.hemendra.worktrackserver.service.AppActivityService;
import com.hemendra.worktrackserver.service.UserActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user-activity")
@RequiredArgsConstructor
public class UserActivityController {

    private final UserActivityService userActivityService;
    private final AppActivityService appActivityService;

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

    @PostMapping(value = "/screenshot", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadBodyImage(@RequestParam MultipartFile image, @RequestParam String userName) {
        String minioObjUrl = userActivityService.uploadBodyImage(image, userName);
        return ResponseEntity.ok().body(minioObjUrl);
    }

    @PostMapping("/website-usage")
    public ResponseEntity saveWebsiteUsage(@RequestBody UserWebsiteActivityDto websiteActivityDto) {
        userActivityService.saveWebsiteUsage(websiteActivityDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/website-usage")
    public List<UserWebsiteActivityDto> findAllWebsiteUsage() {
        return userActivityService.findAllWebsiteUsage();
    }

    @PostMapping("/app-activity")
    public ResponseEntity saveAppUsage(@RequestBody AppActivityDto appActivityDto) {
        AppActivityDto appActivity = appActivityService.saveAppActivity(appActivityDto);
        if (appActivity == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/app-activity")
    public List<AppActivityDto> findAllAppUsage() {
        return appActivityService.findAll();
    }

    @GetMapping("/app-activity/{userName}")
    public AppActivityDto findByUserName(@PathVariable("userName") String userName) {
        return appActivityService.findByUserName(userName);
    }
}
