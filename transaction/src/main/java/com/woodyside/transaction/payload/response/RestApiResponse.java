package com.woodyside.transaction.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class RestApiResponse {

    private int statusCode;

    private String message;

    private String date;

    private String description;

    private Boolean success;
}
