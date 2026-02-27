package fr.paillaugue.outfitter.common;

import fr.paillaugue.outfitter.bootstrap.DatabaseSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class BaseControllerIntegrationTests {

    @Autowired
    protected MockMvcTester mockMvcTester;
    @Autowired
    protected AuthenticateToEndpoint authenticateToEndpoint;
    @Autowired
    protected DatabaseSeeder databaseSeeder;

}
