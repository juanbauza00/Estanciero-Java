create database ESTANCIERO;
go
use ESTANCIERO;
go
create table ACTION_TYPES(
                             action_type_id int identity(0,1),
                             action_type varchar(200),
                             constraint pk_ACTION_TYPES primary key (action_type_id)
);
go
create table ZONES(
                      zone_id int identity(0,1),
                      zone_name varchar(20)
                          constraint pk_ZONES primary key (zone_id)
);
go
create table BOARD_SLOT_TYPES(
                                 board_slot_type_id int identity(0,1),
                                 slot_type varchar(20)
                                     constraint pk_BOARD_SLOT_TYPES primary key (board_slot_type_id)
);
go
create table PLAYER_STYLES(
                              player_style_id int identity(0,1),
                              player_style varchar(20)
                                  constraint pk_PLAYER_STYLES primary key(player_style_id)
);
go
create table PLAYER_TYPES(
							player_type_id int identity(0,1),
							player_type varchar(20)
							constraint pk_PLAYER_TYPES primary key(player_type_id)
);
go
create table MATCHES(
						match_id int,
						match_date datetime,
						money_goal int,
						game_difficulty int,
						game_mode int,
						constraint pk_MATCHES primary key(match_id)
);
go
create table PLAYERS(
						player_id int,
						player_style_id int,
						player_type_id int,
						player_name varchar(30),
						player_money int,
						player_position int,
						is_player_in_jail bit,
						amount_of_free_jail_cards int,
						province_count int,
						railway_count int,
						company_count int,
						upgrade_count int,
						rest_count int,
						is_player_turn bit,
						is_bankrupt bit,
						match_id int,
						constraint pk_PLAYERS primary key(player_id),
						constraint fk_PLAYERS_a_PLAYER_STYLES foreign key(player_style_id)
							references PLAYER_STYLES(player_style_id),
						constraint fk_PLAYERS_a_PLAYER_TYPES foreign key(player_type_id)
							references PLAYER_TYPES(player_type_id),
						constraint fk_PLAYERS_a_MATCHES foreign key(match_id)
							references MATCHES(match_id)
);
go
create table PROVINCE_ZONES(
                          province_zone_id int identity(0,1),
                          zone_id int,
                          province varchar(30),
                          ranch_price int
                              constraint pk_PROVINCES primary key (province_zone_id),
                          constraint fk_PROVINCES_a_ZONES foreign key(zone_id)
                              references ZONES(zone_id)
);
go
create table SPECIAL_CARDS(
                              special_card_id int,
                              board_slot_type_id int,
                              card_message varchar(200),
                              amount int,
                              to_position int,
                              action_type_id int,
							  match_id int,
                                  constraint pk_SPECIAL_CARDS primary key (special_card_id),
                              constraint fk_SPECIAL_CARDS_a_BOARD_SLOT_TYPES foreign key(board_slot_type_id)
                                  references BOARD_SLOT_TYPES (board_slot_type_id),
                              constraint fk_SPECIAL_CARDS_a_ACTION_TYPES foreign key (action_type_id)
                                  references ACTION_TYPES(action_type_id),
							  constraint fk_SPECIAL_CARDS_a_MATCHES foreign key(match_id)
								  references MATCHES(match_id)
);
go
create table BOARD_SLOTS(
                            board_slot_id int not null,
                            board_slot_type_id int,
                            amount_price int
                                constraint pk_BOARD_SLOTS primary key (board_slot_id),
                            constraint fk_BOARD_SLOTS_a_BOARD_SLOT_TYPES foreign key(board_slot_type_id)
                                references BOARD_SLOT_TYPES (board_slot_type_id)
);
go
create table PURCHASABLE_SLOTS(
								board_slot_id int,
								match_id int,
								board_slot_type_id int,
								province_zone_id int,
								owner_id int,
								ranch_count int,
								purchase_price int
								constraint pk_PURCHASABLE_SLOTS primary key(board_slot_id, match_id),
								constraint fk_PURCHASABLE_SLOTS_a_BOARD_SLOTS foreign key(board_slot_id)
									references BOARD_SLOTS(board_slot_id),
								constraint fk_PURCHASABLE_SLOTS_a_MATCHES foreign key(match_id)
									references MATCHES(match_id),
								constraint fk_PURCHASABLE_SLOTS_a_BOARD_SLOT_TYPES foreign key(board_slot_type_id)
									references BOARD_SLOT_TYPES(board_slot_type_id),
								constraint fk_PURCHASABLE_SLOTS_a_PROVINCES foreign key(province_zone_id)
									references PROVINCE_ZONES(province_zone_id),
								constraint fk_PURCHASABLE_SLOTS_a_PLAYERS foreign key(owner_id)
									references PLAYERS(player_id)
);
create table RENTAL_PRICES(
                              rental_price_id int identity(1,1),
                              board_slot_id int,
							  match_id int,
                              rental_count int,
                              price int
                                  constraint pk_RENTAL_PRICES primary key(rental_price_id),
                              constraint fk_RENTAL_PRICES_a_PURCHASABLE_SLOTS foreign key(board_slot_id, match_id)
                                  references PURCHASABLE_SLOTS(board_slot_id, match_id)
);
go
-------------------------------------------------------------------------INSERTS--------------------------------------------------------------
-------------------------------------------------------------------------ACTION_TYPES--------------------------------------------------------------

