package com.example.ontrack_backend.auth

import com.example.ontrack_backend.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

class AuthController {
    @RestController
    @RequestMapping("api/auth")
    class AuthController @Autowired constructor(val userService: UserService) {

        companion object {
            var currentUserId: Long? = null

            fun Long?.isLoggedIn(id:Long): Boolean{
                return this == id
            }
        }

        data class UserData(val username: String, val password: String)

        @PostMapping("/login")
        fun login(@RequestBody userData:UserData): ResponseEntity<String> {
            val (username,password) = userData
            val user = userService.findUserByUsername(username)
            return if (user != null && user.password == password) {
               currentUserId = user.id
                ResponseEntity.ok("Login successful")
            } else {
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials")
            }
        }

        @PostMapping("/register")
        fun register(@RequestBody userData:UserData): ResponseEntity<String> {
            return try {
                val (username,password) = userData
                userService.registerUser(username,password)
                ResponseEntity.ok("Registration successful")
            } catch (e: IllegalArgumentException) {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
            }
        }

        @PostMapping("/logout")
        fun logout(){
            currentUserId=null
            ResponseEntity.ok("Logout successful")
        }

    }
}