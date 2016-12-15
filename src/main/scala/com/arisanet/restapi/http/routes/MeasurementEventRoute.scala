package com.arisanet.restapi.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.IntNumber
import com.arisanet.restapi.http.SecurityDirectives
import com.arisanet.restapi.models.MeasurementEventEntity
import com.arisanet.restapi.services.MeasurementEventService
import com.arisanet.restapi.services.{AuthService}
import de.heikoseeberger.akkahttpcirce.CirceSupport
import io.circe.generic.semiauto._
import io.circe.generic.auto._
import io.circe.syntax._

import scala.concurrent.ExecutionContext




class MeasurementEventRoute(val authService: AuthService,
                            measurementEventService: MeasurementEventService
                           )(implicit executionContext: ExecutionContext) extends CirceSupport with SecurityDirectives {

  import StatusCodes._
  import measurementEventService._

  val route = pathPrefix("measurement_event") {
    path("all") {
      pathEndOrSingleSlash {
        println("Entro a todos")
        get {
          complete(getMeasurementEvents().map(_.asJson))
        }
      }
    }~
    pathPrefix(IntNumber) { id =>
      pathEndOrSingleSlash {
        println("Entro por id")
        get {
          complete(getMeasurementEventById(id).map(_.asJson))
        }
      }
    }~
    pathEndOrSingleSlash {
      post {
        entity(as[MeasurementEventEntity]) { measurementEvent =>
          complete(Created -> createMeasurementEvent(measurementEvent).map(_.asJson))
        }
      }
    }
  }
}
