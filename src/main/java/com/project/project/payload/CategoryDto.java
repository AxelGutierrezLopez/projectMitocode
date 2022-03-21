package com.project.project.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	private long id;
	
	@NotEmpty
	@Size(min=2, message = "el nombre de la categoría debe tener 2 caracteres como mínimo")
	private String name;
	
	@Size(min=2, message = "el detalle de la categoría debe tener 2 caracteres como mínimo")
	private String detail;

}
