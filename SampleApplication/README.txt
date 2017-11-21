Import the project in eclipse or run it from the command line.

Command line instructions.
cd to the project folder

Copy the SendSafely Jar into this folder and name it SendSafely.jar

Compile the code with the following command:
Linux, MacOS
javac -cp SendSafely.jar:lib/*.jar: -d bin src/*.java

Windows
javac -cp "SendSafely.jar;lib/*.jar" -d bin src/*.java

Run the application with the following command:
Linux, MacOS
java -cp SendSafely.jar:lib/*:bin/ SendSafelyRefApp

Windows
java -cp "SendSafely.jar;lib/*;bin/" SendSafelyRefApp

The Application requires 5 arguments
- SendSafelyHost (Use https://app.sendsafely.com)
- UserAPIKey (Go to your profile on www.sendsafely.com and generate a new API Key)
- UserApiSecret (See UserAPIKey)
- FileToUpload (A path to the file that is to be uploaded)
- RecipientEmailAddress (The email to the person you want to send the file to)

Example:
java -cp SendSafely.jar:lib/*:bin/ SendSafelyRefApp https://app.sendsafely.com my-api-key my-api-secret /path-to-file/test.pdf test@example.com
