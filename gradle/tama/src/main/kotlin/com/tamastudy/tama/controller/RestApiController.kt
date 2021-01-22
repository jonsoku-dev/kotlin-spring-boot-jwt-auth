package com.tamastudy.tama.controller

import com.tamastudy.tama.config.auth.PrincipalDetails
import com.tamastudy.tama.entity.User
import com.tamastudy.tama.repository.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/v1")
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

    @GetMapping("user")
    fun user() = "<h1>user</h1>"

    // 매니저 혹은 어드민이 접근 가능
    @GetMapping("manager/reports")
    fun reports() = "<h1>reports</h1>"

    // 어드민이 접근 가능
    @GetMapping("admin/users")
    fun users() = userRepository.findAll()
}