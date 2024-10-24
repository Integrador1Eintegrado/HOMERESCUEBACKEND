package pe.edu.utp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.model.Proveedor;
import pe.edu.utp.model.Servicio;
import pe.edu.utp.model.dto.ServicioInfoDTO;
import pe.edu.utp.repository.ProveedorRepository;
import pe.edu.utp.repository.ServicioRepository;

import java.util.*;
import java.util.stream.Collectors;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioService {
    @Autowired
    private ServicioRepository servicioRepository;
    // Método para listar los servicios como DTOs
    public List<ServicioInfoDTO> listarServicios() {
        List<Servicio> servicios = servicioRepository.findAll();
        Set<ServicioInfoDTO> servicioSet = new HashSet<>(servicios.stream()
                .map(servicio -> new ServicioInfoDTO(servicio.getId(), servicio.getDescripcion(), servicio.getEstado()))
                .collect(Collectors.toList()));
        return List.copyOf(servicioSet); // Convertimos de nuevo a lista
    }
    // Método para actualizar el estado de un servicio
    public void actualizarEstado(String id, String estado) {
        servicioRepository.updateEstadoById(id, estado);
    }
    // Método para obtener proveedor_ids de un servicio específico
    public List<String> obtenerProveedorIdsPorServicioId(String servicioId) {
        return servicioRepository.findProveedorIdsByServicioId(servicioId);
    }
    public Servicio agregarServicio(Servicio nuevoServicio) {
        return servicioRepository.save(nuevoServicio);
    }
    public void eliminarProveedorDeServicio(String idProveedor, String servicioId) {
        // Intentar eliminar el proveedor de un servicio
        servicioRepository.removeProveedorFromServicio(idProveedor, servicioId);
    }

}
