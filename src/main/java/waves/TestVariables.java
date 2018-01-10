package ru.waves.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TestVariables {

    private static Map<Object, Object> variables = new HashMap<>();
    private static final String CONTENT_TYPE = "application/json";

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("./" + System.getProperty("env") + ".properties")));
//            properties.load(new FileInputStream(new File("./" + System.getProperty("env") + "_users.properties")));

        } catch (IOException e) {
            throw new IllegalStateException("There is no such properties file" +
                    System.getProperty("env") + ".properties", e);
        }
        Enumeration em = properties.keys();
        while (em.hasMoreElements()) {
            try {
                String property = (String) em.nextElement();
                properties.put(property, System.getProperty(property));
            } catch (NullPointerException ignore) {
            }
        }
        variables.putAll(properties);
    }

    public static List<String> getUserByProfile(String userProfile) {
        String profile = (String) TestVariables.getVariable(userProfile);
        String[] splittedProfile = profile.split("\\,");
        assertThat("Не полные данные в профиле, возможно отсутствует логин/пароль или неправильный разделитель",
                splittedProfile.length, is(equalTo(2)));
        return Arrays.asList(splittedProfile);
    }

    public static Map<Object, Object> getVariables() {
        return variables;
    }

    public static void setVariable(String name, Object value) {
        variables.put(name, value);
    }

    public static Object getVariable(String name) {
        if (variables.get(name) != null)
            return variables.get(name);
        else
            throw new AssertionError(String.format("Переменная %s не найдена в списке сохраненных", name));
    }

    public static String getLogin() {
        if (variables.get("login") != null)
            return (String) TestVariables.getVariable("login");
        else
            throw new AssertionError("Переменная 'login' не найдена в списке сохраненных");
    }

    public static String getDefaultContentType() {
        if (variables.get("defaultContentType") != null)
            return (String) TestVariables.getVariable("defaultContentType");
        else {
            TestVariables.setVariable("defaultContentType", CONTENT_TYPE);
            return CONTENT_TYPE;
        }

    }

    public static String getPassword() {
        if (variables.get("password") != null)
            return (String) TestVariables.getVariable("password");
        else
            throw new AssertionError("Переменная 'password' не найдена в списке сохраненных");
    }

    public static String getHost() {
        if (variables.get("host") != null)
            return (String) TestVariables.getVariable("host");
        else
            throw new AssertionError("Переменная 'host' не найдена в списке сохраненных");
    }

    public static String getProtocol() {
        if (variables.get("protocol") != null)
            return (String) TestVariables.getVariable("protocol");
        else
            throw new AssertionError("Переменная 'protocol' не найдена в списке сохраненных");
    }

    public static String getApiVersion() {
        if (variables.get("api.version") != null)
            return (String) TestVariables.getVariable("api.version");
        else
            throw new AssertionError("Переменная 'api.version' не найдена в списке сохраненных");
    }

}
