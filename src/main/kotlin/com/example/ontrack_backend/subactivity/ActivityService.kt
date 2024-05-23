package com.example.ontrack_backend.subactivity

import com.example.ontrack_backend.model.SubActivityEntity
import com.example.ontrack_backend.repository.ActivityRepository
import com.example.ontrack_backend.repository.SubActivityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Service
class SubActivityService @Autowired constructor(
    val subactivityRepository: SubActivityRepository,
    val activityRepository: ActivityRepository
) {

    fun getSubActivities(): List<SubActivityEntity> = subactivityRepository.findAll()

    fun getSubActivity(id: Long): Optional<SubActivityEntity> = subactivityRepository.findById(id)

    @Transactional
    fun addSubActivity(subactivityEntity: SubActivityEntity, activityId: Long): SubActivityEntity {
        val userOptional = activityRepository.findById(activityId)
        require(userOptional.isPresent) { "The requested activityId doesn't exist" }
        subactivityEntity.activityId = activityId
        return subactivityRepository.save(subactivityEntity)
    }

    @Transactional
    fun updateSubActivity(subActivityEntity: SubActivitiesController.SubActivityData): SubActivityEntity {
        val existingSubActivity = subactivityRepository.findById(subActivityEntity.id)
        require(existingSubActivity.isPresent) { "User with id ${subActivityEntity.id} does not exist" }
        val currentSubActivity: SubActivityEntity = existingSubActivity.get().apply {
            name = subActivityEntity.name ?: name
            calorie = subActivityEntity.calorie ?: calorie
            type = subActivityEntity.type ?: type
            createdAt = LocalDate.now()
        }
        return subactivityRepository.save(currentSubActivity)
    }

    fun deleteSubActivity(id: Long) {
        require(subactivityRepository.existsById(id)) { "Activity with id $id does not exist" }
        return subactivityRepository.deleteById(id)
    }
}