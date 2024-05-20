package com.example.ontrack_backend.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*
import kotlin.math.max

@Entity
@Table(name = "users")
open class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @Column(nullable = false)
    var username: String,

    @Column(nullable = false)
    var password: String,

    @OneToMany
    @JoinColumn(name="userId")
    var activities: List<ActivityEntity> = mutableListOf()

) {
    protected constructor() : this(0, "", "", mutableListOf())

    private fun isSameDay(date1: LocalDate, date2: LocalDate): Boolean{
        val pattern = "yyyy-MM-dd"
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val localDate1 = LocalDate.parse(date1.toString(), formatter)
        val localDate2 = LocalDate.parse(date2.toString(), formatter)
        return localDate1.isEqual(localDate2)
    }

    fun isSameWeek(date1: LocalDate, date2: LocalDate): Boolean {
        val weekFields = WeekFields.of(Locale.getDefault())
        val week1 = date1.get(weekFields.weekOfWeekBasedYear())
        val week2 = date2.get(weekFields.weekOfWeekBasedYear())
        return week1 == week2 && date1.year == date2.year
    }

    fun getDailyPoint(date:LocalDate)
    = activities
        .filter {isSameDay(it.createdAt.toLocalDate(),date) }
        .sumOf { it.getTotalCalories() }.toInt()

    fun getWeeklyPoint():Int {
        var sum=0
        var index=0
        var date= LocalDate.now().minusDays(6)
        do {
           sum += if (isSameWeek(date, LocalDate.now())) getDailyPoint(date) else 0
            index++
            date=date.plusDays(1)
        } while (isSameWeek(date, LocalDate.now()) || index <7)
        return sum
    }

    fun getPoints()=max(0, activities.sumOf { it.getTotalCalories() }.toInt())
}
