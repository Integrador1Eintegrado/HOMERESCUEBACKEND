package pe.edu.utp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.model.Proveedor;
import pe.edu.utp.model.Servicio;
import pe.edu.utp.model.dto.ServicioInfoDTO;
import pe.edu.utp.repository.ProveedorRepository;
import pe.edu.utp.repository.ServicioRepository;
import pe.edu.utp.service.ServicioService;
import java.util.Map;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private ProveedorRepository proveedorRepository;

    // Método para listar todos los servicios
    @GetMapping
    public ResponseEntity<List<ServicioInfoDTO>> listarServicios() {
        List<ServicioInfoDTO> servicios = servicioService.listarServicios();
        return ResponseEntity.ok(servicios);
    }

    // Método para actualizar el estado de un servicio
    @PutMapping("/{id}/estado")
    public ResponseEntity<Void> actualizarEstadoServicio(
            @PathVariable String id,
            @RequestParam String estado) {
        try {
            servicioService.actualizarEstado(id, estado);
            return ResponseEntity.ok().build(); // Devuelve 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Devuelve 404 si no se encuentra el servicio
        }
    }

    // Método para listar proveedores
    @GetMapping("/{id}/proveedores")
    public ResponseEntity<List<String>> obtenerProveedorIdsPorServicio(@PathVariable("id") String servicioId) {
        List<String> proveedorIds = servicioService.obtenerProveedorIdsPorServicioId(servicioId);
        return ResponseEntity.ok(proveedorIds);
    }

    @PostMapping()
    public ResponseEntity<Servicio> crearServicio(@RequestBody ServicioInfoDTO servicioInfoDTO, @RequestParam String idProveedor) {
        // Verificar si el proveedor existe
        Optional<Proveedor> proveedorOpt = proveedorRepository.findById(idProveedor);
        if (proveedorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Devuelve 404 si no se encuentra el proveedor
        }

        Proveedor proveedor = proveedorOpt.get();

        // Crear nuevo servicio
        Servicio nuevoServicio = new Servicio();
        nuevoServicio.setId(servicioInfoDTO.getId());
        nuevoServicio.setProveedor(proveedor);
        nuevoServicio.setDescripcion(servicioInfoDTO.getDescripcion());
        nuevoServicio.setEstado(servicioInfoDTO.getEstado() != null ? servicioInfoDTO.getEstado() : "disponible");

        // Guardar servicio
        Servicio servicioCreado = servicioService.agregarServicio(nuevoServicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioCreado);
    }
    @DeleteMapping()
    public ResponseEntity<Void> eliminarProveedorDeServicio(
            @RequestParam String idProveedor,
            @RequestParam String servicioId) {
        try {
            servicioService.eliminarProveedorDeServicio(idProveedor, servicioId);
            return ResponseEntity.noContent().build(); // Devuelve 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Devuelve 404 si no se encuentra
        }
    }




}
