--
-- PostgreSQL database dump
--

-- Dumped from database version 13.6 (Ubuntu 13.6-0ubuntu0.21.10.1)
-- Dumped by pg_dump version 13.6 (Ubuntu 13.6-0ubuntu0.21.10.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: id-discussao; Type: SEQUENCE; Schema: public; Owner: ti2cc
--

CREATE SEQUENCE public."id-discussao"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000000
    CACHE 1;


ALTER TABLE public."id-discussao" OWNER TO ti2cc;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: discussao; Type: TABLE; Schema: public; Owner: ti2cc
--

CREATE TABLE public.discussao (
    id integer DEFAULT nextval('public."id-discussao"'::regclass) NOT NULL,
    titulo text,
    conteudo text,
    autor text,
    curtidas integer,
    data text
);


ALTER TABLE public.discussao OWNER TO ti2cc;

--
-- Name: discussao discussao_pkey; Type: CONSTRAINT; Schema: public; Owner: ti2cc
--

ALTER TABLE ONLY public.discussao
    ADD CONSTRAINT discussao_pkey PRIMARY KEY (id);




CREATE SEQUENCE public."id-usuario"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000000
    CACHE 1;
--
-- Name: usuario; Type: TABLE; Schema: public; Owner: ti2cc
--

CREATE TABLE public.usuario (
    id integer DEFAULT nextval('public."id-usuario"'::regclass) NOT NULL,
    nome text,
    senha text
);


ALTER TABLE public.usuario OWNER TO ti2cc;

--
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: ti2cc
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);
    
    CREATE SEQUENCE public."id-aval"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000000
    CACHE 1;


--
-- Name: avaliacao; Type: TABLE; Schema: public; Owner: ti2cc
--

CREATE TABLE public.avaliacao (
    id integer DEFAULT nextval('public."id-aval"'::regclass) NOT NULL,
    valor integer,
    id_usr integer
);


ALTER TABLE public.avaliacao OWNER TO ti2cc;

--
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: ti2cc
--

ALTER TABLE ONLY public.avaliacao
    ADD CONSTRAINT avaliacao_pkey PRIMARY KEY (id);

--
-- PostgreSQL database dump complete
--    
