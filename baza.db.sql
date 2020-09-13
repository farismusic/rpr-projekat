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
COMMIT;
