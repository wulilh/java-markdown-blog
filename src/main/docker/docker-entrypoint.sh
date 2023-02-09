#!/usr/bin/env bash

set -ex;

exec java $JAVA_OPTS -Dspring.profiles.active=prd -Dspring.config.location=/website/conf/application.yml -jar /website/app.jar