*Finally! We can now deploy our applications*

# Task 1 - Apply Kubernetes config for frontend

Under the `/k8s` folder you will find a `/backend` and a `/frontend` folder. Under the `/frontend` folder you will find a Kubernetes Service and Deployment definition. In the deployment definition there is a reference to the docker image the container will be using. You will have to update this to pull from your Harbor instance!

Then apply both configurations.

<details>
  <summary>✨ Se fasit</summary>

```bash
kubectl apply -f /frontend 
```

</details>

# Task 2 - Apply Kubernetes config for backend

This step is similar to the first, but you need to consider three more thing. 

1. The secret we created in Part 2 will have to correspond to the database secret in the `deployment.yaml` file for the backend service. 

2. Also the database url in the backend deployment needs to be updated to point to the database service created by the managed database. Go to the OVHCloud UI to find the database connection string.

3. The Kubernetes nodes needs to be allowed to talk to the database, so the database config must be configured to allow requests from `10.1.0.0/16`. 

💡 _HINT:_ Use `kubectl get secrets` to see the name of the secret you created earlier.

<details>
  <summary>✨ Se fasit</summary>

```bash
kubectl apply -f /backend
```

</details>

# Task 3 

Wait for all pods to get up to "running" status with 1/1 containers "ready".

Then go to the domain you chose in a previous task and see everything working with HTTPS (feel free to check the certificate as well, you should see a Let's Encrypt issued certificate).






