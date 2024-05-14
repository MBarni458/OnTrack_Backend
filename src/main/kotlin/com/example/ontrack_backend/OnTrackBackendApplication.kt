package com.example.ontrack_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
@EntityScan("com.example.ontrack_backend.model")
@EnableJpaRepositories("com.example.ontrack_backend.repository")
class OnTrackBackendApplication {

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			runApplication<OnTrackBackendApplication>(*args)
		}
	}
	@GetMapping("/Hello")
	fun hello() = "Hello!"

}