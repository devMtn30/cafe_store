package com.payhere.kimsan.user.application.dto;

import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
    @NotBlank String userId,
    @NotBlank String password,
    @NotBlank String name,
    @NotBlank String regNo) {

}
