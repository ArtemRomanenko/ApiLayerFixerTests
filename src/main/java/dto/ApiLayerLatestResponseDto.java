package dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ApiLayerLatestResponseDto {
    private String base;
    private String date;
    private Map<String, Object> rates = new HashMap<String, Object>();
    private Boolean success;
    private Long timestamp;

}
