package fr.paillaugue.outfitter.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthMonitoringController {
    @GetMapping
    public String health() {
        return "OK";
    }
}
