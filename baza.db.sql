BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "rentings" (
	"id"	INTEGER,
	"renter"	TEXT,
	"book"	INTEGER,
	"begin"	TEXT,
	"end"	TEXT,
	PRIMARY KEY("id"),
	FOREIGN KEY("renter") REFERENCES "users"("username"),
	FOREIGN KEY("book") REFERENCES "books"("id")
);
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
INSERT INTO "books" VALUES (1,'Derviš i smrt','Meša Selimović','psihološka drama',431,50);
INSERT INTO "admins" VALUES ('farismusic','Faris','Mušić','fmusic2@etf.unsa.ba','fmusic123');
INSERT INTO "users" VALUES ('sturko1','Sajra','Turko','sturko1@etf.unsa.ba','nekaidezivot99');
INSERT INTO "users" VALUES ('aturkusic1','Arslan','Turkušić','aturkusic1@etf.unsa.ba','12345678');
COMMIT;
