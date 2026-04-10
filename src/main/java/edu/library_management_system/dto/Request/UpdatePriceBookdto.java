package edu.library_management_system.dto.Request;



import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePriceBookdto {
	@Min(value= 1, message ="price must be greater than 0")
	private Double price;

}