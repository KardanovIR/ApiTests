package waves;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TestVariables {

    private static Map<Object, Object> variables = new HashMap<>();
    private static final String CONTENT_TYPE = "application/json";

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("./" + System.getProperty("env") + ".properties")));

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
            throw new AssertionError(String.format("Variable %s not found, name"));
    }

    public static String getSeed() {
        if (variables.get("seed") != null)
            return (String) TestVariables.getVariable("seed");
        else
            throw new AssertionError("Variable 'seed' not found");
    }

    public static String getDefaultContentType() {
        if (variables.get("defaultContentType") != null)
            return (String) TestVariables.getVariable("defaultContentType");
        else {
            TestVariables.setVariable("defaultContentType", CONTENT_TYPE);
            return CONTENT_TYPE;
        }

    }

    public static String getAssetId() {
        if (variables.get("assetId") != null)
            return (String) TestVariables.getVariable("assetId");
        else
            throw new AssertionError("Variable 'assetId' not found");
    }

    public static String getHost() {
        if (variables.get("host") != null)
            return (String) TestVariables.getVariable("host");
        else
            throw new AssertionError("Variable 'host' not found");
    }

    public static String getProtocol() {
        if (variables.get("protocol") != null)
            return (String) TestVariables.getVariable("protocol");
        else
            throw new AssertionError("Variable 'protocol' not found");
    }

    public static String getApiVersion() {
        if (variables.get("api.version") != null)
            return (String) TestVariables.getVariable("api.version");
        else
            throw new AssertionError("Variable 'api.version' not found");
    }

}
