#!/bin/sh
liquibase --defaults-file=/script/liquibase.properties --search-path=/liquibase/changelog  update --changelog-file='db.changelog-main.sql'
