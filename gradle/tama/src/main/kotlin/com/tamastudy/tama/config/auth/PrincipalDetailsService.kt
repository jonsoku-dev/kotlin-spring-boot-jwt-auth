package com.tamastudy.tama.config.auth

import com.tamastudy.tama.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

// http://localhost:8080/login 요청이 올때 동작을 한다 !
@Service
class PrincipalDetailsService(
        private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        println("PrincipalDetailsService 의 loadUserByUsername()")
        val userEntity = userRepository.findByUsername(username)
        return PrincipalDetails(userEntity)
    }
}