package examples.gui.web.filLoginForm;

import com.github.javafaker.Faker;
import io.qameta.allure.*;
import org.testng.annotations.Test;

public class RegisterTest {

    Faker faker = new Faker();

    private final String emailValue = faker.internet().emailAddress();
    private final String FirstName = faker.name().firstName();
    private final String LastName = faker.name().lastName();
    private final String passwordValue = faker.internet().password
            (8, 10, true);
    private final String AddressFirstName = faker.name().firstName();
    private final String AddressLastName = faker.name().lastName();
    private final String Company = faker.company().name();
    private final String Address1 = faker.address().fullAddress();
    private final String Address2 = faker.address().secondaryAddress();
    private final String city = faker.address().cityName();
    final String state = "Alabama";
    final String postalCode = "12351";
    final String AdditionalValue = "Whatever additional";
    private final String mobilePhoneNum = faker.phoneNumber().cellPhone();
    private final String alias = faker.funnyName().name();


    @Test
    @Description( "Registration Testcase" )
    @Severity( SeverityLevel.CRITICAL )
    @Epic( "Registration" )
    @Story( "User Can Register Successfully" )
    public void UserCanRegisterSuccessfully() {

    }
}
