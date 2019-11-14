package testentities.dtoembedded;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Car {

    private long car_id;

    private String car_color;

    private Engine car_engine;

}
