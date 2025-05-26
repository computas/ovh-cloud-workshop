*Finally! We can now deploy our applications*

# Task 1 - Apply Kubernetes config for frontend

Under the `/k8s` folder you will find a `/backend` and a `/frontend` folder. Under the `/frontend` folder you will find a Kubernetes Service and Deployment definition. In the deployment definition there is a reference to the docker image the container will be using. You will have to update this to pull from your Harbor instance.

Then apply both configurations.

<details>
  <summary>✨ Se fasit</summary>

```bash
kubectl apply -f /frontend 
```

</details>

# Task 2 - Apply Kubernetes config for backend

This step is similar to the first, but you need to consider one more thing. The secret we created in Part 2 will have to correspond to the database secret in the `deployment.yaml` file for the backend service. 

💡 _HINT:_ Use `kubectl get secrets` to see the name of the secret you created earlier.

<details>
  <summary>✨ Se fasit</summary>

```bash
kubectl apply -f /backend
```

</details>

