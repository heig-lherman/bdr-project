
    alter table if exists foreign_locality 
       drop constraint if exists fk_frgnlocl__country;

    alter table if exists foreign_locality 
       drop constraint if exists fk_frgnlocl__locality;

    alter table if exists journey 
       drop constraint if exists fk_journey__user;

    alter table if exists journey_segment 
       drop constraint if exists fk_journey_segment__segment;

    alter table if exists journey_segment 
       drop constraint if exists fk_journey_segment__journey;

    alter table if exists line 
       drop constraint if exists fk_line__station_end;

    alter table if exists line 
       drop constraint if exists fk_line__station_start;

    alter table if exists line 
       drop constraint if exists fk_line__track_type;

    alter table if exists line_assessment 
       drop constraint if exists fk_lineassmt__line;

    alter table if exists line_assessment 
       drop constraint if exists fk_lineassmt__user;

    alter table if exists segment 
       drop constraint if exists fk_segment__line;

    alter table if exists segment 
       drop constraint if exists fk_segment__station_end;

    alter table if exists segment 
       drop constraint if exists fk_segment__station_start;

    alter table if exists station 
       drop constraint if exists fk_station__locality;

    alter table if exists station 
       drop constraint if exists fk_station__station_type;

    alter table if exists swiss_locality 
       drop constraint if exists fk_swsslocl__canton;

    alter table if exists swiss_locality 
       drop constraint if exists fk_swsslocl__locality;

    alter table if exists traveller 
       drop constraint if exists fk_user__team;

    drop table if exists canton cascade;

    drop table if exists country cascade;

    drop table if exists foreign_locality cascade;

    drop table if exists journey cascade;

    drop table if exists journey_segment cascade;

    drop table if exists line cascade;

    drop table if exists line_assessment cascade;

    drop table if exists locality cascade;

    drop table if exists segment cascade;

    drop table if exists station cascade;

    drop table if exists station_type cascade;

    drop table if exists swiss_locality cascade;

    drop table if exists team cascade;

    drop table if exists track_type cascade;

    drop table if exists traveller cascade;
