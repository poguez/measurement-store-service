package com.arisanet.restapi.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import com.arisanet.restapi.http.SecurityDirectives
import com.arisanet.restapi.models.MeasurementEventEntity
import com.arisanet.restapi.services.MeasurementEventService
import com.arisanet.restapi.services.AuthService
import de.heikoseeberger.akkahttpcirce.CirceSupport
import io.circe.generic.auto._
import io.circe.syntax._
//import io.circe._, io.circe.generic.semiauto._

import scala.concurrent.ExecutionContext


//case class MeasurementEvent(id: Option[Long], measurement_type_id: Option[Long], magnitude: Option[Long], created_at: Option[java.sql.Timestamp]){}


//implicit val measurementEventDecoder: Decoder[MeasurementEvent] = deriveDecoder
//implicit val measurementEventEncoder: Encoder[MeasurementEvent] = deriveEncoder

class MeasurementEventRoute(val authService: AuthService,
                            measurementEventService: MeasurementEventService
                           )(implicit executionContext: ExecutionContext) extends CirceSupport with SecurityDirectives {

  import StatusCodes._
  import measurementEventService._

  val route = pathPrefix("measurement-event") {
    pathPrefix("last" / IntNumber){ measurement_type_id =>
      pathEndOrSingleSlash{
        get{
          complete(getLastMeasurementEventFor(measurement_type_id).map(_.asJson))
        }
      }
    }~
    path("all") {
      pathEndOrSingleSlash {
        get {
          complete(getAllMeasurementEvents().map(_.asJson))
        }
      }
    }~
    pathPrefix("area" / IntNumber){ measurement_area =>
      pathEndOrSingleSlash{
        get{
          complete(getMeasurementEventsByArea(measurement_area).map(_.asJson))
        }
      }~
      pathPrefix("type" / IntNumber) { measurement_type_id =>
        pathEndOrSingleSlash {
          get {
            complete(getMeasurementEventsByAreaAndType(measurement_area, measurement_type_id).map(_.asJson))
          }
        }
      }
    }~
    pathPrefix(IntNumber) { id =>
      pathEndOrSingleSlash {
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
