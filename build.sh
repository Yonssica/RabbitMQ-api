#!/bin/bash

#project build variable
#you can change this code for your project
###START###
#docker hub 仓库名
DOCKERRESPOSITORYUSERNAME=ilstest.azurecr.cn
#docker hub用户名
DOCKERLOGINUSERNAME=ilstest
#dockerhub密码
DOCKERLOGINPASSWORD=PwG309Jsabeyw1+CeR8yAjBr5opHh7El
#dockerhub 仓库tag名
RESPOSITORYNAME=rabbitmq-api
#应用名称
APPNAME=rabbitmq-api
#应用端口
APPPORT=8777
#容器端口
INPORT=8777
#宿主机映射端口
OUTPORT=8777
#挂载宿主机目录
HOSTPATH=~/
#被挂载的容器目录
CONTAINNERPATH=/root
#容器名称
CONTAINNERNAME=${APPNAME}-${APPPORT}
#镜像名称
MYIMAGE=${DOCKERRESPOSITORYUSERNAME}/${RESPOSITORYNAME}:${APPNAME}.${APPPORT}
###END###

#don't change this code
###START###
cd ../
mvn clean install -Dmaven.test.skip=true
cd -
# uncomment if you need push
docker login -u ${DOCKERLOGINUSERNAME} -p ${DOCKERLOGINPASSWORD}
# stop all container
docker stop ${CONTAINNERNAME}
# remove all container
docker rm ${CONTAINNERNAME}
# remove old images
docker rmi ${MYIMAGE}
# build jar and image
#mvn package -e -X docker:build -DskipTest
mvn package docker:build
# push image
#docker push ${MYIMAGE}
# running container
docker run -it -dp ${OUTPORT}:${INPORT} -v ${HOSTPATH}:${CONTAINNERPATH} --name ${CONTAINNERNAME} ${MYIMAGE} /bin/bash
###END###