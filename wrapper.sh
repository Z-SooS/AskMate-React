#!/bin/sh
echo "starting container"

java -jar app.jar &
serve -s build & 
wait
#tail -f /dev/null
