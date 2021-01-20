package com.tamastudy.tama.entity

import javax.persistence.*

@Entity
data class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        var id: Long? = null,

        var username: String? = null,

        @Column(length = 100)
        var password: String? = null,

        @Column(length = 50)
        var nickname: String? = null,

        var activated: Boolean? = null,

        @ManyToMany
        @JoinTable(
                name = "user_authority",
                joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "authority_name", referencedColumnName = "authority_name")]
        )
        var authorities: Set<Authority>
)