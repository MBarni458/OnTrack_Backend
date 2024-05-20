package com.example.ontrack_backend.statistic

import com.example.ontrack_backend.repository.ActivityRepository
import com.example.ontrack_backend.repository.SubActivityRepository
import com.example.ontrack_backend.repository.UserRepository
import com.example.ontrack_backend.statistic.dataclasses.TypeAndCount
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.math.min

@Service
class StatisticService @Autowired public constructor(val userRepository: UserRepository, val activityRepository: ActivityRepository, val subActivityRepository: SubActivityRepository) {

    fun getTopTen(): Map<String,Int>{
        val users= userRepository.findAll().toList().sortedByDescending { it.getPoints() }
        return users.subList(0, min(users.size, 9)).associate { it.username to it.getPoints() }
    }

    fun getPopularType(type:String): TypeAndCount {
        return try{
            val foods = subActivityRepository.findAll().filter { it.type==type }
            val popularity = HashMap<String, Int>();
            for (food in foods){
                popularity[food.name] = popularity[food.name]?.plus(1) ?: 1
            }
        val max=popularity.maxBy { it.value }.toPair()
            TypeAndCount(max.first,max.second)
        } catch (e:Exception){
            TypeAndCount(type,0)
        }
    }

    fun getAverageScore()= userRepository.findAll().map { it.getPoints() }.average()

}