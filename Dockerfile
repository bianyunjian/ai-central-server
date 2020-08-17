FROM java:8

ENV TimeZone Asia/Shanghai

COPY sdk /sdk

RUN bash -c 'sh /sdk/deployLibraries.sh'
 

COPY target/central-server-0.0.1-SNAPSHOT.jar /app.jar

EXPOSE 9000

ENTRYPOINT ["sh","-c","java ${JAVA_OPTS} -jar /app.jar ${EXT_CONFIG}"]
