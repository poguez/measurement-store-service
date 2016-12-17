package com.arisanet.restapi.models.db
import java.sql.Timestamp

import com.arisanet.restapi.models.MeasurementEventEntity
import com.arisanet.restapi.utils.DatabaseService
import io.circe._
import org.joda.time.DateTime


trait MeasurementEventEntityTable {

  protected val databaseService: DatabaseService
  import databaseService.driver.api._

  implicit val dateTimeEncoder: Encoder[Timestamp] = Encoder.encodeString.contramap[Timestamp](_.toString)

  implicit val dateTimeDecoder: Decoder[Timestamp] = Decoder.decodeString.map {
    str => str match {
      case ""  => new Timestamp(DateTime.now().getMillis)
      case _ => new Timestamp(str.toLong)
    }
  }

  class MeasurementEvent(tag: Tag) extends Table[MeasurementEventEntity](tag, "measurement_events") {
    def id = column [Option[Long]]("id", O.PrimaryKey, O.AutoInc)
    def measurement_id = column[Option[Long]]("measurement_id")
    def magnitude = column[Option[Double]]("magnitude")
    def created_at = column[Option[Timestamp]]("created_at", O.Default(Some(new Timestamp(DateTime.now().getMillis))))

    def * = (id, measurement_id, magnitude, created_at) <> ((MeasurementEventEntity.apply _).tupled, MeasurementEventEntity.unapply)

  }

  protected val measurementEvents = TableQuery[MeasurementEvent]

}
