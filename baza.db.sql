BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "user" (
	"username"	TEXT,
	"name"	TEXT,
	"last_name"	TEXT,
	"email"	TEXT,
	"password"	TEXT,
	PRIMARY KEY("username")
);
COMMIT;
