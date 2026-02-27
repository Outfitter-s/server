package fr.paillaugue.outfitter.clothingItem;

import fr.paillaugue.outfitter.common.BaseControllerIntegrationTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ClothingItemControllerIntegrationTests extends BaseControllerIntegrationTests {

    private final String baseUrl = "/clothingItems";

    @Test
    @DisplayName("401 error is thrown when trying to get clothing items without authentication")
    void unauthorizedListEndpointWithoutAuth() {
        mockMvcTester.get()
                .uri(baseUrl)
                .assertThat()
                .hasStatus(HttpStatus.UNAUTHORIZED)
                .bodyJson()
                .extractingPath("$.status")
                .asNumber()
                .isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("401 error is thrown when trying to get a single clothing item without authentication")
    void unauthorizedGetSingleItemWithoutAuth() {
        mockMvcTester.get()
                .uri(baseUrl + "/00000000-0000-0000-0000-000000000000")
                .assertThat()
                .hasStatus(HttpStatus.UNAUTHORIZED)
                .bodyJson()
                .extractingPath("$.status")
                .asNumber()
                .isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("200 is returned when trying to get clothing items with authentication")
    void authorizedListEndpointWithAuth() {
        mockMvcTester.get()
                .uri(baseUrl)
                .with(authenticateToEndpoint.withAuthentication())
                .assertThat()
                .hasStatus(HttpStatus.OK);
    }

    @Test
    @DisplayName("200 and clothing item is returned when trying to get a single clothing item with authentication")
    void getSingleItem() {
        var item = databaseSeeder.getInitService().getClothingItem1();
        mockMvcTester.get()
                .uri(baseUrl +"/"+ item.getId())
                .with(authenticateToEndpoint.withAuthentication())
                .assertThat()
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .extractingPath("$.id")
                .asString()
                .isEqualTo(item.getId().toString());
    }

    @Test
    @DisplayName("Cannot create clothing item with missing fields")
    void createWithMissingFields() {
        mockMvcTester.post()
                .uri(baseUrl)
                .with(authenticateToEndpoint.withAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .assertThat()
                .hasStatus(HttpStatus.BAD_REQUEST);

        mockMvcTester.post()
                .uri(baseUrl)
                .with(authenticateToEndpoint.withAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Jacket\", \"type\": }")
                .assertThat()
                .hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Cannot create clothing item with blank fields")
    void createWithBlankFields() {
        mockMvcTester.post()
                .uri(baseUrl)
                .with(authenticateToEndpoint.withAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"   \"}")
                .assertThat()
                .hasStatus(HttpStatus.BAD_REQUEST);

        mockMvcTester.post()
                .uri(baseUrl)
                .with(authenticateToEndpoint.withAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"   \", \"type\": \"   \", \"color\": \"   \"}")
                .assertThat()
                .hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Can create clothing item with valid fields")
    void createWithValidFields() {
        String name = "Blue Jacket";
        String type = "JACKET";
        String color = "BLUE";
        String description = "A warm blue jacket";
        String requestBody = String.format("{\"name\": \"%s\", \"type\": \"%s\", \"color\": \"%s\", \"description\": \"%s\"}",
                name, type, color, description);

        mockMvcTester.post()
                .uri(baseUrl)
                .with(authenticateToEndpoint.withAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .assertThat()
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .extractingPath("$.name")
                .asString()
                .isEqualTo(name);
    }

    @Test
    @DisplayName("Cannot delete non existing clothing item")
    void deleteNonExistingItem() {
        mockMvcTester.delete()
                .uri(baseUrl + "/00000000-0000-0000-0000-000000000000")
                .with(authenticateToEndpoint.withAuthentication())
                .assertThat()
                .hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Cannot delete item from another user")
    void deleteItemFromAnotherUser() {
        var item = databaseSeeder.getInitService().getClothingItem1();
        mockMvcTester.delete()
                .uri(baseUrl + "/" + item.getId())
                .with(authenticateToEndpoint.withAuthentication(authenticateToEndpoint.asUser2()))
                .assertThat()
                .hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Can delete existing clothing item")
    void deleteExistingItem() {
        var item = databaseSeeder.getInitService().getClothingItem1();
        mockMvcTester.delete()
                .uri(baseUrl + "/" + item.getId())
                .with(authenticateToEndpoint.withAuthentication())
                .assertThat()
                .hasStatus(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Cannot update non existing clothing item")
    void updateNonExistingItem() {
        String requestBody = "{\"name\": \"Updated Name\"}";
        mockMvcTester.patch()
                .uri(baseUrl + "/00000000-0000-0000-0000-000000000000")
                .with(authenticateToEndpoint.withAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .assertThat()
                .hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Cannot update item from another user")
    void updateItemFromAnotherUser() {
        var item = databaseSeeder.getInitService().getClothingItem1();
        String requestBody = "{\"name\": \"Updated Name\"}";
        mockMvcTester.patch()
                .uri(baseUrl + "/" + item.getId())
                .with(authenticateToEndpoint.withAuthentication(authenticateToEndpoint.asUser2()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .assertThat()
                .hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Can update existing clothing item with valid fields")
    void updateExistingItem() {
        var item = databaseSeeder.getInitService().getClothingItem1();
        String newName = "Updated Name";
        String requestBody = String.format("{\"name\": \"%s\"}", newName);
        mockMvcTester.patch()
                .uri(baseUrl + "/" + item.getId())
                .with(authenticateToEndpoint.withAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .assertThat()
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .extractingPath("$.name")
                .asString()
                .isEqualTo(newName);
    }

    @Test
    @DisplayName("Can update existing clothing item with valid fields and null optional fields")
    void updateExistingItemWithNullOptionalFields() {
        var item = databaseSeeder.getInitService().getClothingItem1();
        String newName = "Updated Name";
        String requestBody = String.format("{\"name\": \"%s\", \"type\": null, \"color\": null, \"description\": null}", newName);
        mockMvcTester.patch()
                .uri(baseUrl + "/" + item.getId())
                .with(authenticateToEndpoint.withAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .assertThat()
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .extractingPath("$.name")
                .asString()
                .isEqualTo(newName);
    }

}

