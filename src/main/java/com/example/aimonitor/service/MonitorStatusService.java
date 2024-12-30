package com.example.aimonitor.service;

import com.example.aimonitor.entity.MonitorStatus;
import com.example.aimonitor.repository.MonitorStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitorStatusService {
    @Autowired
    MonitorStatusRepository monitorStatusRepository;

    public List<MonitorStatus> findAll() {
        return monitorStatusRepository.findAll();
    }

    public MonitorStatus save(MonitorStatus monitorStatus){
        return monitorStatusRepository.save(monitorStatus);
    }
}
