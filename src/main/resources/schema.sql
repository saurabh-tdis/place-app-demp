CREATE TABLE IF NOT EXISTS place(
id int auto_increment,name varchar(250),city varchar(100),state varchar(100),created_date date,status varchar(10),
CONSTRAINT place_pkey PRIMARY KEY (id)
);