package com.demoqa.bookStore.models.account;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class User {
    private String userName;
    private String password;
}
