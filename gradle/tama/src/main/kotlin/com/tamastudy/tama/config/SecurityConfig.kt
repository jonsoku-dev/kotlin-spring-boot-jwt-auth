package com.tamastudy.tama.config

import com.tamastudy.tama.config.jwt.JwtAuthenticationFilter
import com.tamastudy.tama.filter.MyFilter3
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.context.SecurityContextPersistenceFilter
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
        private val corsFilter: CorsFilter
): WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable() // token 방식으로 사용
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 이용하지 않음
                .and()
                .addFilter(corsFilter) // @CrossOrigin(인증X), 시큐리티 필터에 등록 인증 (O) -> 여기서 한방에
                .formLogin().disable() // jwt 사용하므로 formLogin 을 사용하지 않는다.
                .httpBasic().disable()
                .addFilter(JwtAuthenticationFilter(authenticationManager()))
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginProcessingUrl("/")
    }
}