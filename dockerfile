# criação da imagem docker
FROM tomcat:11.0-jdk21-temurin

# Remoção dos arquivos WEBAPPS do tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia dos arquivos do localhost para a imagem docker.
COPY target/app.war /usr/local/tomcat/webapps/ROOT.war

# Abrir a porta 8080
EXPOSE 8080

# Execução do docker
CMD ["catalina.sh","run"]