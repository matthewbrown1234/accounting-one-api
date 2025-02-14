create table account (
    id serial primary key,
    account_id varchar(255) not null,
    account_name varchar(255) not null,
    account_type varchar(255) not null
);

create table account_entry (
    id serial primary key,
    account_id serial not null,
    value numeric(15, 2) not null,
    name varchar(255) not null,
    entry_date timestamp with time zone not null,
    constraint fk_account_id foreign key (account_id) references account(id)
);

create table category (
    id serial primary key,
    name varchar(255) not null
);

create table account_entry_category (
    id serial primary key,
    account_entry_id serial not null,
    category_id serial not null,
    constraint fk_account_entry_id foreign key (account_entry_id) references account_entry(id),
    constraint fk_category_id foreign key (category_id) references category(id)
);

create index if not exists account_account_id_idx on account(account_id);
create index if not exists account_account_name_idx on account(account_name);
create index if not exists account_account_type_idx on account(account_type);

create index if not exists account_entry_name_idx on account_entry(name);
create index if not exists account_entry_date_idx on account_entry(entry_date);
create index if not exists account_entry_account_id_idx on account_entry(account_id);
