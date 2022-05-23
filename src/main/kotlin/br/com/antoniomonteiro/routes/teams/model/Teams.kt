package br.com.antoniomonteiro.routes.teams.model

import br.com.antoniomonteiro.players.model.Player
import br.com.antoniomonteiro.players.model.PlayerEntity
import br.com.antoniomonteiro.players.model.Players
import br.com.antoniomonteiro.routes.teams.model.TeamEntity.Companion.optionalBackReferencedOn
import br.com.antoniomonteiro.routes.teams.model.TeamEntity.Companion.optionalReferrersOn
import br.com.antoniomonteiro.routes.teams.model.Teams.nullable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

data class Team(
    val id: Int? = null,
    val name: String,
)

object Teams : IntIdTable() {
    val name: Column<String> = varchar("name", 255)
}

class TeamEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TeamEntity>(Teams)

    var name by Teams.name
    val players by PlayerEntity optionalReferrersOn Players.teamId
}

fun TeamEntity.toDomain() =
    Team(
        id = id.value,
        name = name
    )