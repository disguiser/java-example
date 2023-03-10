package org.example.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF禁用，因为不使用session
                .csrf().disable()
                // 禁用HTTP响应标头
                .headers().cacheControl().disable().and()
                // 认证失败处理类
                .exceptionHandling()
                .authenticationEntryPoint(((request, response, authException) -> {
//                    System.out.println("==================");
//                    System.out.println(authException);
//                    System.out.println("==================");
//                    String msg = "请求访问：" + request.getRequestURI() + "，认证失败，无法访问系统资源";
//                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                    response.setContentType("text/plain;charset=UTF-8");
//                    response.setCharacterEncoding("UTF-8");
//                    response.getWriter().print(msg);
//                    response.sendError(
//                            HttpServletResponse.SC_UNAUTHORIZED,
//                            authException.getMessage()
//                    );
                }))
                .accessDeniedHandler((request, response, exception) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().print("权限不足");
                })
                .and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/user/login", "/user/register").permitAll()
                        .requestMatchers("/hello/admin").hasRole("Admin")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.permitAll()
                    .logoutSuccessHandler((request, response, authentication) -> {
                        response.setStatus(HttpServletResponse.SC_OK);
                    })
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MyMD5PasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 在这个单独的小节中，我想强调一个更微妙的细节，这个细节会让很多新用户感到困惑。
     *
     * Spring Security 框架区分了两个术语：
     *
     * Authority代表一个单独的权限。
     * Role代表一组权限。
     * 两者都可以用一个调用的接口来表示GrantedAuthority，然后在 Spring Security 注释中使用 Spring 表达式语言进行检查，如下所示：
     *
     * Authority: @PreAuthorize(“hasAuthority('EDIT_BOOK')”)
     * Role: @PreAuthorize(“hasRole('BOOK_ADMIN')”)
     * 为了更明确地区分这两个术语，Spring Security 框架ROLE_默认为角色名称添加前缀。因此，它不会检查名为 的角色BOOK_ADMIN，而是检查ROLE_BOOK_ADMIN.
     *
     * 自定义前缀
     * @return
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("ROLE_");
    }
//    @Bean
//    public AuthenticationFailureHandler authenticationFailureHandler() {
//        return new CustomAuthenticationFailureHandler();
//    }
    // Used by Spring Security if CORS is enabled.
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source =
//                new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
}
