# OrangeHRM Website Automation & Manual Testing 

Website URL: `https://opensource-demo.orangehrmlive.com/web/index.php/auth/login`

This repository contains manual and automation testing for the OrangeHRM website.  
The work covers **Login/Logout**, **Admin**, **PIM**, **My Info**, and **Leave** modules based on detailed user stories.  
Future enhancements include completing automation for all remaining modules and performing **performance testing**.



---

## 📁 Project Structure

```
DEPI-Graduation-Project  (Orange_Manual)
│
├── docs
│    ├── Bug Report
│    ├── TestCase Report
│    ├── Test Summary Report
│    ├── Team3-Testing-OrangeHRM

DEPI-Graduation-Project  (Orange_Automation)
│
├── src
│   ├── main
│   │   └── java
│   │       ├── Pages
│   │       │   ├── AddEmployeePage.java
│   │       │   ├── AdminPage.java
│   │       │   ├── DashboardPage.java
│   │       │   ├── EmployeeListPage.java
│   │       │   └── LoginPage.java
│   │       │
│   │       └── utils
│   │           └── AllureTestListener.java
│   │
│   └── test
│       └── java
│           ├── TestData
│           │   └── AdminDataProvider.java
│           │
│           └── tests
│               ├── AddEmployeeTest.java
│               ├── AdminTest.java
│               ├── BaseTest.java
│               ├── EmployeeListTest.java
│               └── LoginTest.java
│
├── allure-results/
├── doc/
├── target/
├── README.md
└── pom.xml
```

---

## 👥 Team Members

- Youssef Zaafan  
- Mariana Shawky  
- Ahmed Sayed  
- Salma Mamdoh  
- Yusif Qabil  

---

## 🏁 Future Work

- Automate remaining OrangeHRM modules.  
- Integrate **performance testing** (JMeter / Gatling).  
- Improve reporting dashboards with Allure.  
- Add CI/CD integration using GitHub Actions.

---
---

## 🚀 Quick Start (Windows 10)

1. Install **JDK 17** and configure `JAVA_HOME`.
2. Install **Maven** (`mvn -v` should work).
3. Ensure **Google Chrome 142+** is installed.  
   WebDriverManager automatically matches the ChromeDriver version.

### Run Tests

```
mvn test
```

If Chrome does not open, set the Chrome binary manually inside:

`src/main/java/org/orangehrm/base/Base.java`

Example:

```java
options.setBinary("C:\Path\To\chrome.exe");
```

