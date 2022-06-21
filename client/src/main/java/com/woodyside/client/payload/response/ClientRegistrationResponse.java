package com.woodyside.client.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ClientRegistrationResponse {

    private String info;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MMM-dd HH:mm:ss z", timezone = "Europe/Moscow")
    @JsonProperty(value = "response_date")
    private LocalDateTime responseDate;

    private Boolean success;
}
