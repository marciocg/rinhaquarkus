
    create sequence saldo_SEQ start with 1 increment by 50;

    create sequence transacoes_SEQ start with 1 increment by 50;

    create table saldo (
        id integer not null,
        limite integer,
        total integer,
        primary key (id)
    );

    create table transacoes (
        id integer not null,
        saldo_id integer,
        tipo varchar(1),
        valor integer,
        descricao varchar(255),
        realizadaEm varchar(255),
        primary key (id)
    );

    alter table if exists transacoes 
       add constraint FK5glwy1bhv9crnua3fowa6ursf 
       foreign key (saldo_id) 
       references saldo;
insert into saldo (id, total, limite) values (1, 0, 100000, 1);
insert into saldo (id, total, limite) values (2, 0, 80000, 1);
insert into saldo (id, total, limite) values (3, 0, 1000000, 1);
insert into saldo (id, total, limite) values (4, 0, 10000000, 1);
insert into saldo (id, total, limite) values (5, 0, 500000, 1);
