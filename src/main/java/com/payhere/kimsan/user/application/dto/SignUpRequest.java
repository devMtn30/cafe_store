package com.payhere.kimsan.user.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignUpRequest(
    @NotBlank @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$") String userId,
    @NotBlank String password,
    @NotBlank String name,
    @NotBlank String regNo) {

}
