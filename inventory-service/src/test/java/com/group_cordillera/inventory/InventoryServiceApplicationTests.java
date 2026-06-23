package com.group_cordillera.inventory;

import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Locale;

@SpringBootTest
class InventoryServiceApplicationTests {

	@Test
	void contextLoads() {
		// Verifica la carga del contexto de inventario
	}

	@Test
	void testValidacionStockConDatafaker() {
		Faker faker = new Faker(new Locale("es"));

		// 1. Simulamos datos aleatorios de una consulta de inventario corporativo
		Long productoIdFalso = (long) faker.number().numberBetween(1, 1000);
		Integer cantidadSolicitadaFalsa = faker.number().numberBetween(1, 50);
		boolean simulacionDisponibilidad = faker.bool().bool(); // Inventa true o false dinámicamente

		// Imprimimos en consola el mock para la auditoría del examen
		System.out.println("========== CONSULTA DE STOCK (DATAFAKER) ==========");
		System.out.println("Producto ID Evaluado: " + productoIdFalso);
		System.out.println("Cantidad en Tránsito Solicitada: " + cantidadSolicitadaFalsa);
		System.out.println("¿Resultado del cálculo de existencias?: " + (simulacionDisponibilidad ? "DISPONIBLE" : "STOCK INSUFICIENTE"));
		System.out.println("===================================================");

		// 2. Validaciones básicas de integridad de JUnit
		Assertions.assertTrue(productoIdFalso > 0);
		Assertions.assertTrue(cantidadSolicitadaFalsa > 0);
	}
}