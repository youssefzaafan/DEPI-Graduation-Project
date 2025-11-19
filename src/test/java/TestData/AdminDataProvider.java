package TestData;

import org.testng.annotations.DataProvider;

public class AdminDataProvider {
    @DataProvider(name = "adminData")
    public Object[][] provideAdminData() {
        return new Object[][] {
                { "Test1", "Admin", "Enabled" , "12345678qz" },
                { "Test2", "Admin", "Disabled", "12345678qz" },
                { "Test3", "ESS", "Enabled","12345678qz" }
        };
    }
}
