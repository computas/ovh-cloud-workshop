*In this part you will configure and setup the OVH resources our application needs.*

## Task 1 - Create Managed Kubernetes in OVHCloud

Our application will be running inside OVHCloud's managed Kubernetes cluster, so we will have to create that cluster now.

**Create a Managaed Kubernetes Cluster under "Public Cloud":**
- Set name
- Select Location
    - deployment mode: 1-AZ region
    - London
- Select plan
    - Standard
- Select minor version and sec policy
    - Maximum security <- vs. Minimum unavaiable
    - Version 1.32
- Choose a private network for this cluster
    - Create a private network -> Add private network (*)
    - Select created network
    - Choose a subnet
        - nodes private interface
- Configure one or more node pools
    - set name
    - Select a model
        - general purpose
        - size 3
    - ingen autoscaling
    - ingen anti-affinity
- Set billing type
    - hour
- Confirm

**Add Private network: (*)**
- Private network name
- Layer 2 network option (la stå tom)
- Configure Subnet
    - Enable DHCP
    - Declare tje first address
    - Create Gateway


## Task 2 - Create Postgres Database

The application is using a PostgreSql database, let's set that up now.

**Create database:**
- Select your database type
    - PostgreSQL 17
- Select a service plan
    - Business
- Select a region
    - London
- Node type
    - Db1-15
- Cluster sizing
    - None
- Configure your options
    - Network
        - Private network (vRack)
        - Private network attached
            - workshop-network
        - Subnetwork
            - 10.1.0.0/16 - UK1
        - Authorised IPs
            - leave empty
- Order!
- Creating...


## Task 3 - Create an Artifact Registry to host Container images

To host our Container Images we need a registry, OVHCloud offers a managed solution for this. It is based on the open source artifact registry [Harbor](https://goharbor.io/). When creating this resource OVH will basically just set up a VM with Harbor installed, expose it over the internet and provide you with login credentials.

Managed Private Registry (Harbor)
- Select a region
    - Frankfurt
- Private Registry name
    - ovh-workshop
- Choose your plan
    - s
- Create
- Installing.... 6min

Nice! Once the VM is up and running you can click "Generate new identification details" to get credentials to be able to log in to your new Harbor instance.

## Task 4 - Build application container images and push to Harbor

We have created a simple TODO app consisting of a frontend React application and a backend Spring Boot application that talks to a database.

Under the folders /frontend and /backend you can find a Dockerfile for each application. You should now build and publish the resulting image to your new Harbor instance.

💡 _HINT:_ Enter Harbor UI to find the URL that the image should be pushed to.
💡 _HINT 2:_ Make sure you build the image for the correct platform (`--platform=linux/amd64`).

<details>
  <summary>✨ Se fasit</summary>

Build and push frontend:
```bash
cd frontend

docker build --platform=linux/amd64 -t <your instance>.container-registry.ovh.net/library/frontend:latest .

docker push <your instance>.container-registry.ovh.net/library/frontend:latest
```

Build and push backend:
```bash
docker build --platform=linux/amd64 -t <your instance>.container-registry.ovh.net/library/backend:latest .

docker push <your instance>.container-registry.ovh.net/library/backend:latest
```
</details>

## Task 5 - Check that your images have been uploaded successfully

Enter the Harbor UI to see that the images you build have been pushed successfully. 


