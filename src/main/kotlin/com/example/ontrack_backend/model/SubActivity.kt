package com.example.ontrack_backend.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "subactivity") // Specify the table name if different from class name
class SubActivityEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var type: String = "any",

    @Column(nullable = false)
    var calorie: Double = 0.00,

    @Column(nullable = false)
    var activityId: Long = 0,

    @Column(nullable = false)
    var createdAt: LocalDate = LocalDate.now()
)