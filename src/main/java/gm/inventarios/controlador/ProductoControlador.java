package gm.inventarios.controlador;

import gm.inventarios.modelo.Producto;
import gm.inventarios.servicio.ProductoServicio;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;

import java.util.List;

@RestController
@RequestMapping("inventario-app") //http://localhost:8080/inventario-app
@CrossOrigin(value = "http://localhost:4200") //Puerto por default de angular (Esto es para poder aceptar peticiones de ese puerto)
public class ProductoControlador {
    private static final Logger logger = LoggerFactory.getLogger(ProductoControlador.class);

    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping("/productos") //Peticion http de tipo get http://localhost:8080/inventario-app/productos
    public List<Producto> obtenerProductos() {
        List<Producto> productos = productoServicio.listarProductos();

        logger.info("Productos obtenidos " + productos);
        productos.forEach(producto -> logger.info(producto.toString()));

        return productos;
    }
}
