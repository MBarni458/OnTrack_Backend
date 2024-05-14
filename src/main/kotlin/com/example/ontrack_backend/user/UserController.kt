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

    @PostMapping("/new")
    fun addUser(@RequestBody user: UserEntity) = userService.addUser(user)

    @DeleteMapping(path = ["{userId}"])
    fun deleteUser(@PathVariable("userId") id:Long) = userService.deleteUser(id)
}