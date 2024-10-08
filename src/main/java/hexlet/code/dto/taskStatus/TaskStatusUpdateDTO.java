package hexlet.code.dto.taskStatus;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Setter
@Getter
public class TaskStatusUpdateDTO {

    @Column(unique = true)
    @Size(min = 1)
    private JsonNullable<String> name;

    @Column(unique = true)
    @Size(min = 1)
    private JsonNullable<String> slug;
}
