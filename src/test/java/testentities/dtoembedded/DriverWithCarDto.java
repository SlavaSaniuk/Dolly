package testentities.dtoembedded;

import by.bsac.annotations.Dto;
import by.bsac.annotations.DtoEmbedded;
import by.bsac.annotations.DtoProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Dto({Driver.class, Car.class})
@Getter @Setter
@ToString
public class DriverWithCarDto {

    @DtoProperty(entityProperty = "driver_id")
    private Integer id;

    @DtoEmbedded(DriverName.class)
    @DtoProperty(entityProperty = "f_name")
    private String driver_name;

    @DtoEmbedded(DriverName.class)
    @DtoProperty(entityProperty = "l_name")
    private String driver_surname;

    private long car_id;

    @DtoProperty(entityProperty = "car_color")
    private String color;

    @DtoEmbedded(Engine.class)
    @DtoProperty(entityProperty = "name")
    private String engine_name;

    @DtoEmbedded(Engine.class)
    private int cylinders;

}
