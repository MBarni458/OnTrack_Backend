package com.example.ontrack_backend.auth

import com.example.ontrack_backend.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

class AuthController {
    @RestController
    @RequestMapping("/auth")
    class AuthController @Autowired constructor(val userService: UserService) {

        companion object {
            var currentUserId: Long? = null

            fun Long?.isLoggedIn(id:Long): Boolean{
                return this == id
            }
        }

        @PostMapping("/login")
        fun login(@RequestParam username: String, @RequestParam password: String): ResponseEntity<String> {
            val user = userService.findUserByUsername(username)
            return if (user != null && user.password == password) {
               currentUserId = user.id
                ResponseEntity.ok("Login successful")
            } else {
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials")
            }
        }

        @PostMapping("/register")
        fun register(@RequestParam username: String, @RequestParam password: String): ResponseEntity<String> {
            return try {
                userService.registerUser(username,password)
                ResponseEntity.ok("Registration successful")
            } catch (e: IllegalArgumentException) {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
            }
        }

        @PostMapping("/logout")
        fun logout(){
            currentUserId=null
        }

    }
}