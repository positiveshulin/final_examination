package cn.edu.swufe.reading_records;

public class RecordsItem {
    private int id;
    private String curDate;
    private String curTime;
    private String curContent;

    public RecordsItem() {
        super();
        curDate = "";
        curTime= "";
        curContent="";
    }
    public RecordsItem(String curDate, String curTime,String curContent) {
        super();
        this.curDate = curDate;
        this.curTime = curTime;
        this.curContent=curContent;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCurDate() {
        return curDate;
    }
    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }
    public String getCurTime() {
        return curTime;
    }
    public void setCurTime(String curTime) {
        this.curTime = curTime;
    }
    public String getCurContent() {
        return curContent;
    }
    public void setCurContent(String curContent) {
        this.curContent= curContent;
    }
}