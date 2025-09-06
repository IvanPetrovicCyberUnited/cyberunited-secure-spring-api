package com.cyberunited.secapi;

import net.jqwik.api.*;
import org.junit.jupiter.api.Assertions;
import org.springframework.web.util.HtmlUtils;

class EchoPropertyTest {
    @Property
    void echoEscapes(@ForAll String input) {
        String sanitized = HtmlUtils.htmlEscape(input);
        Assertions.assertFalse(sanitized.contains("<") || sanitized.contains(">"));
    }
}
