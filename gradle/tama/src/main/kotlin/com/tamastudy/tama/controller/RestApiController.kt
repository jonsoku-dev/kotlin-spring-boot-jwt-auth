package com.tamastudy.tama.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RestApiController {

    @GetMapping("home")
    fun home() = "<h1>home</h1>"
}