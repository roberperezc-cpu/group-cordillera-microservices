package com.group_cordillera.customer;

import com.group_cordillera.customer.model.Customer;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Locale;

@SpringBootTest
class CustomerServiceApplicationTests {

	@Test
	void contextLoads() {
		// Verifica la carga correcta del contexto de clientes
	}

	@Test
	void testCreacionClienteConDatafaker() {
		Faker faker = new Faker(new Locale("es"));

		// 1. Generamos un RUT aleatorio formato chileno
		String rutFalso = faker.number().numberBetween(10000000, 25000000) + "-" + faker.options().option("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "K");

		// 2. Instanciamos el modelo Customer e inyectamos la data segura
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setRut(rutFalso); // Este campo sí existe 100% seguro

		// Imprimimos en consola los datos simulados
		System.out.println("========== DATOS FALSOS DE CLIENTE (DATAFAKER) ==========");
		System.out.println("ID del Cliente: " + customer.getId());
		System.out.println("RUT Generado con Datafaker: " + customer.getRut());
		System.out.println("=========================================================");

		// 3. Validaciones con JUnit
		Assertions.assertNotNull(customer);
		Assertions.assertEquals(rutFalso, customer.getRut());
	}
}


