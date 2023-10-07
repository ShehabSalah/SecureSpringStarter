# SecureSpringStarter

![GitHub release (latest by date)](https://img.shields.io/github/v/release/ShehabSalah/SecureSpringStarter)
![license](https://badgen.net/github/license/ShehabSalah/SecureSpringStarter)

SecureSpringStarter is a comprehensive Spring Boot project that provides robust user authentication and authorization, email and mobile verification, password management, and more. It's designed to jumpstart your application development, ensuring security is never a concern.

With support for the latest Spring Boot 3.1.4 and Spring Security 6.1.4, SecureSpringStarter offers a solid foundation for implementing user authentication, authorization, email verification, and more, making it easier to build secure and feature-rich applications.

## Getting Started

These instructions will help you get started with the SecureSpringStarter project. Please note that this is an initial setup guide, and the project is still in its early stages.

## Prerequisites

Before you begin, make sure you have the following prerequisites installed:

- Java Development Kit (JDK) 17 or later
- [Maven](https://maven.apache.org/) (for building and managing dependencies)
- [Git](https://git-scm.com/) (for version control)

## Configuration

### Application Properties
Before running the application, you need to configure some settings in the `application-dev.yml` file located in the `src/main/resources` directory. Replace the following placeholders with your own values:

- `your_jwt_secret_key`: Your JWT secret key for authentication.
- `your_jwt_token_expiration_in_milliseconds`: The expiration time for JWT tokens in milliseconds.
- `your_admin_email`: Email for the default admin user.
- `your_admin_password`: Password for the default admin user.

```yaml
app:
   jwt:
      expirationMs: [your_jwt_token_expiration_in_milliseconds]
      secret: [your_jwt_secret_key]
   admin:
      email: [your_admin_email]
      password: [your_admin_password]
```

Please ensure that you replace these placeholders with your actual configuration values.

### Mobile Number Region (ValidateUtil Class)
In the ValidateUtil class located in the `src/main/java/com/shehabsalah/securespringstarter/utils`, there is a default region code ("EG") set for validating mobile numbers. If you need to change the default region to match your requirements, locate the following line of code:

```java
// ValidateUtil.java

PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
Phonenumber.PhoneNumber numberProto = phoneUtil.parse(str, "EG");
```
You can change "EG" to your desired region code. For example, if you want to validate numbers in the United States, you can set it to "US":

```java
PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
Phonenumber.PhoneNumber numberProto = phoneUtil.parse(str, "US");
```
Make sure to adjust this default region code to match your specific needs.

## Build the Project

To build the SecureSpringStarter project, follow these steps:

1. Clone the project repository to your local development environment:
    
    ```bash
    git clone https://github.com/ShehabSalah/SecureSpringStarter.git
    ```

2. Navigate to the project's root directory:
    
    ```bash
    cd SecureSpringStarter
    ```

3. Build the project using Maven. This command will download the project dependencies and compile the code.

    ```bash
    mvn clean install
    ```
    
4. Once the build is complete, you should see a message similar to the following:
    
   ```bash
   [INFO] ------------------------------------------------------------------------
   [INFO] BUILD SUCCESS
   [INFO] ------------------------------------------------------------------------
   [INFO] Total time:  01:00 min
   [INFO] Finished at: 2021-10-20T12:00:00+01:00
   [INFO] ------------------------------------------------------------------------
   ```

## Run the Application

To run the SecureSpringStarter application, run the following command:

```bash
mvn spring-boot:run
```
The application will now be running on port 8080. You can access it at http://localhost:8080 in your web browser.

You can also change the default port by updating the `server.port` property in the `application-dev.yml` file.

```yaml
server:
  port: 8080
```

## Database
SecureSpringStarter uses the H2 in-memory database by default for development purposes. You can configure other databases in the `application-dev.yml` file.

## Authentication and Security
SecureSpringStarter uses Spring Security 6.1.4 for user authentication and authorization. JWT (JSON Web Tokens) are used for authentication, and the default admin user is created during the application startup.

## Customization
You can customize various aspects of the application, including validation, error handling, and more, to fit your specific requirements.

## API Documentation
SecureSpringStarter provides various APIs for user authentication, registration, and more. Below is a list of available APIs along with their details:

### User Registration

- **Path:** `/api/auth/register`
- **Request Type:** POST
- **Request Body:** Include user registration information (first name, last name, email, mobile, password, and confirm password).
- **Expected Response:** Successful registration message and JWT token.

### User Login

- **Path:** `/api/auth/login`
- **Request Type:** POST
- **Request Body:** Include user login information (email and password).
- **Expected Response:** Successful login message and JWT token.

### User Profile

- **Path:** `/api/auth/profile`
- **Request Type:** GET (requires authentication)
- **Expected Response:** User profile information (excluding sensitive data).

These APIs enable you to perform user registration, login, and retrieve user profiles. You can integrate these endpoints into your application as needed. Please refer to the API documentation for more details on each endpoint, including request and response formats.

## Contributing

We welcome contributions to the SecureSpringStarter project! If you'd like to contribute, please follow our [contribution guidelines](CONTRIBUTING.md).

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgments

- Thanks to the Spring Boot and Spring Security communities for providing powerful tools for building secure applications.

## Contact

For questions or feedback, you can reach out to me on social media or email:

- [Social Media Links](https://bit.ly/m/shehabsalah)
- Email: shehabsalah25@gmail.com

## Project Status

Please note that SecureSpringStarter is an ongoing project, and while it provides essential authentication and authorization features, there are more functionalities and enhancements planned for the future. We are actively working to improve and expand the project to make it even more comprehensive and valuable for Spring Boot developers. Stay tuned for updates, and feel free to contribute to the project to help make it even better.


We look forward to your involvement in making SecureSpringStarter a valuable resource for Spring Boot developers!


