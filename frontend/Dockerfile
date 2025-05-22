# Use an official Node.js runtime as a parent image
FROM node:20-alpine

# Set the working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the application
RUN npm run build

# Install a lightweight static file server
RUN npm install -g serve

# Expose the port the app runs on
EXPOSE 3000

# Start the application in production mode
CMD ["serve", "-s", "dist", "-l", "3000"]
