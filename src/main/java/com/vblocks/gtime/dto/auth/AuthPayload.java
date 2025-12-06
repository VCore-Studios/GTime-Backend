package com.vblocks.gtime.dto.auth;

import com.vblocks.gtime.dto.user.UserInput;
import com.vblocks.gtime.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthPayload {
    private String accessToken;
    private String refreshToken;
    private UserInput user;

    public AuthPayload(String accessToken, String refreshToken, User user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = UserInput.fromEntity(user);
    }
}