package com3.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

@RestController
public class LastController {

    @GetMapping("/load/{duration}")
    public String test(@PathVariable long duration) {
        long startTime = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - startTime >= duration) {
                break;
            }
        }
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        return String.format("%.2f", osBean.getSystemCpuLoad() * 100);
    }
}
