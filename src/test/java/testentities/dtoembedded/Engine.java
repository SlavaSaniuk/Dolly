package testentities.dtoembedded;

import lombok.*;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Engine {

    private String name;

    private int cylinders;
}
