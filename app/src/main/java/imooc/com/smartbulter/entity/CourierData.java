package imooc.com.smartbulter.entity;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.entity
*文件名：CourierData
*创建者：pavilion15
*创建时间：2017/8/26 16:46
*描述： 快递信息
*/
public class CourierData {
    private String Datetime;
    private  String Zone;
    private String Remark;

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        this.Zone = zone;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        this.Remark = remark;
    }

    public String getDataTime() {
        return Datetime;
    }

    public void setDataTime(String dataTime) {
        this.Datetime = dataTime;
    }
}
