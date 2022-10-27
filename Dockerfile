FROM azul/zulu-openjdk-alpine:11
EXPOSE 8080
ENV JAVA_TOOL_OPTIONS -Dfile.encoding=UTF8

RUN mkdir /apps
RUN mkdir /apps/logs
RUN chown -R 1001 /apps/ && chmod 777 /apps/ \
&& chown -R 1001 /apps/logs \
&& chmod 775 /apps/logs

USER 1001

COPY build/libs/Dguard-0.0.1-SNAPSHOT.jar /apps

CMD java -Xmx512m -Xss1024k -jar /apps/Dguard-0.0.1-SNAPSHOT.jar