package com.tamastudy.tama.controller

import com.tamastudy.tama.entity.User
import com.tamastudy.tama.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class RestApiController(
        private val userRepository: UserRepository,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {

    @GetMapping("home")
    fun home() = "<h1>home</h1>"

    @PostMapping("token")
    fun token() = "<h1>token</h1>"

    @PostMapping("join")
    fun join(@RequestBody user: User): String? {
        println(user)
        user.password = bCryptPasswordEncoder.encode(user.password)
        user.roles = "ROLE_USER"
        userRepository.save(user)
        return "회원가입완료"
    }
}