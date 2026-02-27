package fr.paillaugue.outfitter.user;

import fr.paillaugue.outfitter.common.BaseControllerIntegrationTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class UserControllerIntegrationTests extends BaseControllerIntegrationTests {

    private final String baseUrl = "/users";

    @Test
    @DisplayName("401 error is thrown when trying to get the current user without authentication")
    void unauthorizedMeEndpointWithoutAuth() {
        mockMvcTester.get().uri(baseUrl+"/me").assertThat().hasStatus(HttpStatus.UNAUTHORIZED).bodyJson().extractingPath("$.status").asNumber().isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("200 is returned when trying to get the current user with authentication")
    void authorizedMeEndpointWithAuth() {
        mockMvcTester.get()
                .uri(baseUrl + "/me")
                .with(authenticateToEndpoint.withAuthentication())
                .assertThat()
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .extractingPath("$.username")
                .asString()
                .isEqualTo("Angus");
    }

    @Test
    @DisplayName("Cannot register with missing fields")
    void registerWithMissingFields() {
        mockMvcTester.post().uri(baseUrl+"/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .assertThat()
                .hasStatus(HttpStatus.BAD_REQUEST);
        mockMvcTester.post().uri(baseUrl+"/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"test\", \"email}\": \"test@test.com\"}")
                .assertThat()
                .hasStatus(HttpStatus.BAD_REQUEST);
        mockMvcTester.post().uri(baseUrl+"/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\": \"test\", \"username}\": \"test\"}")
                .assertThat()
                .hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Cannot register with one or more blank fields")
    void registerWithBlankFields() {
        mockMvcTester.post().uri(baseUrl+"/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"   \"}")
                .assertThat()
                .hasStatus(HttpStatus.BAD_REQUEST);
        mockMvcTester.post().uri(baseUrl+"/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"   \", \"password\": \"   \", \"email\": \"   \"}")
                .assertThat()
                .hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Cannot register with an already used username")
    void registerWithAlreadyUsedUsername() {
        String username = "Angus";
        String password = "newpassword";
        String email = "angus@angus.fr";
        String requestBody = String.format("{\"username\": \"%s\", \"password\": \"%s\", \"email\": \"%s\"}", username, password, email);
        mockMvcTester.post().uri(baseUrl + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .assertThat()
                .hasStatus(HttpStatus.CONFLICT);
    }
    @Test
    @DisplayName("Cannot register with an already used email")
    void registerWithAlreadyUsedEmail() {
        String username = "NewAngus";
        String password = "newpassword";
        String email = "angus@paillaugue.fr";
        String requestBody = String.format("{\"username\": \"%s\", \"password\": \"%s\", \"email\": \"%s\"}", username, password, email);
        mockMvcTester.post().uri(baseUrl + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .assertThat()
                .hasStatus(HttpStatus.CONFLICT);
    }

    @Test
    @DisplayName("Can register with valid fields")
    void registerWithValidFields() {
        String username = "Maxence";
        String password = "newpassword";
        String email = "maxence@beth.fr";
        String requestBody = String.format("{\"username\": \"%s\", \"password\": \"%s\", \"email\": \"%s\"}", username, password, email);
        mockMvcTester.post().uri(baseUrl + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .assertThat()
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .extractingPath("$.username")
                .asString()
                .isEqualTo(username);
    }

}
