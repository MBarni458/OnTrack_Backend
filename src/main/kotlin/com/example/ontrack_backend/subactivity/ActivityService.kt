package com.example.ontrack_backend.subactivity

import com.example.ontrack_backend.model.SubActivityEntity
import com.example.ontrack_backend.repository.ActivityRepository
import com.example.ontrack_backend.repository.SubActivityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Service
class SubActivityService @Autowired constructor(
    val subactivityRepository: SubActivityRepository,
    val activityRepository: ActivityRepository
) {

    fun getSubActivities(): List<SubActivityEntity> {
        return subactivityRepository.findAll()
    }

    fun getSubActivity(id: Long): Optional<SubActivityEntity> {
        return subactivityRepository.findById(id)
    }

    @Transactional
    fun addSubActivity(subactivityEntity: SubActivityEntity, activityId: Long): SubActivityEntity {
        val userOptional = activityRepository.findById(activityId)
        if (userOptional.isEmpty) {
            throw IllegalArgumentException("The requested userId doesn't exist")
        }
        subactivityEntity.activityId=activityId

        return subactivityRepository.save(subactivityEntity)
    }

    @Transactional
    fun updateSubActivity(subActivityEntity: SubActivityEntity): SubActivityEntity {
        val existingSubActivity = subactivityRepository.findById(subActivityEntity.id)
        if (existingSubActivity.isEmpty) {
            throw IllegalArgumentException("User with id ${subActivityEntity.id} does not exist")
        }
        val currentSubActivity = existingSubActivity.get()
        currentSubActivity.name = subActivityEntity.name
        currentSubActivity.calorie=subActivityEntity.calorie
        currentSubActivity.type=subActivityEntity.type
        return subactivityRepository.save(currentSubActivity)
    }

    fun deleteSubActivity(id: Long) {
        val exists = subactivityRepository.existsById(id)
        if (exists) {
            subactivityRepository.deleteById(id)
        } else {
            throw IllegalArgumentException("Activity with id $id does not exist")
        }
    }
}