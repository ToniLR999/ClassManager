package com.tonilr.ClassManager.Util;

import org.apache.commons.text.StringEscapeUtils;

public class SanitizationUtils {

    public static String sanitize(String input) {
        return input == null ? null : StringEscapeUtils.escapeHtml4(input.trim());
    }
}