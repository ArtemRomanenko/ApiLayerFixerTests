package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiLayerLatestErrorDto {

    private Boolean success;
    private Map<String, String> error = new HashMap<>();
    private String message;
}
