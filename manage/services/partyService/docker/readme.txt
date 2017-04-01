docker build -t ofbiz-party ./



docker run -i -t --volumes-from=yaImages -p  9082:8080 --name ya-party --link docker-mysql:yamysql --link ya-cas:yacas  ofbiz-party /bin/bash


docker run -d --volumes-from=yaImages -p  9082:8080 --name ya-party --link docker-mysql:yamysql --link ya-cas:yacas  ofbiz-party
启动：java -jar ofbiz.jar start-party



YACAS_PORT_8080_TCP_PORT=8080
YAMYSQL_NAME=/ya-party/yamysql
YACAS_PORT_8080_TCP_ADDR=172.17.0.188
YACAS_ENV_CATALINA_SH=/opt/app/tomcat7/bin/catalina.sh
YACAS_PORT_8443_TCP_ADDR=172.17.0.188
YAMYSQL_PORT_3306_TCP_ADDR=172.17.0.1
PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/opt/app/yuaoq/
YACAS_ENV_JAVA_HOME=/usr/lib/jvm/java-7-oracle
PWD=/opt/app/yuaoq
YAMYSQL_ENV_MYSQL_MAJOR=5.7
JAVA_HOME=/usr/lib/jvm/java-7-oracle
YACAS_ENV_DEPLOYDIR=/opt/app/tomcat7/webapps/
YACAS_PORT_8443_TCP=tcp://172.17.0.188:8443
YAMYSQL_PORT_3306_TCP_PORT=3306
YACAS_PORT_8443_TCP_PROTO=tcp
YAMYSQL_ENV_MYSQL_ROOT_PASSWORD=root
SHLVL=1
HOME=/root
YAMYSQL_PORT_3306_TCP_PROTO=tcp
YACAS_PORT_8443_TCP_PORT=8443
YACAS_NAME=/ya-party/yacas
LESSOPEN=| /usr/bin/lesspipe %s
YACAS_PORT_8080_TCP=tcp://172.17.0.188:8080
YACAS_ENV_CATALINA_BASE=/opt/app/tomcat7
YACAS_PORT_8080_TCP_PROTO=tcp
LESSCLOSE=/usr/bin/lesspipe %s %s
JAVA_VER=7
YAMYSQL_PORT=tcp://172.17.0.1:3306
_=/usr/bin/env