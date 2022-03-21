package com.project.project.payload;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	private long id;
	@NotEmpty
	@Size(min=2, message = "el nombre del producto debe tener 2 caracteres como mínimo")
	private String name;
	@NotNull(message = "la fecha de expiración del producto no puede ser vacío")
	private Date expiration_date;
	@Min(1)
	private Integer stock;
	@NotNull(message = "el precio de venta del producto no puede ser vacío")
	private Double sale_price; 
	@NotNull(message = "la categoría del producto no puede ser vacío")
	private CategoryDto category; 
	
}
