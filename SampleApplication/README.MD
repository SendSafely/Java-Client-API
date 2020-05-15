SampleApplication is intended to provide a starting point for building custom Java application integrations using the SendSafely Java Client API. 

# Instructions
Download the most recent release of the SendSafely Client API, extract, and copy all of the contained jar files into the SampleApplication/lib folder.

## Compile

`mkdir bin`

`javac -cp "lib/*" -d bin src/*.java`

## Run

```
java -cp "lib/*;bin/" SendSafelyRefApp
Usage: SendSafelyRefApp SendSafelyHost UserApiKey UserApiSecret FileToUpload RecipientEmailAddress

This program will print out the secure URL to access the package after it has been submitted.`
```

### Command Line Argments
 
- SendSafelyHost - Use your SendSafely organization host or https://app.sendsafely.com
- UserApiKey - Generate ApiKey and ApiSecret from your Edit Profile page when logged into the SendSafely web portal 
- UserApiSecret - Generate ApiKey and ApiSecret from your Edit Profile page when logged into the SendSafely web portal
- FileToUpload - Path to the file that is to be uploaded (i.e. /path-to-file/test.pdf)
- RecipientEmailAddress - The email address of the person you want to send the file to (i.e. anybody@example.com)