# Import base image
FROM 822459375388.dkr.ecr.ap-southeast-2.amazonaws.com/infra-jdk:latest

# Create log file directory and set permission
RUN groupadd -r redcap2hpo && useradd -r --create-home -g redcap2hpo redcap2hpo
RUN if [ ! -d /var/log/agha/ ];then mkdir /var/log/agha/;fi
RUN chown -R redcap2hpo:redcap2hpo /var/log/agha

# Move project artifact
ADD target/redcap-to-hpo-*.jar /home/redcap2hpo/
RUN mkdir /home/redcap2hpo/resources
ADD src/main/resources/mito /home/redcap2hpo/resources/mito
RUN chown -R redcap2hpo:redcap2hpo /home/redcap2hpo/resources

RUN touch /etc/ld.so.conf.d/java.conf
RUN echo $JAVA_HOME/lib/amd64/jli > /etc/ld.so.conf.d/java.conf
RUN ldconfig

RUN setcap CAP_NET_BIND_SERVICE=+eip $JAVA_HOME/bin/java

USER redcap2hpo

# Launch application server
ENTRYPOINT exec $JAVA_HOME/bin/java $XMX $XMS -jar -Dspring.profiles.active=$ENVIRONMENT -Djavax.net.ssl.trustStore=$TRUST_STORE -Djavax.net.ssl.trustStorePassword=$KEY_PASSWORD /home/redcap2hpo/redcap-to-hpo-*.jar --server.ssl.key-store-password="$KEYSTORE_PASSWORD" --server.ssl.key-password="$KEY_PASSWORD"