package com.demoqa.bookStore.models.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class RegisterUserResponse {
    private String userID;
    private String username;
}
