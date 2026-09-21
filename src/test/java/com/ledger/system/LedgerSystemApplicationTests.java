package com.ledger.system;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LedgerSystemApplicationTests {

	public static Logger logger = LoggerFactory.getLogger(LedgerSystemApplicationTests.class);

	@Test
	public void contextLoads() {
		logger.info("Test case execution started with Assad..");
		Assertions.assertTrue(true);
	}

}
