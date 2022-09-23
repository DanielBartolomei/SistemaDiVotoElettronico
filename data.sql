--
-- File generated with SQLiteStudio v3.3.3 on ven set 23 22:24:18 2022
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: candidato
CREATE TABLE `candidato` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `nome` VARCHAR NOT NULL , `cognome` VARCHAR NOT NULL , `partito_id` VARCHAR NOT NULL );
INSERT INTO candidato (id, nome, cognome, partito_id) VALUES (1, 'Mario', 'Rossi', 'Cinque Stelle');
INSERT INTO candidato (id, nome, cognome, partito_id) VALUES (2, 'Berto', 'Pertici', 'Partito Democratico');
INSERT INTO candidato (id, nome, cognome, partito_id) VALUES (3, 'Normanno', 'Tiraghi', 'Partito Democratico');
INSERT INTO candidato (id, nome, cognome, partito_id) VALUES (4, 'Matteo', 'Salvini', 'Lega Nord');
INSERT INTO candidato (id, nome, cognome, partito_id) VALUES (5, 'Silvio', 'Berlusconi', 'Forza Italia');
INSERT INTO candidato (id, nome, cognome, partito_id) VALUES (6, 'Giorgia', 'Meloni', 'Forza Italia');

-- Table: partito
CREATE TABLE `partito` (`nome` VARCHAR , PRIMARY KEY (`nome`) );
INSERT INTO partito (nome) VALUES ('Cinque Stelle');
INSERT INTO partito (nome) VALUES ('Partito Democratico');
INSERT INTO partito (nome) VALUES ('Forza Italia');
INSERT INTO partito (nome) VALUES ('Lega Nord');
INSERT INTO partito (nome) VALUES ('Italia Viva');

-- Table: referendum
CREATE TABLE `referendum` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `nome` VARCHAR , `quesito` VARCHAR , `dataInizio` VARCHAR NOT NULL , `dataFine` VARCHAR NOT NULL , `hasQuorum` BOOLEAN , `totFavorevoli` BIGINT , `totContrari` BIGINT , `totVoti` BIGINT );
INSERT INTO referendum (id, nome, quesito, dataInizio, dataFine, hasQuorum, totFavorevoli, totContrari, totVoti) VALUES (1, 'Energia Nucleare', 'Usare energia nucleare come fonte principale di energia?', '2023-02-10', '2023-02-15', 1, 2, 0, 3);
INSERT INTO referendum (id, nome, quesito, dataInizio, dataFine, hasQuorum, totFavorevoli, totContrari, totVoti) VALUES (2, 'Test referendum', 'Sei favorevole a usare i referendum?', '2022-09-24', '2022-09-25', 0, 1, 0, 25);
INSERT INTO referendum (id, nome, quesito, dataInizio, dataFine, hasQuorum, totFavorevoli, totContrari, totVoti) VALUES (3, 'Abolizione Monarchia', 'Sei favorevole all''abolizione della monarchia?', '1946-06-10', '1946-06-11', 0, 12717923, 10719284, 23437207);

-- Table: utente
CREATE TABLE `utente` (`cf` VARCHAR , `hashPassword` VARCHAR , `nome` VARCHAR NOT NULL , `cognome` VARCHAR NOT NULL , `isAdmin` BOOLEAN ,  UNIQUE (`cf`), PRIMARY KEY (`cf`) );
INSERT INTO utente (cf, hashPassword, nome, cognome, isAdmin) VALUES ('BRTDNL98E27F205P', '2454dfaa2580b7dc781b6d8a371459625ae00a74f1ff2d0586f0682828fd5cb1', 'Daniel', 'Bartolomei', 1);
INSERT INTO utente (cf, hashPassword, nome, cognome, isAdmin) VALUES ('RVRSMN98A19F205F', '5c9421892095ce47eca0ab710df9fcc79f6b17326e8a194eb8da3046f0fc4e88', 'Simone', 'Rover', 1);
INSERT INTO utente (cf, hashPassword, nome, cognome, isAdmin) VALUES ('RSSLGU68P12F205J', '5c9421892095ce47eca0ab710df9fcc79f6b17326e8a194eb8da3046f0fc4e88', 'Luigi', 'Rossi', 0);
INSERT INTO utente (cf, hashPassword, nome, cognome, isAdmin) VALUES ('RDNFNC07A25F205K', '5c9421892095ce47eca0ab710df9fcc79f6b17326e8a194eb8da3046f0fc4e88', 'Francesco', 'Arduini', 0);

-- Table: votazione
CREATE TABLE `votazione` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `nome` VARCHAR , `dataInizio` VARCHAR NOT NULL , `dataFine` VARCHAR NOT NULL , `isAssoluta` BOOLEAN , `tipoVotazione` VARCHAR , `totValidi` BIGINT , `totVoti` BIGINT );
INSERT INTO votazione (id, nome, dataInizio, dataFine, isAssoluta, tipoVotazione, totValidi, totVoti) VALUES (1, 'Elezioni', '2023-02-10', '2023-02-15', 1, 'CATEGORICO_PARTITI', 4, 4);
INSERT INTO votazione (id, nome, dataInizio, dataFine, isAssoluta, tipoVotazione, totValidi, totVoti) VALUES (2, 'Elezioni 2022', '2022-01-01', '2022-01-02', 1, 'ORDINALE_PARTITI', 688983, 765007);
INSERT INTO votazione (id, nome, dataInizio, dataFine, isAssoluta, tipoVotazione, totValidi, totVoti) VALUES (3, 'Test voto', '2022-09-24', '2022-09-25', 1, 'CATEGORICO_PARTITI', 2, 2);
INSERT INTO votazione (id, nome, dataInizio, dataFine, isAssoluta, tipoVotazione, totValidi, totVoti) VALUES (4, 'Test voto ordinale', '2022-09-24', '2022-09-25', 0, 'ORDINALE_CANDIDATI', 0, 0);
INSERT INTO votazione (id, nome, dataInizio, dataFine, isAssoluta, tipoVotazione, totValidi, totVoti) VALUES (5, 'Test preferenza', '2022-09-24', '2022-09-25', 0, 'CON_PREFERENZA', 1, 1);

