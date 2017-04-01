From ya-jdk:7
MAINTAINER changsy tom <csyer2004@sina.com>

#setup copy start command



#setup copy ofbiz.jar

COPY ./ofbiz.jar /opt/app/yuaoq/

#setup ***************** framework
COPY ./framework /opt/app/yuaoq/framework
COPY ./runtime /opt/app/yuaoq/runtime
COPY ./applications /opt/app/yuaoq/applications
COPY ./themes /opt/app/yuaoq/themes
COPY ./specialpurpose /opt/app/yuaoq/specialpurpose

#setup Java
#Expose the ports we're interested in
EXPOSE 8080
#set system property
WORKDIR /opt/app/yuaoq
COPY ./start.sh /opt/app/yuaoq/
RUN chmod +x ./*.sh
ENV PATH $PATH:/opt/app/yuaoq/
CMD /opt/app/yuaoq/start.sh
