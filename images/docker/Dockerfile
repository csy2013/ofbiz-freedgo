From daocloud.io/ubuntu:14.04
MAINTAINER changsy tom <csyer2004@sina.com>

#setup copy start command
COPY ./start.sh /opt/app/yuaoq/


#setup copy ofbiz.jar

COPY ./ofbiz.jar /opt/app/yuaoq/

#setup ***************** framework
COPY ./framework /opt/app/yuaoq/framework
COPY ./runtime /opt/app/yuaoq/runtime

#setup Java
RUN mkdir /opt/java
COPY ./jdk-7u72-linux-x64.tar.gz /opt/java/
#change dir to Java installation dir 
WORKDIR /opt/java/ 
RUN tar -zxf jdk-7u72-linux-x64.tar.gz 
#setup environment variables 
RUN update-alternatives --install /usr/bin/javac javac /opt/java/jdk1.7.0_72/bin/javac 100 
RUN update-alternatives --install /usr/bin/java java /opt/java/jdk1.7.0_72/bin/java 100 
RUN update-alternatives --display java 
RUN java -version 



#Expose the ports we're interested in
EXPOSE 8080 9080


#set system property
#startup application
#Set the default command to run on boot
#This will boot tomcat in the standalone mode and bind to all interface