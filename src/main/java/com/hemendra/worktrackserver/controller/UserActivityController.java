package com.hemendra.worktrackserver.controller;

import com.hemendra.worktrackserver.UserActivityDto;
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

}
