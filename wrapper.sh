#!/bin/sh
echo "starting container"

java -jar app.jar &
npm run debug &
wait
#tail -f /dev/null
