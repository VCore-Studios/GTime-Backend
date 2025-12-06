package com.vblocks.gtime.dto.user;

import com.vblocks.gtime.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInput {
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public static UserInput fromEntity(User user) {
        UserInput dto = new UserInput();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toList()));
        return dto;
    }
}