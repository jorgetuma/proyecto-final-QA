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
        // Login as admin
        loginAsAdmin();

        // Open the create product modal
        page.click("button[data-bs-target='#createProductModal']");
        assertThat(page.locator("#createProductModal")).isVisible();

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
        // Login as admin
        loginAsAdmin();

        // Open the create product modal
        page.click("button[data-bs-target='#createProductModal']");
        assertThat(page.locator("#createProductModal")).isVisible();

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
        // Login as admin
        loginAsAdmin();

        // Open the create product modal
        page.click("button[data-bs-target='#createProductModal']");
        assertThat(page.locator("#createProductModal")).isVisible();

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
        // Login as admin
        loginAsAdmin();

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
        // Login as admin
        loginAsAdmin();

        // Assert that update and delete icons are visible
        assertThat(page.locator("button[data-bs-target^='#modifyProductModal-']").first()).isVisible();

        // Open the modify product modal
        page.locator("button[data-bs-target^='#modifyProductModal-']").first().click();

        assertThat(page.locator("[id^='modifyProductModal-']").first()).isVisible();

        // Fill out the form in the modal with updated product name
        page.locator("[id^='modifyProductModal-'] input[id^='modifyNombre']").first().fill("Product Updated");

        // Submit the form
        page.locator("[id^='modifyProductModal-'] button[type='submit']").first().click();

        // Wait for the table to update
        page.waitForSelector("table#productsTable:has-text('Product Updated')");

        assertThat(page.locator("table#productsTable")).containsText("Product Updated");
    }

    @Test
    public void testDeleteProduct() {
        // Login as admin
        loginAsAdmin();

        assertEquals("http://localhost:8080/", page.url());

        // Open the confirm delete modal
        page.click("button[data-bs-toggle='modal'][data-bs-target^='#confirmDeleteModal-']");

        // Click the delete button in the modal
        page.click("button#confirmDeleteButton");

        // Wait for the product to be removed from the table
        page.waitForSelector("table#productsTable");

        // Verify that the product has been removed
        assertTrue(page.locator("table#productsTable").locator("tr").allTextContents().stream()
                .noneMatch(text -> text.contains("Product Name")));
    }

    @Test
    void testCreateUsuario() {
        // Login as admin
        loginAsAdmin();

        page.click("a[href='/usuarios']");
        assertEquals("http://localhost:8080/usuarios", page.url());

        // Open the create user modal
        page.click("button[data-bs-target='#createUserModal']");
        assertThat(page.locator("#createUserModal")).isVisible();

        // Fill out the form in the modal
        page.fill("input#username", "newuser");
        page.fill("input#password", "password123");
        page.selectOption("select#role", "ROLE_USER");

        // Submit the form
        page.click("form#createUserForm button[type=submit]");

        // Assert that the new user is in the table
        assertThat(page.locator("table#usersTable")).containsText("newuser");
    }

    @Test
    void testCreateEmptyUsuario() {
        loginAsAdmin();

        page.click("a[href='/usuarios']");
        assertEquals("http://localhost:8080/usuarios", page.url());

        // Open the create user modal
        page.click("button[data-bs-target='#createUserModal']");
        assertThat(page.locator("#createUserModal")).isVisible();

        // Submit the form without filling it
        page.click("form#createUserForm button[type=submit]");

        // Assert that error messages are visible
        assertThat(page.locator("input#username ~ .invalid-feedback")).isVisible();
        assertThat(page.locator("input#password ~ .invalid-feedback")).isVisible();
        assertThat(page.locator("select#role ~ .invalid-feedback")).isVisible();
    }

    @Test
    void testUpdateUsuario() {
        loginAsAdmin();

        page.click("a[href='/usuarios']");
        assertEquals("http://localhost:8080/usuarios", page.url());

        // Open the modify user modal for the first user
        page.locator("button[data-bs-target^='#modifyUserModal-']").last().click();
        assertThat(page.locator("[id^='modifyUserModal-']").last()).isVisible();

        // Update the username
        page.locator("[id^='modifyUserModal-'] input[id^='modifyUsername']").last().fill("updateduser");

        // Submit the form
        page.locator("[id^='modifyUserModal-'] button[type='submit']").last().click();

        // Assert that the updated username is in the table
        assertThat(page.locator("table#usersTable")).containsText("updateduser");
    }

    @Test
    void testDeleteUsuario() {
        loginAsAdmin();

        page.click("a[href='/usuarios']");
        assertEquals("http://localhost:8080/usuarios", page.url());

        // Get the username of the last user in the table
        Locator lastUserRow = page.locator("table#usersTable tr").last();
        String lastUsername = lastUserRow.locator("td").first().textContent();

        // Open the confirm delete modal for the last user
        lastUserRow.locator("button[data-bs-toggle='modal'][data-bs-target^='#confirmDeleteUserModal-']").click();

        // Click the delete button in the modal
        page.locator("button#confirmDeleteUserButton").last().click();

        // Wait for the user to be removed from the table
        page.waitForSelector("table#usersTable");

        // Verify that the user has been removed
        assertThat(page.locator("table#usersTable")).not().containsText(lastUsername);
    }

    private void loginAsAdmin() {
        page.navigate("http://localhost:8080/login");
        page.fill("input[name=username]", "admin");
        page.fill("input[name=password]", "admin");
        page.click("button[type=submit]");
        assertEquals("http://localhost:8080/", page.url());
    }

}
