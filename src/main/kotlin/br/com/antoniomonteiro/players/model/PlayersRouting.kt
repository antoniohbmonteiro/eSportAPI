package br.com.antoniomonteiro.players.model

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.playersRouting() {

    configureTables()

    routing {
        get("/players") {
            val players = transaction {
                PlayerEntity.all().map {
                    Player(
                        id = it.id.value,
                        name = it.name
                    )
                }
            }
            call.respond(players)
        }
        get("/players/{id}") {
            call.parameters["id"]?.let { id ->

                transaction {
                    PlayerEntity.findById(id.toInt())
                }?.toDomain()?.let {
                    call.respond(it)
                }

            } ?: kotlin.run {
                call.respond(status = HttpStatusCode.NotFound, "Jogador não encontrado")
            }
        }
        post("/players") {
            val player = call.receive<Player>()

            val newPlayer = transaction {
                PlayerEntity.new {
                    name = player.name
                }.toDomain()
            }

            call.respond(newPlayer)
        }
        delete("/players/{id}") {

            call.parameters["id"]?.let { id ->

                transaction {
                    PlayerEntity.findById(id.toInt())?.delete()
                }

                call.respond(status = HttpStatusCode.OK, "")
            } ?: kotlin.run {
                call.respond(status = HttpStatusCode.NotFound, "Jogador não encontrado")
            }
        }
        put("/players/{id}") {

            call.parameters["id"]?.let { id ->

                val updatedPlayer = call.receive<Player>()

                transaction {
                    PlayerEntity.findById(id.toInt())?.run {
                        name = updatedPlayer.name
                    }
                }

                call.respond(status = HttpStatusCode.OK, "")

            } ?: kotlin.run {
                call.respond(status = HttpStatusCode.NotFound, "Jogador não encontrado")
            }
        }
    }
}

fun configureTables() {
    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            Players
        )
    }
}
