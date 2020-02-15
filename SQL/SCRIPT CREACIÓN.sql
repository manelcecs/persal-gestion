/*SCRIPT DE CREACIÓN DE TABLAS*/
DROP SCHEMA IF EXISTS `persal_dev`;
CREATE SCHEMA `persal_dev`;
USE `persal_dev`;

/*BORRADO DE TABLAS EXISTENTES*/
/*RESPETAR EL ORDEN DE BORRADO*/
/*PRIMERO LAS TABLAS HIJAS, PADRES DESPUES*/

/*BORRADO HIJOS CLIENTES*/
DROP TABLE IF EXISTS telefonos;
DROP TABLE IF EXISTS emails;

/*BORRADO HIJOS APERTURAS, REFORMAS, ETC*/
DROP TABLE IF EXISTS imagenes_negocios;
DROP TABLE IF EXISTS planos_negocios;
DROP TABLE IF EXISTS imagenes_obras;
DROP TABLE IF EXISTS planos_obras;
DROP TABLE IF EXISTS imagenes_reformas;
DROP TABLE IF EXISTS planos_reformas;

/*BORRADO APERTURAS, REFORMAS, OBRAS, ETC*/
DROP TABLE IF EXISTS obras_nuevas;
DROP TABLE IF EXISTS aperturas_negocio;
DROP TABLE IF EXISTS reformas;

/*BORRADO CLIENTES INACTIVOS*/
DROP TABLE IF EXISTS clientes_inactivos;

/*BORRADO CUENTAS, PRESUPUESTOS, FACTURAS*/
DROP TABLE IF EXISTS facturas;
DROP TABLE IF EXISTS cuentas_expediente;
DROP TABLE IF EXISTS presupuestos;

/*BORADO EXPEDIENTE*/
DROP TABLE IF EXISTS expedientes;

/*ULTIMAS EN BORRAR, TABLAS PADRES*/
DROP TABLE IF EXISTS actividades;
DROP TABLE IF EXISTS clientes;
/*CREACION DE TABLAS*/

CREATE TABLE clientes (
    nif CHAR(9) PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL,
    apellidos VARCHAR(40) NOT NULL,
    domicilio VARCHAR(60),
    poblacion VARCHAR(30),
    codigo_postal CHAR(5),
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_ultima_actividad TIMESTAMP NOT NULL DEFAULT NOW()
);

/*TABLA TELÉFONOS*/
CREATE TABLE telefonos (
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    nif_cliente CHAR(9) NOT NULL,
    numero NUMERIC(9) NOT NULL,
    FOREIGN KEY (nif_cliente)
        REFERENCES clientes (nif)
        ON DELETE CASCADE
);

/*TABLA EMAILS*/
CREATE TABLE emails (
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    nif_cliente CHAR(9) NOT NULL,
    email VARCHAR(30) NOT NULL,
    FOREIGN KEY (nif_cliente)
        REFERENCES clientes (nif)
        ON DELETE CASCADE
);

/*TABLA CLIENTES INACTIVOS*/
CREATE TABLE clientes_inactivos (
    fecha_inactivo TIMESTAMP NOT NULL DEFAULT NOW(),
    nif_cliente CHAR(9) NOT NULL
);

/*TABLA ACTIVIDADES*/
CREATE TABLE actividades (
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    nif_cliente CHAR(9) NOT NULL,
    tipo_actividad NUMERIC(1) NOT NULL,
    fecha_inicio TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_fin TIMESTAMP NOT NULL DEFAULT '2035-01-01',
    FOREIGN KEY (nif_cliente)
        REFERENCES clientes (nif)
);

/*TABLA APERTURAS DE NEGOCIO*/
CREATE TABLE aperturas_negocio (
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    id_actividad SMALLINT NOT NULL,
    tipo_actividad VARCHAR(40) NOT NULL,
    domicilio VARCHAR(70) NOT NULL,
    poblacion VARCHAR(30) NOT NULL,
    FOREIGN KEY (id_actividad)
        REFERENCES actividades (id)
        ON DELETE CASCADE
);

/*TABLA IMAGENES DE NEGOCIOS*/
CREATE TABLE imagenes_negocio (
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    id_ape_neg SMALLINT NOT NULL,
    imagen BLOB NOT NULL,
    FOREIGN KEY (id_ape_neg)
        REFERENCES aperturas_negocio (id)
        ON DELETE CASCADE
);

/*TABLA PLANOS DE NEGOCIO*/
CREATE TABLE planos_negocio (
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    id_ape_neg SMALLINT NOT NULL,
    plano BLOB NOT NULL,
    FOREIGN KEY (id_ape_neg)
        REFERENCES aperturas_negocio (id)
        ON DELETE CASCADE
);

/*TABLA OBRAS NUEVAS*/
CREATE TABLE obras_nuevas (
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    id_actividad SMALLINT NOT NULL,
    domicilio VARCHAR(70) NOT NULL,
    poblacion VARCHAR(30) NOT NULL,
    superficie FLOAT,
    aprob_col_arq CHAR(2) DEFAULT 'NO' CHECK (aprob_col_arq = 'SI'
        OR aprob_col_arq = 'NO'),
    dir_obra CHAR(2) DEFAULT 'NS' NOT NULL CHECK (dir_obra = 'SI' 
		OR dir_obra = 'NO'
        OR dir_obra = 'NS'),
    FOREIGN KEY (id_actividad)
        REFERENCES actividades (id)
        ON DELETE CASCADE
);

