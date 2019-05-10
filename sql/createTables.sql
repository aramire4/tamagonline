CREATE TABLE player (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(200) NOT NULL,
	password VARCHAR(200) NOT NULL,
    coins INT NOT NULL,
    debt INT NOT NULL,
    kills INT NOT NULL,
    deaths INT NOT NULL,
    globalRank INT NOT NULL,
    score INT NOT NULL,
    numberOfTamagos INT NOT NULL);

CREATE TABLE tamago (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    ownerID INT NOT NULL,
    FOREIGN KEY (ownerID)
        REFERENCES player(id)
        ON DELETE CASCADE,
    attack INT NOT NULL,
    defense INT NOT NULL,
    speed INT NOT NULL,
    health INT NOT NULL,
    kneesBroken BOOLEAN NOT NULL,
    level INT NOT NULL,
    isClean BOOLEAN NOT NULL,
    isAlive BOOLEAN NOT NULL,
    lastfed TIMESTAMP NOT NULL,
    age INT NOT NULL,
    respect INT NOT NULL,
    timesKneesBroken INT NOT NULL);

CREATE TABLE post (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    playerID INT NOT NULL,
    time TIMESTAMP NOT NULL,
    messsage VARCHAR(240) NOT NULL,
    FOREIGN KEY (playerID)
        REFERENCES player(id)
        ON DELETE CASCADE);

CREATE TABLE postComment (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    message VARCHAR(240) NOT NULL,
    time TIMESTAMP NOT NULL,
    playerID INT NOT NULL,
    FOREIGN KEY (playerID)
        REFERENCES player(id)
        ON DELETE CASCADE,
    postID INT NOT NULL,
    FOREIGN KEY (postID)
        REFERENCES post(id)
        ON DELETE CASCADE);
