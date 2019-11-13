package testentities.dtoembedded;

import lombok.*;

//Embedded entity object
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DriverName {

    private String f_name;

    private String l_name;

}
