package com.cyberunited.secureapi;

import com.code_intelligence.jazzer.junit.FuzzTest;
import java.nio.charset.StandardCharsets;
import org.springframework.web.util.HtmlUtils;

class EchoFuzzTest {
    @FuzzTest(maxDuration = "1s")
    void fuzz(byte[] data) {
        String input = new String(data, StandardCharsets.UTF_8);
        HtmlUtils.htmlEscape(input);
    }
}
