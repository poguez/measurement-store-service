package com.arisanet

import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpcirce.CirceSupport
import com.arisanet.restapi.http.HttpService
import com.arisanet.restapi.models.UserEntity
import com.arisanet.restapi.services.{AuthService, UsersService, MeasurementEventService}
import com.arisanet.restapi.utils.DatabaseService
import com.arisanet.utils.InMemoryPostgresStorage._
import org.scalatest._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.Random

trait BaseServiceTest extends WordSpec with Matchers with ScalatestRouteTest with CirceSupport {

  dbProcess.getProcessId

  private val databaseService = new DatabaseService(jdbcUrl, dbUser, dbPassword)

  val usersService = new UsersService(databaseService)
  val authService = new AuthService(databaseService)(usersService)
  val measurementEventService = new MeasurementEventService(databaseService)
  val httpService = new HttpService(usersService, authService,measurementEventService)

  def provisionUsersList(size: Int): Seq[UserEntity] = {
    val savedUsers = (1 to size).map { _ =>
      UserEntity(Some(Random.nextLong()), Random.nextString(10), Random.nextString(10))
    }.map(usersService.createUser)

    Await.result(Future.sequence(savedUsers), 10.seconds)
  }

  def provisionTokensForUsers(usersList: Seq[UserEntity]) = {
    val savedTokens = usersList.map(authService.createToken)
    Await.result(Future.sequence(savedTokens), 10.seconds)
  }

}
