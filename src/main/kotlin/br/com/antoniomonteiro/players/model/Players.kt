package br.com.antoniomonteiro.players.model

import br.com.antoniomonteiro.routes.teams.model.Team
import br.com.antoniomonteiro.routes.teams.model.TeamEntity
import br.com.antoniomonteiro.routes.teams.model.Teams
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

data class Player(
    val id: Int? = null,
    val name: String,
    val team: Team? = null,
    val teamId: Int? = null
)

object Players : IntIdTable() {
    val name: Column<String> = varchar("name", 255)
    val teamId = reference("teamId", Teams.id).nullable()
}

class PlayerEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PlayerEntity>(Players)

    var name by Players.name
    var team by TeamEntity optionalReferencedOn Players.teamId
}

fun PlayerEntity.toDomain() =
    Player(
        id = id.value,
        name = name
    )