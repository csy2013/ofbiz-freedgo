docker build -t ofbiz-images ./

docker run -i -t --name yaImages -p 8080:9080  ya-images /bin/bash

docker run -i -t  -v /opt/app/yuaoq/framework/images/webapp/images -p 8080:8080 --name yaImages --link docker-mysql:yamysql  ofbiz-images /bin/bash

启动：java -jar ofbiz.jar start-images