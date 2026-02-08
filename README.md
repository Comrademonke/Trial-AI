# Trial AI

Trial AI is an interactive AI accountability simulation where players critically evaluate an AI's autonomous actions through a simulated legal proceeding.

## To setup the API to access Chat Completions and TTS

- add in the root of the project (i.e., the same level where `pom.xml` is located) a file named `apiproxy.config`

  ```
  email: "YOUR_EMAIL"
  apiKey: "YOUR_KEY"
  ```

## To run the game

`./mvnw clean javafx:run`
