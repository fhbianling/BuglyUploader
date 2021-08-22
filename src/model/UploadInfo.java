package model;

import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * author 边凌
 * date 2021/8/22 18:05
 * 类描述：
 */
@XmlRootElement(name = "uploadInfo")
public class UploadInfo {
    private final StringProperty appId = new SimpleStringProperty();
    private final StringProperty appKey = new SimpleStringProperty();
    private final StringProperty bundleId = new SimpleStringProperty();
    private final StringProperty version = new SimpleStringProperty();
    private final StringProperty platform = new SimpleStringProperty();
    private final StringProperty inputSymbol = new SimpleStringProperty();
    private final StringProperty inputMapping = new SimpleStringProperty();
    private final StringProperty bulgyJarPath = new SimpleStringProperty();

    public UploadInfo() {
    }

    public String getBulgyJarPath() {
        return bulgyJarPath.get();
    }

    public StringProperty bulgyJarPathProperty() {
        return bulgyJarPath;
    }

    public void setBulgyJarPath(String bulgyJarPath) {
        this.bulgyJarPath.set(bulgyJarPath);
    }

    public String getAppId() {
        return appId.get();
    }

    public StringProperty appIdProperty() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId.set(appId);
    }

    public String getAppKey() {
        return appKey.get();
    }

    public StringProperty appKeyProperty() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey.set(appKey);
    }

    public String getBundleId() {
        return bundleId.get();
    }

    public StringProperty bundleIdProperty() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId.set(bundleId);
    }

    public String getVersion() {
        return version.get();
    }

    public StringProperty versionProperty() {
        return version;
    }

    public void setVersion(String version) {
        this.version.set(version);
    }

    public String getPlatform() {
        return platform.get();
    }

    public StringProperty platformProperty() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform.set(platform);
    }

    public String getInputSymbol() {
        return inputSymbol.get();
    }

    public StringProperty inputSymbolProperty() {
        return inputSymbol;
    }

    public void setInputSymbol(String inputSymbol) {
        this.inputSymbol.set(inputSymbol);
    }

    public String getInputMapping() {
        return inputMapping.get();
    }

    public StringProperty inputMappingProperty() {
        return inputMapping;
    }

    public void setInputMapping(String inputMapping) {
        this.inputMapping.set(inputMapping);
    }
}
