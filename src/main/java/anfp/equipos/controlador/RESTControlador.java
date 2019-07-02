package anfp.equipos.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import anfp.equipos.modelo.Equipo;
import anfp.equipos.modelo.dao.IEquipoDAO;

@RestController
public class RESTControlador {
	
	@Autowired
	IEquipoDAO equipoDao;
	
	@GetMapping("/equipos/{nombre}")
	public Equipo getEquipo(@PathVariable String nombre) {
		return this.equipoDao.findById(nombre).orElse(new Equipo());
	}
	
	
	@GetMapping("/equipos")
	public List<Equipo> getEquipos() {
		return this.equipoDao.findAll();
	}
	@DeleteMapping("/equipos/{nombre}")
	public boolean delEquipo(@PathVariable String nombre) {
		if(this.equipoDao.existsById(nombre)) {
			this.equipoDao.deleteById(nombre);
			return !this.equipoDao.existsById(nombre);
		}
		return false;
	}
	@PostMapping("/equipos")
	public boolean addEquipo(@RequestBody Equipo nuevoEquipo) {
		if(!this.equipoDao.existsById(nuevoEquipo.getNombre())) {
			this.equipoDao.save(nuevoEquipo);
			return this.equipoDao.existsById(nuevoEquipo.getNombre());
		}
		return false;
	}
	@PutMapping("/equipos")
	public boolean modEquipo(@RequestBody Equipo modEquipo) {
		if(this.equipoDao.existsById(modEquipo.getNombre())) {
			this.equipoDao.save(modEquipo);
			return true;
		}
		return false;
	}
	
}
