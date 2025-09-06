package com.cyberunited.secureapi;

import static org.assertj.core.api.Assertions.assertThat;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.springframework.web.util.HtmlUtils;

class EchoPropertyTest {
    @Property
    void sanitized(@ForAll String input) {
        String out = HtmlUtils.htmlEscape(input);
        assertThat(out).doesNotContain("<").doesNotContain(">");
    }
}
