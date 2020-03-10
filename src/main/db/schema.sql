--
-- PostgreSQL database dump
--

-- Dumped from database version 10.10
-- Dumped by pg_dump version 10.10

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
-- Name: tokiomarine_nichido; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA tokiomarine_nichido;


ALTER SCHEMA tokiomarine_nichido OWNER TO postgres;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: recommended_text_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.recommended_text_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.recommended_text_id_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: RECOMMENDED_TEXT; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."RECOMMENDED_TEXT" (
    id integer DEFAULT nextval('public.recommended_text_id_seq'::regclass) NOT NULL,
    statement1 text,
    statement2 text,
    statement3 text,
    confidence character varying(8),
    is_correct boolean,
    speech_id character varying,
    final_classification boolean,
    code_statement1 character varying,
    code_statement2 character varying,
    code_statement3 character varying
);


ALTER TABLE public."RECOMMENDED_TEXT" OWNER TO postgres;

--
-- Name: SPEECH_TEXT; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."SPEECH_TEXT" (
    id integer NOT NULL,
    own_phone_number character varying,
    case_id character varying,
    not_evaluation_flg boolean,
    data text,
    update_at timestamp with time zone,
    speech_started character varying,
    speech_id character varying
);


ALTER TABLE public."SPEECH_TEXT" OWNER TO postgres;

--
-- Name: SPEECH_TEXT_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."SPEECH_TEXT_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."SPEECH_TEXT_id_seq" OWNER TO postgres;

--
-- Name: SPEECH_TEXT_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."SPEECH_TEXT_id_seq" OWNED BY public."SPEECH_TEXT".id;


--
-- Name: TAGS; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."TAGS" (
    id integer NOT NULL,
    category character varying(100),
    type character varying(100)
);


ALTER TABLE public."TAGS" OWNER TO postgres;

--
-- Name: TEXT; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."TEXT" (
    id integer NOT NULL,
    utterance_start_time bigint,
    utterance_end_time bigint,
    channel_id character varying(4),
    text text
);


ALTER TABLE public."TEXT" OWNER TO postgres;

--
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_id_seq OWNER TO postgres;

--
-- Name: USER; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."USER" (
    id integer DEFAULT nextval('public.user_id_seq'::regclass) NOT NULL,
    name character varying,
    phone_ip character varying,
    role integer,
    password bytea,
    salt bytea,
    failed_count integer,
    failed_time timestamp without time zone,
    update_interval integer,
    initial_display_interval integer,
    user_id character varying
);


ALTER TABLE public."USER" OWNER TO postgres;

--
-- Name: SPEECH_TEXT id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."SPEECH_TEXT" ALTER COLUMN id SET DEFAULT nextval('public."SPEECH_TEXT_id_seq"'::regclass);


--
-- Name: RECOMMENDED_TEXT FIXED_PHRASE_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."RECOMMENDED_TEXT"
    ADD CONSTRAINT "FIXED_PHRASE_pkey" PRIMARY KEY (id);


--
-- Name: SPEECH_TEXT SPEECH_TEXT_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."SPEECH_TEXT"
    ADD CONSTRAINT "SPEECH_TEXT_pkey" PRIMARY KEY (id);


--
-- Name: USER USER_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."USER"
    ADD CONSTRAINT "USER_pkey" PRIMARY KEY (id);


--
-- Name: RECOMMENDED_TEXT_id_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "RECOMMENDED_TEXT_id_idx" ON public."RECOMMENDED_TEXT" USING btree (id);


--
-- Name: RECOMMENDED_TEXT_speech_id_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "RECOMMENDED_TEXT_speech_id_idx" ON public."RECOMMENDED_TEXT" USING btree (speech_id);


--
-- Name: SPEECH_TEXT_own_phone_number_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "SPEECH_TEXT_own_phone_number_idx" ON public."SPEECH_TEXT" USING btree (own_phone_number);


--
-- Name: SPEECH_TEXT_own_phone_number_speech_started_update_at_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "SPEECH_TEXT_own_phone_number_speech_started_update_at_idx" ON public."SPEECH_TEXT" USING btree (own_phone_number, speech_started, update_at);


--
-- Name: SPEECH_TEXT_speech_id_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "SPEECH_TEXT_speech_id_idx" ON public."SPEECH_TEXT" USING btree (speech_id);


--
-- Name: SPEECH_TEXT_speech_started_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "SPEECH_TEXT_speech_started_idx" ON public."SPEECH_TEXT" USING btree (speech_started);


--
-- Name: SPEECH_TEXT_update_at_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "SPEECH_TEXT_update_at_idx" ON public."SPEECH_TEXT" USING btree (update_at);


--
-- Name: USER_user_id_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "USER_user_id_idx" ON public."USER" USING btree (user_id);


--
-- PostgreSQL database dump complete
--

