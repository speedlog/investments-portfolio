package pl.speedlog.investments;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
class WalletApplicationTests {

	@Container
	private static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.2.12"));

	@DynamicPropertySource
	static void mongoDbProperties(DynamicPropertyRegistry registry) {
		String test = mongoDBContainer.getReplicaSetUrl();
		registry.add("spring.data.mongodb.host", () -> mongoDBContainer.getHost());
		registry.add("spring.data.mongodb.port", () -> mongoDBContainer.getFirstMappedPort());
		registry.add("spring.data.mongodb.database", () -> "test");
		registry.add("spring.data.mongodb.username", () -> null);
		registry.add("spring.data.mongodb.password", () -> null);
	}

	@Test
	void contextLoads() {
		//assertNotNull(mongoDBContainer.getReplicaSetUrl());
	}

}
