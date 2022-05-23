package br.com.antoniomonteiro

import br.com.antoniomonteiro.db.DbSettings
import br.com.antoniomonteiro.players.model.playersRouting
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import br.com.antoniomonteiro.plugins.*
import br.com.antoniomonteiro.routes.teams.model.teamsRouting

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
        DbSettings.configureDatabase()

        playersRouting()
        teamsRouting()
    }.start(wait = true)
}
