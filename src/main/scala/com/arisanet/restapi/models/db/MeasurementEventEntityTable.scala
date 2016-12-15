package com.arisanet.restapi.models.db

import com.arisanet.restapi.models.MeasurementEventEntity
import com.arisanet.restapi.utils.DatabaseService

trait MeasurementEventEntityTable {

  protected val databaseService: DatabaseService
  import databaseService.driver.api._

  class MeasurementEvent(tag: Tag) extends Table[MeasurementEventEntity](tag, "measurement_events") {
    def id = column [Option[Long]]("id", O.PrimaryKey, O.AutoInc)
    def measurement_id = column[Option[Long]]("measurement_id")
    def magnitude = column[Option[Long]]("magnitude")
    def created_at = column[Option[java.sql.Timestamp]]("created_at")

    def * = (id, measurement_id, magnitude) <> ((MeasurementEventEntity.apply _).tupled, MeasurementEventEntity.unapply)

  }

  protected val measurementEvents = TableQuery[MeasurementEvent]

}
