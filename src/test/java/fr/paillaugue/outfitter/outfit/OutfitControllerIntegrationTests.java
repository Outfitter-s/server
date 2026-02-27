package fr.paillaugue.outfitter.outfit;

import fr.paillaugue.outfitter.common.BaseControllerIntegrationTests;
import fr.paillaugue.outfitter.outfit.dto.CreateOutfitDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

public class OutfitControllerIntegrationTests extends BaseControllerIntegrationTests {

    private final String baseUrl = "/outfits";

    @Test
    @DisplayName("401 error is thrown when trying to get outfits without authentication")
    void unauthorizedGetOutfitsWithoutAuth() {
        mockMvcTester.get().uri(baseUrl).assertThat().hasStatus(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("Get the first 20 outfits of the user")
    void getMyOutfits() {
        mockMvcTester.get()
                .uri(baseUrl)
                .with(authenticateToEndpoint.withAuthentication())
                .assertThat()
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .hasPathSatisfying("$.totalElements", path -> path.assertThat().isEqualTo(databaseSeeder.getInitService().getUser1().getOutfits().size()))
                .hasPathSatisfying("$.pageSize", path -> path.assertThat().isEqualTo(20))
                .hasPathSatisfying("$.content.[0].id", path -> path.assertThat().isEqualTo(databaseSeeder.getInitService().getOutfit1().getId().toString()))
                .hasPathSatisfying("$.content.[0].items.length()", path -> path.assertThat().isEqualTo(databaseSeeder.getInitService().getOutfit1().getClothingItems().size()))
                .hasPathSatisfying("$.content.[0].items.[0].id", path -> path.assertThat().isEqualTo(databaseSeeder.getInitService().getClothingItem1().getId().toString()));
    }

    @Test
    @DisplayName("Create an outfit with non-existing clothing item ids")
    void createOutfitWithNonExistingClothingItemIds() {
        mockMvcTester.post()
                .uri(baseUrl)
                .with(authenticateToEndpoint.withAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"clothingItemIds\": [\"" + UUID.randomUUID() + "\", \"" + UUID.randomUUID() + "\"] }")
                .assertThat()
                .hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Create an outfit with empty clothing item id list")
    void createOutfitWithEmptyClothingItemIdList() {
        mockMvcTester.post()
                .uri(baseUrl)
                .with(authenticateToEndpoint.withAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"clothingItemIds\": [] }")
                .assertThat()
                .hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Create an outfit successfully")
    void createAnOutfitSuccessfully() {
        mockMvcTester.post()
                .uri(baseUrl)
                .with(authenticateToEndpoint.withAuthentication())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"clothingItemIds\": [\""+databaseSeeder.getInitService().getClothingItem1().getId()+"\", \""+databaseSeeder.getInitService().getClothingItem2().getId()+"\"] }")
                .assertThat()
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .hasPathSatisfying("$.id", path -> path.assertThat().isNotEmpty())
                .hasPathSatisfying("$.items.length()", path -> path.assertThat().isEqualTo(2));
    }

    @Test
    @DisplayName("Delete a non-existing outfit")
    void deleteNonExistingOutfit() {
        mockMvcTester.delete()
                .uri(baseUrl + "/" + UUID.randomUUID())
                .with(authenticateToEndpoint.withAuthentication())
                .assertThat()
                .hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Delete an outfit that belongs to another user")
    void deleteOutfitThatBelongsToAnotherUser() {
        mockMvcTester.delete()
                .uri(baseUrl + "/" + databaseSeeder.getInitService().getOutfit2().getId())
                .with(authenticateToEndpoint.withAuthentication())
                .assertThat()
                .hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Delete an outfit successfully")
    void deleteOutfitSuccessfully() {
        mockMvcTester.delete()
                .uri(baseUrl + "/" + databaseSeeder.getInitService().getOutfit1().getId())
                .with(authenticateToEndpoint.withAuthentication())
                .assertThat()
                .hasStatus(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Get a non-existing outfit")
    void getNonExistingOutfit() {
        mockMvcTester.get()
                .uri(baseUrl + "/" + UUID.randomUUID())
                .with(authenticateToEndpoint.withAuthentication())
                .assertThat()
                .hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Get an outfit that belongs to another user")
    void getOutfitThatBelongsToAnotherUser() {
        mockMvcTester.get()
                .uri(baseUrl + "/" + databaseSeeder.getInitService().getOutfit2().getId())
                .with(authenticateToEndpoint.withAuthentication())
                .assertThat()
                .hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Get an outfit successfully")
    void getOutfitSuccessfully() {
        mockMvcTester.get()
                .uri(baseUrl + "/" + databaseSeeder.getInitService().getOutfit1().getId())
                .with(authenticateToEndpoint.withAuthentication())
                .assertThat()
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .hasPathSatisfying("$.id", path -> path.assertThat().isEqualTo(databaseSeeder.getInitService().getOutfit1().getId().toString()))
                .hasPathSatisfying("$.items.length()", path -> path.assertThat().isEqualTo(databaseSeeder.getInitService().getOutfit1().getClothingItems().size()))
                .hasPathSatisfying("$.items.[0].id", path -> path.assertThat().isEqualTo(databaseSeeder.getInitService().getClothingItem1().getId().toString()));
    }

}
