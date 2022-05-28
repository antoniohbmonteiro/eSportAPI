package br.com.antoniomonteiro.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager

object DbSettings {
    private val db by lazy {
        Database.connect("jdbc:mysql://127.0.0.1:3306/esports_schema", driver = "com.mysql.cj.jdbc.Driver",
            user = "root", password = "Ahbm101292*")
    }

    fun configureDatabase(){
        TransactionManager.defaultDatabase = db
    }
}
