#FROM java:8
#
#COPY target/central-server-0.0.1-SNAPSHOT.jar /app.jar
#COPY sdk /sdk
#ENV TimeZone Asia/Shanghai
#
#RUN bash -c  'sh /sdk/deployLibraries.sh'
#
##RUN bash -c  'touch /app.jar'
#EXPOSE 9000
#ENTRYPOINT ["sh","-c","java ${JAVA_OPTS} -jar /app.jar ${EXT_CONFIG}"]

FROM 172.16.114.245:5000/dev/dev-face-ai-base:1.0

ENV TimeZone Asia/Shanghai

COPY target/central-server-0.0.2-SNAPSHOT.jar /app.jar

EXPOSE 19001
EXPOSE 5000
EXPOSE 5001

ENTRYPOINT ["sh","-c","java ${JAVA_OPTS} -jar /app.jar ${EXT_CONFIG}"]
