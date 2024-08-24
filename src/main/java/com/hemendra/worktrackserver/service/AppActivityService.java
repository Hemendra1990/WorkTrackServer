package com.hemendra.worktrackserver.service;

import com.hemendra.worktrackserver.dto.AppActivityDto;
import com.hemendra.worktrackserver.entity.AppActivity;
import com.hemendra.worktrackserver.repository.AppActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppActivityService {
    private final AppActivityRepository appActivityRepository;

    public List<AppActivityDto> findAll() {
        List<AppActivity> appActivities = appActivityRepository.findAll();
        if (appActivities == null) {
            return null;
        }
        List<AppActivityDto> appActivityDtos = appActivities.stream().map(appActivity -> {
            AppActivityDto appActivityDto = new AppActivityDto();
            appActivityDto.setId(appActivity.getId());
            appActivityDto.setActiveWindow(appActivity.getActiveWindow());
            appActivityDto.setAppCategory(appActivity.getAppCategory());
            appActivityDto.setAppName(appActivity.getAppName());
            appActivityDto.setDuration(appActivity.getDuration());
            appActivityDto.setEndTime(appActivity.getEndTime());
            appActivityDto.setMacAddress(appActivity.getMacAddress());
            appActivityDto.setStartTime(appActivity.getStartTime());
            appActivityDto.setUserName(appActivity.getUserName());
            return appActivityDto;
        }).toList();
        return appActivityDtos;
    }

    public AppActivityDto saveAppActivity(AppActivityDto appActivity) {
        AppActivity appActivityEntity = new AppActivity();
        appActivityEntity.setActiveWindow(appActivity.getActiveWindow());
        appActivityEntity.setAppCategory(appActivity.getAppCategory());
        appActivityEntity.setAppName(appActivity.getAppName());
        appActivityEntity.setDuration(appActivity.getDuration());
        appActivityEntity.setEndTime(appActivity.getEndTime());
        appActivityEntity.setMacAddress(appActivity.getMacAddress());
        appActivityEntity.setStartTime(appActivity.getStartTime());
        appActivityEntity.setUserName(appActivity.getUserName());
        AppActivity saved = appActivityRepository.save(appActivityEntity);
        if (saved == null) {
            return null;
        }
        return appActivity;
    }


    public AppActivityDto findByUserName(String userName) {
        AppActivity appActivity = appActivityRepository.findByUserName(userName).orElse(null);
        if (appActivity == null) {
            return null;
        }
        AppActivityDto appActivityDto = new AppActivityDto();
        appActivityDto.setId(appActivity.getId());
        appActivityDto.setActiveWindow(appActivity.getActiveWindow());
        appActivityDto.setAppCategory(appActivity.getAppCategory());
        appActivityDto.setAppName(appActivity.getAppName());
        appActivityDto.setDuration(appActivity.getDuration());
        appActivityDto.setEndTime(appActivity.getEndTime());
        appActivityDto.setMacAddress(appActivity.getMacAddress());
        appActivityDto.setStartTime(appActivity.getStartTime());
        appActivityDto.setUserName(appActivity.getUserName());
        return appActivityDto;
    }

}
