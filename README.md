# RestAssured PetStore API Automation Framework

A comprehensive REST API automation testing framework built with RestAssured for the PetStore API. This framework provides a robust, scalable, and maintainable approach to API testing with support for multiple endpoints, advanced reporting, and test data management.

## 📋 Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Test Organization](#test-organization)
- [API Endpoints](#api-endpoints)
- [Using the Framework](#using-the-framework)
- [Allure Reporting](#allure-reporting)

## ✨ Features

- **RestAssured Integration**: Fluent API for HTTP request/response handling
- **Multiple API Modules**: Pet, Store, and User API endpoints
- **Data Models**: Type-safe POJO models for request/response serialization
- **Assertion Utilities**: Comprehensive assertion helpers for response validation
- **Test Data Builder**: Dynamic test data generation utilities
- **Configuration Management**: Centralized configuration for base URLs and timeouts
- **Allure Reporting**: Detailed test execution reports with attachments
- **Logging**: SLF4J integration for detailed test execution logs
- **Maven Build**: Maven POM with standard project structure

## 📁 Project Structure

```
RestAssuredPetStore/
├── src/
│   ├── main/
│   │   └── java/com/petstore/
│   │       ├── api/
│   │       │   ├── ApiClient.java           # Base API client class
│   │       │   ├── PetApi.java              # Pet API endpoints
│   │       │   ├── StoreApi.java            # Store API endpoints
│   │       │   └── UserApi.java             # User API endpoints
│   │       ├── config/
│   │       │   └── ConfigManager.java       # Configuration management
│   │       ├── models/
│   │       │   ├── Pet.java                 # Pet model
│   │       │   ├── Category.java            # Category model
│   │       │   ├── Order.java               # Order model
│   │       │   └── User.java                # User model
│   │       └── utils/
│   │           ├── AssertionHelper.java     # Assertion utilities
│   │           └── TestDataBuilder.java     # Test data generation
│   └── test/
│       ├── java/com/petstore/tests/
│       │   ├── PetApiTests.java             # Pet API test cases
│       │   ├── StoreApiTests.java           # Store API test cases
│       │   └── UserApiTests.java            # User API test cases
│       └── resources/
│           ├── config.properties            # Configuration file
│           └── testng.xml                   # TestNG suite configuration
├── pom.xml                                   # Maven configuration
└── README.md                                 # This file
```

## 🔧 Prerequisites

- Java 11 or higher
- Maven 3.6.0 or higher
- Internet connection (for API calls to PetStore)

## 📝 Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/Rajasekhar741/RestAssuredPetStore.git
   cd RestAssuredPetStore
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Verify setup**
   ```bash
   mvn --version
   java -version
   ```

## ⚙️ Configuration

Configuration is managed through `src/test/resources/config.properties`:

```properties
base.url=https://petstore.swagger.io/v2
request.timeout=5000
response.timeout=5000
log.level=INFO
```

### Configuration Options

| Property | Description | Default |
|----------|-------------|---------|
| `base.url` | PetStore API base URL | https://petstore.swagger.io/v2 |
| `request.timeout` | Request timeout in milliseconds | 5000 |
| `response.timeout` | Response timeout in milliseconds | 5000 |
| `log.level` | Logging level | INFO |

## 🚀 Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=PetApiTests
```

### Run Specific Test Method
```bash
mvn test -Dtest=PetApiTests#testAddPet
```

### Run with TestNG XML
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

## 📊 Test Organization

Tests are organized using TestNG with the following structure:

- **PetApiTests**: Tests for pet management endpoints
- **StoreApiTests**: Tests for store operations
- **UserApiTests**: Tests for user account management

Each test class includes:
- `@BeforeClass`: Test setup and initialization
- `@Test`: Individual test methods with descriptive names
- Allure annotations for reporting

## 🐾 API Endpoints

### Pet API (`com.petstore.api.PetApi`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `addPet(Pet)` | POST /pet | Add a new pet |
| `updatePet(Pet)` | PUT /pet | Update existing pet |
| `getPetById(Long)` | GET /pet/{id} | Get pet by ID |
| `findPetsByStatus(String)` | GET /pet/findByStatus | Find pets by status |
| `deletePet(Long)` | DELETE /pet/{id} | Delete a pet |
| `uploadPetImage(Long, String)` | POST /pet/{id}/uploadImage | Upload pet image |

### Store API (`com.petstore.api.StoreApi`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `placeOrder(Order)` | POST /store/order | Place a new order |
| `getOrderById(Long)` | GET /store/order/{id} | Get order by ID |
| `deleteOrder(Long)` | DELETE /store/order/{id} | Delete an order |
| `getInventory()` | GET /store/inventory | Get store inventory |

### User API (`com.petstore.api.UserApi`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `createUser(User)` | POST /user | Create new user |
| `getUserByUsername(String)` | GET /user/{username} | Get user by username |
| `updateUser(String, User)` | PUT /user/{username} | Update user |
| `deleteUser(String)` | DELETE /user/{username} | Delete user |
| `loginUser(String, String)` | GET /user/login | User login |
| `logoutUser()` | GET /user/logout | User logout |

## 💻 Using the Framework

### Writing a Simple Test

```java
@Test(description = "Test adding a pet")
public void testAddPet() {
    // Arrange
    PetApi petApi = new PetApi();
    Pet pet = new Pet();
    pet.setId(TestDataBuilder.generatePetId());
    pet.setName(TestDataBuilder.generatePetName());
    pet.setStatus("available");

    // Act
    Response response = petApi.addPet(pet);

    // Assert
    AssertionHelper.assertStatusCode(response, 200);
    AssertionHelper.assertJsonPath(response, "name", pet.getName());
}
```

### Key Classes and Utilities

**ApiClient** - Base class for all API operations:
```java
petApi = new PetApi();
Response response = petApi.getPetById(1L);
```

**AssertionHelper** - Validation utilities:
```java
AssertionHelper.assertStatusCode(response, 200);
AssertionHelper.assertJsonPath(response, "name", "Fluffy");
AssertionHelper.assertResponseTime(response, 3000);
```

**TestDataBuilder** - Generate test data:
```java
Long petId = TestDataBuilder.generatePetId();
String username = TestDataBuilder.generateUsername();
String email = TestDataBuilder.generateEmail();
```

## 📈 Allure Reporting

### Generate Allure Report

```bash
# Run tests with Allure
mvn clean test

# Generate HTML report
mvn allure:report

# View report
mvn allure:serve
```

The report will open in your default browser showing detailed test execution results.

### Allure Annotations

Tests use Allure annotations for better reporting:

```java
@Feature("Pet Store")
@Story("Pet Management")
@Test(description = "Add a new pet")
@Description("Test adding a pet with valid data")
public void testAddPet() {
    // Test implementation
}
```

## 🔍 Troubleshooting

### Issue: Tests fail with connection errors
- **Solution**: Verify the PetStore API is accessible at `https://petstore.swagger.io/v2`
- Check your internet connection
- Update `base.url` in `config.properties` if needed

### Issue: Tests timeout
- **Solution**: Increase timeout values in `config.properties`
- Check network connectivity and API server status

### Issue: Reports not generating
- **Solution**: Ensure Allure is installed: `mvn allure:help`
- Clear target folder: `mvn clean`
- Rebuild: `mvn clean test`

## 📚 Dependencies

- **RestAssured 5.3.2**: REST API testing library
- **TestNG 7.8.1**: Testing framework
- **Jackson 2.16.0**: JSON/XML processing
- **SLF4J 2.0.9**: Logging framework
- **Allure 2.25.0**: Test reporting
- **AssertJ 3.24.1**: Fluent assertions
- **Hamcrest 2.2**: Matcher library

## 📝 Best Practices

1. **Use descriptive test names**: Makes test reports more readable
2. **Follow AAA pattern**: Arrange, Act, Assert in each test
3. **Use AssertionHelper**: Centralized assertions for consistency
4. **Generate test data**: Use TestDataBuilder for dynamic data
5. **Handle responses properly**: Always validate status codes and content
6. **Keep tests independent**: Each test should be runnable in any order
7. **Use Allure annotations**: Document tests for better reporting

## 🤝 Contributing

To contribute to this framework:

1. Create a feature branch
2. Make your changes
3. Add tests for new functionality
4. Update documentation
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

**Rajasekhar741**

---

Happy Testing! 🚀