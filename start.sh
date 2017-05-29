#!/usr/bin/env bash
nohup java -Xms512M -Xmx1024M -jar ../framework/ofbiz.jar front/daojia/daojia/docker/base/config/start-mdaojia >/dev/null 2>&1 &
