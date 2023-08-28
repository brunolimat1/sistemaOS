/**
 * Sistema para gestão de OS
 * @author Bruno Lima
 */
 
 drop database dbsistema;
 create database dbsistema;
 use dbsistema;
 
 create table usuarios (
	id int primary key auto_increment,
    nome varchar(50) not null,
    login varchar(15) not null unique,
    senha varchar(250) not null,
    perfil varchar(10) not null
);

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

-- uso do md5() para criptografar uma senha
insert into usuarios (nome, login, senha, perfil)
values ('bruno','admin', md5('admin'),'admin');

insert into usuarios (nome, login, senha, perfil)
values ('Robson','vava', md5('123@senac'),'user');

ALTER TABLE servicos
ADD constraint cliente UNIQUE (nome);

-- reposição de estoque
select codigo as código,produto,date_format(dataval, '%d/%m/%Y') as validade,estoque, estoquemin as estoque_mínimo from produtos where estoque < estoquemin;

-- produtos vencidos
select codigo as código,produto,
date_format(dataval, '%d/%m/%Y') as validade, date_format(dataent, '%d/%m/%Y') as entrada
from produtos where dataval < dataent;

-- patrimônio(inventário) custo dos produtos
select sum(custo * estoque)as Total from produtos;

select sum((custo + (custo * lucro)/100) * estoque) as total from produtos;

select * from produtos inner join fornecedores on produtos.idfor = fornecedores.idfor where produto = 1;