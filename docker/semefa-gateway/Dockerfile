FROM vicozizou/semefa-gateway-seed:latest as build-semefa-gateway

LABEL com.saludaunclic.contact="info@dotcms.com"
LABEL com.saludaunclic.vendor="Salud a un Clic"
LABEL com.saludaunclic.description="SAC Semefa Gateway"

ARG BUILD_ID=HEAD
ENV GRADLE_USER_HOME=/build/src/semefa-gateway/.gradle
ENV GRADLE_OPTS="-Dfile.encoding=utf-8 -Xmx2g"

WORKDIR /app

COPY build-src/ /build/
RUN chmod 500 /build/build_semefa_gateway.sh && /build/build_semefa_gateway.sh ${BUILD_ID}

EXPOSE 9000 9001

CMD ["semefa-gateway-1.0.jar", "--spring.config.location=file:./application.yml"]
