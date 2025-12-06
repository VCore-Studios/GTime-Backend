package com.vblocks.gtime.security;

import com.netflix.graphql.dgs.context.DgsCustomContextBuilder;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomContextBuilder implements DgsCustomContextBuilder<Map<String, Object>> {

    @Override
    @Nullable
    public Map<String, Object> build() {
        Map<String, Object> context = new HashMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {

            context.put("username", authentication.getName());
            context.put("authorities", authentication.getAuthorities());
            context.put("isAuthenticated", true);
        } else {
            context.put("isAuthenticated", false);
        }

        return context;
    }
}