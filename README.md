# OrangeHRM Automation — Reformatted Professional Structure

This project is reformatted into a professional Maven layout and updated to use explicit waits (15s), GUI Chrome mode, and a WaitHelper utility.

## Quick start (Windows 11)
1. JDK 17 installed and `JAVA_HOME` set.
2. Maven installed (`mvn -v` should work).
3. Chrome 142 installed (the tests use WebDriverManager to match chromedriver to this version).

Run tests:
```
mvn test
```

If Chrome doesn't open, ensure Chrome is installed at the standard location. To troubleshoot, open `src/main/java/org/orangehrm/base/Base.java` and add `options.setBinary("C:\\Path\\To\\chrome.exe");` if needed.

Logs: Wait actions are printed to console, prefixed with `[WaitHelper]`.
