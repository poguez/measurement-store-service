package com.arisanet.restapi.models

import java.sql.Timestamp

case class MeasurementEventEntity(id: Option[Long]= None,
                                   measurement_type_id: Option[Long] = None,
                                   magnitude: Option[Double] = None,
                                   measurement_area: Option[Long] = None,
                                   created_at: Option[Timestamp])