-- Table: voti
CREATE TABLE `voti` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `votazione_id` INTEGER , `utente_id` VARCHAR , UNIQUE (`votazione_id`,`utente_id`) );
INSERT INTO voti (id, votazione_id, utente_id) VALUES (1, 1, 'BRTDNL98E27F205P');
INSERT INTO voti (id, votazione_id, utente_id) VALUES (2, 1, 'RVRSMN98A19F205F');
INSERT INTO voti (id, votazione_id, utente_id) VALUES (3, 1, 'RSSLGU68P12F205J');
INSERT INTO voti (id, votazione_id, utente_id) VALUES (4, 3, 'RVRSMN98A19F205F');
INSERT INTO voti (id, votazione_id, utente_id) VALUES (5, 4, 'RVRSMN98A19F205F');
INSERT INTO voti (id, votazione_id, utente_id) VALUES (6, 5, 'RVRSMN98A19F205F');
INSERT INTO voti (id, votazione_id, utente_id) VALUES (7, 3, 'BRTDNL98E27F205P');
INSERT INTO voti (id, votazione_id, utente_id) VALUES (8, 4, 'BRTDNL98E27F205P');

-- Table: votiCandidato
CREATE TABLE `votiCandidato` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `votazione_id` INTEGER NOT NULL , `candidato_id` INTEGER NOT NULL , `totVoti` BIGINT , UNIQUE (`votazione_id`,`candidato_id`) );
INSERT INTO votiCandidato (id, votazione_id, candidato_id, totVoti) VALUES (3, 4, 1, 2);
INSERT INTO votiCandidato (id, votazione_id, candidato_id, totVoti) VALUES (4, 4, 2, 5);
INSERT INTO votiCandidato (id, votazione_id, candidato_id, totVoti) VALUES (5, 4, 3, 5);
INSERT INTO votiCandidato (id, votazione_id, candidato_id, totVoti) VALUES (6, 5, 4, 1);
INSERT INTO votiCandidato (id, votazione_id, candidato_id, totVoti) VALUES (7, 5, 5, 0);
INSERT INTO votiCandidato (id, votazione_id, candidato_id, totVoti) VALUES (8, 5, 6, 0);

-- Table: votiPartito
CREATE TABLE `votiPartito` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `votazione_id` INTEGER NOT NULL , `partito_id` VARCHAR NOT NULL , `totVoti` BIGINT , UNIQUE (`votazione_id`,`partito_id`) );
INSERT INTO votiPartito (id, votazione_id, partito_id, totVoti) VALUES (1, 1, 'Cinque Stelle', 2);
INSERT INTO votiPartito (id, votazione_id, partito_id, totVoti) VALUES (2, 1, 'Partito Democratico', 2);
INSERT INTO votiPartito (id, votazione_id, partito_id, totVoti) VALUES (3, 1, 'Forza Italia', 0);
INSERT INTO votiPartito (id, votazione_id, partito_id, totVoti) VALUES (4, 1, 'Lega Nord', 0);
INSERT INTO votiPartito (id, votazione_id, partito_id, totVoti) VALUES (5, 3, 'Cinque Stelle', 0);
INSERT INTO votiPartito (id, votazione_id, partito_id, totVoti) VALUES (6, 3, 'Partito Democratico', 0);
INSERT INTO votiPartito (id, votazione_id, partito_id, totVoti) VALUES (7, 3, 'Forza Italia', 1);
INSERT INTO votiPartito (id, votazione_id, partito_id, totVoti) VALUES (8, 3, 'Lega Nord', 1);
INSERT INTO votiPartito (id, votazione_id, partito_id, totVoti) VALUES (9, 5, 'Lega Nord', 1);
INSERT INTO votiPartito (id, votazione_id, partito_id, totVoti) VALUES (10, 5, 'Forza Italia', 0);
INSERT INTO votiPartito (id, votazione_id, partito_id, totVoti) VALUES (11, 2, 'Forza Italia', 303211);
INSERT INTO votiPartito (id, votazione_id, partito_id, totVoti) VALUES (12, 2, 'Cinque Stelle', 192886);
INSERT INTO votiPartito (id, votazione_id, partito_id, totVoti) VALUES (13, 2, 'Partito Democratico', 192886);

-- Table: votiReferendum
CREATE TABLE `votiReferendum` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `referendum_id` INTEGER , `utente_id` VARCHAR , UNIQUE (`referendum_id`,`utente_id`) );
INSERT INTO votiReferendum (id, referendum_id, utente_id) VALUES (1, 1, 'BRTDNL98E27F205P');
INSERT INTO votiReferendum (id, referendum_id, utente_id) VALUES (2, 1, 'RVRSMN98A19F205F');
INSERT INTO votiReferendum (id, referendum_id, utente_id) VALUES (3, 1, 'RSSLGU68P12F205J');
INSERT INTO votiReferendum (id, referendum_id, utente_id) VALUES (4, 2, 'RVRSMN98A19F205F');
INSERT INTO votiReferendum (id, referendum_id, utente_id) VALUES (5, 2, 'BRTDNL98E27F205P');

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
