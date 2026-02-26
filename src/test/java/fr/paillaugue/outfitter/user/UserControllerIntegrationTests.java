package fr.paillaugue.outfitter.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestTestClient
public class UserControllerIntegrationTests {

    @Test
    void testWithMockMvcTester(@Autowired MockMvcTester mvc) {
        assertThat(mvc.get().uri("/"))
                .hasStatusOk()
                .hasBodyTextEqualTo("OK");
    }

}
