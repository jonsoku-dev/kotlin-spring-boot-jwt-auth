package com.tamastudy.tama.service

import com.tamastudy.tama.entity.User
import com.tamastudy.tama.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.function.Supplier
import java.util.stream.Collectors


@Component("userDetailsService")
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    @Transactional
    override fun loadUserByUsername(username: String): UserDetails? {
        return userRepository.findOneWithAuthoritiesByUsername(username)
                .map { user -> createUser(username, user) }
                .orElseThrow(Supplier { UsernameNotFoundException("$username -> 데이터베이스에서 찾을 수 없습니다.") })
    }

    private fun createUser(username: String, user: User): org.springframework.security.core.userdetails.User {
        if (user.activated == null) {
            throw RuntimeException("$username -> 활성화되어 있지 않습니다.")
        }
        val grantedAuthorities: List<GrantedAuthority> = user.authorities.stream()
                .map { authority -> SimpleGrantedAuthority(authority.authorityName) }
                .collect(Collectors.toList())
        return org.springframework.security.core.userdetails.User(user.username,
                user.password,
                grantedAuthorities)
    }
}