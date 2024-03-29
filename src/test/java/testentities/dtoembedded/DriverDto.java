package testentities.dtoembedded;

import by.bsac.annotations.Dto;
import by.bsac.annotations.DtoEmbedded;
import by.bsac.annotations.DtoProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@ToString
@Dto({Driver.class})
public class DriverDto {

    @DtoProperty(entityProperty = "driver_id")
    private Integer id;

    @DtoEmbedded(value = DriverName.class)
    private String f_name;

    @DtoEmbedded(value = DriverName.class)
    @DtoProperty(entityProperty = "l_name")
    private String surname;



}
