package com.springboot.backend.proyecto1.controller;

import com.springboot.backend.proyecto1.controller.request.RequestCreateProduct;
import com.springboot.backend.proyecto1.controller.response.ResponseProduct;
import com.springboot.backend.proyecto1.model.Product;
import com.springboot.backend.proyecto1.service.IProductService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for {@link Product}
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private static final String PRODUCT_CREATED_MESSAGE = "El producto fue creado con éxito";

    private static final String RESPONSE = "Respuesta: {}";

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    /**
     * Controller to Create {@link Product}
     *
     * @return {@link Product}
     */
    @ApiOperation("Petición para crear un producto")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseProduct> createAProduct(@RequestBody RequestCreateProduct product) {
        logger.info("Petición para crear un producto {}", product);
        Product newProduct = productService.save(product);
        ResponseProduct response = new ResponseProduct(PRODUCT_CREATED_MESSAGE, newProduct);
        logger.info(RESPONSE, response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Controller to Paginated List of {@link Product}
     *
     * @return Page to {@link Product}
     */
    @ApiOperation("Petición para obtener una página de productos")
    @GetMapping
    public ResponseEntity<Page<Product>> getProductPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        logger.info("Petición para obtener página de producto tamaño {}, número {}", pageSize, pageNumber);
        Page<Product> products = productService.findAllPaginated(pageNumber, pageSize);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Controller to List {@link Product} by name
     *
     * @return List to {@link Product}
     */
    @ApiOperation("Petición para listar productos por nombre")
    @GetMapping("/{name}")
    public ResponseEntity<List<Product>> getProductsByName(@PathVariable String name) {
        logger.info("Petición para obtener productos por nombre {}", name);
        List<Product> products = productService.findAllByName(name);
        logger.info("Producto obtenidos {}", products.size());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
