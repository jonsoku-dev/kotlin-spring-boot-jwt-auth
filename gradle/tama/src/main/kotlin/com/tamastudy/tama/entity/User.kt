package com.tamastudy.tama.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,
        var username: String,
        var password: String,
        var roles: String = ""// ROLE_USER, ROLE_MANAGER, ROLE_ADMIN
) {
    fun getRoleList(): MutableList<String> {
        val list = mutableListOf<String>()
        if (this.roles.isNotEmpty()) {
            this.roles.split(",").let { roleList ->
                roleList.forEach { role ->
                    list.add(role)
                }
                return list
            }
        }
        return list
    }
}