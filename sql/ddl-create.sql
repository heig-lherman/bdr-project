CREATE TABLE Station
(
    code               VARCHAR,
    identifiant_didok  VARCHAR,
    nom                VARCHAR,
    geoposition        GEOMETRY,
    point_kilometrique NUMERIC(4, 1),
    commune            VARCHAR,
    type_station       VARCHAR,
    PRIMARY KEY (code),
    FOREIGN KEY (commune) REFERENCES Commune.code,
    FOREIGN KEY (type_station) REFERENCES TypeDeStation.code
);

CREATE TABLE Segment
(
    id            SERIAL PRIMARY KEY,
    distance      NUMERIC(4, 1),
    station_start VARCHAR NOT NULL,
    station_end   VARCHAR NOT NULL,
    ligne         INT,
    FOREIGN KEY (station_start, station_end) REFERENCES Station.code,
    FOREIGN KEY (ligne) REFERENCES Ligne.numero
);

CREATE TABLE Ligne
(
    numero    INT PRIMARY KEY,
    nom       VARCHAR,
    type_voie VARCHAR,
    station_start VARCHAR NOT NULL,
    station_end   VARCHAR NOT NULL,
    FOREIGN KEY (type_voie) REFERENCES TypeDeVoie.code,
    FOREIGN KEY (station_start, station_end) REFERENCES Station.code
);

CREATE TABLE Utilisateur
(
    email          VARCHAR PRIMARY KEY,
    username       VARCHAR,
    password       VARCHAR,
    prenom         VARCHAR,
    nom_de_famille VARCHAR,
    groupe_id      INT,
    FOREIGN KEY (groupe_id) REFERENCES Groupe.id
);

CREATE TABLE Groupe
(
    id  SERIAL PRIMARY KEY,
    nom VARCHAR
);

CREATE TABLE Voyage
(
    id          SERIAL PRIMARY KEY,
    date_debut  DATE,
    date_fin    DATE,
    note        VARCHAR,
    commentaire VARCHAR,
    user        VARCHAR,
    FOREIGN KEY (user) REFERENCES Utilisateur.email
);

CREATE TABLE VoyageSegment
(
    voyage  BIGINT,
    segment BIGINT,
    PRIMARY KEY (voyage, segment),
    FOREIGN KEY (voyage) REFERENCES Voyage.id,
    FOREIGN KEY (segment) REFERENCES Segment.id
);

CREATE TABLE EvaluationLigne
(
    utilisateur VARCHAR,
    ligne       INT,
    note        NUMERIC(2, 1) NOT NULL,
    commentaire VARCHAR(2000),
    PRIMARY KEY (utilisateur, ligne),
    FOREIGN KEY (utilisateur) REFERENCES Utilisateur.email,
    FOREIGN KEY (ligne) REFERENCES Ligne.numero
);

CREATE TABLE TypeDeVoie
(
    code        VARCHAR PRIMARY KEY,
    description VARCHAR
);

CREATE TABLE TypeDeStation
(
    code        VARCHAR PRIMARY KEY,
    description VARCHAR
);

CREATE TABLE Commune
(
    code VARCHAR PRIMARY KEY,
    nom  VARCHAR
);

CREATE TABLE Commune_Suisse
(
    code   VARCHAR PRIMARY KEY,
    canton VARCHAR,
    FOREIGN KEY (code) REFERENCES Commune.code,
    FOREIGN KEY (canton) REFERENCES Canton.code_iso
);

CREATE TABLE Commune_Etrangere
(
    code VARCHAR PRIMARY KEY,
    pays VARCHAR,
    FOREIGN KEY (code) REFERENCES Commune.code,
    FOREIGN KEY (pays) REFERENCES Pays.code_iso
);

CREATE TABLE Pays
(
    code_iso VARCHAR PRIMARY KEY,
    nom      VARCHAR
);

CREATE TABLE Canton
(
    code_iso VARCHAR PRIMARY KEY,
    nom      VARCHAR
);