package hxy.dream.common.configuration;

import ch.qos.logback.core.PropertyDefinerBase;

public class HostnamePropertyDefiner extends PropertyDefinerBase {
    @Override
    public String getPropertyValue() {
        try {
            return java.net.InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "unknown-host";
        }
    }
}