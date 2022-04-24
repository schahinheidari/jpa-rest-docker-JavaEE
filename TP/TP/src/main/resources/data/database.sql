LOAD DATA
    CHARACTERSET UTF8
INFILE 'commune.csv'
APPEND
INTO TABLE commune
FIELDS TERMINATED BY ';'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;
(
    codeinsee  "to_char(:codeinsee)",
    nom_com  "to_char(:nom_com)",
    code_postal "to_number(:code_postal)",
    libelle  "to_char(:libelle)",
    latitude "to_number(:latitude)",
    longitude "to_number(:longitude)"
)


CREATE TABLE commune (
                         id INT NOT NULL AUTO_INCREMENT,
                         codeinsee VARCHAR(5) NOT NULL,
                         nom_com VARCHAR(255) NOT NULL,
                         code_postal DECIMAL(5 ) NULL,
                         libelle VARCHAR(255) NULL,
                         latitude DECIMAL(10 , 6 ) NULL,
                         longitude DECIMAL(10 , 6 ) NULL,
                         PRIMARY KEY (id)
);

ALTER TABLE commune CHANGE `Code_INSEE` `codeinsee` VARCHAR(5);
ALTER TABLE commune CHANGE `Nom_commune` `nom_com` VARCHAR(255);
ALTER TABLE commune CHANGE `Code_postal` `code_postal` DECIMAL(5);
ALTER TABLE commune CHANGE `Libelle` `libelle` VARCHAR(255);
ALTER TABLE commune CHANGE `latitude` `latitude` latitude DECIMAL(10,6);
ALTER TABLE commune CHANGE `longitude` `longitude` longitude DECIMAL(10,6);

ALTER TABLE commune MODIFY COLUMN latitude float(9,6);

SELECT colum INTO newtable [IN externaldb] FROM oldtable WHERE condition;
select codeinsee, nom_com, code_postal, latitude, longitude into amir (codeinsee, nom_com, code_postal,longitude, FIELD7) from commune where FIELD7 != "NULL";
select * into amir from commune where FIELD7 != 'NULL';

INSERT INTO amir (codeinsee, nom_com, code_postal, latitude, longitude) SELECT codeinsee, nom_com, code_postal,longitude, FIELD7 FROM commune WHERE FIELD7 != "NULL";