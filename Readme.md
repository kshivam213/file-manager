file-manager

## Steps to Setup

**1. Clone the repository** 

```bash
git clone https://github.com/kshivam213/file-manager.git
```

**2. Specify the file uploads directory**

Open `src/main/resources/application.properties` file and change the property `file.upload-dir` to the path where you want the uploaded files to be stored.

```
file.upload-dir=storage
```

**2. Run the app using maven**

```bash
cd file-manager
mvn spring-boot:run
```

That's it! The application can be accessed at `http://localhost:8080`.

You may also package the application in the form of a jar and then run the jar file like so -

```bash
mvn clean package
java -jar target/file-demo-0.0.1-SNAPSHOT.jar
```

Apis - 

1. copy Api - http://localhost:8080/api/v1/file/copy
   type - GET

   It returns fileId
2. download - http://localhost:8080/api/v1/file/download/{fileId}
  
   type - GET
   fileId - either "master" or any fileId

3. delete - http://localhost:8080/api/v1/file/download/{fileId}

   type - Delete
   fileId - either "master" or any fileId


Swagger UI - http://localhost:8080/swagger-ui.html


TO DOS
- AUthentication - To add JWT with spring security so that before calling any apis
  Request must pass Access token. If access token is valid then only request should processed.


