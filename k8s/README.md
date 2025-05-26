# k8s

## Using Traefik as an ingress controller:

Apply charts:
- Navigate to /traefik and run `helm dependency update`
- helm upgrade --install traefik-umbrella . -n traefik --create-namespace -f values.yaml

## Using cert-manager for TLS certificates:

First install CRD to cluster:
kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.17.2/cert-manager.crds.yaml

Apply charts:
- Navigate to /cert-manager and run `helm dependency update`
- helm upgrade --install cert-manager-umbrella . -n cert-manager --create-namespace -f values.yaml


