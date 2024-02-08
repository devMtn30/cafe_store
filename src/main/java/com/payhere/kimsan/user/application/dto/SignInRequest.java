package com.payhere.kimsan.user.application.dto;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(@NotBlank String userId, @NotBlank String password) {

}
