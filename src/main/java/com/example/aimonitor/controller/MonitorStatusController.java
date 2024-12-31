package com.example.aimonitor.controller;

import com.example.aimonitor.entity.MonitorStatus;
import com.example.aimonitor.repository.MonitorStatusRepository;
import com.example.aimonitor.service.MonitorStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status")
public class MonitorStatusController {
    @Autowired
    private MonitorStatusService monitorStatusService;
    @Autowired
    private MonitorStatusRepository monitorStatusRepository;

    @GetMapping
    public Page<MonitorStatus> getMonitorStatuses(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String startTime,   // 时间范围开始
            @RequestParam(required = false) String endTime      // 时间范围结束
    ){
        Pageable pageable = PageRequest.of(page - 1, size);

        if (startTime != null && endTime != null) {
            // 如果提供了时间区间，调用时间区间查询方法
            return monitorStatusRepository.findMonitorStatusByTimeRange(startTime, endTime, pageable);
        } else {
            // 如果没有提供时间区间，按时间降序查询所有记录
            return monitorStatusRepository.findAllByOrderByTimeDesc(pageable);
        }
    };

    @PostMapping
    public MonitorStatus updateMonitorStatus(@RequestBody MonitorStatus monitorStatus) {
        return monitorStatusService.save(monitorStatus);
    }

    // 删除MonitorStatus，根据id删除
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMonitorStatus(@PathVariable Long id) {

        if (monitorStatusRepository.existsById(id)) {
            monitorStatusRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("ID为 " + id + " 的MonitorStatus成功被删除.");
        } else {
            // 如果ID不存在，返回404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("ID为 " + id + " 的MonitorStatus不存在.");
        }
    }
}
