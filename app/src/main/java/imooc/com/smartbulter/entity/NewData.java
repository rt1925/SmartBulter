package imooc.com.smartbulter.entity;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.entity
*文件名：NewData
*创建者：pavilion15
*创建时间：2017/9/23 21:50
*描述： 新闻实体
*/
public class NewData {
    private String ctime,title,description,picUrl,url;

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