INSERT INTO ACTION_TYPES (action_type)
VALUES ('PAY'),
       ('PAYFOREACH'),
       ('COLLECT'),
	   ('COLLECTFOREACH'),
       ('GOJAIL'),
       ('OUTJAIL'),
	   ('MOVE');
GO
-------------------------------------------------------------------------ZONES--------------------------------------------------------------
INSERT INTO ZONES (zone_name)
VALUES ('SUR'),
	   ('CENTRO'),
	   ('NORTE');
GO
-------------------------------------------------------------------------BOARD_SLOT_TYPES--------------------------------------------------------------
INSERT INTO BOARD_SLOT_TYPES(slot_type)
VALUES ('Province'),--0
	   ('Railway'),--1
	   ('Company'),--2
	   ('Jail'),--3
	   ('Rest'),--4
	   ('Tax'),--5
	   ('Prize'),--6
	   ('Luck'), -- 7
	   ('Destiny'); -- 8
	   
GO 
-------------------------------------------------------------------------PLAYER_STYLES--------------------------------------------------------------
INSERT INTO PLAYER_STYLES(player_style)
VALUES ('CONSERVATIVE'),
	   ('EQUILIBRATED'),
	   ('AGGRESSIVE');
GO
-------------------------------------------------------------------------PLAYER_TYPES--------------------------------------------------------------
INSERT INTO PLAYER_TYPES(player_type)
VALUES ('HUMAN'),
	   ('BOT');
GO
-------------------------------------------------------------------------MATCHES--------------------------------------------------------------
INSERT INTO MATCHES(match_id, match_date, game_difficulty, game_mode)
VALUES (0,GETDATE(),null,null);
GO
-------------------------------------------------------------------------PROVINCES--------------------------------------------------------------
INSERT INTO PROVINCE_ZONES(zone_id, province, ranch_price)
VALUES	(0,'FORMOSA',1000),
		(1,'FORMOSA',1000),
		(2,'FORMOSA',1000),
		(0,'RIO_NEGRO',1000),
		(2,'RIO_NEGRO',1000),
		(0,'SALTA',1500),
		(1,'SALTA',1500),
		(2,'SALTA',1500),
		(0,'MENDOZA',2000),
		(1,'MENDOZA',2000),
		(2,'MENDOZA',2000),
		(0,'SANTA_FE',2500),
		(1,'SANTA_FE',2500),
		(2,'SANTA_FE',2500),
		(0,'TUCUMAN',3000),
		(2,'TUCUMAN',3000),
		(0,'CORDOBA',3000),
		(1,'CORDOBA',3000),
		(2,'CORDOBA',3000),
		(0,'BSAS',4000),
		(1,'BSAS',4000),
		(0,'BSAS',4000);
