package com.example.ontrack_backend.repository

import com.example.ontrack_backend.model.ActivityEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ActivityRepository: JpaRepository<ActivityEntity, Long>{

     fun findActivityEntitiesByUserId(@Param("userId") id:Long): Optional<List<ActivityEntity>>

}