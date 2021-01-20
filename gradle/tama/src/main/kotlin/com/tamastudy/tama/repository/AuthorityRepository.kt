package com.tamastudy.tama.repository

import com.tamastudy.tama.entity.Authority

import org.springframework.data.jpa.repository.JpaRepository

interface AuthorityRepository : JpaRepository<Authority, String>