go
-------------------------------------------------------------------------SPECIAL_CARDS--------------------------------------------------------------
INSERT INTO SPECIAL_CARDS(special_card_id,board_slot_type_id,card_message,amount,to_position,action_type_id, match_id)
				      VALUES(0,7,'Advance to Salta, North Side, collect $5,000 if you pass through the Start Line',null, 13,6,0),
                            (1,7,'Advance to Santa Fe, North Side, collect $5,000 if you pass through the Start Line',null, 26,6,0),
                            (2,7,'OH NO! YOU ARE GOING STRAIGHT TO JAIL FOR TAX EVASION',null, 14,4,0),
                            (3,7,'Advance to Buenos Aires, North Side',null,40,6,0),
                            (4,7,'Advance to the Start Line',null, 0,6,0),
                            (5,7,'Advance to La Bodega, collect $5,000 if you pass through the Start Line',null,16,6,0),
                            (6,7,'Pay a speed fee of $300',300, null,0,0),
                            (7,7,'Collect $1,000 in bank interest',1000, null,2,0),
                            (8,7,'You have to repair your properties, pay $500 for each ranch',500,null,1,0),
                            (9,7,'Habeas Corpus granted! With this card, you can escape from Prison! You are free to keep it or sell it.',null,null,5,0),
                            (10,7,'You spent $3,000 on school expenses',3000,null,0,0),
                            (11,7,'YOU WON THE LOTTERY! CONGRATULATIONS! Collect $10,000',10000,null,2,0),
                            (12,7,'For seed purchase, pay $800 for each ranch',null,null,1,0),
                            (13,7,'Pay a speed fee of $400',400,null,0,0),
                            (14,7,'You won $3,000 in the horse races!',3000,null,2,0),
                            (15,8,'Advance to the Start Line',null, 0,6,0),
                            (16,8,'Return to Formosa, South Side',null,1,6,0),
                            (17,8,'OH NO! YOU ARE GOING STRAIGHT TO JAIL FOR MONEY LAUNDERING',null, 14,4,0),
                            (18,8,'You inherited $2,000',2000,null,2,0),
                            (19,8,'You sold shares, collect $1,000',1000,null,2,0),
                            (20,8,'You are "inimputable", brother! With this card, you can escape from Prison! You are free to keep it or sell it.',null,null,5,0),
                            (21,8,'You spent $1,000 on your insurance policy',1000,null,0,0),
                            (22,8,'You won an agricultural contest! Collect $2,000',2000,null,2,0),
                            (23,8,'You won the second prize in a beauty contest! Collect $200',200,null,2,0),
                            (24,8,'You collected 5% of the interest on mortgage bonds, collect $500',500,null,2,0),
                            (25,8,'Tax refund, collect $500',500,null,2,0),
                            (26,8,'Bank calculation error, collect $5,000',5000,null,2,0),
                            (27,8,'You won $2,000 in a cow contest!',2000,null,2,0),
                            (28,8, 'Happy Birthday! Every other player pays you $500.', 500, null,3,0),
                            (29,8,'You spent $1,000 on health care expenses',1000,null,0,0),
                            (30,8,'Pay a speed fee of $200',200,null,0,0);
go

-------------------------------------------------------------------------BOARD_SLOTS--------------------------------------------------------------
INSERT INTO BOARD_SLOTS(board_slot_id,board_slot_type_id, amount_price) --Slot amount
				 VALUES(0,6, 5000),
					   (1,0,1000),
					   (2,0,1000),
					   (3,0,1200),
					   (4,7,null),
					   (5,0,2000),
					   (6,0,2200),
					   (7,6,2500),
					   (8,2,3800),
					   (9,0,2600),
					   (10,8,null),
					   (11,0,2600),
					   (12,1,3600),
					   (13,0,3000),
					   (14,3,null),
					   (15,7,null),
					   (16,2,3800),
					   (17,0,3400),
					   (18,1,3600),
					   (19,0,3400),
					   (20,0,3800),
					   (21,4,null),
					   (22,1,3600),
					   (23,0,4200),
					   (24,0,4200),
					   (25,8,null),
					   (26,0,4600),
					   (27,1,3600),
					   (28,4,null),
					   (29,0,5000),
					   (30,0,5400),
					   (31,5,5000),
					   (32,0,6000),
					   (33,0,6000),
					   (34,0,6400),
					   (35,3,null),
					   (36,7,null),
					   (37,0,7000),
					   (38,8,null),
					   (39,0,7000),
					   (40,0,7400),
					   (41,5,2000);

go
-------------------------------------------------------------------------PURCHASABLE_SLOTS--------------------------------------------------------------
INSERT INTO PURCHASABLE_SLOTS(board_slot_id, match_id, board_slot_type_id, province_zone_id, owner_id,ranch_count, purchase_price) --purchase_price es el mismo valor de amount_price de BOARD_SLOTS
				 VALUES (1, 0, 0, 0, null, 0, 1000),
					    (2, 0, 0, 1, null, 0, 1000),
						(3, 0, 0, 2, null, 0, 1200),
						(5, 0, 0, 3, null, 0, 2000),
						(6, 0, 0, 4, null, 0, 2200),
						(8, 0, 2, null, null, 0, 3800),
						(9, 0, 0, 5, null, 0, 2600),
						(11, 0, 0, 6, null, 0, 2600),
						(12, 0, 1, null, null, 0, 3600),
						(13, 0, 0, 7, null, 0, 3000),
						(16, 0, 2, null, null, 0, 3800),
						(17, 0, 0, 8, null, 0, 3400),
						(18, 0, 1, null, null, 0, 3600),
						(19, 0, 0, 9, null, 0, 3400),
						(20, 0, 0, 10, null, 0, 3800),
						(22, 0, 1, null, null, 0, 3600),
						(23, 0, 0, 11, null, 0, 4200),
						(24, 0, 0, 12, null, 0, 4200),
						(26, 0, 0, 13, null, 0, 4600),
						(27, 0, 1, null, null, 0, 3600),
						(29, 0, 0, 14, null, 0, 5000),
						(30, 0, 0, 15, null, 0, 5400),
						(32, 0, 0, 16, null, 0, 6000),
						(33, 0, 0, 17, null, 0, 6000),
						(34, 0, 0, 18, null, 0, 6400),
						(37, 0, 0, 19, null, 0, 7000),
						(39, 0, 0, 20, null, 0, 7000),
						(40, 0, 0, 21, null, 0, 7400);

