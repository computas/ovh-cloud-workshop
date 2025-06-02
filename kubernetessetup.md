*In this part you will make the Kubernetes cluster ready to host our application*

## Task 1 - Set up Kubernetes Config

Under managed Kubernetes you can download your kubeconfig file. 

Set the Kubernetes context to use the new kubeconfig.

<details>
  <summary>✨ Se fasit</summary>

To use it you can run: `export KUBECONFIG=<path to kubeconfig file>`

NOTE: This config will only be applied to the current terminal session!

</details>

## Task 2 - Create application namespace

Create a namespace called `app`. This is where we are going to run our applications.

<details>
  <summary>✨ Se fasit</summary>

```bash
kubectl create namespace app
```

</details>

## Task 3 - Generate Database password secret

We will be using Kubernetes Secrets to store the password to our database. To simplify we will provide the admin user's credentials for our backend application. (this is obviously not a good idea in production...)

So your task is to find the credentials (hint: look under users tab under the managed database section) and create a Kubernetes secret on the form `PASSWORD=<secret>` named dbsecret, in the `app` namespace you created in the previous step.

❗❗❗️ _NOTE:_ In a production environment Kubernetes secrets are not secure out of the box. You will have to configure your cluster to encrypt the secrets or use external secret manager. (out of scope for this workshop)


<details>
  <summary>✨ Se fasit</summary>

```bash
kubectl create secret generic dbsecret --from-literal=PASSWORD=<secret> --namespace=app
```

</details>

## Task 4 - Install Traefik using helm 

<details>
  <summary>📚 More info about Traefik</summary>

> Traefik is an open-source reverse proxy which will be responsible for routing traffic to our Kubernetes services. It will also be responsible for terminating TLS sessions, so it will forward traffic as an HTTP request to our services. Traefik integrates well with Kubernetes and will read our `ingress.yaml` and configure itself accordingly. It also integrates with `cert-manager` which is a certificate management controller.

</details>


```shell
helm repo add traefik https://helm.traefik.io/traefik
helm repo update
helm install traefik traefik/traefik --namespace traefik --create-namespace
```

Now if you run `kubectl get svc --all-namespaces` you will see that you have a new service in the `traefik` namespace. This service is of type LoadBalancer. This type of service is a bit special, its behavior is defined by the "cloud-controller-manager" which embeds cloud-specific control logic. This means that since we are running inside OVHCloud, it is OVHCloud's own logic that is triggered when a LoadBalancer service is created. The result is that a OVHCloud Load Balancer is created outside our cluster and configured to forward traffic to the cluster's nodes.

⏸️ Please pause the workshop here and ask the workshop organizers to configure the DNS records to point to the external IP of the Traefik Load Balancer service.

(It will probably say <pending> under External IP for a couple of minutes. Just grab a ☕️ and relax.)

## Task 5 - Configure ingress to point subdomain

In the `k8s/ingress` folder change the #TODO fields to point to the subdomain you specified when building the frontend application.

## Task 5 - Install Cert-Manager using helm 

Then install cert-manager using helm:

```shell
helm repo add jetstack https://charts.jetstack.io
helm repo update
helm install \
  cert-manager jetstack/cert-manager \
  --namespace cert-manager \
  --create-namespace \
  --version v1.17.2 \
  --set crds.enabled=true
```

## Task 6 - Configure ClusterIssuer, Certificate and Ingress

Docs if you are especially interested: https://doc.traefik.io/traefik/user-guides/cert-manager/

- Configure ClusterIssuer:
    - ℹ️ This resource represents a CA (Certificate Authority) which is responsible for creating and signing the TLS certificates. The ClusterIssuer object is globally scoped, meaning it can be used across namespaces.
    - Apply the `cluster-issuer.yaml` configuration

- Configure Certificate:
    - ℹ️This resource represents a certificate request. `cert-manager` uses this input to generate a private key and a CertificateRequest resource in order to obtain a signed certificate from the ClusterIssuer we defined above.
    - Make sure your chosen DNS name is specified under: `spec.dnsNames` and then apply the `certificate.yaml` configuration.
    - Apply the `certificate.yaml` configuration

- Configure Ingress:
    - ℹ️The Ingress is a definition of our ingress that is read and handled by Traefik.
    - Make sure your chosen DNS name is specified under: `spec.rules[0].host` and `spec.tls[0].hosts`
    - Then apply the `ingress.yaml` configuration.

🚀 You can now continue to part 3 🚀 
