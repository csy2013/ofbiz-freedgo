docker build -t ofbiz-content ./



docker run -i -t --volumes-from=yaImages -p  9083:8080 --name ya-content --link docker-mysql:yamysql --link ya-cas:yacas  ofbiz-content /bin/bash


docker run -d --volumes-from=yaImages -p  9083:8080 --name ya-content --link docker-mysql:yamysql --link ya-cas:yacas  ofbiz-content
启动：java -jar ofbiz.jar start-content