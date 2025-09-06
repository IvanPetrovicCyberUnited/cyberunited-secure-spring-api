package com.cyberunited.secureapi.controller;

import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
@Validated
public class EchoController {
    @GetMapping("/api/echo")
    public ResponseEntity<String> echo(@RequestParam("q") @Size(max = 100) String q) {
        return ResponseEntity.ok(HtmlUtils.htmlEscape(q));
    }
}
