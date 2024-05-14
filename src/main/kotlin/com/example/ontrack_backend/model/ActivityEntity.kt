package com.example.ontrack_backend.model

import jakarta.persistence.*
import java.time.LocalDateTime
import kotlin.jvm.Transient

@Entity
@Table(name = "activity") // Specify the table name if different from class name
class ActivityEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long=0,
    @Column(nullable = false)
    var name: String="",

    @Column(nullable = false)
    var createdAt:LocalDateTime = LocalDateTime.now(),

    @Column(nullable = true)
    var userId: Long=0,

    @OneToMany
    @JoinColumn(name="activityId")
    var subActivities: List<SubActivityEntity> = mutableListOf()
) {
    fun getConsumedCalories() = subActivities.filter { it.type=="food" } .sumOf { it.calorie }
    fun getBurnedCalories() = subActivities.filter { it.type=="sport" } .sumOf { it.calorie }
    fun getTotalCalories() = getConsumedCalories()-getBurnedCalories()
}