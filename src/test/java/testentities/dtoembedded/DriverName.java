package testentities.dtoembedded;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Embedded entity object
@Getter @Setter
@NoArgsConstructor
public class DriverName {

    private String f_name;

    private String l_name;

}
