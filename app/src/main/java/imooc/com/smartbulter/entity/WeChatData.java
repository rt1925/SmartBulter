package imooc.com.smartbulter.entity;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.entity
*文件名：WeChatData
*创建者：pavilion15
*创建时间：2017/9/3 18:49
*描述： TODO
*/
public class WeChatData {
    private String title;
    private String source;
    private String firstImg;
    private String url;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }
}
