package com.example.ontrack_backend.repository

import com.example.ontrack_backend.model.SubActivityEntity
import com.example.ontrack_backend.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SubActivityRepository: JpaRepository<SubActivityEntity, Long>