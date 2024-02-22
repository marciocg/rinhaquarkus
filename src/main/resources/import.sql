-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

insert into Saldo (id, total, limite) values (1, 0, 100000);
insert into Saldo (id, total, limite) values (2, 0, 80000);
insert into Saldo (id, total, limite) values (3, 0, 1000000);
insert into Saldo (id, total, limite) values (4, 0, 10000000);
insert into Saldo (id, total, limite) values (5, 0, 500000);