#将jdk镜像作为基础镜像
FROM ilstest.azurecr.cn/oracle-serverjre:8
#作者信息
MAINTAINER hwd <1003186308@qq.com>
#VOLUME是挂载 将/tmp挂载到宿主机 ~/目录也就是用户目录 这个目录笔者用来放工程日志
VOLUME /tmp
VOLUME ~/
#将mvn 构建后的jar包放在容器根目录下
ADD rabbitmq-api-0.0.1-SNAPSHOT.jar /rabbitmq-api.jar
ENTRYPOINT [ "java","-Djava.security.egd=file:/dev/./urandom","-jar","/rabbitmq-api.jar"]