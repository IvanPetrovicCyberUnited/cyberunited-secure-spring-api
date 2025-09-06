package com.cyberunited.secapi.controller;

import com.cyberunited.secapi.model.EchoResponse;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequestMapping("/api")
@Validated
public class EchoController {
    @GetMapping("/echo")
    public EchoResponse echo(@RequestParam("q") @Size(max = 32) String q) {
        String sanitized = HtmlUtils.htmlEscape(q);
        return new EchoResponse(sanitized);
    }
}
