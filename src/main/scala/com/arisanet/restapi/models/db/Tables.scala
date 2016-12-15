package com.arisanet.restapi.models.db

// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = MeasurementEvent.schema ++ SchemaVersion.schema ++ Tokens.schema ++ Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table MeasurementEvent
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param measurementId Database column measurement_id SqlType(int8), Default(None)
   *  @param magnitude Database column magnitude SqlType(int8), Default(None)
   *  @param createdAt Database column created_at SqlType(timestamptz), Default(None) */
  case class MeasurementEventRow(id: Long, measurementId: Option[Long] = None, magnitude: Option[Long] = None, createdAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching MeasurementEventRow objects using plain SQL queries */
  implicit def GetResultMeasurementEventRow(implicit e0: GR[Long], e1: GR[Option[Long]], e2: GR[Option[java.sql.Timestamp]]): GR[MeasurementEventRow] = GR{
    prs => import prs._
    MeasurementEventRow.tupled((<<[Long], <<?[Long], <<?[Long], <<?[java.sql.Timestamp]))
  }
  /** Table description of table measurement_event. Objects of this class serve as prototypes for rows in queries. */
  class MeasurementEvent(_tableTag: Tag) extends Table[MeasurementEventRow](_tableTag, "measurement_event") {
    def * = (id, measurementId, magnitude, createdAt) <> (MeasurementEventRow.tupled, MeasurementEventRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), measurementId, magnitude, createdAt).shaped.<>({r=>import r._; _1.map(_=> MeasurementEventRow.tupled((_1.get, _2, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column measurement_id SqlType(int8), Default(None) */
    val measurementId: Rep[Option[Long]] = column[Option[Long]]("measurement_id", O.Default(None))
    /** Database column magnitude SqlType(int8), Default(None) */
    val magnitude: Rep[Option[Long]] = column[Option[Long]]("magnitude", O.Default(None))
    /** Database column created_at SqlType(timestamptz), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table MeasurementEvent */
  lazy val MeasurementEvent = new TableQuery(tag => new MeasurementEvent(tag))

  /** Entity class storing rows of table SchemaVersion
   *  @param versionRank Database column version_rank SqlType(int4)
   *  @param installedRank Database column installed_rank SqlType(int4)
   *  @param version Database column version SqlType(varchar), PrimaryKey, Length(50,true)
   *  @param description Database column description SqlType(varchar), Length(200,true)
   *  @param `type` Database column type SqlType(varchar), Length(20,true)
   *  @param script Database column script SqlType(varchar), Length(1000,true)
   *  @param checksum Database column checksum SqlType(int4), Default(None)
   *  @param installedBy Database column installed_by SqlType(varchar), Length(100,true)
   *  @param installedOn Database column installed_on SqlType(timestamp)
   *  @param executionTime Database column execution_time SqlType(int4)
   *  @param success Database column success SqlType(bool) */
  case class SchemaVersionRow(versionRank: Int, installedRank: Int, version: String, description: String, `type`: String, script: String, checksum: Option[Int] = None, installedBy: String, installedOn: java.sql.Timestamp, executionTime: Int, success: Boolean)
  /** GetResult implicit for fetching SchemaVersionRow objects using plain SQL queries */
  implicit def GetResultSchemaVersionRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[Int]], e3: GR[java.sql.Timestamp], e4: GR[Boolean]): GR[SchemaVersionRow] = GR{
    prs => import prs._
    SchemaVersionRow.tupled((<<[Int], <<[Int], <<[String], <<[String], <<[String], <<[String], <<?[Int], <<[String], <<[java.sql.Timestamp], <<[Int], <<[Boolean]))
  }
  /** Table description of table schema_version. Objects of this class serve as prototypes for rows in queries.
   *  NOTE: The following names collided with Scala keywords and were escaped: type */
  class SchemaVersion(_tableTag: Tag) extends Table[SchemaVersionRow](_tableTag, "schema_version") {
    def * = (versionRank, installedRank, version, description, `type`, script, checksum, installedBy, installedOn, executionTime, success) <> (SchemaVersionRow.tupled, SchemaVersionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(versionRank), Rep.Some(installedRank), Rep.Some(version), Rep.Some(description), Rep.Some(`type`), Rep.Some(script), checksum, Rep.Some(installedBy), Rep.Some(installedOn), Rep.Some(executionTime), Rep.Some(success)).shaped.<>({r=>import r._; _1.map(_=> SchemaVersionRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7, _8.get, _9.get, _10.get, _11.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column version_rank SqlType(int4) */
    val versionRank: Rep[Int] = column[Int]("version_rank")
    /** Database column installed_rank SqlType(int4) */
    val installedRank: Rep[Int] = column[Int]("installed_rank")
    /** Database column version SqlType(varchar), PrimaryKey, Length(50,true) */
    val version: Rep[String] = column[String]("version", O.PrimaryKey, O.Length(50,varying=true))
    /** Database column description SqlType(varchar), Length(200,true) */
    val description: Rep[String] = column[String]("description", O.Length(200,varying=true))
    /** Database column type SqlType(varchar), Length(20,true)
     *  NOTE: The name was escaped because it collided with a Scala keyword. */
    val `type`: Rep[String] = column[String]("type", O.Length(20,varying=true))
    /** Database column script SqlType(varchar), Length(1000,true) */
    val script: Rep[String] = column[String]("script", O.Length(1000,varying=true))
    /** Database column checksum SqlType(int4), Default(None) */
    val checksum: Rep[Option[Int]] = column[Option[Int]]("checksum", O.Default(None))
    /** Database column installed_by SqlType(varchar), Length(100,true) */
    val installedBy: Rep[String] = column[String]("installed_by", O.Length(100,varying=true))
    /** Database column installed_on SqlType(timestamp) */
    val installedOn: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("installed_on")
    /** Database column execution_time SqlType(int4) */
    val executionTime: Rep[Int] = column[Int]("execution_time")
    /** Database column success SqlType(bool) */
    val success: Rep[Boolean] = column[Boolean]("success")

    /** Index over (installedRank) (database name schema_version_ir_idx) */
    val index1 = index("schema_version_ir_idx", installedRank)
    /** Index over (success) (database name schema_version_s_idx) */
    val index2 = index("schema_version_s_idx", success)
    /** Index over (versionRank) (database name schema_version_vr_idx) */
    val index3 = index("schema_version_vr_idx", versionRank)
  }
  /** Collection-like TableQuery object for table SchemaVersion */
  lazy val SchemaVersion = new TableQuery(tag => new SchemaVersion(tag))

  /** Entity class storing rows of table Tokens
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param userId Database column user_id SqlType(int8), Default(None)
   *  @param token Database column token SqlType(varchar) */
  case class TokensRow(id: Long, userId: Option[Long] = None, token: String)
  /** GetResult implicit for fetching TokensRow objects using plain SQL queries */
  implicit def GetResultTokensRow(implicit e0: GR[Long], e1: GR[Option[Long]], e2: GR[String]): GR[TokensRow] = GR{
    prs => import prs._
    TokensRow.tupled((<<[Long], <<?[Long], <<[String]))
  }
  /** Table description of table tokens. Objects of this class serve as prototypes for rows in queries. */
  class Tokens(_tableTag: Tag) extends Table[TokensRow](_tableTag, "tokens") {
    def * = (id, userId, token) <> (TokensRow.tupled, TokensRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), userId, Rep.Some(token)).shaped.<>({r=>import r._; _1.map(_=> TokensRow.tupled((_1.get, _2, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column user_id SqlType(int8), Default(None) */
    val userId: Rep[Option[Long]] = column[Option[Long]]("user_id", O.Default(None))
    /** Database column token SqlType(varchar) */
    val token: Rep[String] = column[String]("token")

    /** Foreign key referencing Users (database name USER_FK) */
    lazy val usersFk = foreignKey("USER_FK", userId, Users)(r => Rep.Some(r.id), onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Tokens */
  lazy val Tokens = new TableQuery(tag => new Tokens(tag))

  /** Entity class storing rows of table Users
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param username Database column username SqlType(varchar)
   *  @param password Database column password SqlType(varchar) */
  case class UsersRow(id: Long, username: String, password: String)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Long], e1: GR[String]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[Long], <<[String], <<[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends Table[UsersRow](_tableTag, "users") {
    def * = (id, username, password) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(username), Rep.Some(password)).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column username SqlType(varchar) */
    val username: Rep[String] = column[String]("username")
    /** Database column password SqlType(varchar) */
    val password: Rep[String] = column[String]("password")
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
