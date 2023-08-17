/**
 * Sistema para gestão de OS
 * @author Bruno Lima
 */
 
 drop database dbsistema;
 create database dbsistema;
 use dbsistema;
 show tables;
 
 create table usuarios (
	id int primary key auto_increment,
    nome varchar(50) not null,
    login varchar(15) not null unique,
    senha varchar(250) not null,
    perfil varchar(10) not null
);

select * from clientes;

create table clientes (
	idcli int primary key auto_increment,
    nome varchar(50) not null,
    telefone varchar (15) not null,
    cpf varchar (50) not null,
	cep varchar (10),
    endereco varchar (50) not null,
    numero varchar (10) not null,
    complemento varchar (20),
    bairro varchar (30) not null,
    cidade varchar (30) not null,
    uf char (2) not null
);

drop table servicos;
create table servicos (
    os int primary key auto_increment,
    data_os timestamp default current_timestamp,
	equipamentos varchar (200) not null,
    defeito varchar (200) not null,
    valor decimal (10,2),
    nome varchar (50) not null,
	idcli int not null,
    foreign key(idcli) references clientes(idcli)
);

select * from fornecedores;

drop table fornecedores;

create table fornecedores (
	idfor int primary key auto_increment,
    razao varchar(50) not null,
    fantasia varchar(50) not null,
    fone varchar(15) not null,
    vendedor varchar(20),
    email varchar(50) not null,
    site varchar(50),
    cnpj varchar(20) not null unique,
    ie varchar(20),
    cep varchar(10),
    endereco varchar(50) not null,
    numero varchar(10) not null,
    complemento varchar(20),
    bairro varchar(30) not null,
    cidade varchar(30) not null,
    uf char(2) not null
);

drop table produtos;
CREATE TABLE produtos (
  codigo int primary key AUTO_INCREMENT,
  barcode varchar(250) unique,
  produto varchar(50) not null,
  lote varchar(20) not null,
  descricao varchar(250) not null,
  foto longblob,
  fabricante varchar(50),
  dataent timestamp default current_timestamp,
  dataval date not null,
  estoque int not null,
  estoquemin int not null,
  unidade char(2) not null,
  localarm varchar(50),
  custo decimal(10,2) not null,
  lucro decimal(10,2),
  idfor int not null,
  foreign key (idfor) references fornecedores (idfor) 
  );
  

delete from usuarios WHERE id = 6;

describe usuarios;
select * from usuarios;
select * from usuarios where nome = "Jorge";

describe clientes;
select * from clientes;
select * from clientes where nome = "";

-- uso do md5() para criptografar uma senha
insert into usuarios (nome, login, senha, perfil)
values ('bruno','admin', md5('admin'),'admin');

insert into usuarios (nome, login, senha, perfil)
values ('Robson','vava', md5('123'),'user');

update usuarios set nome = 'robson vaamonde' where id = 1;

update clientes set nome = 'ricardo' where id = 1;

update servicos set os = 1 where id = 6;

-- login(autenticação)
select * from usuarios where login = "admin" and senha = md5('admin');

drop table servicos;

drop table clientes;

drop table usuarios;

ALTER TABLE produtos
ADD constraint id UNIQUE (idfor);

ALTER TABLE servicos
ADD constraint cliente UNIQUE (nome);

select * from servicos
inner join clientes
on servicos.idcli = clientes.idcli;

select * from servicos inner join clientes on servicos.idcli = clientes.idcli where OS = 1;


select * from servicos inner join clientes on servicos.idcli = clientes.idcli order by idcli;

select * from servicos;

select * from fornecedores inner join produtos on fornecedores.idfor = produtos.idfor;


select * from fornecedores
inner join produtos
on fornecedores.idfor = produtos.idfor;

select * from produtos inner join fornecedores on produtos.idfor = fornecedores.idfor where codigo = 1;