package TestData;

import org.testng.annotations.DataProvider;

public class LoginData {

    @DataProvider(name = "validLogin")
    public Object[][] validLogin() {
        return new Object[][]{
                {"Admin", "admin123"}
        };
    }

    @DataProvider(name = "invalidLogin")
    public Object[][] invalidLogin() {
        return new Object[][]{
                {"wrongUser", "wrongPass"},   // fully incorrect
                {"admin", "admin123"},        // admin case-sensitive
                {"mariana", "m123"}           // employee case-sensitive
        };
    }

    @DataProvider(name = "emptyFields")
    public Object[][] emptyFields() {
        return new Object[][]{
                {"", ""},
                {"Admin", ""},
                {"", "admin123"},
        };
    }

    @DataProvider(name = "enterKeyLogin")
    public Object[][] enterKeyLogin() {
        return new Object[][]{
                {"Admin", "admin123"}
        };
    }

    @DataProvider(name = "maskingData")
    public Object[][] maskingData() {
        return new Object[][]{
                {"mypassword"}
        };
    }

    @DataProvider(name = "sessionPersistence")
    public Object[][] sessionPersistence() {
        return new Object[][]{
                {"Admin", "admin123"}
        };
    }

    @DataProvider(name = "logoutData")
    public Object[][] logoutData() {
        return new Object[][]{
                {"Admin", "admin123"}
        };
    }
}
