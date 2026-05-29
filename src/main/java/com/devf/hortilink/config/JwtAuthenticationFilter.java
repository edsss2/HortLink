package com.devf.hortilink.config; // Ou o pacote de segurança

import com.devf.hortilink.service.impl.AuthService;
import com.devf.hortilink.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component // 1. MUITO IMPORTANTE: Torna o filtro um Bean do Spring
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil; // O seu utilitário que lê tokens

    @Autowired
    private AuthService authService; // O seu serviço que busca usuários

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Pega o cabeçalho "Authorization"
        final String authHeader = request.getHeader("Authorization");

        // 2. Se não houver token ou não for "Bearer", continua sem autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extrai o token (remove o "Bearer ")
        final String jwt = authHeader.substring(7);

        // 4. Extrai o username (email) do token
        final String username = jwtUtil.extractUsername(jwt);

        // 5. Se temos um username E o usuário ainda não foi autenticado nesta requisição
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Extraímos informações importantes diretamente do token para evitar consulta ao banco
            String role = jwtUtil.extractRole(jwt);
            Long userId = jwtUtil.extractUserId(jwt);
            Long commerceId = jwtUtil.extractCommerceId(jwt);

            // Se o token for válido (verifica assinatura/expiração e username)
            if (jwtUtil.validateToken(jwt, username)) {
                // Construímos um UserDetails simples usando os dados do token
                List<SimpleGrantedAuthority> authorities = Collections.emptyList();
                if (role != null) {
                    authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
                }

                UserDetails userDetails = User.withUsername(username)
                        .password("") // sem senha no token
                        .authorities(authorities)
                        .build();

                // Cria a autenticação para o Spring
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Senha (não necessária, estamos usando token)
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Coloca o usuário no Contexto de Segurança do Spring
                SecurityContextHolder.getContext().setAuthentication(authToken);

                
                if (userId != null) request.setAttribute("userId", userId);
                if (commerceId != null) request.setAttribute("commerceId", commerceId);
                if (role != null) request.setAttribute("role", role);
            }
        }
        
        // 10. Passa a requisição para o próximo filtro
        filterChain.doFilter(request, response);
    }
}