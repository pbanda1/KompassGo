package hr.algebra.kompassgo.util;

import hr.algebra.kompassgo.exception.XmlException;
import hr.algebra.kompassgo.poco.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;

public final class ConfigParserUtility {

    private static final Logger log = LoggerFactory.getLogger(ConfigParserUtility.class);

    private ConfigParserUtility() {
    }

    public static AppConfig parseConfig(InputStream xml) {
        if (xml == null) {
            String msg = "Config xml file not found!";
            log.error(msg);
            throw new XmlException(msg);
        }
        AppConfig cfg = new AppConfig();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                private final StringBuilder value = new StringBuilder();

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    value.setLength(0);
                }

                @Override
                public void characters(char[] chars, int start, int length) {
                    value.append(chars, start, length);
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    String text = value.toString().trim();
                    switch (qName) {
                        case "url" -> cfg.setUrl(text);
                        case "username" -> cfg.setUsername(text);
                        case "password" -> cfg.setPassword(text);
                        default -> {
                        }
                    }
                }
            };
            saxParser.parse(xml, handler);
        } catch (Exception e) {
            String msg = "Error while parsing config XML file";
            log.error(msg, e);
            throw new XmlException(msg, e);
        }
        return cfg;
    }
}