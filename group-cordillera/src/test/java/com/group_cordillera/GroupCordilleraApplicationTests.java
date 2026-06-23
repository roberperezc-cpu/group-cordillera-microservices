package com.group_cordillera;

import com.group_cordillera.model.Venta;
import com.group_cordillera.model.DetalleVenta;
import com.group_cordillera.model.CanalVenta; // Importamos tu Enum de canales
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal; // Importamos BigDecimal para los montos financieros
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

@SpringBootTest
class GroupCordilleraApplicationTests {

	@Test
	void contextLoads() {
		// Verifica que el microservicio cargue bien
	}

	@Test
	void testCreacionVentaConDatafaker() {
		Faker faker = new Faker(new Locale("es"));

		// 1. Generamos los datos aleatorios
		String codigoBoletaFalso = "BOL-" + faker.number().numberBetween(100000, 999999);
		String sucursalFalsa = faker.commerce().department() + " " + faker.address().cityName();

		// Seleccionamos un valor real de tu Enum CanalVenta de forma aleatoria
		CanalVenta canalFalso = faker.options().option(CanalVenta.values());

		// Creamos el BigDecimal para el total falsificado
		BigDecimal totalFalso = BigDecimal.valueOf(faker.number().randomDouble(2, 5000, 500000));

		// 2. Instanciamos e inyectamos
		Venta venta = new Venta();
		venta.setId(1L);
		venta.setCodigoBoleta(codigoBoletaFalso);
		venta.setSucursal(sucursalFalsa);
		venta.setCanal(canalFalso); // Ahora sí recibe el Enum correcto
		venta.setTotal(totalFalso); // Ahora sí recibe el BigDecimal
		venta.setFechaVenta(LocalDateTime.now());
		venta.setDetalles(new ArrayList<>());

		// 3. Agregamos el detalle adaptado a BigDecimal también
		DetalleVenta detalle = new DetalleVenta();
		detalle.setId(101L);
		detalle.setProductoId((long) faker.number().numberBetween(1, 500));
		detalle.setCantidad(faker.number().numberBetween(1, 5));

		BigDecimal precioFalso = BigDecimal.valueOf(faker.number().randomDouble(2, 1000, 100000));
		detalle.setPrecioUnitario(precioFalso); // Ahora sí recibe el BigDecimal

		venta.getDetalles().add(detalle);

		// Imprimimos en consola el resultado
		System.out.println("========== DATOS FALSOS GENERADOS POR DATAFAKER ==========");
		System.out.println("Boleta: " + venta.getCodigoBoleta());
		System.out.println("Sucursal de Grupo Cordillera: " + venta.getSucursal());
		System.out.println("Canal de Venta: " + venta.getCanal());
		System.out.println("Monto Total: $" + venta.getTotal());
		System.out.println("Producto ID mockeado: " + detalle.getProductoId());
		System.out.println("==========================================================");

		// 4. Verificaciones
		Assertions.assertNotNull(venta);
		Assertions.assertEquals(codigoBoletaFalso, venta.getCodigoBoleta());
		Assertions.assertFalse(venta.getDetalles().isEmpty());
	}
}