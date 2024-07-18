package com.jorge.aneury.proyecto_final_QA;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;
import java.util.function.BooleanSupplier;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlaywrightTests {

    static Playwright playwright;
    static Browser browser;

    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser =  playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
//        context.tracing().start(new Tracing.StartOptions()
//                .setScreenshots(true)
//                .setSnapshots(true)
//                .setSources(true));

        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
//        context.tracing().stop(new Tracing.StopOptions()
//                .setPath(Paths.get("trace.zip")));
        context.close();
    }

    @Test
    void testLogin() {
        page.navigate("http://localhost:8080/login");
        assertThat(page).hasTitle(Pattern.compile("Please sign in"));

        // Asegurar que los inputs y botón están visibles
        assertThat(page.locator("input[name='username']")).isVisible();
        assertThat(page.locator("input[name='password']")).isVisible();
        assertThat(page.locator("button[type=submit]")).isVisible();

        // Llenar los campos de nombre de usuario y contraseña
        page.fill("input[name=username]", "admin");
        page.fill("input[name=password]", "admin");

        // Hacer clic en el botón de inicio de sesión
        page.click("button[type=submit]");

        // Asegurar que se redirigió a la página principal de la app
        assertEquals("http://localhost:8080/", page.url());
    }

    @Test
    void testFailLogin() {
        page.navigate("http://localhost:8080/login");
        assertThat(page).hasTitle(Pattern.compile("Please sign in"));

        // Asegurar que los inputs y botón están visibles
        assertThat(page.locator("input[name='username']")).isVisible();
        assertThat(page.locator("input[name='password']")).isVisible();
        assertThat(page.locator("button[type=submit]")).isVisible();

        // Llenar los campos de nombre de usuario y contraseña
        page.fill("input[name=username]", "admin");
        page.fill("input[name=password]", "password");

        // Hacer clic en el botón de inicio de sesión
        page.click("button[type=submit]");

        // Asegurar que se redirigió a la página principal de la app
        Locator alert = page.locator("div.alert.alert-danger[role=alert]");
        assertTrue(alert.isVisible(), "The 'Bad credentials' alert should be visible");
        assertTrue(alert.textContent().contains("Bad credentials"), "The alert text should be 'Bad credentials'");
    }

    @Test
    void testLoginAndLogout() {
        // Go to the login page
        page.navigate("http://localhost:8080/login");

        // Fill in the username and password with correct credentials
        page.fill("input[name=username]", "admin");
        page.fill("input[name=password]", "admin");

        // Click the login button
        page.click("button[type=submit]");

        // Check if the logout link is visible
        Locator logoutLink = page.locator("a.link-danger[href='/logout']");
        assertTrue(logoutLink.isVisible(), "The logout link should be visible");

        // Click the logout link
        logoutLink.click();

        // Verify that the login page is shown again
        Locator loginForm = page.locator("form.form-signin");
        assertTrue(loginForm.isVisible(), "The login form should be visible after logging out");
    }

    @Test
    void testCreateProduct() {
        // Go to the login page and login with valid credentials
        page.navigate("http://localhost:8080/login");
        page.fill("input[name=username]", "admin");
        page.fill("input[name=password]", "admin");
        page.click("button[type=submit]");

        // Open the create product modal
        page.click("button[data-bs-target='#createProductModal']");

        // Fill out the form in the modal
        page.fill("input#nombre", "Test Product");
        page.fill("input#precio", "10.99");
        page.fill("input#cantidad", "100");
        page.fill("textarea#descripcion", "This is a test product.");
        page.fill("input#categoria", "Test Category");
        page.fill("input#cantidadMinima", "1");

        // Submit the form
        page.click("form#createProductForm button[type=submit]");

        assertEquals("http://localhost:8080/", page.url());
    }

    @Test
    void testCreateEmptyProduct() {
        // Go to the login page and login with valid credentials
        page.navigate("http://localhost:8080/login");
        page.fill("input[name=username]", "admin");
        page.fill("input[name=password]", "admin");
        page.click("button[type=submit]");

        // Open the create product modal
        page.click("button[data-bs-target='#createProductModal']");

        // Submit the form
        page.click("form#createProductForm button[type=submit]");

        assertThat(page.locator("input#nombre ~ .invalid-feedback")).isVisible();
        assertThat(page.locator("input#precio ~ .invalid-feedback")).isVisible();
        assertThat(page.locator("input#cantidad ~ .invalid-feedback")).isVisible();
        assertThat(page.locator("input#categoria ~ .invalid-feedback")).isVisible();
        assertThat(page.locator("input#cantidadMinima ~ .invalid-feedback")).isVisible();
        assertThat(page.locator("textarea#descripcion ~ .invalid-feedback")).isVisible();
    }

    @Test
    void testCreateProductWithNegativePrice() {
        // Go to the login page and login with valid credentials
        page.navigate("http://localhost:8080/login");
        page.fill("input[name=username]", "admin");
        page.fill("input[name=password]", "admin");
        page.click("button[type=submit]");

        // Open the create product modal
        page.click("button[data-bs-target='#createProductModal']");

        // Fill out the form in the modal with a negative price
        page.fill("input#nombre", "Test Product Negative Price");
        page.fill("input#precio", "-10.99");
        page.fill("input#cantidad", "100");
        page.fill("textarea#descripcion", "This is a test product with a negative price.");
        page.fill("input#categoria", "Test Category");
        page.fill("input#cantidadMinima", "1");

        // Try to submit the form
        page.click("form#createProductForm button[type=submit]");

        assertThat(page.locator("input#precio ~ .invalid-feedback")).isVisible();
    }

    @Test
    void testCreateProductWithNegativeQuantity() {
        // Go to the login page and login with valid credentials
        page.navigate("http://localhost:8080/login");
        page.fill("input[name=username]", "admin");
        page.fill("input[name=password]", "admin");
        page.click("button[type=submit]");

        // Open the create product modal
        page.click("button[data-bs-target='#createProductModal']");

        // Fill out the form in the modal with a negative price
        page.fill("input#nombre", "Test Product Negative Price");
        page.fill("input#precio", "10.99");
        page.fill("input#cantidad", "-100");
        page.fill("textarea#descripcion", "This is a test product with a negative price.");
        page.fill("input#categoria", "Test Category");
        page.fill("input#cantidadMinima", "1");

        // Try to submit the form
        page.click("form#createProductForm button[type=submit]");

        assertThat(page.locator("input#cantidad ~ .invalid-feedback")).isVisible();
    }

    @Test
    void testUpdateProduct() {
        // Go to the login page and login with valid credentials
        page.navigate("http://localhost:8080/login");
        page.fill("input[name=username]", "admin");
        page.fill("input[name=password]", "admin");
        page.click("button[type=submit]");

        // Open the create product modal
        page.click("button[data-bs-target='#modifyProductModal-1']");

        assertThat(page.locator("#modifyProductModal-1")).isVisible();

        // Fill out the form in the modal with a negative price
        page.fill("input#modifyNombre1", "Product Updated");

        // Try to submit the form
        page.click("#submit1");

        assertThat(page.locator("table#productsTable")).containsText("Product Updated");
    }

    @Test
    public void testDeleteProduct() {
        // Go to the login page and log in with valid credentials
        page.navigate("http://localhost:8080/login");
        page.fill("input[name=username]", "admin");
        page.fill("input[name=password]", "admin");
        page.click("button[type=submit]");

        // Open the confirm delete modal
        page.click("button[data-bs-toggle='modal'][data-bs-target='#confirmDeleteModal-1']");

        // Click the delete button in the modal
        page.click("button#confirmDeleteButton");

        // Wait for the product to be removed from the table
        page.waitForSelector("table#productsTable");

        // Verify that the product has been removed
        assertTrue(page.locator("table#productsTable").locator("tr").allTextContents().stream()
                .noneMatch(text -> text.contains("Product Name")));
    }

}
