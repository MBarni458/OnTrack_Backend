package com.example.ontrack_backend.activity

import com.example.ontrack_backend.auth.AuthController.AuthController.Companion.currentUserId
import com.example.ontrack_backend.auth.AuthController.AuthController.Companion.isLoggedIn
import com.example.ontrack_backend.model.ActivityEntity
import com.example.ontrack_backend.repository.ActivityRepository
import com.example.ontrack_backend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class ActivityService @Autowired constructor(
    val activityRepository: ActivityRepository,
    val userRepository: UserRepository
) {

    data class ActivityInfo(
        val id:Long,
        val name:String,
        val totalCalories:Double,
        val caloriesConsumed:Double,
        val caloriesBurned: Double,
    )

    fun ActivityEntity.toActivityInfo()=
        ActivityInfo(this.id, this.name,this.getTotalCalories(), this.getConsumedCalories(), this.getBurnedCalories())

    fun getActivities(): List<ActivityInfo>
        = activityRepository.findAll().map { it.toActivityInfo() }

    fun getActivity(id: Long): Optional<ActivityEntity>
        = activityRepository.findById(id)

    @Transactional
    fun addActivity(activityEntity: ActivityEntity, userId: Long): ActivityEntity {
        require(userRepository.findById(userId).isPresent) {"The requested userId doesn't exist"}
        require(currentUserId.isLoggedIn(userId)) {"You can only add activities to your own profile"}
        activityEntity.userId = userId
        return activityRepository.save(activityEntity)
    }

    @Transactional
    fun updateActivity(activityEntity: ActivityEntity): ActivityEntity {
        val existingActivity = activityRepository.findById(activityEntity.id)
        require(existingActivity.isPresent) {"Activity with id ${activityEntity.id} does not exist"}
        val currentActivity = existingActivity.get().apply {
            name = activityEntity.name
            createdAt = LocalDateTime.now()
            userId = activityEntity.userId
        }
        return activityRepository.save(currentActivity)
    }

    fun deleteActivity(id: Long) {
        require (activityRepository.existsById(id)) {"Activity with id $id does not exist"}
        activityRepository.deleteById(id)
    }
}