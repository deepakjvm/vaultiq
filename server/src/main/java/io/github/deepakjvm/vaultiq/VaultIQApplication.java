package io.github.deepakjvm.vaultiq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching // Add this annotation to enable caching support
@EnableAsync
public class VaultIQApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaultIQApplication.class, args);
	}

}
