# Pattern: Unit Test Suite

**Category:** Testing
**Language:** TypeScript/Jest, Java/JUnit 5
**Success Rate:** 89%
**Last Verified:** January 2026

## Prompt Template

```
Generate a comprehensive test suite for [target-function/class]:

Context:
- Language: [TypeScript/Java]
- Framework: [Jest/JUnit 5/Jasmine]
- Testing style: [BDD describe/it | AAA Arrange-Act-Assert]
- Code type: [pure function/service/component/controller]

Code to test:
[paste the code or describe it]

Mocking strategy:
- Dependencies to mock: [list services/repos to mock]
- Mock library: [jest.mock/Mockito/@testing-library]
- Spy vs Mock: [when to use each]

Required test categories:
1. Happy path - [number] tests covering normal operation
2. Edge cases - [describe specific edges to test]
3. Error scenarios - [list error conditions]
4. Boundary values - [list boundaries]
5. Async behavior - [if applicable: timeouts, retries, cancellation]

Assertions should verify:
- [what to assert-1]
- [what to assert-2]
- [what to assert-3]

Coverage targets:
- Line coverage: [target %]
- Branch coverage: [target %]
- Critical paths: [list must-cover scenarios]

Format:
- Use [describe/it | @Nested/@Test] blocks with clear descriptions
- Group by [functionality/scenario/method]
- Include setup in [beforeEach/setUp/@BeforeEach]
- Use [test.each/parameterized tests] for data-driven tests

Constraints:
- Tests must be independent (no shared mutable state)
- Each test should have single responsibility
- Use meaningful test names that describe behavior
- [Framework-specific constraints]
```

## Variables

| Variable | Description | Example Values |
|----------|-------------|----------------|
| `[target]` | What to test | calculatePortfolioReturn, UserService, LoginComponent |
| `[language]` | Target language | TypeScript, Java 17 |
| `[framework]` | Test framework | Jest, JUnit 5, Jasmine, Vitest |
| `[code-type]` | Type of code | pure function, service with DI, Angular component, Spring controller |
| `[mock-library]` | Mocking approach | jest.fn(), Mockito, ng-mocks, @MockBean |
| `[test-categories]` | What to cover | happy path, edge cases, errors, boundaries, async |
| `[coverage-target]` | Coverage goals | 80% line, 70% branch, 100% critical paths |

## Example Usage

### TypeScript/Jest: Pure Function

```
Generate a comprehensive test suite for calculatePortfolioReturn:

Context:
- Language: TypeScript (strict mode)
- Framework: Jest 29
- Testing style: BDD describe/it
- Code type: Pure function

Code to test:
function calculatePortfolioReturn(
  initialValue: number,
  finalValue: number,
  dividends: number = 0
): { absoluteReturn: number; percentReturn: number }

Mocking strategy:
- Dependencies to mock: None (pure function)
- Mock library: N/A
- Spy vs Mock: N/A

Required test categories:
1. Happy path - 4 tests: gains, losses, break-even, with dividends
2. Edge cases - zero initial value, negative values, very large numbers, NaN
3. Error scenarios - Infinity inputs, undefined parameters
4. Boundary values - 0, 0.01, Number.MAX_SAFE_INTEGER, Number.MIN_VALUE

Assertions should verify:
- Return object has both required properties
- absoluteReturn = finalValue + dividends - initialValue
- percentReturn = ((finalValue + dividends - initialValue) / initialValue) * 100
- Values are rounded to 2 decimal places

Coverage targets:
- Line coverage: 100% (small function)
- Branch coverage: 100%
- Critical paths: division by zero handling

Format:
- Use describe/it blocks with behavior descriptions
- Group by scenario type (gains, losses, edge cases)
- Use test.each for boundary value testing

Constraints:
- Tests must be independent
- Use toBeCloseTo for floating point comparisons
- Each test should verify one behavior
```

### TypeScript/Jest: Angular Service with HttpClient

