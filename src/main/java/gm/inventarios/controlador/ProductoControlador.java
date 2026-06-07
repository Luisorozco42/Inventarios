package gm.inventarios.controlador;

import gm.inventarios.modelo.Producto;
import gm.inventarios.servicio.ProductoServicio;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gm.inventarios.Excepcion.RecursoNoEncontradoExcepcion;

import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/productos")
    public Producto agregarProducto(@RequestBody Producto producto) {
        logger.info("Producto a agregar " + producto);
        return this.productoServicio.guardarProducto(producto);
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable int id) {
        Producto producto = this.productoServicio.buscarProductoPorId(id);
        if(producto != null) {
            return ResponseEntity.ok(producto);
        }else{
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable int id, @RequestBody Producto productoRecibido) {
        Producto producto = this.productoServicio.buscarProductoPorId(id);

        if(producto != null) {
            producto.setNombre(productoRecibido.getNombre());
            producto.setDescripcion(productoRecibido.getDescripcion());
            producto.setPrecio(productoRecibido.getPrecio());
            producto.setExistencia(productoRecibido.getExistencia());

            this.productoServicio.guardarProducto(producto);
            return ResponseEntity.ok(producto);
        }else throw new RecursoNoEncontradoExcepcion("No se encontro el id del producto a editar: " + id);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable int id) {
        Producto producto = this.productoServicio.buscarProductoPorId(id);
        if (producto == null){
            throw new RecursoNoEncontradoExcepcion("No se encontro el id:" + id)
        }
        this.productoServicio.eliminarProductoPorId(id);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("Eliminado", Boolean.TRUE);

        return ResponseEntity.ok(respuesta);
    }
}
