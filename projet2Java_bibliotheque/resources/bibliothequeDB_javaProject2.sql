PGDMP          	             }            bibliotheque    17.2    17.2                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            	           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            
           1262    16388    bibliotheque    DATABASE     �   CREATE DATABASE bibliotheque WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE bibliotheque;
                     postgres    false            �            1259    16408    emprunts    TABLE     �   CREATE TABLE public.emprunts (
    id_emprunt integer NOT NULL,
    membre_id integer,
    livre_id integer,
    date_emprunt date NOT NULL,
    date_retour_prevue date NOT NULL,
    date_retour_effective date
);
    DROP TABLE public.emprunts;
       public         heap r       postgres    false            �            1259    16407    emprunts_id_emprunt_seq    SEQUENCE     �   CREATE SEQUENCE public.emprunts_id_emprunt_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.emprunts_id_emprunt_seq;
       public               postgres    false    222                       0    0    emprunts_id_emprunt_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.emprunts_id_emprunt_seq OWNED BY public.emprunts.id_emprunt;
          public               postgres    false    221            �            1259    16390    livres    TABLE     �   CREATE TABLE public.livres (
    id integer NOT NULL,
    titre character varying(255) NOT NULL,
    auteur character varying(255) NOT NULL,
    categorie character varying(100),
    nombre_exemplaires integer NOT NULL
);
    DROP TABLE public.livres;
       public         heap r       postgres    false            �            1259    16389    livres_id_seq    SEQUENCE     �   CREATE SEQUENCE public.livres_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.livres_id_seq;
       public               postgres    false    218                       0    0    livres_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.livres_id_seq OWNED BY public.livres.id;
          public               postgres    false    217            �            1259    16399    membres    TABLE     �   CREATE TABLE public.membres (
    id integer NOT NULL,
    nom character varying(100) NOT NULL,
    prenom character varying(100) NOT NULL,
    email character varying(100) NOT NULL,
    adhesion_date date NOT NULL
);
    DROP TABLE public.membres;
       public         heap r       postgres    false            �            1259    16398    membres_id_seq    SEQUENCE     �   CREATE SEQUENCE public.membres_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.membres_id_seq;
       public               postgres    false    220                       0    0    membres_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.membres_id_seq OWNED BY public.membres.id;
          public               postgres    false    219            c           2604    16411    emprunts id_emprunt    DEFAULT     z   ALTER TABLE ONLY public.emprunts ALTER COLUMN id_emprunt SET DEFAULT nextval('public.emprunts_id_emprunt_seq'::regclass);
 B   ALTER TABLE public.emprunts ALTER COLUMN id_emprunt DROP DEFAULT;
       public               postgres    false    221    222    222            a           2604    16393 	   livres id    DEFAULT     f   ALTER TABLE ONLY public.livres ALTER COLUMN id SET DEFAULT nextval('public.livres_id_seq'::regclass);
 8   ALTER TABLE public.livres ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    217    218    218            b           2604    16402 
   membres id    DEFAULT     h   ALTER TABLE ONLY public.membres ALTER COLUMN id SET DEFAULT nextval('public.membres_id_seq'::regclass);
 9   ALTER TABLE public.membres ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    219    220    220                      0    16408    emprunts 
   TABLE DATA           |   COPY public.emprunts (id_emprunt, membre_id, livre_id, date_emprunt, date_retour_prevue, date_retour_effective) FROM stdin;
    public               postgres    false    222   �                  0    16390    livres 
   TABLE DATA           R   COPY public.livres (id, titre, auteur, categorie, nombre_exemplaires) FROM stdin;
    public               postgres    false    218                     0    16399    membres 
   TABLE DATA           H   COPY public.membres (id, nom, prenom, email, adhesion_date) FROM stdin;
    public               postgres    false    220   !                  0    0    emprunts_id_emprunt_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.emprunts_id_emprunt_seq', 7, true);
          public               postgres    false    221                       0    0    livres_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.livres_id_seq', 14, true);
          public               postgres    false    217                       0    0    membres_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.membres_id_seq', 5, true);
          public               postgres    false    219            k           2606    16413    emprunts emprunts_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.emprunts
    ADD CONSTRAINT emprunts_pkey PRIMARY KEY (id_emprunt);
 @   ALTER TABLE ONLY public.emprunts DROP CONSTRAINT emprunts_pkey;
       public                 postgres    false    222            e           2606    16397    livres livres_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.livres
    ADD CONSTRAINT livres_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.livres DROP CONSTRAINT livres_pkey;
       public                 postgres    false    218            g           2606    16406    membres membres_email_key 
   CONSTRAINT     U   ALTER TABLE ONLY public.membres
    ADD CONSTRAINT membres_email_key UNIQUE (email);
 C   ALTER TABLE ONLY public.membres DROP CONSTRAINT membres_email_key;
       public                 postgres    false    220            i           2606    16404    membres membres_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.membres
    ADD CONSTRAINT membres_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.membres DROP CONSTRAINT membres_pkey;
       public                 postgres    false    220            l           2606    16419    emprunts emprunts_livre_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.emprunts
    ADD CONSTRAINT emprunts_livre_id_fkey FOREIGN KEY (livre_id) REFERENCES public.livres(id) ON DELETE CASCADE;
 I   ALTER TABLE ONLY public.emprunts DROP CONSTRAINT emprunts_livre_id_fkey;
       public               postgres    false    218    222    4709            m           2606    16414     emprunts emprunts_membre_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.emprunts
    ADD CONSTRAINT emprunts_membre_id_fkey FOREIGN KEY (membre_id) REFERENCES public.membres(id) ON DELETE CASCADE;
 J   ALTER TABLE ONLY public.emprunts DROP CONSTRAINT emprunts_membre_id_fkey;
       public               postgres    false    220    4713    222               K   x�e���0߰K*C�F]�d�9�W\��gB���BM��/̇Zg�.�"���
8���j09��걬�������          �   x�E�MN�0���S��p�Heي�EP5 6lL2Ԗ�q�OS�8'�܄��a�4z�}�q��`�֝�?�	����5��Y'���q��֙�f���6^&�z����M�A[�d54R9$�:�p��g��]�����NY�^�	��&�C�JMa������3��6��Q����]1!���z��I}�a���ל`{F�aš�A��4SD�D	�8�]쥇&�LT�J�?���m�/�v2�FR�>�ʗ�1���bv            x�U��
�@E��0;Y%vZX�O��!��$A?�]�"�=\����j� K�
�)��P�c�F�p���Ų��z�'>�P���l0�S��7֮}���-����̡�+�MOܓ0�;D� �3�     