package com.example.ontrack_backend.activity

import com.example.ontrack_backend.model.ActivityEntity
import com.example.ontrack_backend.model.UserEntity
import com.example.ontrack_backend.repository.ActivityRepository
import com.example.ontrack_backend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Service
class ActivityService @Autowired constructor(
    val activityRepository: ActivityRepository,
    val userRepository: UserRepository
) {

    fun getActivities(): List<ActivityEntity> {
        return activityRepository.findAll()
    }

    fun getActivity(id: Long): Optional<ActivityEntity> {
        return activityRepository.findById(id)
    }

    @Transactional
    fun addActivity(activityEntity: ActivityEntity, userId: Long): ActivityEntity {
        val userOptional = userRepository.findById(userId)
        if (userOptional.isEmpty) {
            throw IllegalArgumentException("The requested userId doesn't exist")
        }
        activityEntity.userId = userId
        return activityRepository.save(activityEntity)
    }

    @Transactional
    fun updateActivity(activityEntity: ActivityEntity): ActivityEntity {
        val existingActivity = activityRepository.findById(activityEntity.id)
        if (existingActivity.isEmpty) {
            throw IllegalArgumentException("Activity with id ${activityEntity.id} does not exist")
        }
        val currentActivity = existingActivity.get()
        currentActivity.name = activityEntity.name
        currentActivity.createdAt = activityEntity.createdAt
        currentActivity.userId = activityEntity.userId
        return activityRepository.save(currentActivity)
    }

    fun deleteActivity(id: Long) {
        val exists = activityRepository.existsById(id)
        if (exists) {
            activityRepository.deleteById(id)
        } else {
            throw IllegalArgumentException("Activity with id $id does not exist")
        }
    }
}