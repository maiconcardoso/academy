package academy.cardoso.springboot.request;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimePostRequestBody {
    
    @NotEmpty(message = "The anime name cannot be empty")
    String name;
}
