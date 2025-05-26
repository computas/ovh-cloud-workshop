*In this part you will make the Kubernetes cluster ready to host our application*

## Task 1 - Generate Database password secret

We will be using Kubernetes Secrets to store the password to our database. To simply we will provide the admin user's credentials for our backend application. (this is obviously not a good idea in production...)

So your task is to find the credentials (hint: look under users tab under the managed database section) and create a Kubernetes secret on the form:

```PASSWORD=<secret>```

❗❗❗️_NOTE:_ In a production environment Kubernetes secrets are not secure out of the box. You will have to configure your cluster to encrypt the secrets or use external secret manager. (out of scope for this workshop)


<details>
  <summary>✨ Se fasit</summary>

```bash
kubectl create secret generic <name of secret> --from-literal=PASSWORD=<secret>
```

</details>

## Install Traefik using helm 

...need to be able to do this ourselves first...

## Install Cert-Manager using helm 

...

## Configure ingress

...




