# DEPLOY

	mvn clean package -Dno=xxx
	sls deploy --no xxx

	e.g. sls deploy --no a001

# Invoke

	sls invoke -f put -p event.json --no a001


# e.g.
## maven build
```
E:\workspaces\e.4.7.2\face-detection>mvn clean package -Dno=a001
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
E:\workspaces\e.4.7.2\face-detection>sls deploy --no a001
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
service: a001-face-detection
stage: dev
region: us-east-1
stack: a001-face-detection-dev
api keys:
  None
endpoints:
  None
functions:
  put: a001-face-detection-dev-put

E:\workspaces\e.4.7.2\face-detection>
```
## Invoke
```
E:\workspaces\e.4.7.2\face-detection>sls invoke -f put -p event.json --no a001
{
    "userId": "satoh001",
    "s3Key": "1_happy_face.jpg",
    "s3Bucket": "stylez-sls-handson-rekognition",
    "collectionId": "rekognition-satoh-test-id",
    "detectedFaceDetails": {
        "boundingBox": {
            "width": 0.4091653,
            "height": 0.5118456,
            "left": 0.3717559,
            "top": 0.14331676
        },
～～～～～～
        "quality": {
            "brightness": 56.283524,
            "sharpness": 99.97486
        },
        "confidence": 99.99857
    }
}

E:\workspaces\e.4.7.2\face-detection>
```