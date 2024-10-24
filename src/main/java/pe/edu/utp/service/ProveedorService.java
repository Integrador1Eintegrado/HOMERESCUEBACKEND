package pe.edu.utp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.model.Proveedor;
import pe.edu.utp.repository.ProveedorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;


    // Method to save a new proveedor
    public Proveedor agregarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }


    public List<Proveedor> obtenerTodosLosProveedores() {
        return proveedorRepository.findAll();
    }
}
