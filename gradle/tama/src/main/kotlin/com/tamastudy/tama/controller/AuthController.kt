package com.tamastudy.tama.controller

import com.tamastudy.tama.dto.LoginDto
import com.tamastudy.tama.dto.TokenDto
import com.tamastudy.tama.jwt.JwtFilter
import com.tamastudy.tama.jwt.TokenProvider
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping("/api")
class AuthController(private val tokenProvider: TokenProvider, private val authenticationManagerBuilder: AuthenticationManagerBuilder) {
    @PostMapping("/authenticate")
    fun authorize(@RequestBody loginDto: @Valid LoginDto): ResponseEntity<TokenDto?> {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.username, loginDto.password)
        val authentication: Authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken)
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = tokenProvider.createToken(authentication)
        val httpHeaders = HttpHeaders()
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer $jwt")
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(TokenDto().apply {
                    this.token = jwt
                })
    }
}