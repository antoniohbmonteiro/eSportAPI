package br.com.antoniomonteiro.players.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

data class Player(
    val id: Int? = null,
    val name: String
)

object Players : IntIdTable() {
    val name: Column<String> = varchar("name", 255)
}

class PlayerEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PlayerEntity>(Players)

    var name by Players.name
}

fun PlayerEntity.toDomain() =
    Player(
        id = id.value,
        name = name
    )