```
Generate a comprehensive test suite for PortfolioService:

Context:
- Language: TypeScript (strict mode)
- Framework: Jest 29 with Angular TestBed
- Testing style: BDD describe/it with AAA comments
- Code type: Angular service with HttpClient and caching

Code to test:
@Injectable({ providedIn: 'root' })
export class PortfolioService {
  private http = inject(HttpClient);
  getPortfolio(id: string): Observable<Portfolio>;
  getPortfoliosByUser(userId: string): Observable<Portfolio[]>;
  createPortfolio(data: CreatePortfolioRequest): Observable<Portfolio>;
}

Mocking strategy:
- Dependencies to mock: HttpClient
- Mock library: jest.fn() with of() for Observable responses
- Spy vs Mock: Use spyOn for HttpClient methods

Required test categories:
1. Happy path - successful GET, POST responses
2. Edge cases - empty responses, large payloads, special characters in IDs
3. Error scenarios - 401, 404, 500, network timeout, invalid JSON
4. Async behavior - request cancellation, concurrent requests, retry logic

Assertions should verify:
- Correct HTTP method and URL called
- Request headers/body match expected
- Response correctly transformed
- Errors properly propagated or transformed
- Caching behavior (second call uses cache)

Coverage targets:
- Line coverage: 90%
- Branch coverage: 85%
- Critical paths: auth errors trigger logout, 404 returns empty

Format:
- Use describe blocks per method
- beforeEach with TestBed.configureTestingModule
- Use HttpClientTestingModule and HttpTestingController
- Verify no outstanding requests in afterEach

Constraints:
- Tests must not make real HTTP calls
- Each test should flush exactly one request
- Use fakeAsync/tick for timing-dependent tests
```

### Java/JUnit 5: Spring Service

```
Generate a comprehensive test suite for PortfolioService:

Context:
- Language: Java 17
- Framework: JUnit 5 with Mockito
- Testing style: AAA with @Nested for grouping
- Code type: Spring @Service with repository dependency

Code to test:
@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final MarketDataClient marketDataClient;

    public Optional<Portfolio> findById(String id);
    public List<Portfolio> findByUserId(String userId);
    public Portfolio create(CreatePortfolioRequest request);
    public BigDecimal calculateTotalValue(String portfolioId);
}

Mocking strategy:
- Dependencies to mock: PortfolioRepository, MarketDataClient
- Mock library: Mockito with @Mock and @InjectMocks
- Spy vs Mock: Mock for repository, Spy if testing partial behavior

Required test categories:
1. Happy path - found, created, calculated correctly
2. Edge cases - empty list, null fields, very long strings
3. Error scenarios - not found, duplicate, external service failure
4. Boundary values - empty portfolio (0 holdings), max holdings

Assertions should verify:
- Repository called with correct parameters
- Return values match expected
- Exceptions thrown for error conditions
- BigDecimal calculations use proper scale/rounding

Coverage targets:
- Line coverage: 90%
- Branch coverage: 85%
- Critical paths: calculateTotalValue handles null prices

Format:
- @Nested classes for each method
- @DisplayName for readable test names
- @BeforeEach for mock setup
- AssertJ for fluent assertions

Constraints:
- Use @ExtendWith(MockitoExtension.class)
- Verify mock interactions where important
- Use @ParameterizedTest for data-driven tests
- BigDecimal assertions use isEqualByComparingTo()
```

### Java/JUnit 5: Spring Controller (Integration)

```
Generate a comprehensive test suite for PortfolioController:

Context:
- Language: Java 17
- Framework: JUnit 5 with Spring MockMvc
- Testing style: Integration test with @WebMvcTest
- Code type: Spring @RestController

Code to test:
@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;

    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getById(@PathVariable String id);

    @PostMapping
    public ResponseEntity<Portfolio> create(@Valid @RequestBody CreatePortfolioRequest request);
}

Mocking strategy:
- Dependencies to mock: PortfolioService (via @MockBean)
- Mock library: Mockito
- Spy vs Mock: Mock (we're testing controller, not service)

Required test categories:
1. Happy path - 200 OK with valid response body
2. Edge cases - empty body, special characters in path
3. Error scenarios - 400 validation, 404 not found, 500 service error
4. Security - 401 unauthorized, 403 forbidden (if secured)

Assertions should verify:
- HTTP status codes
- Response body structure and content
- Content-Type header
- Validation error messages

Coverage targets:
- All endpoints tested
- All HTTP methods tested
- All error responses tested

Format:
- @WebMvcTest(PortfolioController.class)
- MockMvc for HTTP testing
- @WithMockUser for security tests
- JSONPath assertions for response body

Constraints:
- Use @MockBean for service layer
- Test JSON serialization/deserialization
- Verify request validation (@Valid)
```

## Common Pitfalls

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Shared mutable state | Tests affect each other | Reset mocks in beforeEach/setUp |
| Testing implementation | Tests break on refactor | Test behavior, not implementation details |
| Flaky async tests | Random failures | Use fakeAsync, tick(), flush(), waitFor() |
| Over-mocking | Tests pass but code fails | Use integration tests for critical paths |
| Floating point equality | `0.1 + 0.2 !== 0.3` | Use toBeCloseTo/isCloseTo with tolerance |
| Missing error paths | 100% happy path, 0% errors | Require error scenario tests in review |
| Huge test files | Hard to maintain | Split by feature, use test utilities |
| Magic numbers | Unclear what's tested | Use constants, builder patterns |

