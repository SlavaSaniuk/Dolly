package testentities.dtoembedded;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@ToString
public class Driver {

    private Integer driver_id;

    //EMBEDDED entity  field
    private DriverName driver_name;

    private int driver_experience;


}
