package imooc.com.smartbulter.entity;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.entity
*文件名：WeatherData
*创建者：pavilion15
*创建时间：2017/9/19 22:02
*描述： 天气
*/
public class WeatherData {
    //天气文本
    private String text;
    //当前气温
    private String temperature;
    //最新更新
    private String last_update;
    //地区
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }
}
