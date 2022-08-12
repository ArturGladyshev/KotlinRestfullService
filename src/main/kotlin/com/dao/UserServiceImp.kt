package com.dao

import com.model.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.Closeable


interface UserService: Closeable {
    fun init()
    fun createUser(name:String, family:String, patronymic:String, email:String, phone:String)
    fun updateUser(id:Int, name:String, family:String, patronymic:String, email:String, phone:String)
    fun deleteUser(id:Int)
    fun getUser(id:Int): User?
    fun getAllUsers(): List<User>
}

class UserServiceImp(val db: Database) : UserService {

    override fun init() = transaction(db) {
        SchemaUtils.create(Users)
        val users = listOf(
            User(
                1, "Yuri", "Nikitin", "Sergeevich",
                "yuri@.writer.com", "Moscow"
            ),
            User(
                2, "Nick", "Perumov", "Sergeevich",
                "nick.writer.com", "Rostov"
            )
        )
        Users.batchInsert(users) { user ->
            this[Users.id] = user.id
            this[Users.name] = user.name
            this[Users.family] = user.family
            this[Users.patronymic] = user.patronymic;
            this[Users.email] = user.email
            this[Users.phone] = user.phone
        }
        Unit
    }


    override fun createUser(name: String, family: String, patronymic: String, email: String, phone: String) =
        transaction(db) {
            Users.insert {
                it[Users.name] = name;
                it[Users.family] = family;
                it[Users.patronymic] = patronymic;
                it[Users.email] = email;
                it[Users.phone] = phone;
            }
            Unit
        }


    override fun updateUser(id: Int, name: String, family: String, patronymic: String, email: String, phone: String) =
        transaction(db) {
            Users.update({ Users.id eq id }) {
                it[Users.name] = name
                it[Users.family] = family
                it[Users.patronymic] = patronymic
                it[Users.email] = email
                it[Users.phone] = phone
            }
            Unit
        }

    override fun deleteUser(id: Int) = transaction(db) {
        Users.deleteWhere { Users.id eq id }
        Unit
    }

    override fun getUser(id: Int) = transaction(db) {
            Users.select { Users.id eq id }.map {
                User(
                    it[Users.id],
                    it[Users.name],
                    it[Users.family],
                    it[Users.patronymic],
                    it[Users.email],
                    it[Users.phone]
                )
            }.singleOrNull()
        }

    override fun getAllUsers(): List<User> = transaction(db) {
        Users.selectAll().map {
            User(
                it[Users.id],
                it[Users.name],
                it[Users.family],
                it[Users.patronymic],
                it[Users.email],
                it[Users.phone]
            )
        }
    }

    override fun close() {}
}