go
-------------------------------------------------------------------------RENTAL_PRICES--------------------------------------------------------------

---------------------------FORMOSA ZONA SUR-----------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (1,0,0,40),
       (1,0,1,200),
       (1,0,2,600),
       (1,0,3,1700),
       (1,0,4,3000),
       (1,0,5,4750);
go
---------------------------FORMOSA CENTRO--------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (2,0,0,40),
       (2,0,1,200),
       (2,0,2,600),
       (2,0,3,1700),
       (2,0,4,3000),
       (2,0,5,4750);
go
---------------------------FORMOSA NORTE---------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (3,0,0,80),
       (3,0,1,400),
       (3,0,2,800),
       (3,0,3,3400),
       (3,0,4,6000),
       (3,0,5,9500);
go
---------------------------RIO NEGRO SUR---------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (5,0,0,110),
       (5,0,1,570),
       (5,0,2,1700),
       (5,0,3,3150),
       (5,0,4,7600),
       (5,0,5,9500);
go
---------------------------RIO NEGRO NORTE-------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (6,0,0,150),
       (6,0,1,750),
       (6,0,2,2000),
       (6,0,3,5700),
       (6,0,4,8500),
       (6,0,5,11500);
go
---------------------------SALTA SUR-------------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (9,0,0,200),
       (9,0,1,1000),
       (9,0,2,2800),
       (9,0,3,8500),
       (9,0,4,12000),
       (9,0,5,14200);
go
---------------------------SALTA CENTRO----------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (11,0,0,200),
       (11,0,1,1000),
       (11,0,2,2800),
       (11,0,3,8500),
       (11,0,4,12000),
       (11,0,5,14200);
go
---------------------------SALTA NORTE-----------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (13,0,0,230),
       (13,0,1,1150),
       (13,0,2,3400),
       (13,0,3,9500),
       (13,0,4,13000),
       (13,0,5,17000);
go
---------------------------MENDOZA SUR-----------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (17,0,0,250),
       (17,0,1,1350),
       (17,0,2,3800),
       (17,0,3,10500),
       (17,0,4,14200),
       (17,0,5,18000);
go
---------------------------MENDOZA CENTRO--------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (19,0,0,250),
       (19,0,1,1350),
       (19,0,2,3800),
       (19,0,3,10500),
       (19,0,4,14200),
       (19,0,5,18000);
go
---------------------------MENDOZA NORTE---------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (20,0,0,300),
       (20,0,1,1500),
       (20,0,2,4200),
       (20,0,3,11500),
       (20,0,4,15000),
       (20,0,5,19000);
go
---------------------------SANTA FE SUR----------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (23,0,0,350),
       (23,0,1,1700),
       (23,0,2,4750),
       (23,0,3,13000),
       (23,0,4,16000),
       (23,0,5,20000);
go
---------------------------SANTA FE CENTRO-------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (24,0,0,350),
       (24,0,1,1700),
       (24,0,2,4750),
       (24,0,3,13000),
       (24,0,4,16000),
       (24,0,5,20000);
go
---------------------------SANTA FE NORTE--------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (26,0,0,400),
       (26,0,1,2000),
       (26,0,2,5750),
       (26,0,3,14000),
       (26,0,4,17000),
       (26,0,5,21000);
go
---------------------------TUCUMAN SUR-----------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (29,0,0,400),
       (29,0,1,2200),
       (29,0,2,6000),
       (29,0,3,15000),
       (29,0,4,18000),
       (29,0,5,21000);
go
---------------------------TUCUMAN NORTE---------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (30,0,0,450),
       (30,0,1,2400),
       (30,0,2,6800),
       (30,0,3,16000),
       (30,0,4,19500),
       (30,0,5,23000);
