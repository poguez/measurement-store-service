package com.arisanet.restapi.models

import java.sql.Timestamp

case class MeasurementEventEntity(id: Option[Long]= None,
                                   measurement_id: Option[Long] = None,
                                   magnitude: Option[Double] = None,
                                   created_at: Option[Timestamp])
