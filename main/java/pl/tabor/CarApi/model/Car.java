package pl.tabor.CarApi.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car extends RepresentationModel<Car> {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull(message = "name cannot be null")
    private String mark;
    @NotBlank
    @NotNull
    private String model;
    @NotNull
    private Color color;

}
