FROM java:8

ENV TimeZone Asia/Shanghai

COPY sdk /sdk

RUN bash -c 'sh /sdk/deployLibraries.sh'
