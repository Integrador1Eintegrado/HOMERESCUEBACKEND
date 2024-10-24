package pe.edu.utp.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.utp.model.Servicio;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, String> {
    // Método para encontrar todos los servicios
    List<Servicio> findAll();
    // Método para actualizar el estado de un servicio por su ID
    @Modifying
    @Transactional
    @Query("UPDATE Servicio s SET s.estado = :estado WHERE s.id = :id")
    void updateEstadoById(@Param("id") String id, @Param("estado") String estado);
    // Método para obtener proveedores de un servicio
    @Query("SELECT s.proveedor.id FROM Servicio s WHERE s.id = :id")
    List<String> findProveedorIdsByServicioId(@Param("id") String servicioId);
    @Modifying
    @Transactional
    @Query("DELETE Servicio s  WHERE s.id = :servicioId AND s.proveedor.id = :idProveedor")
    void removeProveedorFromServicio(String idProveedor, String servicioId);

}
