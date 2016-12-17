CREATE TABLE "measurement_events" (
  "id"      BIGSERIAL PRIMARY KEY,
  "measurement_id" BIGINT,
  "magnitude" double precision,
  "created_at" timestamp without time zone
);
