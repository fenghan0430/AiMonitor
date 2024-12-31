package com.example.aimonitor.repository;

import com.example.aimonitor.entity.MonitorStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MonitorStatusRepository extends JpaRepository<MonitorStatus, Long> {
    // 分页 + 时间降序
    Page<MonitorStatus> findAllByOrderByTimeDesc(Pageable pageable);

    @Query("SELECT i FROM MonitorStatus i WHERE i.time BETWEEN :startTime AND :endTime ORDER BY i.time DESC")
    Page<MonitorStatus> findMonitorStatusByTimeRange(String startTime, String endTime, Pageable pageable);

}
