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
class UserService @Autowired public constructor(
    val userRepository: UserRepository,
    val activityRepository: ActivityRepository
) {

    data class UserSummary(
        val id: Long,
        val name: String,
        val dailyPoints: Int,
        val weeklyPoints: Int,
        val dailyPlace: Int
    )

    fun UserEntity.toUserSummary() =
        UserSummary(id, username, getUserDailyScores(id), getUserWeeklyScores(id), getUserDailyPlace(id))

    fun getUsers(): List<UserSummary> = userRepository.findAll().map { it.toUserSummary() }


    fun getUser(id: Long): Optional<UserEntity> = userRepository.findById(id)


    fun findUserByUsername(username: String): UserEntity? = userRepository.findByUsername(username)


    fun getUserActivities(id: Long): Optional<List<ActivityEntity>> {
        return if (id == currentUserId) {
            activityRepository.findActivityEntitiesByUserId(id)
        } else {
            Optional.empty()
        }
    }

    fun getUserWeeklyScores(id: Long) = userRepository.findById(id).get().getWeeklyPoint()

    fun getUserDailyScores(id: Long) = userRepository.findById(id).get().getDailyPoint(LocalDate.now())

    fun getUserDailyPlace(id: Long) =
        userRepository.findAll().toList()
            .sortedByDescending { it.getDailyPoint(LocalDate.now()) }
            .indexOf(userRepository.findById(id).get()) + 1

    @Transactional
    fun updateUser(userEntity: UserEntity): UserEntity {
        require(currentUserId == userEntity.id) { "You are not authorized to edit this user" }
        val existingUser = userRepository.findById(userEntity.id)
        require(existingUser.isPresent) { "User with id ${userEntity.id} does not exist" }
        require(userEntity.id == existingUser.get().id || userRepository.findByUsername(userEntity.username) == null) { "This username has already taken" }
        val currentUser = existingUser.get().apply {
            username = userEntity.username
            password = userEntity.password
        }
        return userRepository.save(currentUser)
    }

    fun deleteUser(id: Long) {
        require(userRepository.existsById(id)) { "User with id $id does not exist" }
        userRepository.deleteById(id)
    }

    fun registerUser(username: String, password: String) {
        require(userRepository.findByUsername(username) == null) { "Username is already taken" }
        val newUser = UserEntity(
            username = username,
            password = password
        )
        userRepository.save(newUser)
    }
}