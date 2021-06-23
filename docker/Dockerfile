FROM alpine:3.13

ARG BUILD_ID=HEAD

WORKDIR /app

RUN apk --no-cache update && apk upgrade \
    && apk add --no-cache bash openjdk11 openssl ca-certificates gnupg tzdata grep sed tini git \
#    libc6-compat nss s6-dns go libpq gcc musl-dev url \
    && update-ca-certificates
#RUN apk add libsass --no-cache --repository http://dl-cdn.alpinelinux.org/alpine/edge/community/



COPY build-src/ /build/
RUN chmod 500 /build/build_semefa.sh && /build/build_semefa.sh ${BUILD_ID}

COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

RUN mkdir -p /app/src/

RUN mkdir -p /app/src/test/java

