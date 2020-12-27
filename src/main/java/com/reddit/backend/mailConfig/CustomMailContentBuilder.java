package com.reddit.backend.mailConfig;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class CustomMailContentBuilder {

    private final TemplateEngine templateEngine;

    protected String buildMail(String body,String link,String msg) {
        Context context = new Context();
        context.setVariable("body", body);
        context.setVariable("link", link);
        context.setVariable("msg", msg);
        return templateEngine.process("mailTemplate", context);

    }
}
