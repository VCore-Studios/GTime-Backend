package com.vblocks.gtime.service;

import com.vblocks.gtime.entity.RefreshToken;
import com.vblocks.gtime.entity.User;
import com.vblocks.gtime.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserService userService;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration; // in milliseconds

    @Transactional
    public RefreshToken saveRefreshToken(String username, String token) {
        User user = userService.findByUsername(username);

        // Optional: Revoke all previous refresh tokens for this user
        // revokeAllUserTokens(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setUser(user);
        refreshToken.setExpiresAt(LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000));
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public boolean isValidRefreshToken(String username, String token) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(token);

        if (refreshTokenOpt.isEmpty()) {
            return false;
        }

        RefreshToken refreshToken = refreshTokenOpt.get();

        return refreshToken.getUser().getUsername().equals(username)
                && refreshToken.isValid();
    }

    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public void revokeToken(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(refreshToken -> {
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);
        });
    }

    @Transactional
    public void revokeAllUserTokens(User user) {
        List<RefreshToken> tokens = refreshTokenRepository.findByUser(user);
        tokens.forEach(token -> token.setRevoked(true));
        refreshTokenRepository.saveAll(tokens);
    }

    @Transactional
    public void revokeAllUserTokens(String username) {
        User user = userService.findByUsername(username);
        revokeAllUserTokens(user);
    }

    @Transactional
    public void deleteExpiredTokens() {
        refreshTokenRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }

    @Transactional
    public void deleteAllUserTokens(String username) {
        User user = userService.findByUsername(username);
        refreshTokenRepository.deleteByUser(user);
    }
}