## Implementation Hints

### TypeScript/Jest

```typescript
// Mock HttpClient responses
const mockHttp = {
  get: jest.fn(),
  post: jest.fn()
};

// Return Observable from mock
mockHttp.get.mockReturnValue(of({ id: '1', name: 'Test' }));

// Return error Observable
mockHttp.get.mockReturnValue(throwError(() => new HttpErrorResponse({ status: 404 })));

// Test async with fakeAsync
it('should debounce search', fakeAsync(() => {
  component.search('test');
  tick(300); // wait for debounce
  expect(mockService.search).toHaveBeenCalledWith('test');
}));

// Data-driven tests with test.each
test.each([
  [100, 150, 0, 50, 50],
  [100, 80, 0, -20, -20],
  [100, 100, 10, 10, 10],
])('calculateReturn(%d, %d, %d) = { absolute: %d, percent: %d }',
  (initial, final, dividends, expectedAbs, expectedPct) => {
    const result = calculateReturn(initial, final, dividends);
    expect(result.absoluteReturn).toBe(expectedAbs);
    expect(result.percentReturn).toBeCloseTo(expectedPct, 2);
  }
);

// Angular TestBed setup
beforeEach(() => {
  TestBed.configureTestingModule({
    providers: [
      PortfolioService,
      { provide: HttpClient, useValue: mockHttp }
    ]
  });
  service = TestBed.inject(PortfolioService);
});
```

### Java/JUnit 5

```java
// Mockito setup
@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {
    @Mock
    private PortfolioRepository repository;

    @InjectMocks
    private PortfolioService service;

    @BeforeEach
    void setUp() {
        // Additional setup if needed
    }

    @Nested
    @DisplayName("findById")
    class FindById {
        @Test
        @DisplayName("returns portfolio when found")
        void returnsPortfolioWhenFound() {
            // Arrange
            var expected = new Portfolio("1", "Test");
            when(repository.findById("1")).thenReturn(Optional.of(expected));

            // Act
            var result = service.findById("1");

            // Assert
            assertThat(result).isPresent().contains(expected);
            verify(repository).findById("1");
        }
    }
}

// Parameterized tests
@ParameterizedTest
@CsvSource({
    "100, 150, 0, 50.00, 50.00",
    "100, 80, 0, -20.00, -20.00",
    "100, 100, 10, 10.00, 10.00"
})
@DisplayName("calculates returns correctly")
void calculatesReturns(BigDecimal initial, BigDecimal finalVal,
                       BigDecimal dividends, BigDecimal expectedAbs,
                       BigDecimal expectedPct) {
    var result = service.calculateReturn(initial, finalVal, dividends);

    assertThat(result.absoluteReturn()).isEqualByComparingTo(expectedAbs);
    assertThat(result.percentReturn()).isEqualByComparingTo(expectedPct);
}

// MockMvc for controllers
@WebMvcTest(PortfolioController.class)
class PortfolioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PortfolioService portfolioService;

    @Test
    @DisplayName("GET /api/portfolios/{id} returns 200 when found")
    void getByIdReturns200() throws Exception {
        when(portfolioService.findById("1"))
            .thenReturn(Optional.of(new Portfolio("1", "Test")));

        mockMvc.perform(get("/api/portfolios/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.name").value("Test"));
    }
}
```

## Test Organization Patterns

```
tests/
├── unit/
│   ├── services/           # Service unit tests (mocked deps)
│   ├── utils/              # Pure function tests
│   └── validators/         # Validation logic tests
├── integration/
│   ├── api/                # Controller/endpoint tests
│   └── repositories/       # Database integration tests
└── e2e/
    └── flows/              # End-to-end user flows
```

## Related Patterns

- [api-service-method.md](../code-generation/api-service-method.md) - Code to test
- [data-transformer.md](../code-generation/data-transformer.md) - Transform functions to test
- [input-validation.md](../security/input-validation.md) - Validation logic to test

## Expected Output Quality: 9/10

A well-crafted prompt using this pattern typically produces:
- Comprehensive test coverage across happy/error/edge paths
- Properly isolated tests with clean mocking
- Data-driven tests for boundary conditions
- Framework-appropriate patterns (TestBed, MockMvc)
- Readable test names describing behavior
