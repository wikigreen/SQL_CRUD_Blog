CREATE TABLE region(
    ID   BIGINT      	    NOT NULL AUTO_INCREMENT,
    region VARCHAR (20)     NOT NULL,

    PRIMARY KEY (ID)
);

CREATE TABLE post(
    ID   BIGINT      		 NOT NULL AUTO_INCREMENT,
    content VARCHAR (2000)   NOT NULL,
    create_date DATETIME     DEFAULT CURRENT_TIMESTAMP() NOT NULL,
    update_date DATETIME     DEFAULT CURRENT_TIMESTAMP() NOT NULL,

    PRIMARY KEY (ID)
);

CREATE TABLE user(
    ID   BIGINT      			NOT NULL AUTO_INCREMENT,
    First_name VARCHAR (20)   	NOT NULL,
    Last_name VARCHAR (20)   	NOT NULL,
    Region_ID BIGINT,
    Role enum('ADMIN' ,'MODERATOR' ,'USER') NOT NULL,

    PRIMARY KEY (ID),
    FOREIGN KEY (Region_ID) REFERENCES region(ID)
);

CREATE TABLE users_posts(
    Person_ID   BIGINT NOT NULL,
    Post_ID   	BIGINT NOT NULL,

    FOREIGN KEY (Person_ID) REFERENCES user(ID),
    FOREIGN KEY (Post_ID) REFERENCES post(ID)
);
