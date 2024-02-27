    create sequence saldo_SEQ start with 1 increment by 50;

    create sequence transacoes_SEQ start with 1 increment by 50;

    create table saldo (
        limite integer,
        total integer,
        id bigint not null,
        primary key (id)
    );

    create table transacoes (
        tipo varchar(1),
        valor integer,
        id bigint not null,
        realizada_em timestamp(6) with time zone,
        saldo_id bigint,
        descricao varchar(10),
        primary key (id)
    );

    alter table if exists transacoes 
       add constraint FK5glwy1bhv9crnua3fowa6ursf 
       foreign key (saldo_id) 
       references saldo;
insert into saldo (id, total, limite) values (1, 0, 100000);
insert into saldo (id, total, limite) values (2, 0, 80000);
insert into saldo (id, total, limite) values (3, 0, 1000000);
insert into saldo (id, total, limite) values (4, 0, 10000000);
insert into saldo (id, total, limite) values (5, 0, 500000);
