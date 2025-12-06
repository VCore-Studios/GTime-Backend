package com.vblocks.gtime.component.user;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.vblocks.gtime.dto.user.UserInput;
import com.vblocks.gtime.entity.User;
import com.vblocks.gtime.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.stream.Collectors;

import static com.vblocks.gtime.util.AuthUtil.getAuthenticatedUsername;

@DgsComponent
@AllArgsConstructor
public class UserQuery {
    private UserService userService;

    @DgsQuery
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public UserInput me(DgsDataFetchingEnvironment dfe) {
        String username = getAuthenticatedUsername(dfe);
        User user = userService.findByUsername(username);
        return UserInput.fromEntity(user);
    }

    @DgsQuery
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserInput user(@InputArgument Long id) {
        User user = userService.findById(id);
        return UserInput.fromEntity(user);
    }

    @DgsQuery
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserInput> allUsers() {
        return userService.findAll().stream()
                .map(UserInput::fromEntity)
                .collect(Collectors.toList());
    }

    @DgsQuery
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserInput userByUsername(@InputArgument String username) {
        User user = userService.findByUsername(username);
        return UserInput.fromEntity(user);
    }

    @DgsQuery
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserInput userByEmail(@InputArgument String email) {
        User user = userService.findByEmail(email);
        return UserInput.fromEntity(user);
    }

}
