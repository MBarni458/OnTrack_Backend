package com.example.ontrack_backend.user

import com.example.ontrack_backend.model.ActivityEntity
import com.example.ontrack_backend.model.UserEntity
import com.example.ontrack_backend.repository.ActivityRepository
import com.example.ontrack_backend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.Optional

@Service
class UserService @Autowired public constructor(val userRepository: UserRepository, val activityRepository: ActivityRepository) {

    fun getUsers(): List<UserEntity>{
        return userRepository.findAll()
    }

    fun getUser(id:Long) : Optional<UserEntity>{
        return userRepository.findById(id)
    }

    fun getUserActivities(id: Long): Optional<List<ActivityEntity>> =
        activityRepository.findActivityEntitiesByUserId(id)

    fun getUserWeeklyScores(id:Long) = userRepository.findById(id).get().getWeeklyPoint()

    fun getUserDailyScores(id:Long) = userRepository.findById(id).get().getDailyPoint(LocalDate.now())

    fun getUserDailyPlace(id:Long)  = userRepository.findAll().toList()
        .sortedByDescending { it.getDailyPoint(LocalDate.now()) }
        .indexOf(userRepository.findById(id).get())+1

    fun addUser(userEntity: UserEntity){
        userRepository.save(userEntity)
    }

    @Transactional
    fun updateUser(userEntity: UserEntity): UserEntity {
        val existingUser = userRepository.findById(userEntity.id)
        if (existingUser.isEmpty) {
            throw IllegalArgumentException("User with id ${userEntity.id} does not exist")
        }
        val currentUser = existingUser.get()
        currentUser.name = userEntity.name
        return userRepository.save(currentUser)
    }

    fun deleteUser(id:Long){
        val exists = userRepository.existsById(id)
        if (exists){
            userRepository.deleteById(id)
        }
    }
}