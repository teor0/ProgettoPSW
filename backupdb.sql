--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

-- Started on 2024-01-18 19:02:58

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
-- TOC entry 2 (class 3079 OID 16384)
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- TOC entry 3376 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 16460)
-- Name: cart; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cart (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    order_id bigint,
    version bigint NOT NULL
);


ALTER TABLE public.cart OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16561)
-- Name: cart_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.cart ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.cart_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 218 (class 1259 OID 16438)
-- Name: order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."order" (
    id bigint NOT NULL,
    user_id bigint,
    total double precision NOT NULL,
    create_date timestamp without time zone NOT NULL,
    status character varying
);


ALTER TABLE public."order" OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16560)
-- Name: order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."order" ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 217 (class 1259 OID 16431)
-- Name: orderproducts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orderproducts (
    id bigint NOT NULL,
    order_id bigint NOT NULL,
    product_id bigint NOT NULL,
    quantity integer NOT NULL,
    price double precision NOT NULL
);


ALTER TABLE public.orderproducts OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16558)
-- Name: orderproducts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.orderproducts ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.orderproducts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 216 (class 1259 OID 16406)
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    id bigint NOT NULL,
    bar_code character varying(15) NOT NULL,
    name character varying(30) NOT NULL,
    category character varying(30) NOT NULL,
    price double precision NOT NULL,
    description character varying(200),
    quantity integer NOT NULL,
    version bigint NOT NULL
);


ALTER TABLE public.product OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16557)
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.product ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 215 (class 1259 OID 16401)
-- Name: utente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.utente (
    id bigint NOT NULL,
    name character varying(50) NOT NULL,
    email character varying(50) NOT NULL,
    username character varying(15) NOT NULL,
    password character varying(30),
    role character varying DEFAULT USER
);


ALTER TABLE public.utente OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16556)
-- Name: utente_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.utente ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.utente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 3365 (class 0 OID 16460)
-- Dependencies: 219
-- Data for Name: cart; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cart (id, user_id, order_id, version) FROM stdin;
9	23	\N	1
\.


--
-- TOC entry 3364 (class 0 OID 16438)
-- Dependencies: 218
-- Data for Name: order; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."order" (id, user_id, total, create_date, status) FROM stdin;
159	\N	10	2024-01-18 18:48:49.764	Handled
160	\N	0	2024-01-18 18:53:56.227	Handled
\.


--
-- TOC entry 3363 (class 0 OID 16431)
-- Dependencies: 217
-- Data for Name: orderproducts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.orderproducts (id, order_id, product_id, quantity, price) FROM stdin;
114	159	37	1	10
\.


--
-- TOC entry 3362 (class 0 OID 16406)
-- Dependencies: 216
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product (id, bar_code, name, category, price, description, quantity, version) FROM stdin;
27	KI02LS	Lampadina Led	Elettronica	14.99	\N	100	3
34	JI83LS	Lampadario	Elettronica	229.99	\N	145	21
37	0K1SAS	Cavo usb	Elettronica	10	\N	130	9
32	LI11LS	Lampadina Led	Elettronica	19.99	\N	145	9
42	KI11OSC	Ferro da stiro	Casa	119.19	\N	100	0
31	KI09LS2	Lampadina Led	Elettronica	109.99	\N	150	30
20	0AJIE62	Terriccio universale	Giardinaggio	34.99	Terriccio universale da 40L	995	20
29	OLE111	Lampadina Led	Elettronica	39.99	\N	95	25
30	KI08LS	Lampadina Led	Elettronica	69.99	\N	100	1
28	KI03LS	Lampadina Led	Elettronica	19.99	\N	100	2
43	IS9V	Mattone Bianco	Edilizia	15	\N	5	30
38	90K1SAS	Cavo usb	Elettronica	11	\N	115	8
40	IS8H	Mattone	Costruction	11	Mattone semplice	120	17
24	LIO99D	Collana oro	Gioielleria	769	Collana in oro bianco	250	11
25	MMX817D	Set cacciaviti 30pz	Cancelleria	19.49	\N	394	16
\.


