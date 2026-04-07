package com.devf.hortilink.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devf.hortilink.dto.DashboardDTO;
import com.devf.hortilink.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService service;

    @GetMapping("/{comercioId}")
    public ResponseEntity<DashboardDTO> getDashboard(@PathVariable Long comercioId) {
        return ResponseEntity.ok(service.obterDadosDashboard(comercioId));
    }
}
