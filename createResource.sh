export JOBNAME=createResource
export ARGS1=db/resource.csv

export TOMCAT=/mnt/c/Users/secon/Desktop/apache-tomcat-9.0.55/
export TOMCATLIB="$TOMCAT"lib/
export APP="$TOMCAT"webapps/demo050/
export APP_LIB="$APP"WEB-INF/lib/

export BATCHES=bin/spring-batch-core-4.3.4.jar:bin/spring-batch-infrastructure-4.3.4.jar:bin/javax.batch-api-1.0.jar:bin/micrometer-core-1.8.0.jar

export TOMCATJAR="$TOMCATLIB"servlet-api.jar:"$TOMCATLIB"el-api.jar:"$TOMCATLIB"jasper-el.jar

export OWN=build/libs/demo-0.5.0-SNAPSHOT-batch.jar:"$BATCHES":"$TOMCATJAR"

export JARS="$OWN":"$APP_LIB"HikariCP-4.0.3.jar:"$APP_LIB"antlr-2.7.7.jar:"$APP_LIB"aspectjweaver-1.9.7.jar:"$APP_LIB"byte-buddy-1.11.22.jar:"$APP_LIB"classmate-1.5.1.jar:"$APP_LIB"gson-2.8.9.jar:"$APP_LIB"h2-1.4.200.jar:"$APP_LIB"hibernate-commons-annotations-5.1.2.Final.jar:"$APP_LIB"hibernate-core-5.6.1.Final.jar:"$APP_LIB"hibernate-validator-6.2.0.Final.jar:"$APP_LIB"istack-commons-runtime-3.0.12.jar:"$APP_LIB"jackson-annotations-2.13.0.jar:"$APP_LIB"jackson-core-2.13.0.jar:"$APP_LIB"jackson-databind-2.13.0.jar:"$APP_LIB"jackson-datatype-jdk8-2.13.0.jar:"$APP_LIB"jackson-datatype-jsr310-2.13.0.jar:"$APP_LIB"jackson-module-parameter-names-2.13.0.jar:"$APP_LIB"jakarta.activation-1.2.2.jar:"$APP_LIB"jakarta.persistence-api-2.2.3.jar:"$APP_LIB"jakarta.transaction-api-1.3.3.jar:"$APP_LIB"jakarta.validation-api-2.0.2.jar:"$APP_LIB"jakarta.xml.bind-api-2.3.3.jar:"$APP_LIB"jandex-2.2.3.Final.jar:"$APP_LIB"jaxb-runtime-2.3.5.jar:"$APP_LIB"jboss-logging-3.4.2.Final.jar:"$APP_LIB"jul-to-slf4j-1.7.32.jar:"$APP_LIB"log4j-api-2.16.0.jar:"$APP_LIB"log4j-to-slf4j-2.14.1.jar:"$APP_LIB"logback-classic-1.2.7.jar:"$APP_LIB"logback-core-1.2.7.jar:"$APP_LIB"slf4j-api-1.7.32.jar:"$APP_LIB"snakeyaml-1.29.jar:"$APP_LIB"spring-aop-5.3.13.jar:"$APP_LIB"spring-aspects-5.3.13.jar:"$APP_LIB"spring-beans-5.3.13.jar:"$APP_LIB"spring-boot-2.6.0.jar:"$APP_LIB"spring-boot-autoconfigure-2.6.0.jar:"$APP_LIB"spring-boot-devtools-2.6.0.jar:"$APP_LIB"spring-boot-starter-2.6.0.jar:"$APP_LIB"spring-boot-starter-aop-2.6.0.jar:"$APP_LIB"spring-boot-starter-data-jdbc-2.6.0.jar:"$APP_LIB"spring-boot-starter-data-jpa-2.6.0.jar:"$APP_LIB"spring-boot-starter-jdbc-2.6.0.jar:"$APP_LIB"spring-boot-starter-json-2.6.0.jar:"$APP_LIB"spring-boot-starter-logging-2.6.0.jar:"$APP_LIB"spring-boot-starter-validation-2.6.0.jar:"$APP_LIB"spring-boot-starter-web-2.6.0.jar:"$APP_LIB"spring-context-5.3.13.jar:"$APP_LIB"spring-core-5.3.13.jar:"$APP_LIB"spring-data-commons-2.6.0.jar:"$APP_LIB"spring-data-jdbc-2.3.0.jar:"$APP_LIB"spring-data-jpa-2.6.0.jar:"$APP_LIB"spring-data-relational-2.3.0.jar:"$APP_LIB"spring-expression-5.3.13.jar:"$APP_LIB"spring-jcl-5.3.13.jar:"$APP_LIB"spring-jdbc-5.3.13.jar:"$APP_LIB"spring-orm-5.3.13.jar:"$APP_LIB"spring-tx-5.3.13.jar:"$APP_LIB"spring-web-5.3.13.jar:"$APP_LIB"spring-webmvc-5.3.13.jar:"$APP_LIB"txw2-2.3.5.jar

export CLASSES=bin/main
export CLASSPATH="$CLASSES":"$JARS"

java -Dspring.profiles.active=batch -Dspring.batch.job.names=$JOBNAME -cp $CLASSPATH com.example.demo.BatchApplication -resourceCSV=$ARGS1
