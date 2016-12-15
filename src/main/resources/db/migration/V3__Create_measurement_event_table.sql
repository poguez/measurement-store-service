CREATE TABLE "measurement_events" (
  "id"      BIGSERIAL PRIMARY KEY,
  "measurement_id" BIGINT,
  "magnitude" BIGINT,
  "created_at" TIMESTAMP
);
