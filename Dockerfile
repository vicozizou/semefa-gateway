FROM alpine:3.13

WORKDIR /app

RUN apk --no-cache update && apk upgrade \
    && apk add --no-cache bash openjdk11 openssl ca-certificates gnupg tzdata grep sed tini git \
#    libc6-compat nss s6-dns go libpq gcc musl-dev url \
    && update-ca-certificates
#RUN apk add libsass --no-cache --repository http://dl-cdn.alpinelinux.org/alpine/edge/community/

COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

RUN ./gradlew build -x bootJar --no-daemon
RUN rm -rf /app/src
RUN rm -rf /app/libs
RUN ./gradlew clean --no-daemon
COPY src /app/src
COPY libs /app/libs
RUN ./gradlew bootJar --no-daemon --build-cache
RUN cp /app/build/libs/semefa-gateway-1.0.jar .
COPY application.yml .

EXPOSE 9000 9001

ENTRYPOINT ["/sbin/tini", "--"]
CMD ["semefa-gateway-1.0.jar", "--spring.config.location=file:./application.yml"]
