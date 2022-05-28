package br.com.antoniomonteiro.routes.teams.model

import br.com.antoniomonteiro.players.model.Player
import br.com.antoniomonteiro.players.model.PlayerEntity
import br.com.antoniomonteiro.players.model.Players
import br.com.antoniomonteiro.players.model.toDomain
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.Join
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

data class Amendoim(
    val team: Team,
    val players: List<Player>
)

fun Application.teamsRouting() {
    val a = false

    configureTables()

    routing {
        get("/teams") {
            val teamsWithPlayers = transaction {
                val teams = TeamEntity.all()

                teams.map {
                    val players = it.players.map { player ->
                        player.toDomain()
                    }

                    it.toDomain().also { it.players?.addAll(players) }
                }
            }

            call.respond(teamsWithPlayers)
        }
        post("/teams") {
            call.receive<Team>().let { team ->
                val newTeam = transaction {
                    TeamEntity.new {
                        name = team.name
                    }.toDomain()
                }

                call.respond(newTeam)
            }
        }
        delete("/teams/{id}") {

        }

        put("/teams/{id}") {

        }
    }
}

fun configureTables() {
    transaction {
        SchemaUtils.create(
            Teams
        )
    }
}