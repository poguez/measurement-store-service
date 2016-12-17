package com.arisanet.restapi.utils

import com.github.tminglei.slickpg._
import com.github.tminglei.slickpg.PgDateSupportJoda

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}

// Declaration of my Postgres Driver
// To use types related to Timestamp
// Visit https://github.com/tminglei/slick-pg

trait MyPostgresDriver extends ExPostgresProfile with PgDateSupportJoda{
  def pgjson = "jsonb" // jsonb support is in postgres 9.4.0 onward; for 9.3.x use "json"

  override val api = MyAPI

  object MyAPI extends API with DateTimeImplicits
}

object MyPostgresDriver extends MyPostgresDriver

class DatabaseService(jdbcUrl: String, dbUser: String, dbPassword: String) {
  private val hikariConfig = new HikariConfig()
  hikariConfig.setJdbcUrl(jdbcUrl)
  hikariConfig.setUsername(dbUser)
  hikariConfig.setPassword(dbPassword)

  private val dataSource = new HikariDataSource(hikariConfig)

  val driver = MyPostgresDriver
  import driver.api._
  val db = Database.forDataSource(dataSource)
  db.createSession()
}
