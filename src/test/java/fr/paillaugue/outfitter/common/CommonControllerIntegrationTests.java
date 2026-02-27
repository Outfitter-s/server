package fr.paillaugue.outfitter.common;

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
public class CommonControllerIntegrationTests {

    private final MockMvcTester mvc;

    public CommonControllerIntegrationTests(@Autowired MockMvcTester mvc) {
        this.mvc = mvc;
    }

    @Test
    void testWithMockMvcTester() {
        assertThat(mvc.get().uri("/"))
                .hasStatusOk()
                .hasBodyTextEqualTo("OK");
    }

}
