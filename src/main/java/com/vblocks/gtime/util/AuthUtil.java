package com.vblocks.gtime.util;

import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.GraphQLContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

public class AuthUtil {
    public static String getAuthenticatedUsername(DgsDataFetchingEnvironment dfe) {
        try {
            // Method 1: Try to get from DGS GraphQL context
            GraphQLContext graphQLContext = dfe.getGraphQlContext();
            Map<String, Object> customContext = graphQLContext.get("customContext");

            if (customContext != null && customContext.containsKey("username")) {
                String username = (String) customContext.get("username");
                if (username != null && !username.isEmpty()) {
                    return username;
                }
            }

            // Method 2: Try direct key access
            Object usernameObj = graphQLContext.get("username");
            if (usernameObj instanceof String && !((String) usernameObj).isEmpty()) {
                return (String) usernameObj;
            }

        } catch (Exception e) {
            // Context not available or empty, fall through to SecurityContext
        }

        // Method 3: Fallback to Spring SecurityContext (most reliable)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {
            return authentication.getName();
        }

        throw new DgsEntityNotFoundException("User not authenticated");
    }
}
