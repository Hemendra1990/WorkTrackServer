package com.hemendra.worktrackserver.service;

import com.hemendra.worktrackserver.dto.UserActivityDto;
import com.hemendra.worktrackserver.dto.UserWebsiteActivityDto;
import com.hemendra.worktrackserver.entity.UserActivity;
import com.hemendra.worktrackserver.entity.UserWebsiteActivity;
import com.hemendra.worktrackserver.repository.UserActivityRepository;
import com.hemendra.worktrackserver.repository.UserWebsiteActivityRepository;
import exception.StorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserActivityService {
    private final UserActivityRepository userActivityRepository;
    private final UserWebsiteActivityRepository websiteActivityRepository;
    private final MinIOUtil minIOUtil;

    public void saveUserActivity(UserActivityDto userActivityDto) {

        Optional<UserActivity> bySessionId = userActivityRepository.findBySessionId(userActivityDto.getSessionId());
        if (bySessionId.isPresent()) {
            //update
            UserActivity userActivity = bySessionId.get();
            userActivity.setEndTime(userActivityDto.getEndTime());
            //calcualte duration
            long idleDuration = java.time.Duration.between(userActivity.getStartTime(), userActivityDto.getEndTime()).getSeconds();
            userActivity.setDuration(idleDuration);
            userActivityRepository.save(userActivity);
        } else {
            UserActivity userActivity = new UserActivity();
            userActivity.setUserName(userActivityDto.getUserName());
            userActivity.setMacAddress(userActivityDto.getMacAddress());
            userActivity.setActivityType(userActivityDto.getActivityType());
            userActivity.setStartTime(userActivityDto.getStartTime());
            userActivity.setEndTime(userActivityDto.getEndTime());
            userActivity.setDuration(userActivityDto.getDuration());
            userActivity.setSessionId(userActivityDto.getSessionId());
            userActivityRepository.save(userActivity);
        }

    }

    public List<UserActivityDto> findAll() {
        return userActivityRepository.findAll().stream().map(userActivity -> {
            UserActivityDto userActivityDto = new UserActivityDto();
            userActivityDto.setId(userActivity.getId());
            userActivityDto.setUserName(userActivity.getUserName());
            userActivityDto.setMacAddress(userActivity.getMacAddress());
            userActivityDto.setActivityType(userActivity.getActivityType());
            userActivityDto.setStartTime(userActivity.getStartTime());
            userActivityDto.setEndTime(userActivity.getEndTime());
            userActivityDto.setDuration(userActivity.getDuration());
            userActivityDto.setSessionId(userActivity.getSessionId());
            userActivityDto.setState(userActivity.getState());
            return userActivityDto;
        }).toList();
    }

    public UserActivityDto findById(Long id) {
        UserActivity userActivity = userActivityRepository.findById(id).orElse(null);
        if (userActivity == null) {
            return null;
        }
        UserActivityDto userActivityDto = new UserActivityDto();
        userActivityDto.setId(userActivity.getId());
        userActivityDto.setUserName(userActivity.getUserName());
        userActivityDto.setMacAddress(userActivity.getMacAddress());
        userActivityDto.setActivityType(userActivity.getActivityType());
        userActivityDto.setStartTime(userActivity.getStartTime());
        userActivityDto.setEndTime(userActivity.getEndTime());
        userActivityDto.setDuration(userActivity.getDuration());
        userActivityDto.setSessionId(userActivity.getSessionId());
        userActivityDto.setState(userActivity.getState());
        return userActivityDto;
    }

    public String uploadBodyImage(MultipartFile image, String userName) {
        try {
            return minIOUtil.prepareMinIOObject(image, "work-track/screenshot/", false, userName);
        } catch (Exception e) {
            throw new StorageException("Unable to upload image");
        }
    }

    public void saveWebsiteUsage(UserWebsiteActivityDto websiteActivityDto) {
        UserWebsiteActivity userWebsiteActivity = new UserWebsiteActivity();
        userWebsiteActivity.setUserName(websiteActivityDto.getUserName());
        userWebsiteActivity.setMacAddress(websiteActivityDto.getMacAddress());
        userWebsiteActivity.setActivityType(websiteActivityDto.getActivityType());
        userWebsiteActivity.setStartTime(websiteActivityDto.getStartTime());
        userWebsiteActivity.setEndTime(websiteActivityDto.getEndTime());
        userWebsiteActivity.setDuration(websiteActivityDto.getDuration());
        userWebsiteActivity.setSessionId(websiteActivityDto.getSessionId());
        userWebsiteActivity.setUrl(websiteActivityDto.getUrl());
        userWebsiteActivity.setActiveWindow(websiteActivityDto.getActiveWindow());

        websiteActivityRepository.save(userWebsiteActivity);
    }
}
