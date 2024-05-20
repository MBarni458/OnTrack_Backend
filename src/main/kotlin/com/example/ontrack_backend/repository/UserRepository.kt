package com.example.ontrack_backend.repository

import com.example.ontrack_backend.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<UserEntity, Long>{

    fun findByUsername(username: String): UserEntity?
}