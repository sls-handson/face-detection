# DEPLOY

	mvn clean package -Dno=xxx
	sls deploy --no xxx

	e.g. sls deploy --no A001

# Invoke

	sls invoke -f put -p event.json --no A001


# e.g.
## maven build
```
E:\workspaces\e.4.7.2\face-detection>mvn clean package -Dno=A001
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building aws-spring-cloud-function-maven dev
[INFO] ------------------------------------------------------------------------
[INFO]
[INFO] --- maven-clean-plugin:2.6.1:clean (default-clean) @ aws-spring-cloud-function-maven ---
[INFO] Deleting E:\workspaces\e.4.7.2\face-detection\target
～～～～～～
[INFO] Attaching shaded artifact.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 6.978 s
[INFO] Finished at: 2018-01-10T18:04:36+09:00
[INFO] Final Memory: 87M/402M
[INFO] ------------------------------------------------------------------------

E:\workspaces\e.4.7.2\face-detection>
```
## serverless deploy
```
E:\workspaces\e.4.7.2\face-detection>sls deploy --no A001
Serverless: Packaging service...
Serverless: Creating Stack...
Serverless: Checking Stack create progress...
.....
Serverless: Stack create finished...
Serverless: Uploading CloudFormation file to S3...
Serverless: Uploading artifacts...
Serverless: Validating template...
Serverless: Updating Stack...
Serverless: Checking Stack update progress...
...............
Serverless: Stack update finished...
Service Information
service: A001-face-detection
stage: dev
region: us-east-1
stack: A001-face-detection-dev
api keys:
  None
endpoints:
  None
functions:
  put: A001-face-detection-dev-put

E:\workspaces\e.4.7.2\face-detection>
```
## Invoke
```
E:\workspaces\e.4.7.2\face-detection>sls invoke -f put -p event.json --no A001
true

E:\workspaces\e.4.7.2\face-detection>
```