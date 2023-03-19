# k8s-coding
A k8s pet-project

<!-- TOC -->
* [k8s-coding](#k8s-coding)
  * [Requirements](#requirements)
  * [Steps](#steps)
    * [Install k3d (if hasn't done yet)](#install-k3d--if-hasnt-done-yet-)
    * [Create local docker registry](#create-local-docker-registry)
    * [Create Kubernetes cluster](#create-kubernetes-cluster)
    * [Install Kubernetes Dashboard](#install-kubernetes-dashboard)
    * [Install RabbitMQ into Kubernetes](#install-rabbitmq-into-kubernetes)
    * [Deploy apps into Kubernetes](#deploy-apps-into-kubernetes)
    * [Configure Ingress in Kubernetes](#configure-ingress-in-kubernetes)
    * [Play!](#play-)
<!-- TOC -->

## Requirements
- docker
- jdk 17
- k3d (https://k3d.io/)
- helm (https://helm.sh/)

## Steps
### Install k3d (if hasn't done yet)
Install k3d 
```shell
curl -s https://raw.githubusercontent.com/k3d-io/k3d/main/install.sh | bash
```

### Create local docker registry
```shell
k3d registry create --port 0.0.0.0:5111
```
NOTE: Delete cluster
```shell
k3d registry delete k3d-registry
```

### Create Kubernetes cluster
```shell
k3d cluster create k8scodingdemo  --registry-use k3d-registry:5111 -p 8080:80@loadbalancer
```
NOTE: Delete cluster
```shell
k3d cluster delete k8scodingdemo 
```
Check nodes:
```shell
kubectl get nodes
```

### Install Kubernetes Dashboard
```shell
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.7.0/aio/deploy/recommended.yaml
```
Create `admin-user`. Create a file `admin-user.yml`
```yaml
---
apiVersion: v1
kind: ServiceAccount
metadata:
  name: admin-user
  namespace: kubernetes-dashboard
---
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: admin-user
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: cluster-admin
subjects:
  - kind: ServiceAccount
    name: admin-user
    namespace: kubernetes-dashboard
```
Apply config file by command `kubectl apply -f admin-user.yml`

Create a Bearer Tokens token:
```shell
kubectl -n kubernetes-dashboard create token admin-user
```
Start proxy:
```shell
kubectl proxy
```
Dashboard  will be accessible as: http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/

### Install RabbitMQ into Kubernetes
```shell
helm repo add bitnami https://charts.bitnami.com/bitnami
helm install rabbitmq-broker --set auth.username=admin,auth.password=secretpassword bitnami/rabbitmq
```
RabbitMQ can be accessed within the cluster on port  at rabbitmq-broker.default.svc

To access for outside the cluster, perform the following steps:

To Access the RabbitMQ AMQP port:
```shell
echo "URL : amqp://127.0.0.1:5672/"
kubectl port-forward --namespace default svc/rabbitmq-broker 5672:5672
```
To Access the RabbitMQ Management interface:
```shell
echo "URL : http://127.0.0.1:15672/"
kubectl port-forward --namespace default svc/rabbitmq-broker 15672:15672
```
### Deploy apps into Kubernetes
Look at [call-collector's README](call-collector/README.md)

### Configure Ingress in Kubernetes
k3d/k3s already have  https://traefik.io/ as ingress implementation.
```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: echoserver
  annotations:
    ingress.kubernetes.io/ssl-redirect: "false"
spec:
  rules:
    - http:
        paths:
          - path: /answer
            pathType: Prefix
            backend:
              service:
                name: q-c-call-collector
                port:
                  number: 8080          
          - path: /call
            pathType: Prefix
            backend:
              service:
                name: q-c-call-collector
                port:
                  number: 8080
          - path: /ask
            pathType: Prefix
            backend:
              service:
                name: q-c-call-collector
                port:
                  number: 8080
```

### Play!

Try that it works:
```shell
curl localhost:9080/ask
```

Try to send a message
```shell
curl -v POST localhost:9080/call -d "some body content"
```

Check the set of received messages
```shell
curl http://localhost:9080/answer/snapshot
```

Check the set of received messages as a stream
```shell
curl http://localhost:9080/answer/stream
```