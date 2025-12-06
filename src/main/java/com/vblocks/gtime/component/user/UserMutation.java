package com.vblocks.gtime.component.user;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import com.vblocks.gtime.dto.auth.AuthPayload;
import com.vblocks.gtime.dto.auth.RegisterInput;
import com.vblocks.gtime.entity.Role;
import com.vblocks.gtime.entity.User;
import com.vblocks.gtime.service.JwtTokenProvider;
import com.vblocks.gtime.service.RefreshTokenService;
import com.vblocks.gtime.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.vblocks.gtime.util.AuthUtil.getAuthenticatedUsername;

@DgsComponent
@AllArgsConstructor
public class UserMutation {

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    private UserService userService;

    private RefreshTokenService refreshTokenService;

    // ==================== MUTATIONS ====================

    @DgsMutation
    public AuthPayload login(@InputArgument String username, @InputArgument String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userService.findByUsername(username);
            List<String> roles = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toList());

            String accessToken = jwtTokenProvider.generateAccessToken(username, roles);
            String refreshToken = jwtTokenProvider.generateRefreshToken(username);

            refreshTokenService.saveRefreshToken(username, refreshToken);

            return new AuthPayload(accessToken, refreshToken, user);

        } catch (Exception e) {
            throw new DgsEntityNotFoundException("Invalid username or password");
        }
    }

    @DgsMutation
    public AuthPayload register(@InputArgument("input") RegisterInput input) {
        try {
            Set<String> roles = new HashSet<>();
            roles.add("USER");

            User user = userService.createUser(
                    input.getUsername(),
                    input.getEmail(),
                    input.getPassword(),
                    roles
            );

            List<String> roleNames = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toList());

            String accessToken = jwtTokenProvider.generateAccessToken(user.getUsername(), roleNames);
            String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUsername());

            refreshTokenService.saveRefreshToken(user.getUsername(), refreshToken);

            return new AuthPayload(accessToken, refreshToken, user);

        } catch (IllegalArgumentException e) {
            throw new DgsEntityNotFoundException(e.getMessage());
        }
    }

    @DgsMutation
    public AuthPayload refreshToken(@InputArgument String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new DgsEntityNotFoundException("Invalid or expired refresh token");
        }

        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);

        if (!refreshTokenService.isValidRefreshToken(username, refreshToken)) {
            throw new DgsEntityNotFoundException("Refresh token not found, expired, or revoked");
        }

        User user = userService.findByUsername(username);
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        String newAccessToken = jwtTokenProvider.generateAccessToken(username, roles);

        return new AuthPayload(newAccessToken, refreshToken, user);
    }

    @DgsMutation
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public Boolean logout(@InputArgument String refreshToken) {
        try {
            refreshTokenService.revokeToken(refreshToken);
            SecurityContextHolder.clearContext();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @DgsMutation
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public Boolean logoutAll(DgsDataFetchingEnvironment dfe) {
        String username = getAuthenticatedUsername(dfe);
        refreshTokenService.revokeAllUserTokens(username);
        SecurityContextHolder.clearContext();
        return true;
    }
}