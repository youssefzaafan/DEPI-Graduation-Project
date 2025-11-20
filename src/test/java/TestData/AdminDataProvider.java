package TestData;

import org.testng.annotations.DataProvider;
import java.lang.reflect.Method;

public class AdminDataProvider {
    @DataProvider(name = "adminData")
    public Object[][] provideData(Method method) {
        if (method.getName().equals("searchForUnExistsUser") || method.getName().equals("searchForExistsUser")) {
            return new Object[][] {
                    { "Test1", "Admin", "Enabled" },
                    { "Test2", "Admin", "Disabled" },
                    { "Test3", "ESS", "Enabled" }
            };
        } else if (method.getName().equals("addUser")) {
            return new Object[][] {
                    { "Test1", "Admin", "Enabled", "12345678qz" },
                    { "Test2", "Admin", "Disabled", "12345678qz" },
                    { "Test3", "ESS", "Enabled", "12345678qz" }
            };
        } else if (method.getName().equals("deleteExistUser")) {
            return new Object[][] {
                    { "Test1" },
                    { "Test2" },
                    { "Test3" }
            };
        }
        return new Object[0][];
    }
}