package com.arisanet.restapi.http

import akka.http.scaladsl.server.Directives._
import com.arisanet.restapi.http.routes.{AuthServiceRoute, UsersServiceRoute, MeasurementEventRoute}
import com.arisanet.restapi.services.{AuthService, UsersService, MeasurementEventService}
import com.arisanet.restapi.utils.CorsSupport

import scala.concurrent.ExecutionContext

class HttpService(usersService: UsersService,
                  authService: AuthService,
                  measurementEventService: MeasurementEventService
                 )(implicit executionContext: ExecutionContext) extends CorsSupport {

  val usersRouter = new UsersServiceRoute(authService, usersService)
  val authRouter = new AuthServiceRoute(authService)
  val measurementEventRouter = new MeasurementEventRoute(authService, measurementEventService)

  val routes =
    pathPrefix("v1") {
      corsHandler {
        usersRouter.route ~
        authRouter.route ~
        measurementEventRouter.route
      }
    }

}
