rm *.log
rm *.log.*.gz
mvn clean install -DtrimStackTrace=false
# java -jar target/scaling-winner-0.0.1-SNAPSHOT.jar