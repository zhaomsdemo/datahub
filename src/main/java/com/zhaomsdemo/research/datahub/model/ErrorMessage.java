package com.zhaomsdemo.research.datahub.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorMessage {

    String message;
    String errorCode;
}
