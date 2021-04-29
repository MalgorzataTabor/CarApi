package pl.tabor.CarApi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {

    private Long id;
    private String mark;
    private String model;
    private Color color;

}
