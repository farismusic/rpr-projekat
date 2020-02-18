BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "admin" (
	"username"	TEXT,
	"name"	TEXT,
	"last_name"	TEXT,
	"email"	TEXT,
	"password"	TEXT,
	PRIMARY KEY("username")
);
CREATE TABLE IF NOT EXISTS "user" (
	"username"	TEXT,
	"name"	TEXT,
	"last_name"	TEXT,
	"email"	TEXT,
	"password"	TEXT,
	PRIMARY KEY("username")
);
INSERT INTO "admin" VALUES ('farismusic','Faris','Mušić','fmusic2@etf.unsa.ba','fmusic123');
COMMIT;
