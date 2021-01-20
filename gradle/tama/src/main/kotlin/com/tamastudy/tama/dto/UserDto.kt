package com.tamastudy.tama.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


data class UserDto(
        @field:NotNull
        @field:Size(min = 3, max = 50)
        private var username: String? = null,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @field:NotNull
        @field:Size(min = 3, max = 100)
        private var password: String? = null,

        @field:NotNull
        @field:Size(min = 3, max = 50)
        private var nickname: String? = null
)