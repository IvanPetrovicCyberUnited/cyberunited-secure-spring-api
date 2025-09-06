package com.cyberunited.secapi;

import com.code_intelligence.jazzer.junit.FuzzTest;
import org.springframework.web.util.HtmlUtils;

class EchoFuzzTest {
    @FuzzTest
    void fuzzEcho(String input) {
        if(input!=null) HtmlUtils.htmlEscape(input);
    }
}
