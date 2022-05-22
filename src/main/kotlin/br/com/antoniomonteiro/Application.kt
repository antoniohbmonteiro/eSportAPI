package br.com.antoniomonteiro

import br.com.antoniomonteiro.db.DbSettings
import br.com.antoniomonteiro.players.model.playersRouting
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import br.com.antoniomonteiro.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
        DbSettings.configureDatabase()

        playersRouting()
    }.start(wait = true)
}
