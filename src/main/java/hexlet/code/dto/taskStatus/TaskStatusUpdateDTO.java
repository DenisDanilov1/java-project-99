package hexlet.code.dto.taskStatus;

import jakarta.validation.constraints.NotBlank;
import org.openapitools.jackson.nullable.JsonNullable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskStatusUpdateDTO {
    @NotBlank
    private JsonNullable<String> name;
    @NotBlank
    private JsonNullable<String> slug;
}
