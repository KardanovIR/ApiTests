package com.wavesplatform.steps;


import io.qameta.allure.Attachment;

public class Hooks {

    @Attachment(value = "{0}", type = "text/plain")
    static String saveTextAttachment(String attachName, String message) {
        System.out.println(attachName + " " + message);
        return message;
    }

    @Attachment(value = "{0}", type = "text/html")
    static String saveHtmlAsTextAttachment(String attachName, String html) {
        return html;
    }
}