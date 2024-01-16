
    create table canton (
        code varchar(2) not null,
        name varchar(255) not null,
        primary key (code)
    );

    create table country (
        code varchar(2) not null,
        name varchar(255) not null,
        primary key (code)
    );

    create table foreign_locality (
        id bigint not null,
        country_fk varchar(2) not null,
        primary key (id)
    );

    create table journey (
        id bigserial not null,
        end_date timestamp(6) with time zone not null,
        grade integer not null,
        review varchar(2000),
        start_date timestamp(6) with time zone not null,
        user_fk varchar(255) not null,
        primary key (id)
    );

    create table journey_segment (
        journey_fk bigint not null,
        segment_fk bigint not null,
        primary key (journey_fk, segment_fk)
    );

    create table line (
        line_number integer not null,
        name varchar(255) not null,
        station_end_fk bigint not null,
        station_start_fk bigint not null,
        track_type_fk varchar(255),
        primary key (line_number)
    );

    create table line_assessment (
        id bigserial not null,
        grade integer not null,
        review varchar(2000),
        line_fk integer not null,
        user_fk varchar(255) not null,
        primary key (id),
        constraint uk_lineassmt__line_user unique (line_fk, user_fk)
    );

    create table locality (
        id bigserial not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table segment (
        id bigserial not null,
        distance float(53) not null,
        line_fk integer not null,
        station_end_fk bigint not null,
        station_start_fk bigint not null,
        primary key (id)
    );

    create table station (
        opuic bigint not null,
        abbreviated_name varchar(6) not null,
        name varchar(255) not null,
        position geometry not null,
        locality_fk bigint not null,
        station_type_fk varchar(255),
        primary key (opuic),
        constraint uk_station__abbrv unique (abbreviated_name)
    );

    create table station_type (
        code varchar(255) not null,
        description varchar(255) not null,
        primary key (code)
    );

    create table swiss_locality (
        code integer not null,
        id bigint not null,
        canton_fk varchar(2) not null,
        primary key (id)
    );

    create table team (
        id bigserial not null,
        nom varchar(255) not null,
        primary key (id)
    );

    create table track_type (
        code varchar(255) not null,
        description varchar(255) not null,
        primary key (code)
    );

    create table traveller (
        email varchar(255) not null,
        first_name varchar(255) not null,
        last_name varchar(255) not null,
        password varchar(255) not null,
        username varchar(255) not null,
        team_fk bigint,
        primary key (email)
    );

    create table traveller_token (
        id bigserial not null,
        expired boolean not null,
        refresh_token varchar(255) not null,
        revoked boolean not null,
        token varchar(255) not null,
        traveller_fk varchar(255) not null,
        primary key (id)
    );

    create index idx_station__name 
       on station (name);

    create index idx_station__abbrv 
       on station (abbreviated_name);

    alter table if exists foreign_locality 
       add constraint fk_frgnlocl__country 
       foreign key (country_fk) 
       references country;

    alter table if exists foreign_locality 
       add constraint fk_frgnlocl__locality 
       foreign key (id) 
       references locality;

    alter table if exists journey 
       add constraint fk_journey__user 
       foreign key (user_fk) 
       references traveller;

    alter table if exists journey_segment 
       add constraint fk_journey_segment__segment 
       foreign key (segment_fk) 
       references segment;

    alter table if exists journey_segment 
       add constraint fk_journey_segment__journey 
       foreign key (journey_fk) 
       references journey;

    alter table if exists line 
       add constraint fk_line__station_end 
       foreign key (station_end_fk) 
       references station;

    alter table if exists line 
       add constraint fk_line__station_start 
       foreign key (station_start_fk) 
       references station;

    alter table if exists line 
       add constraint fk_line__track_type 
       foreign key (track_type_fk) 
       references track_type;

    alter table if exists line_assessment 
       add constraint fk_lineassmt__line 
       foreign key (line_fk) 
       references line;

    alter table if exists line_assessment 
       add constraint fk_lineassmt__user 
       foreign key (user_fk) 
       references traveller;

    alter table if exists segment 
       add constraint fk_segment__line 
       foreign key (line_fk) 
       references line;

    alter table if exists segment 
       add constraint fk_segment__station_end 
       foreign key (station_end_fk) 
       references station;

    alter table if exists segment 
       add constraint fk_segment__station_start 
       foreign key (station_start_fk) 
       references station;

    alter table if exists station 
       add constraint fk_station__locality 
       foreign key (locality_fk) 
       references locality;

    alter table if exists station 
       add constraint fk_station__station_type 
       foreign key (station_type_fk) 
       references station_type;

    alter table if exists swiss_locality 
       add constraint fk_swsslocl__canton 
       foreign key (canton_fk) 
       references canton;

    alter table if exists swiss_locality 
       add constraint fk_swsslocl__locality 
       foreign key (id) 
       references locality;

    alter table if exists traveller 
       add constraint fk_user__team 
       foreign key (team_fk) 
       references team;

    alter table if exists traveller_token 
       add constraint fk_token__user 
       foreign key (traveller_fk) 
       references traveller;
