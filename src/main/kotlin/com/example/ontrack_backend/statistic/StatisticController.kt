package com.example.ontrack_backend.statistic

import com.example.ontrack_backend.model.UserEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["api/statistics"])
class StatisticController @Autowired constructor(private final val userService: StatisticService) {


    @GetMapping("/top")
    fun getTopTen() = userService.getTopTen()

    @GetMapping("/popularfood")
    fun getPopularFood() = userService.getPopularFood()

}