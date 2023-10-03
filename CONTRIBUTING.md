# Contributing to SecureSpringStarter

Thank you for your interest in contributing to SecureSpringStarter! We welcome contributions from the community to help make this project even better.

Before you get started, please take a moment to read through the following guidelines.

## Code of Conduct

Please review our [Code of Conduct](CODE_OF_CONDUCT.md) before contributing. We aim to foster a respectful and inclusive community.

## How to Contribute

### Reporting Bugs

If you encounter a bug or unexpected behavior in SecureSpringStarter, please help us by [reporting it](#reporting-bugs). When reporting a bug, be sure to provide as much detail as possible to help us understand and reproduce the issue.

### Suggesting Enhancements

If you have an idea for an enhancement or a new feature you'd like to see in SecureSpringStarter, please [suggest it](#suggesting-enhancements). We value your input and would love to hear your ideas.

### Pull Requests

If you'd like to contribute code or documentation to SecureSpringStarter, please follow these steps:

1. Fork the SecureSpringStarter repository.
2. Create a new branch for your feature or bug fix:

   ```bash
   git checkout -b feature-name
    ```

3. Make your changes, and ensure that your code follows the project's coding conventions and style guidelines.
4. Write tests to cover your code changes, if applicable.
5. Commit your changes with a descriptive commit message:

   ```bash
   git commit -m "commit message"
   ```
6. Push your branch to your fork:

   ```bash
   git push origin feature-name
   ```
7. Open a pull request on the SecureSpringStarter repository, providing a clear title and description of your changes. Be sure to reference any related issues.
8. Your pull request will be reviewed by maintainers, and changes may be requested. Once your changes are approved, they will be merged into the project.

### Code Style
Please adhere to the code style and conventions used in the SecureSpringStarter project. If you're unsure about any style guidelines, feel free to ask for clarification.

### Commit Messages
Please use descriptive commit messages that clearly describe your changes. Commit messages should be written in the present tense, and should not include any trailing punctuation.

### Documentation
Please update the documentation as needed to reflect your changes. If you're adding a new feature, be sure to include it in the appropriate section of the documentation.

### Code Comments
#### Class level comments
Please include a comment at the top of each class that describes the purpose of the class. This comment should be written in the form of a sentence or short paragraph that describes the class in detail following the guidelines below:
1. The comment should be written in the third person, and should not include any personal pronouns (e.g. "I", "we", "you", etc.).
2. The comment should be written in the present tense.
3. The comment should not include any trailing punctuation.
4. The comment should contain the author's name and the date the class was created.
5. The comment should contain the version number of the class.
6. You can include additional information about other classes that the class depends on using the `@see` tag.
7. You can add HTML tags to the comment to format it as needed (e.g. `<p>`, `<b>`, `<i>`, `<ol>`, `<ul>`, `<li>`, etc.).

**_For example:_**
```java
/**
 * The {@code User} class represents a user of the application.
 * 
 * <p>
 * This class contains information about the user, such as their name, email address, and password.
 * It also contains methods for retrieving and updating the user's information.
 * 
 * @see UserRole
 * @author shehabsalah  
 * @version 1.0
 * @since 2023-10-03
 */
public class User {
    // class implementation
}

public class UserRole {
    // class implementation
}
```

#### Method level comments
Please include a comment at the top of each method that describes the purpose of the method. This comment should be written in the form of a sentence or short paragraph that describes the method in detail following the guidelines below:
1. The comment should be written in the third person, and should not include any personal pronouns (e.g. "I", "we", "you", etc.).
2. The comment should be written in the present tense.
3. The comment should not include any trailing punctuation.
4. The comment should contain the method parameters and return value.
5. If the method throws any exceptions, the comment should include the exceptions.
6. You can include additional information about other methods that the method depends on using the `@see` tag.

**_For example:_**
```java

public class User {
   /**
    * Returns the user with the specified ID.
    * 
    * <p>
    * This method returns the user with the specified ID. If no user with the specified ID exists, 
    * a {@code UserNotFoundException} is thrown.
    * If the user with the specified ID exists, check if the user is active {@link #checkUserActive(User)}.
    *
    * @param id the ID of the user to return
    * @return the user with the specified ID
    * @throws UserNotFoundException if no user with the specified ID exists
    * @see #checkUserActive(User)
    */
   public User getUserById(Long id) throws UserNotFoundException {
       // method implementation
   }
   
    /**
     * Checks if the specified user is active.
     * 
     * <p>
     * This method checks if the specified user is active. If the user is not active, a 
     * {@code UserNotActiveException} is thrown.
     *
     * @param user the user to check
     * @return {@code true} if the specified user is active, {@code false} otherwise
     * @throws UserNotActiveException if the specified user is not active
     */
   public boolean checkUserActive(User user) throws UserNotActiveException {
       // method implementation
   }
}

// Custom exceptions for the User class
public class UserNotFoundException extends RuntimeException {
    // class implementation
}

public class UserNotActiveException extends RuntimeException {
    // class implementation
}
```

## Licensing
By contributing to SecureSpringStarter, you agree that your contributions will be licensed under the [MIT License](LICENSE).

## Contact

For questions or feedback, you can reach out to me on social media or email:

- [Social Media Links](https://bit.ly/m/shehabsalah)
- Email: shehabsalah25@gmail.com

Thank you for your contributions and for helping to make SecureSpringStarter better for everyone!