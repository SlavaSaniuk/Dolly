package core;

import by.bsac.core.beans.EmbeddedDeConverter;
import by.bsac.core.beans.EmbeddedDtoEntityConverter;
import by.bsac.core.exceptions.NoDtoClassException;
import by.bsac.core.exceptions.NoSupportedEntitiesException;
import by.bsac.core.exceptions.NoSupportedEntityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testentities.dtoembedded.*;

class EmbeddedDEConverterIntegrationTest {

    private EmbeddedDtoEntityConverter<DriverWithCarDto> CONVERTER;

    @BeforeEach
    void setUp() throws NoSupportedEntitiesException, NoDtoClassException {
        this.CONVERTER = new EmbeddedDeConverter<>(DriverWithCarDto.class);
    }

    @Test
    void toEntity_dtoHasEmbeddedFields_shouldReturnEntityWithEmbeddedFields() {

        DriverWithCarDto dto = new DriverWithCarDto();
        dto.setId(24); //Driver id
        dto.setDriverName("DRIVER-NAME"); //DriverName - f_name
        dto.setDriverSurname("DRIVER-SURNAME"); //DriverName -  l_name
        dto.setCarId(25); //Car id
        dto.setColor("GREY"); //car_color
        dto.setEngineName("ICE"); //Engine - name
        dto.setCylinders(12); //Engine cylinders
        System.out.println(dto.toString());

        Driver driver_entity = this.CONVERTER.toEntity(dto, new Driver(), new DriverName());
        Car car_entity = this.CONVERTER.toEntity(dto, new Car(), new Engine());

        Assertions.assertNotNull(driver_entity);
        Assertions.assertEquals(dto.getId(), driver_entity.getDriverId());
        Assertions.assertEquals(dto.getDriverName(), driver_entity.getDriverName().getFName());
        Assertions.assertEquals(dto.getDriverSurname(), driver_entity.getDriverName().getLName());
        System.out.println(driver_entity.toString());

        Assertions.assertNotNull(car_entity);
        Assertions.assertEquals(dto.getCarId(), car_entity.getCarId());
        Assertions.assertEquals(dto.getColor(), car_entity.getCarColor());
        Assertions.assertEquals(dto.getEngineName(), car_entity.getCarEngine().getName());
        Assertions.assertEquals(dto.getCylinders(), car_entity.getCarEngine().getCylinders());
        System.out.println(car_entity.toString());

    }

    @Test
    void toDto_entityHasEmbeddedFields_shouldReturnDtoWithFields() {

        Car car = new Car();
        car.setCarColor("RED");
        car.setCarId(23);
        car.setCarEngine(new Engine("ICE", 4));
        System.out.println(car.toString());

        Driver driver = new Driver();
        driver.setDriverId(24);
        driver.setDriverExperience(12);
        driver.setDriverName(new DriverName("NAME", "SURNAME"));
        System.out.println(driver.toString());

        DriverWithCarDto dto = this.CONVERTER.toDto(car, new DriverWithCarDto());
        dto = this.CONVERTER.toDto(driver, dto);

        Assertions.assertNotNull(dto);
        System.out.println(dto.toString());

        Assertions.assertEquals(car.getCarId(), dto.getCarId());
        Assertions.assertEquals(car.getCarColor(), dto.getColor());
        Assertions.assertEquals(car.getCarEngine().getName(), dto.getEngineName());
        Assertions.assertEquals(car.getCarEngine().getCylinders(), dto.getCylinders());

        Assertions.assertEquals(driver.getDriverId(), dto.getId());
        Assertions.assertEquals(driver.getDriverName().getFName(), dto.getDriverName());
        Assertions.assertEquals(driver.getDriverName().getLName(), dto.getDriverSurname());

    }

    @Test
    void embeddedDtoEntityConverter_noDtoAnnotations_shouldThrowNoDtoClassException() {

        Assertions.assertThrows(NoDtoClassException.class, () -> new EmbeddedDeConverter<>(Driver.class));

    }

    @Test
    void toEntity_noSupportedEntity_shouldThrowNoSupportedEntityException() {
        Assertions.assertThrows(NoSupportedEntityException.class, () -> this.CONVERTER.toEntity(new DriverWithCarDto(), new Engine()));
    }

    @Test
    void toDto_noSupportedEntity_shouldThrowNoSupportedEntityException() {
        Assertions.assertThrows(NoSupportedEntityException.class, () -> this.CONVERTER.toDto(new Engine(), new DriverWithCarDto()));
    }

}
