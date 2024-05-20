package com.example.ontrack_backend.user

import com.example.ontrack_backend.auth.AuthController.AuthController.Companion.currentUserId
import com.example.ontrack_backend.model.ActivityEntity
import com.example.ontrack_backend.model.UserEntity
import com.example.ontrack_backend.repository.ActivityRepository
import com.example.ontrack_backend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Service
class UserService @Autowired public constructor(val userRepository: UserRepository, val activityRepository: ActivityRepository) {

    fun getUsers(): List<UserEntity> {
        return userRepository.findAll()
    }

    fun getUser(id: Long): Optional<UserEntity> {
        return userRepository.findById(id)
    }

    fun findUserByUsername(username: String): UserEntity? {
        return userRepository.findByUsername(username)
    }
    fun getUserActivities(id: Long): Optional<List<ActivityEntity>> {
        return if (id == currentUserId) {
            activityRepository.findActivityEntitiesByUserId(id)
        } else {
            Optional.empty()
        }
    }

    fun getUserWeeklyScores(id:Long): Int {
        return if (id == currentUserId) {
            userRepository.findById(id).get().getWeeklyPoint()
        } else {
            0
        }
    }

    fun getUserDailyScores(id:Long):Int {
        return if (id == currentUserId) {
        userRepository.findById(id).get().getDailyPoint(LocalDate.now())
        } else {
            0
        }
    }

    fun getUserDailyPlace(id:Long):Int {
        return if (id == currentUserId) {
        userRepository.findAll().toList()
            .sortedByDescending { it.getDailyPoint(LocalDate.now()) }
            .indexOf(userRepository.findById(id).get()) + 1
    } else {
        0
    }
    }

    fun addUser(userEntity: UserEntity){
        userRepository.save(userEntity)
    }

    @Transactional
    fun updateUser(userEntity: UserEntity): UserEntity {
        return if (currentUserId == userEntity.id) {
            val existingUser = userRepository.findById(userEntity.id)
            if (existingUser.isEmpty) {
                throw IllegalArgumentException("User with id ${userEntity.id} does not exist")
            }
            val currentUser = existingUser.get()
            currentUser.username = userEntity.username
            userRepository.save(currentUser)
        } else{
            throw IllegalArgumentException("You are not authorized to edit this user")
        }
    }

    fun deleteUser(id:Long){
        val exists = userRepository.existsById(id)
        if (exists){
            userRepository.deleteById(id)
        }
    }

    fun registerUser(username:String, password:String) {
        if (userRepository.findByUsername(username) != null) {
            throw IllegalArgumentException("Username is already taken")
        }
        val newUser = UserEntity(
            username = username,
            password = password
        )
        userRepository.save(newUser)
    }
}