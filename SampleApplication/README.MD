SampleApplication is intended to provide a starting point for building custom Java application integrations using the SendSafely Java Client API. 

# Instructions
Build and run SampleApplication using the Maven instructions below.


## Maven
### Build
```mvn clean compile package```

### Run (macOS/Linux)
```java -cp "lib/*:target/SampleApplication-1.0-SNAPSHOT.jar" SendSafelyRefApp```

### Run (Windows)
```java -cp "lib/*;target/SampleApplication-1.0-SNAPSHOT.jar" SendSafelyRefApp```

### Command Line Argments
 
 ```
 Usage: SendSafelyRefApp SendSafelyHost UserApiKey UserApiSecret FileToUpload RecipientEmailAddress
 
 This program will print out the secure URL to access the package after it has been submitted.`
 ```
 
- SendSafelyHost - Use your SendSafely organization host or https://app.sendsafely.com
- UserApiKey - Generate ApiKey and ApiSecret from your Edit Profile page when logged into the SendSafely web portal 
- UserApiSecret - Generate ApiKey and ApiSecret from your Edit Profile page when logged into the SendSafely web portal
- FileToUpload - Path to the file that is to be uploaded (i.e. /path-to-file/test.pdf)
- RecipientEmailAddress - The email address of the person you want to send the file to (i.e. anybody@example.com)