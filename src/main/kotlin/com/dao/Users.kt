package com.dao

import org.jetbrains.exposed.sql.Table

    object Users: Table(){
        val id = integer("id").primaryKey().autoIncrement()
        val name = varchar("name", 50)
        val family = varchar("family", 50)
        val patronymic = varchar("patronymic", 50)
        val email = varchar("email", 100)
        val phone = varchar("phone", 50)
}