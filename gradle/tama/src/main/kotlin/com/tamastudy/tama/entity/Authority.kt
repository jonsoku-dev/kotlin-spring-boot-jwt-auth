package com.tamastudy.tama.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Authority(
        @Id
        @Column(name = "authority_name", length = 50)
        var authorityName: String? = null
)