package com.example.ontrack_backend.statistic

import com.example.ontrack_backend.repository.SubActivityRepository
import com.example.ontrack_backend.repository.UserRepository
import com.example.ontrack_backend.statistic.dataclasses.TypeAndCount
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.math.min

@Service
class StatisticService @Autowired public constructor(
    val userRepository: UserRepository,
    val subActivityRepository: SubActivityRepository
) {

    fun getTopTen(): Map<String, Int> = userRepository.findAll().toList().sortedByDescending { it.getPoints() }.run {
        subList(0, min(size, 9)).associate { it.username to it.getPoints() }
    }

    fun getPopularType(type: String): TypeAndCount {
        val max = subActivityRepository.findAll().filter { it.type == type }.groupingBy { it.name }.eachCount()
            .maxByOrNull { it.value }?.toPair()
        return if (max != null) {
            TypeAndCount(max.first, max.second)
        } else {
            TypeAndCount(type, 0)
        }
    }

    fun getAverageScore() = userRepository.findAll().map { it.getPoints() }.average()

}