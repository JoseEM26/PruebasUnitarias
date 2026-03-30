package edu.pe.cibertec.infracciones;

import edu.pe.cibertec.infracciones.config.DotenvConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InfraccionesApplicationTests {

	static {
		new DotenvConfig();
	}

	@Test
	void contextLoads() {
	}

}
