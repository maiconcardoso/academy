package academy.cardoso.springboot.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimePutRequestBody {
    Long id;
    String name;
}
