package com.example.ontrack_backend.subactivity

import com.example.ontrack_backend.model.ActivityEntity
import com.example.ontrack_backend.model.SubActivityEntity
import com.example.ontrack_backend.model.UserEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["api/subactivities"])
class SubActivitiesController @Autowired constructor(private final val subactivityService: SubActivityService) {

    @GetMapping()
    fun getAllSubactivities() = subactivityService.getSubActivities()

    @GetMapping(path = ["{subactivityId}"])
    fun getSubactivity(@PathVariable("subactivityId") id:Long) = subactivityService.getSubActivity(id)

    @PostMapping(path = ["{activityId}"])
    fun addSubactivity(@RequestBody subactivity: SubActivityEntity, @PathVariable("activityId") id:Long) = subactivityService.addSubActivity(subactivity, id)

    @PatchMapping()
    fun updateSubactivity(@RequestBody subactivity: SubActivityEntity) = subactivityService.updateSubActivity(subactivity)

    @DeleteMapping(path = ["{subactivityId}"])
    fun deleteSubactivity(@PathVariable("subactivityId") id:Long) = subactivityService.deleteSubActivity(id)
}