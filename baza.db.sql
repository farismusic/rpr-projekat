BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "books" (
	"id"	INTEGER,
	"naziv"	TEXT,
	"autor"	TEXT,
	"zanr"	TEXT,
	"broj_stranica"	INTEGER,
	"broj_knjiga"	INTEGER,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "admins" (
	"username"	TEXT,
	"name"	TEXT,
	"last_name"	TEXT,
	"email"	TEXT,
	"password"	TEXT,
	PRIMARY KEY("username")
);
CREATE TABLE IF NOT EXISTS "users" (
	"username"	TEXT,
	"name"	TEXT,
	"last_name"	TEXT,
	"email"	TEXT,
	"password"	TEXT,
	PRIMARY KEY("username")
);
INSERT INTO "admins" VALUES ('farismusic','Faris','Mušić','fmusic2@etf.unsa.ba','fmusic123');
INSERT INTO "users" VALUES ('sturko1','Sajra','Turko','sturko1@etf.unsa.ba','nekaidezivot99');
COMMIT;