--
-- TOC entry 3361 (class 0 OID 16401)
-- Dependencies: 215
-- Data for Name: utente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.utente (id, name, email, username, password, role) FROM stdin;
5	Mario Rossi	mario.rossi@mail.it	marossi	pass	User
15	Francesco Bellissimo	bellissimo@mail.sl	frabell	sanluca	User
23	Ferdinando Leo	ferdi.leo@gmail.com	leoFe	password	Admin
31	TEST T	te@te.t	test	password	Vendor
\.


--
-- TOC entry 3377 (class 0 OID 0)
-- Dependencies: 224
-- Name: cart_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cart_id_seq', 16, true);


--
-- TOC entry 3378 (class 0 OID 0)
-- Dependencies: 223
-- Name: order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_id_seq', 160, true);


--
-- TOC entry 3379 (class 0 OID 0)
-- Dependencies: 222
-- Name: orderproducts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orderproducts_id_seq', 114, true);


--
-- TOC entry 3380 (class 0 OID 0)
-- Dependencies: 221
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_id_seq', 43, true);


--
-- TOC entry 3381 (class 0 OID 0)
-- Dependencies: 220
-- Name: utente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.utente_id_seq', 31, true);


--
-- TOC entry 3213 (class 2606 OID 16464)
-- Name: cart cart_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_pkey PRIMARY KEY (id);


--
-- TOC entry 3205 (class 2606 OID 16435)
-- Name: orderproducts orderProducts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderproducts
    ADD CONSTRAINT "orderProducts_pkey" PRIMARY KEY (id);


--
-- TOC entry 3209 (class 2606 OID 16563)
-- Name: order order_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."order"
    ADD CONSTRAINT order_pkey PRIMARY KEY (id);


--
-- TOC entry 3211 (class 2606 OID 16577)
-- Name: order order_prkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."order"
    ADD CONSTRAINT order_prkey UNIQUE (user_id, create_date);


--
-- TOC entry 3207 (class 2606 OID 16437)
-- Name: orderproducts ordpr_prkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderproducts
    ADD CONSTRAINT ordpr_prkey UNIQUE (order_id, product_id);


--
-- TOC entry 3200 (class 2606 OID 16410)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- TOC entry 3202 (class 2606 OID 16515)
-- Name: product product_prkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_prkey UNIQUE (bar_code);


--
-- TOC entry 3196 (class 2606 OID 16405)
-- Name: utente utente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_pkey PRIMARY KEY (id);


--
-- TOC entry 3198 (class 2606 OID 16604)
-- Name: utente utente_prkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_prkey UNIQUE (username, email);


--
-- TOC entry 3203 (class 1259 OID 16614)
-- Name: fki_orderProducts_order_fkey; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "fki_orderProducts_order_fkey" ON public.orderproducts USING btree (order_id);


--
-- TOC entry 3217 (class 2606 OID 24826)
-- Name: cart cart_order_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_order_fkey FOREIGN KEY (order_id) REFERENCES public."order"(id) ON UPDATE CASCADE ON DELETE SET NULL NOT VALID;


--
-- TOC entry 3218 (class 2606 OID 24841)
-- Name: cart cart_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_user_fkey FOREIGN KEY (user_id) REFERENCES public.utente(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 3214 (class 2606 OID 16609)
-- Name: orderproducts orderProducts_order_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderproducts
    ADD CONSTRAINT "orderProducts_order_fkey" FOREIGN KEY (order_id) REFERENCES public."order"(id) ON DELETE CASCADE;


--
-- TOC entry 3215 (class 2606 OID 24836)
-- Name: orderproducts orderProducts_product_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderproducts
    ADD CONSTRAINT "orderProducts_product_fkey" FOREIGN KEY (product_id) REFERENCES public.product(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;


--
-- TOC entry 3216 (class 2606 OID 16598)
-- Name: order order_user_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."order"
    ADD CONSTRAINT order_user_fkey FOREIGN KEY (user_id) REFERENCES public.utente(id) ON UPDATE CASCADE ON DELETE SET NULL NOT VALID;


-- Completed on 2024-01-18 19:02:58

--
-- PostgreSQL database dump complete
--

