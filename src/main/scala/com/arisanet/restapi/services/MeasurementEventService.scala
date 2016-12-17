package com.arisanet.restapi.services

import com.arisanet.restapi.models.db.MeasurementEventEntityTable
import com.arisanet.restapi.models.MeasurementEventEntity
import com.arisanet.restapi.utils.DatabaseService

import scala.concurrent.{ExecutionContext, Future}

class MeasurementEventService(val databaseService: DatabaseService)(implicit executionContext: ExecutionContext) extends MeasurementEventEntityTable {

  import databaseService._
  import databaseService.driver.api._

  def getMeasurementEvents(): Future[Seq[MeasurementEventEntity]] = db.run(measurementEvents.result)
  def getMeasurementEventById(id: Long): Future[Option[MeasurementEventEntity]] = db.run(measurementEvents.filter(_.id === id).result.headOption)
  def createMeasurementEvent(measurementEvent: MeasurementEventEntity): Future[MeasurementEventEntity] = db.run(measurementEvents returning measurementEvents += measurementEvent)

}
