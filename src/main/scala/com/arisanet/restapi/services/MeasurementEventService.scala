package com.arisanet.restapi.services

import com.arisanet.restapi.models.db.MeasurementEventEntityTable
import com.arisanet.restapi.models.MeasurementEventEntity
import com.arisanet.restapi.utils.DatabaseService

import scala.concurrent.{ExecutionContext, Future}

class MeasurementEventService(val databaseService: DatabaseService)(implicit executionContext: ExecutionContext) extends MeasurementEventEntityTable {

  import databaseService._
  import databaseService.driver.api._

  //  Event methods

  def getAllMeasurementEvents(): Future[Seq[MeasurementEventEntity]] =
    db.run(measurementEvents.result)

  def getLastMeasurementEventFor(measurement_type_id: Long): Future[Option[MeasurementEventEntity]] =
    db.run(measurementEvents.filter(_.measurement_type_id === measurement_type_id).sortBy(_.created_at.desc.nullsFirst).result.headOption)

  def getMeasurementEventById(id: Long): Future[Option[MeasurementEventEntity]] =
    db.run(measurementEvents.filter(_.id === id).result.headOption)

  def createMeasurementEvent(measurementEvent: MeasurementEventEntity): Future[MeasurementEventEntity] =
    db.run(measurementEvents returning measurementEvents += measurementEvent)


  //  Events methods

  def getMeasurementEventsByArea(measurement_area: Long): Future[Seq[MeasurementEventEntity]] =
    db.run(measurementEvents.filter(_.measurement_area === measurement_area).sortBy(_.created_at.desc.nullsFirst).result)

  def getMeasurementEventsByAreaAndType(measurement_area: Long, measurement_type_id: Long): Future[Seq[MeasurementEventEntity]] =
    db.run(measurementEvents.filter(_.measurement_area === measurement_area).filter(_.measurement_type_id === measurement_type_id).sortBy(_.created_at.desc.nullsFirst).result)


}

