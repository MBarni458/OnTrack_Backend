package com.example.ontrack_backend.model

import jakarta.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "users")
class UserEntity constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @Column(nullable = false)
    var name: String = "",

    @OneToMany
    @JoinColumn(name="userId")
    var activities: List<ActivityEntity> = mutableListOf()

) {
    fun getPoints()=activities.sumOf { it.getTotalCalories() }.toInt()
}
