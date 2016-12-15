package com.arisanet.restapi

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.arisanet.restapi.http.HttpService
import com.arisanet.restapi.services.{AuthService, UsersService, MeasurementEventService}
import com.arisanet.restapi.utils.{Config, DatabaseService, FlywayService}

import scala.concurrent.ExecutionContext

object Main extends App with Config {
  implicit val actorSystem = ActorSystem()
  implicit val executor: ExecutionContext = actorSystem.dispatcher
  implicit val log: LoggingAdapter = Logging(actorSystem, getClass)
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val flywayService = new FlywayService(jdbcUrl, dbUser, dbPassword)
  flywayService.migrateDatabaseSchema

  val databaseService = new DatabaseService(jdbcUrl, dbUser, dbPassword)

  val usersService = new UsersService(databaseService)
  val authService = new AuthService(databaseService)(usersService)
  val measurementEventService = new MeasurementEventService(databaseService)

  val httpService = new HttpService(usersService, authService, measurementEventService)

  Http().bindAndHandle(httpService.routes, httpHost, httpPort)
}
