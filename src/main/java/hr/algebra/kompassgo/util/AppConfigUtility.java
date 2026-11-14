package hr.algebra.kompassgo.util;

import hr.algebra.kompassgo.poco.AppConfig;

import java.io.InputStream;

public final class AppConfigUtility {

    private static final String CONFIG_PATH = "/hr/algebra/kompassgo/data/app-config.xml";

    private static AppConfig instance;

    private AppConfigUtility() {
    }

    public static synchronized AppConfig getConfig() {
        if (instance == null) {
            InputStream xml = AppConfigUtility.class.getResourceAsStream(CONFIG_PATH);
            instance = ConfigParserUtility.parseConfig(xml);
        }
        return instance;
    }
}