package com.taskflow.global.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
    public String encode(String inputPw) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, inputPw.toCharArray());
    }
    public boolean matches(String inputPw, String savedPw) {
        BCrypt.Result result = BCrypt.verifyer().verify(inputPw.toCharArray(), savedPw);
        return result.verified;
    }
}

