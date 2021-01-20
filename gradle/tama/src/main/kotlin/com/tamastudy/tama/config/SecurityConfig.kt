package com.tamastudy.tama.config

import com.tamastudy.tama.jwt.JwtAccessDeniedHandler
import com.tamastudy.tama.jwt.JwtAuthenticationEntryPoint
import com.tamastudy.tama.jwt.JwtSecurityConfig
import com.tamastudy.tama.jwt.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.CorsFilter


@EnableWebSecurity
class SecurityConfig(
        private val tokenProvider: TokenProvider,
        private val corsFilter: CorsFilter,
        private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
        private val jwtAccessDeniedHandler: JwtAccessDeniedHandler
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    override fun configure(web: WebSecurity) {
        web.ignoring()
                .antMatchers("/h2-console/**", "/favicon.ico", "/error")
    }

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity
                .csrf().disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter::class.java)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler) // enable h2-console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/hello").permitAll()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/signup").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(JwtSecurityConfig(tokenProvider))
    }
}