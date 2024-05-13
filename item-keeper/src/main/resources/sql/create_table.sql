create table sources
(
    id                      uuid primary key default gen_random_uuid(),
    name                    varchar(256) not null,
    link                    varchar(256) not null,
    configuration_filename  varchar(256) not null,
    created_at              timestamp,
    updated_at              timestamp,
    deleted_at              timestamp,
    is_banned               boolean default false
);

create table items
(
    id               uuid primary key default gen_random_uuid(),
    title            varchar(256) not null,
    link             varchar(256) not null,
    publication_date date,
    description      text,
    category         varchar(256),
    guid             varchar(256) not null,
    pda_link         varchar(256),
    full_text        text,
    news_id          varchar(256),
    type             varchar(128),
    tags             text[],
    news_line        varchar(256),
    created_at       timestamp,
    updated_at       timestamp,
    deleted_at       timestamp,
    source_id        uuid,
    foreign key(source_id) references sources(id)
);