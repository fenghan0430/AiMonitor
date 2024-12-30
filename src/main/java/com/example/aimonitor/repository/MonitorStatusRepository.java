package com.example.aimonitor.repository;

import com.example.aimonitor.entity.MonitorStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitorStatusRepository extends JpaRepository<MonitorStatus, Long> {

}
