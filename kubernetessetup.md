*In this part you will make the Kubernetes cluster ready to host our application*

## Task 1 - Create application namespace

Create a namespace called `app`. This is where we are going to run our applications.

<details>
  <summary>✨ Se fasit</summary>

```bash
kubectl create namespace app
```

</details>

## Task 1 - Generate Database password secret

We will be using Kubernetes Secrets to store the password to our database. To simplify we will provide the admin user's credentials for our backend application. (this is obviously not a good idea in production...)

So your task is to find the credentials (hint: look under users tab under the managed database section) and create a Kubernetes secret on the form:

```PASSWORD=<secret>```

❗❗❗️_NOTE:_ In a production environment Kubernetes secrets are not secure out of the box. You will have to configure your cluster to encrypt the secrets or use external secret manager. (out of scope for this workshop)


<details>
  <summary>✨ Se fasit</summary>

```bash
kubectl create secret generic <name of secret> --from-literal=PASSWORD=<secret> --namespace=app
```

</details>

## Install Traefik using helm 

```shell
helm repo add traefik https://helm.traefik.io/traefik
helm repo update
helm install traefik traefik/traefik --namespace traefik --create-namespace
```

Now if you run `kubectl get svc --all-namespaces` you will see that you have a new service in the `traefik` namespace. This service is of type LoadBalancer. This type of service is a bit special, its behavior is defined by the "cloud-controller-manager" which embeds cloud-specific control logic. This means that since we are running inside OVHCloud, it is OVHCloud own logic that is triggered when a LoadBalancer service is created. The result is that a OVHCloud Load Balancer is created outside our cluster and configured to forward traffic to the cluster's nodes.


## Install Cert-Manager using helm 

```shell
helm repo add jetstack https://charts.jetstack.io
helm repo update
helm install cert-manager jetstack/cert-manager --namespace cert-manager --create-namespace --version v1.14.5 --set installCRDs=true
```

## Configure ingress and Let's Encrypt Issuer

Apply the ingress and Let's Encrypt issuer configuration:

```bash
kubectl apply -f ingress
```

You can now inspect the ingress and cluster issuer resources:

```bash
kubectl get clusterissuers.cert-manager.io
kubectl get ingress
```

This allows us to use Let's Encrypt to automatically issue TLS certificates for our applications.

Note the IP address listed under the ingress, you will have to provide that IP to the workshop organizers to that they can configure the DNS records for you.

