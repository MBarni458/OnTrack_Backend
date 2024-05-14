package com.example.ontrack_backend.user

import com.example.ontrack_backend.model.ActivityEntity
import com.example.ontrack_backend.model.UserEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["api/users"])
class UserController @Autowired constructor(private final val userService: UserService) {


    @GetMapping()
    fun getAllUser() = userService.getUsers()

    @GetMapping(path = ["{userId}"])
    fun getUser(@PathVariable("userId") id:Long) = userService.getUser(id)

    @GetMapping(path=["/activities/{userId}"])
    fun getUserActivities(@PathVariable("userId") id:Long) = userService.getUserActivities(id)

    @GetMapping(path=["/scores/weekly/{userId}"])
    fun getUserWeeklyScore(@PathVariable("userId") id:Long) = userService.getUserWeeklyScores(id)

    @GetMapping(path=["/scores/daily/{userId}"])
    fun getUserDailyScore(@PathVariable("userId") id:Long) = userService.getUserDailyScores(id)

    @GetMapping(path=["/place/daily/{userId}"])
    fun getUserDailyPlace(@PathVariable("userId") id:Long) = userService.getUserDailyPlace(id)

    @PostMapping("/new")
    fun addUser(@RequestBody user: UserEntity) = userService.addUser(user)

    @PatchMapping()
    fun updateUser(@RequestBody user: UserEntity) = userService.updateUser(user)

    @DeleteMapping(path = ["{userId}"])
    fun deleteUser(@PathVariable("userId") id:Long) = userService.deleteUser(id)
}