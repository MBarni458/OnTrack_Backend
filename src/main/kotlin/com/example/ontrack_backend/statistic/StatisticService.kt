package com.example.ontrack_backend.statistic

import com.example.ontrack_backend.model.ActivityEntity
import com.example.ontrack_backend.model.SubActivityEntity
import com.example.ontrack_backend.model.UserEntity
import com.example.ontrack_backend.repository.ActivityRepository
import com.example.ontrack_backend.repository.SubActivityRepository
import com.example.ontrack_backend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional
import kotlin.math.min

@Service
class StatisticService @Autowired public constructor(val userRepository: UserRepository, val activityRepository: ActivityRepository, val subActivityRepository: SubActivityRepository) {

    fun getTopTen(): List<UserEntity>{
        val users= userRepository.findAll().toList().sortedByDescending { it.getPoints() }
        return users.subList(0,min(users.size,9))
    }

    fun getPopularFood(): SubActivityEntity{
            val foods = subActivityRepository.findAll().filter { it.type=="food" }
            val popularity = HashMap<SubActivityEntity, Int>();
            for (food in foods){
                popularity[food] = popularity[food]?.plus(1) ?: 1
            }
        val maxEntry = popularity.maxBy { it.value }
        return maxEntry.key
    }

    fun getAverageScore()= userRepository.findAll().map { it.getPoints() }.average()

}