/*TABLA IMAGENES DE OBRAS*/
CREATE TABLE imagenes_obras (
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    id_obra SMALLINT NOT NULL,
    imagen BLOB NOT NULL,
    FOREIGN KEY (id_obra)
        REFERENCES obras_nuevas (id)
        ON DELETE CASCADE
);

/*TABLA PLANOS DE OBRAS*/
CREATE TABLE planos_obras (
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    id_obra SMALLINT NOT NULL,
    plano BLOB NOT NULL,
    FOREIGN KEY (id_obra)
        REFERENCES obras_nuevas (id)
        ON DELETE CASCADE
);

/*TABLA REFORMAS*/
CREATE TABLE reformas (
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    id_actividad SMALLINT NOT NULL,
    domicilio VARCHAR(70) NOT NULL,
    poblacion VARCHAR(30) NOT NULL,
    tipo_actuacion VARCHAR(20) NOT NULL CHECK (tipo_actuacion = 'local comercial'
        OR tipo_actuacion = 'nave industrial'
        OR tipo_actuacion = 'vivienda'),
    tipo_reforma VARCHAR(20) NOT NULL CHECK (tipo_reforma = 'apliación'
        OR tipo_reforma = 'remodelación'),
    demolicion CHAR(2) NOT NULL CHECK (demolicion = 'SI' 
		OR demolicion = 'NO'),
    superficie FLOAT,
    aprob_col_arq CHAR(2) DEFAULT 'NO' CHECK (aprob_col_arq = 'SI'
        OR aprob_col_arq = 'NO'),
    dir_obra CHAR(2) DEFAULT 'NS' NOT NULL CHECK (dir_obra = 'SI' 
		OR dir_obra = 'NO'
        OR dir_obra = 'NS'),
    FOREIGN KEY (id_actividad)
        REFERENCES actividades (id)
        ON DELETE CASCADE
);

/*TABLA IMAGENES DE OBRAS*/
CREATE TABLE imagenes_reformas (
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    id_refor SMALLINT NOT NULL,
    imagen BLOB NOT NULL,
    FOREIGN KEY (id_refor)
        REFERENCES reformas (id)
        ON DELETE CASCADE
);

/*TABLA PLANOS DE OBRAS*/
CREATE TABLE planos_reformas (
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    id_refor SMALLINT NOT NULL,
    plano BLOB NOT NULL,
    FOREIGN KEY (id_refor)
        REFERENCES reformas (id)
        ON DELETE CASCADE
);

/*TABLA EXPEDIENTE*/
CREATE TABLE expedientes (
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    id_actividad SMALLINT NOT NULL,
    fecha_apertura TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_cierre TIMESTAMP NOT NULL DEFAULT '2035-01-01',
    aprob_mun CHAR(2) NOT NULL DEFAULT 'NO' CHECK (aprob_mun = 'SI' OR aprob_mun = 'NO'),
    FOREIGN KEY (id_actividad)
        REFERENCES actividades (id)
);

/*TABLA PRESUPUESTOS*/
CREATE TABLE presupuestos (
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    id_expediente SMALLINT NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT NOW(),
    descripcion VARCHAR(560) NOT NULL,
    cantidad FLOAT NOT NULL,
    descuento NUMERIC(3) NOT NULL DEFAULT 0 CHECK (0 <= descuento <= 100),
    impuestos NUMERIC(3) NOT NULL DEFAULT 0 CHECK (0 <= impuestos <= 100),
    cantidad_total FLOAT NOT NULL,
    FOREIGN KEY (id_expediente)
        REFERENCES expedientes (id)
);

/*TABLA CUENTA EXPEDIENTE*/
CREATE TABLE cuentas_expediente (
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    id_expediente SMALLINT NOT NULL,
    cantidad FLOAT NOT NULL,
    descuento NUMERIC(3) NOT NULL DEFAULT 0 CHECK (0 <= descuento <= 100),
    impuestos NUMERIC(3) NOT NULL DEFAULT 0 CHECK (0 <= impuestos <= 100),
    cantidad_total FLOAT NOT NULL,
    FOREIGN KEY (id_expediente)
        REFERENCES expedientes (id)
);

/*TABLA FACTURA*/
CREATE TABLE facturas (
    id SMALLINT AUTO_INCREMENT PRIMARY KEY,
    id_cuenta SMALLINT NOT NULL,
    fecha_emision TIMESTAMP NOT NULL DEFAULT NOW(),
    descripcion VARCHAR(560) NOT NULL,
    cantidad FLOAT NOT NULL,
    impuestos NUMERIC(3) NOT NULL DEFAULT 0 CHECK (0 <= impuestos <= 100),
    cantidad_total FLOAT NOT NULL,
    FOREIGN KEY (id_cuenta)
        REFERENCES cuentas_expediente (id)
);