go
---------------------------CORDOBA SUR-----------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (32,0,0,500),
       (32,0,1,2500),
       (32,0,2,6500),
       (32,0,3,17000),
       (32,0,4,21000),
       (32,0,5,24000);
go
---------------------------CORDOBA CENTRO--------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (33,0,0,450),
       (33,0,1,2400),
       (33,0,2,6800),
       (33,0,3,16000),
       (33,0,4,19500),
       (33,0,5,23000);
go
---------------------------CORDOBA NORTE---------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (34,0,0,550),
       (34,0,1,2850),
       (34,0,2,8500),
       (34,0,3,19000),
       (34,0,4,23000),
       (34,0,5,27000);
go
---------------------------BS.AIRES SUR----------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (37,0,0,650),
       (37,0,1,3300),
       (37,0,2,9500),
       (37,0,3,22000),
       (37,0,4,25000),
       (37,0,5,30000);
go
---------------------------BS.AIRES CENTRO----------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (39,0,0,650),
       (39,0,1,3300),
       (39,0,2,9500),
       (39,0,3,22000),
       (39,0,4,25000),
       (39,0,5,30000);
go
---------------------------BS.AIRES NORTE----------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (40,0,0,1000),
       (40,0,1,4000),
       (40,0,2,12000),
       (40,0,3,26000),
       (40,0,4,31000),
       (40,0,5,36000);
go
---------------------------FERROCARRIL GRAL. BELGRANO----------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (12,0,1,500),
       (12,0,2,1000),
       (12,0,3,2000),
       (12,0,4,4000);
go
---------------------------FERROCARRIL GRAL. SAN MARTIN--------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (18,0,1,500),
       (18,0,2,1000),
       (18,0,3,2000),
       (18,0,4,4000);
go
---------------------------FERROCARRIL GRAL. B. MITRE----------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (22,0,1,500),
       (22,0,2,1000),
       (22,0,3,2000),
       (22,0,4,4000);
go
---------------------------FERROCARRIL GRAL. URQUIZA-----------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (27,0,1,500),
       (27,0,2,1000),
       (27,0,3,2000),
       (27,0,4,4000);
go
---------------------------COMPAÑIA PETROLERA------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (8,0,1,100),
       (8,0,2,200);
go
---------------------------BODEGA------------------------------------------------
insert RENTAL_PRICES(board_slot_id, match_id, rental_count, price)
values (16,0,1,100),
       (16,0,2,200);

	   GO
---------------------------------------------------------------------------------------------------------------------------------------------------------

------------------------------------------------JUAN--------------------------------------------------------------------------------
/*
create procedure SP_GET_BOARD_SLOTS
as
begin
SELECT bs.board_slot_id 'position', bs.board_slot_type_id 'type_id', bs.amount_price 'price', pv. 'province', ZN.zone_name 'zone', pv.ranch_price 'ranch_price'
from BOARD_SLOTS bs
join BOARD_SLOT_TYPES as bst on bst.id_board_slot_type = bs.id_board_slot_type
left join PROVINCE_ZONES as pv on pv.province_zone_id = bs.
left join ZONES as zn on zn.id_zone = pv.id_zone
end

/*
EXECUTE SP_GET_BOARD_SLOTS
select * from BOARD_SLOTS
*/
go

create procedure SP_GET_RENT_VALUES
@id_position int
as
begin
select rp.price 'price'
from BOARD_SLOTS bs
join RENTAL_PRICES rp on rp.board_slot_id = bs.board_slot_id
where bs.board_slot_id = @id_position
end

GO
------------------------------------------------MATEO--------------------------------------------------------------------------------
/*create procedure SP_GET_SPECIAL_CARDS
    as
begin
select BST.slot_type 'CARD TYPE', SC.card_message , amount, to_position, aty.action_type
from SPECIAL_CARDS SC join ACTION_TYPES ATY on SC.action_type_id= ATY.action_type_id
                      JOIN BOARD_SLOT_TYPES BST on SC.board_slot_type_id= BST.board_slot_type_id;
end;
go

--create procedure SP_GET_SPECIAL_CARD_BY_ID
create procedure SP_GET_SPECIAL_CARD_BY_ID
    @id_card int
as
begin
select BST.slot_type 'CARD TYPE', SC.card_message , amount, to_position, aty.action_type
from SPECIAL_CARDS SC join ACTION_TYPES ATY on SC.action_type_id= ATY.action_type_id
                      JOIN BOARD_SLOT_TYPES BST on SC.board_slot_type_id= BST.board_slot_type_id
WHERE sc.special_card_id= @id_card;
end;
go

*/