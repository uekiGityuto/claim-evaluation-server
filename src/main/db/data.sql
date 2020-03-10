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
-- Data for Name: TAGS; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."TAGS" (id, category, type) FROM stdin;
\.


--
-- Data for Name: TEXT; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."TEXT" (id, utterance_start_time, utterance_end_time, channel_id, text) FROM stdin;
\.


--
-- Data for Name: USER; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."USER" (id, name, phone_ip, role, password, salt, failed_count, failed_time, update_interval, initial_display_interval, user_id) FROM stdin;
2	管理者	user-3001	2	\\xd4996bbbed831e087e74e39b31e93240c83c8b34779603fcdd3ea8718371b98cd5039b7ffd4b709116d8c4ba79193f304e1c6610887e3791d54f469b4a409504	\\x9e28f1a69971915fd1ea606db1b6ef67	0	\N	10	5	admin
\.




--
-- PostgreSQL database dump complete
--

