package com.tamastudy.tama.dto

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class LoginDto(
        @field:NotNull
        @field:Size(min = 3, max = 50)
        val username: String,

        @field:NotNull
        @field:Size(min = 3, max = 100)
        val password: String
)