package anfp.equipos.test;

import static org.junit.Assert.*;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import anfp.equipos.modelo.Equipo;
import anfp.equipos.modelo.dao.IEquipoDAO;


@RunWith(SpringRunner.class)
@DataJpaTest
public class EquiposTests {

	@Autowired
	TestEntityManager entityManager;
	@Autowired
	IEquipoDAO dao;
	
	@Before
	public void setUp() throws Exception {
		Equipo equipo = new Equipo("Cobreloa", 38, "Calama", "Fantasma Figueroa");
		this.entityManager.persist(equipo);
		equipo = new Equipo("Palestino", 25, "Santiago", "Peineta garcés");
		this.entityManager.persist(equipo);
		equipo = new Equipo("Naval", 41, "Talcahuano", "Patricio Almendra");
		this.entityManager.persist(equipo);
		equipo = new Equipo("Concepción", 25, "Concepción", "Estéban González");
		this.entityManager.persist(equipo);
		equipo = new Equipo("Coquimbo", 25, "Coquimbo", "Patricio Graff");
		this.entityManager.persist(equipo);
	}
	
	@Test
	public void cuandoFindAllEntonces5() {
		int cuantos = this.dao.findAll().size();
		assertTrue("SON " + cuantos + " PERO DEBERÍAN SER 5", cuantos == 5);
	}
	
	@Test
	public void cuandoFindByIdEntoncesTieneTodosSusDatos() {
		Equipo cobreloa = this.dao.findById("Cobreloa").get();
		Equipo cobreloaOriginal = new Equipo("Cobreloa", 38, "Calama", "Fantasma Figueroa");
		boolean sonIguales = cobreloa.equals(cobreloaOriginal);
		assertTrue(sonIguales);
	}
	
	@Test
	public void cuandoFindByPuntos25() {
			SoftAssertions todas = new SoftAssertions();
			List<Equipo> lista25 =this.dao.findByPuntos(25);
			int cantidad = lista25.size();
			System.out.println(lista25.get(0).getNombre()+","+lista25.get(1).getNombre()+","+lista25.get(2).getNombre());
			//en caso que sean muchos datos
			String nombres[]= {"Palestino","Concepción","Coquimbo"};
			for(int i=0;i<lista25.size();i++) {
				
			//assertTrue(lista25.get(i).getNombre().equals(nombres[i]));
			todas.assertThat(lista25.get(i).getNombre().equals(nombres[i]));	
			}
			
			
			System.out.println("la cantidad de equipos con 25 puntos es: "+cantidad);
			assertTrue("SON " + cantidad + " PERO DEBERÍAN SER 3", cantidad==3);		
			assertTrue(lista25.get(0).getNombre().equalsIgnoreCase("Palestino"));
			assertTrue(lista25.get(1).getNombre().equalsIgnoreCase("Concepción"));
			assertTrue(lista25.get(2).getNombre().equalsIgnoreCase("Coquimbo"));
			
	}
	
	
	@Test
	public void cuandoEliminoRegistroEntonces4() {
		
		this.dao.deleteById("Cobreloa");
		int cuantos = this.dao.findAll().size();
		System.out.println((this.dao.findAll().size()));
		assertTrue("SON " + cuantos + " PERO DEBERÍAN SER 4", cuantos == 4);
		
		
	}
	
	@Test
	public void cuandoInsertarRegistroEntonces6() {
		Equipo equipo = new Equipo("Deportes Temuco", 18, "Temuco", "Bomvalet");
		this.dao.save(equipo);
		int cuantos = this.dao.findAll().size();
		System.out.println("la cantidad despues de insertar es: "+this.dao.findAll().size());
		assertTrue("SON " + cuantos + " PERO DEBERÍAN SER 6", cuantos == 6);
		
	}
	
	@Test
	public void modificarNombreCobreloaNoNull() {
		
		Equipo modificado = this.dao.findById("Cobreloa").get();
		modificado.setNombre("Cobresal");
		this.dao.save(modificado);
		assertNotNull(this.dao.findById("Cobresal"));
		
		
	}
	
	public void cuandoModificarNombreDTSeObtieneModificar() {
		
		this.dao.save(new Equipo("Cobreloa", 38, "Calama", "Riveros"));
		Equipo cobreloa =  this.dao.findById("Cobreloa").get();
		assertNotNull(cobreloa);
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
