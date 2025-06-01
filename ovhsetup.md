*In this part you will configure and set up the OVH resources our application needs.*

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
    - Create a private network -> Add private network (do this in another tab, will return to this tab shortly)
        - Private network name
        - London / 1-AZ region
        - Layer 2 network option (la stå tom)
        - Configure Subnet
            - Check off for "Enable DHCP"
            - Check off for "Declare the first address..." (already checked)
            - Check off for "assign Gateway and connect to the private network"
    - Go back to previous tab and select the created network (you may have click the refresh button) 
    - Choose a subnet
        - Check off for "Nodes private interface"
- Configure one or more node pools
    - set name
    - Select a model
        - general purpose (B3-8)
        - size 2
    - ingen autoscaling
    - ingen anti-affinity
- Set billing type
    - hour
- Click "Add node pool" -> "next"
- "Confirm cluster"


## Task 2 - Create Postgres Database

The application is using a PostgreSql database, let's set that up now.

**Create database:**
- Select your database type
    - PostgreSQL 17
- Select a service plan
    - Business
- Select a region
    - Frankfurt (DE)
- Node type
    - Db1-4
- Cluster sizing
    - None
- Configure your options
    - Network
        - Private network (vRack)
        - Private network attached
            - (use network created in Task 1)
        - Subnetwork
            - 10.1.0.0/16 - DE1
        - Authorised IPs
            - leave empty
        - Price: "Hour"
- Order!
- Creating...


## Task 3 - Create an Artifact Registry to host Container images

To host our Container Images we need a registry, OVHCloud offers a managed solution for this. It is based on the open source artifact registry [Harbor](https://goharbor.io/). When creating this resource OVH will set up a VM with Harbor installed, expose it over the internet and provide you with login credentials.

Managed Private Registry (Harbor)
- Select a region
    - Frankfurt
- Private Registry name
    - ovh-workshop
- Choose your plan
    - s
- Create
- Installing... (~6min)

Nice! Once the VM is up and running you can click "Generate new identification details" to get credentials to be able to log in to your new Harbor instance. Please take note of the credentials, you will need them later.

You can try to log in to the Harbor UI by clicking the "Access the Harbor user interface" button. From here you should be able to find the URL to which you will push images in the next task.

## Task 4 - Prepare the applications

We have created a simple TODO app consisting of a frontend React application and a backend Spring Boot application that talks to a database.

Before proceeding you need to configure VITE_API_URL in the `/frontend/.env` file and set it to <prefix>.computas.dev which references the subdomain (you choose the subdomain, but it must be unique) which your app will finally be hosed on.

## Task 5 - Build application container images and push to Harbor

Under the folders `/frontend` and `/backend` you can find a Dockerfile for each application. You should now build and publish the resulting image to your new Harbor instance.

💡 _HINT 1:_ Enter Harbor UI to find the URL that the image should be pushed to.

💡 _HINT 2:_ `docker login <your instance>.container-registry.ovh.net` to log in to your Harbor instance before pushing the images.

💡 _HINT 3:_ Make sure you build the image for the correct platform (`--platform=linux/amd64`).

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
cd backend 

docker build --platform=linux/amd64 -t <your instance>.container-registry.ovh.net/library/backend:latest .

docker push <your instance>.container-registry.ovh.net/library/backend:latest
```
</details>

## Task 6 - Check that your images have been uploaded successfully

Enter the Harbor UI to see that the images you build have been pushed successfully. 


🚀 You can now continue to part 2 🚀 