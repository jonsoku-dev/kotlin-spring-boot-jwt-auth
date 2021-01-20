package com.tamastudy.tama.entity

import javax.persistence.*

@Entity
@Table(name = "hello")
data class Hello(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        var name: String? = null
)