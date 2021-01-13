package com.reddit.backend.mailConfig;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationEmail {

    private String subject;
    private String recipient;
    private String body;
    private String link;
    private String msg;
}
