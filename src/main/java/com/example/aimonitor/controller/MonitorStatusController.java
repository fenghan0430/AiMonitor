package com.example.aimonitor.controller;

import com.example.aimonitor.entity.MonitorStatus;
import com.example.aimonitor.service.MonitorStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/status")
public class MonitorStatusController {
    @Autowired
    private MonitorStatusService monitorStatusService;

    @GetMapping("/all")
    public List<MonitorStatus> getAllMonitorStatuses() {
        return monitorStatusService.findAll();
    }

    @PostMapping("/update")
    public MonitorStatus updateMonitorStatus(MonitorStatus monitorStatus) {
        return monitorStatusService.save(monitorStatus);
    }
}
