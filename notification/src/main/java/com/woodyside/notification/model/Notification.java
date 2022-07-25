package com.woodyside.notification.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter(PRIVATE)
@ToString
@Builder
@Document(collection = "notification")
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @Field(value = "notification_id")
    private String id;

    @Field(value = "to_customer_id")
    private Integer toCustomerId;

    @Field(value = "to_customer_email")
    private String toCustomerEmail;

    @Field(value = "sender")
    private String sender;

    @Field(value = "message")
    private String message;

    @Field(value = "sent_at")
    private String sentAt;
}
