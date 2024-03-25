# propsi-microservices

Access with kubectl to deployed cluster:
gcloud container clusters get-credentials propsi-cluster --region=europe-central2-a

Prometheus:
helm install prometheus bitnami/kube-prometheus
helm uninstall prometheus
kubectl patch svc prometheus-kube-prometheus-prometheus -p '{"spec": {"type": "LoadBalancer"}}'

kubectl:
