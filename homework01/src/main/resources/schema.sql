create table if not exists tracking_data(
    id bigserial primary key,
    executed_at timestamp not null,
    package_name varchar(256) not null,
    method_name varchar(256) not null,
    execution_time bigint not null
);