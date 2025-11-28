# Copilot Instructions for RestAssured PetStore Automation Framework

## Project Overview

RestAssured PetStore is a test automation framework for the Swagger PetStore API using RestAssured. It provides a structured, maintainable approach to API testing with support for three main API modules: Pet, Store, and User management.

## Architecture & Key Components

### API Module Structure (src/main/java/com/petstore/api/)

The framework uses a hierarchical API client pattern:

1. **ApiClient (Base Class)**: Provides common HTTP operations (GET, POST, PUT, DELETE) and request specification setup
   - Manages RestAssured RequestSpecification initialization
   - Handles base URL and timeout configuration from ConfigManager
   - All API-specific classes extend this

2. **API-Specific Classes**: PetApi, StoreApi, UserApi
   - Wrap domain-specific endpoints
   - Example: `PetApi.getPetById(Long)` maps to `GET /pet/{id}`
   - Always call `resetRequestSpec()` after GET operations to avoid state pollution

### Data Models (src/main/java/com/petstore/models/)

- **Pet, Order, User, Category**: POJO models with Jackson @JsonProperty annotations
- All models support Jackson serialization/deserialization
- Models use constructor overloading for convenient test data creation

### Test Support Utilities (src/main/java/com/petstore/utils/)

- **AssertionHelper**: Use instead of raw assertions - provides logging and clear failure messages
  - Key methods: `assertStatusCode()`, `assertJsonPath()`, `assertResponseTime()`
- **TestDataBuilder**: Generate random test data to avoid conflicts
  - Use `generatePetId()`, `generateUsername()`, `generateEmail()` instead of hardcoded values

### Configuration (src/main/java/com/petstore/config/)

- **ConfigManager**: Singleton pattern - loads from `src/test/resources/config.properties`
- Update `base.url` to point to different environments
- Never hardcode API endpoints in tests

## Testing Patterns & Conventions

### Test Class Structure

```java
@Feature("Feature Name")
@Story("Story Name")
public class ApiTests {
    private SomeApi api;
    
    @BeforeClass
    public void setup() {
        api = new SomeApi();  // Instantiate API client
    }
    
    @Test(description = "Human readable description")
    @Description("Extended description for Allure report")
    public void testFeatureName() {
        // Arrange: Set up test data using TestDataBuilder
        // Act: Call API methods
        // Assert: Use AssertionHelper for validations
    }
}
```

### Critical Patterns to Follow

1. **Always reset request spec after GET operations**: `resetRequestSpec()` prevents request state bleeding
2. **Use AAA pattern**: Arrange (setup), Act (execute), Assert (validate)
3. **Generate test data dynamically**: Use TestDataBuilder to avoid ID conflicts in parallel execution
4. **Chain assertions with helper**: Bad: `response.statusCode()`, Good: `AssertionHelper.assertStatusCode(response, 200)`
5. **Don't hardcode IDs**: Generate them or use endpoints that return IDs

### Example Test Pattern

```java
@Test
public void testCreateAndRetrieveEntity() {
    // Create new entity with unique data
    String uniqueId = TestDataBuilder.generateUsername();
    User user = new User(uniqueId, TestDataBuilder.generateEmail(), TestDataBuilder.generatePassword());
    
    Response createResponse = userApi.createUser(user);
    AssertionHelper.assertStatusCode(createResponse, 200);
    
    // Verify retrieval
    Response getResponse = userApi.getUserByUsername(uniqueId);
    AssertionHelper.assertStatusCode(getResponse, 200);
    AssertionHelper.assertJsonPath(getResponse, "username", uniqueId);
}
```

## Build & Test Execution

### Maven Commands

```bash
mvn clean install               # Full build with dependencies
mvn test                        # Run all tests (uses testng.xml)
mvn test -Dtest=PetApiTests    # Run specific test class
mvn allure:report && mvn allure:serve  # Generate and view reports
```

### TestNG Configuration

Tests are orchestrated via `src/test/resources/testng.xml`:
- Defines test classes and execution order
- Can be modified to run parallel tests (currently thread-count="1")
- Currently runs PetApiTests, StoreApiTests, UserApiTests

### Allure Reporting

- Annotations generate structured reports: `@Feature`, `@Story`, `@Description`
- Reports include request/response details, timing, and execution logs
- Generated in `target/allure-results` and viewable via `mvn allure:serve`

## Common Development Tasks

### Adding a New API Endpoint

1. Create method in corresponding API class (e.g., PetApi)
2. Follow logging pattern: `logger.info("Action description: {}", variable)`
3. Reset request spec if GET operation: `resetRequestSpec()`
4. Return Response object for assertion in test

### Adding New Test Cases

1. Create test in appropriate test class (PetApiTests, StoreApiTests, UserApiTests)
2. Use `@Test` with `description` parameter
3. Add `@Description` for Allure report
4. Use AssertionHelper for all validations
5. Use TestDataBuilder for dynamic test data

### Updating Configuration

- Modify `src/test/resources/config.properties`
- Access via `ConfigManager.getInstance().getProperty(key)`
- No hardcoded URLs or timeouts in code

## Integration Points & External Dependencies

### PetStore API

- **Base URL**: https://petstore.swagger.io/v2
- **Authentication**: None (public API)
- **Rate Limiting**: None enforced (test environment)
- **Endpoints**: /pet, /store, /user with standard HTTP methods

### Dependencies to Know

- **RestAssured**: HTTP client - all API calls go through this
- **TestNG**: Test runner - reads testng.xml
- **Jackson**: Serializes/deserializes POJOs - declare @JsonProperty on model fields
- **SLF4J**: Logging - use `LoggerFactory.getLogger(Class.class)` in classes
- **Allure**: Test reporting - use annotations on test methods

## Troubleshooting Guide for AI Agents

### Issue: Response assertions fail with NPE

**Cause**: Model fields don't have @JsonProperty matching API response
**Fix**: Add `@JsonProperty("apiFieldName")` to all model fields

### Issue: Tests pass individually but fail in parallel execution

**Cause**: Hardcoded IDs or test data conflicts
**Fix**: Use TestDataBuilder for all IDs, avoid test interdependencies

### Issue: GET operations return stale data

**Cause**: RequestSpecification not reset between requests
**Fix**: Call `resetRequestSpec()` after GET operations or before next request

### Issue: Base URL not loading from config

**Cause**: config.properties not in classpath
**Fix**: Ensure file is in `src/test/resources/` and Maven includes test-resources

## File References for Quick Navigation

| Purpose | File |
|---------|------|
| Base API operations | src/main/java/com/petstore/api/ApiClient.java |
| Pet endpoints | src/main/java/com/petstore/api/PetApi.java |
| Test execution setup | src/test/resources/testng.xml |
| Test properties | src/test/resources/config.properties |
| Assertion utilities | src/main/java/com/petstore/utils/AssertionHelper.java |
| Example tests | src/test/java/com/petstore/tests/ |
