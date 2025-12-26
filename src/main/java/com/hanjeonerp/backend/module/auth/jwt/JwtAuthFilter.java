package com.hanjeonerp.backend.module.auth.jwt;

import com.hanjeonerp.backend.core.exception.BaseException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();
        String uri = request.getRequestURI();
        String method = request.getMethod();

        log.info("[JWT FILTER] ▶ {} {}", method, uri);

        // OPTIONS 요청은 JWT 검사 안 하고 바로 통과
        if ("OPTIONS".equalsIgnoreCase(method)) {
            log.info("[JWT FILTER] OPTIONS request bypass");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = resolveToken(request);

            if (token != null) {
                log.info("[JWT FILTER] token detected");

                jwtTokenProvider.validateTokenOrThrow(token);
                Authentication authentication =
                        jwtTokenProvider.getAuthentication(token);

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);

                log.info("[JWT FILTER] authentication success: {}",
                        authentication.getName());
            } else {
                log.info("[JWT FILTER] no token");
            }

            filterChain.doFilter(request, response);

        } catch (BaseException e) {
            log.error("[JWT FILTER] authentication failed", e);

            // ❗ 여기서 throw 하지 말고 응답을 만들어서 종료
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("""
                {
                  "code": "UNAUTHORIZED",
                  "message": "Invalid or expired JWT token"
                }
            """);
        } catch (Exception e) {
            log.error("[JWT FILTER] unexpected error", e);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("""
                {
                  "code": "SERVER_ERROR",
                  "message": "Internal server error"
                }
            """);
        } finally {
            log.info("[JWT FILTER] ◀ {} {} ({}ms)",
                    method, uri, System.currentTimeMillis() - start);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
