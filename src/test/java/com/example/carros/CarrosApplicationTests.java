package com.example.carros;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDTO;
import com.example.carros.domain.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CarrosServiceTest {

	@Autowired
	private CarroService service;

	@Test
	void testInsert() {
		Carro carro = new Carro();
		carro.setNome("Palio");
		carro.setTipo("Utilitário");

		CarroDTO c = service.insert(carro);

		assertNotNull(c);

		Long id = c.getId();
		assertNotNull(id);

		//Buscar o objeto
		c = service.getCarroById(id);
		assertNotNull(c);

		//Validar atributos
		assertEquals("Palio", c.getNome());
		assertEquals("Utilitário", c.getTipo());

		//Deletar o objeto teste
		service.delete(id);

		//Verificar se deletou
		try {
			assertNotNull(service.getCarroById(id));
			fail("O carro não foi encontrado");
		}catch (ObjectNotFoundException e){
			//OK
		}
	}

	@Test
	void testList(){
		List<CarroDTO> carros = service.getCarros();
		assertEquals(30, carros.size());
	}

	@Test
	void testListCarType(){
		List<CarroDTO> carros = service.getCarros();
		assertEquals(10, service.getCarroByTipo("esportivos").size());
		assertEquals(10, service.getCarroByTipo("luxo").size());
		assertEquals(10, service.getCarroByTipo("classicos").size());
		assertEquals(0, service.getCarroByTipo("utilitário").size());
	}

	@Test
	void testGet(){
		CarroDTO c = service.getCarroById(11L);
		assertNotNull(c);
		assertEquals("Ferrari FF", c.getNome());
	}

}
