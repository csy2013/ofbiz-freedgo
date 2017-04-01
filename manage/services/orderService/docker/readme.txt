docker build -t ofbiz-order ./



docker run -i -t --volumes-from=yaImages -p  9084:8080 --name ya-order --link docker-mysql:yamysql --link ya-cas:yacas  ofbiz-order /bin/bash


docker run -d --volumes-from=yaImages -p  9084:8080 --name ya-order --link docker-mysql:yamysql --link ya-cas:yacas  ofbiz-order
启动：java -jar ofbiz.jar start-order