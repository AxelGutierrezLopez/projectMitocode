package com.project.project.payload;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsolidatedDto {
	@NotNull
	private CategoryDto category;
	private List<ProductDto> products;
}
