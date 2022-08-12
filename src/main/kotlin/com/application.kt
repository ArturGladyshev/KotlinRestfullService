package com



import com.dao.UserServiceImp
import com.model.User
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.jackson.jackson
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database

val dao = UserServiceImp(Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver"))

fun main() {
    embeddedServer(Netty, port = 8080){
        dao.init()
        install(FreeMarker){
            templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        }
        install(CallLogging)
        install(ContentNegotiation){
            jackson {}
        }
        routing {

            route("/"){
                get{
                    call.respond(FreeMarkerContent("index.ftl", mapOf("users" to dao.getAllUsers())))
                }
            }

            route("/user"){
                get {
                    val action = (call.request.queryParameters["action"] ?: "new")
                    when(action){
                        "new" -> call.respond(FreeMarkerContent("user.ftl",
                            mapOf("action" to action)))
                        "edit" -> {
                            val id = call.request.queryParameters["id"]
                            if(id != null){
                                call.respond(FreeMarkerContent("user.ftl",
                                    mapOf("user" to dao.getUser(id.toInt()),
                                        "action" to action)))
                            }
                        }
                    }
                }

                post{
                    val postParameters: Parameters = call.receiveParameters()
                    val action = postParameters["action"] ?: "new"
                    when(action){
                        "new" -> dao.createUser(
                            postParameters["name"] ?: "",
                            postParameters["family"]?:"",
                            postParameters["patronymic"] ?: "",
                            postParameters["email"] ?: "",
                            postParameters["phone"] ?: ""
                            )
                        "edit" ->{
                            val id = postParameters["id"]
                            if(id != null)
                                dao.updateUser(id.toInt(),
                                    postParameters["name"] ?: "",
                                    postParameters["family"]?:"",
                                    postParameters["patronymic"] ?: "",
                                    postParameters["email"] ?: "",
                                    postParameters["phone"] ?: "")
                        }
                    }
                    call.respond(FreeMarkerContent("index.ftl", mapOf("users" to dao.getAllUsers())))
                }
            }

            route("/delete"){
                get{
                    val id = call.request.queryParameters["id"]
                    if(id != null){
                        dao.deleteUser(id.toInt())
                        call.respond(FreeMarkerContent("index.ftl", mapOf("users" to dao.getAllUsers())))
                    }
                }
            }
        }
    }.start(wait = true)
}
