# scaling-winner
The most awesome greeting service

## Docker

docker run amazoncorretto:17 java -version
docker build -t daveayan/scaling-winner .
docker run -p 8080:8080 daveayan/scaling-winner
docker run -d -p 8081:8080 daveayan/scaling-winner
docker run -d -p 8082:8080 daveayan/scaling-winner
docker run -d -p 8083:8080 daveayan/scaling-winner

minikube dashboard

kubectl create deployment scaling-winner --image=daveayan/scaling-winner --dry-run -o=yaml > deployment.yaml
kubectl create service clusterip scaling-winner --tcp=8081:8080 --dry-run=client -o=yaml >> deployment.yaml
kubectl apply -f deployment.yaml

kubectl get all 

kubectl delete -n default service scaling-winner

kubectl port-forward service/scaling-winner 8080:8080

TODO
- security / auth / jwt
X logging
- distributed tracing
- config and multiple envs
- chaos injection
- circuit breaker
- retry logic
- docker
- k8s local
- reverse proxy
- terraform and aws
- elk
- prometheus
- in memory db
- caching
- cloud data flow

Docker and Kubes
kafka
mongodb
grafana
datadog
Hazelcast