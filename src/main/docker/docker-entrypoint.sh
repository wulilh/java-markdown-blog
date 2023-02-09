#!/usr/bin/env bash

set -o pipefail
set -e

# start app
exec java $JAVA_OPTS \
-Djava.security.egd=file:/dev/./urandom \
-Duser.timezone=${TZ:-Asia/Shanghai} \
-Dspring.profiles.active=prd \
-Dspring.config.location=/website/conf/application.yml \
-jar /website/app.jar
 "$@"


exit 0