FROM java:8

COPY target/central-server-0.0.1-SNAPSHOT.jar /app.jar
COPY sdk /sdk
ENV TimeZone Asia/Shanghai

RUN bash -c  'sh /sdk/deployLibraries.sh'

RUN bash -c  'touch /app.jar'
EXPOSE 9000
ENTRYPOINT ["sh","-c","java ${JAVA_OPTS} -jar /app.jar ${EXT_CONFIG}"]