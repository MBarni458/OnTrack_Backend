package com.example.ontrack_backend.activity

import com.example.ontrack_backend.model.ActivityEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["api/activities"])
class ActivitiesController @Autowired constructor(private final val activityService: ActivityService) {


    @GetMapping()
    fun getAllActivities() = activityService.getActivities()

    @GetMapping(path = ["{activityId}"])
    fun getActivity(@PathVariable("activityId") id:Long) = activityService.getActivity(id)

    @PostMapping(path = ["{userId}"])
    fun addActivity(@RequestBody activity:ActivityEntity, @PathVariable("userId") id:Long) = activityService.addActivity(activity, id)

    @PatchMapping()
    fun updateActivity(@RequestBody activity:ActivityEntity) = activityService.updateActivity(activity)

    @DeleteMapping(path = ["{activityId}"])
    fun deleteActivity(@PathVariable("activityId") id:Long) = activityService.deleteActivity(id)
}