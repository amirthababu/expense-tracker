package com.financeapp.expensetracker.controller;

import com.financeapp.expensetracker.dto.DashboardResponse;
import com.financeapp.expensetracker.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // Example: GET /api/dashboard/summary?year=2026&month=6
    // If year/month omitted, defaults to the current month
    @GetMapping("/summary")
    public ResponseEntity<DashboardResponse> getSummary(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {
        return ResponseEntity.ok(dashboardService.getMonthlySummary(year, month));
    }
}
