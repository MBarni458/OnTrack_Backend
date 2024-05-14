package com.example.ontrack_backend.user

import com.example.ontrack_backend.model.ActivityEntity
import com.example.ontrack_backend.model.UserEntity
import com.example.ontrack_backend.repository.ActivityRepository
import com.example.ontrack_backend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
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

    fun addUser(userEntity: UserEntity){
        userRepository.save(userEntity)
    }

    fun deleteUser(id:Long){
        val exists = userRepository.existsById(id)
        if (exists){
            userRepository.deleteById(id)
        }
    }
}