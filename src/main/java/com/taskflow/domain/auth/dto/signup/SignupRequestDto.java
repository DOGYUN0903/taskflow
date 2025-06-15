package com.taskflow.domain.auth.dto.signup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @NotBlank(message = "사용자 닉네임은 필수로 입력해주세요.")
    private String username;

    @NotBlank(message = "이메일은 필수로 입력해주세요.")
    @Email
    private String email;

    @NotBlank(message = "비밀번호는 필수로 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+\\[{\\]};:'\",<.>/?]).{8,}$",
            message = "비밀번호는 최소 8글자 이상, 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야합니다."
    )
    private String password;

    @NotBlank(message = "사용자 이름은 필수로 입력해주세요.")
    private String name;

    @NotBlank
    private String userRole;
}
