package cn.edu.swufe.reading_records;

public class RecordsItem {
    private int id;
    private String curBookName;
    private String curDate;
    private String curTime;
    private String curContent;

    public RecordsItem() {
        super();
        curBookName="";
        curDate = "";
        curTime= "";
        curContent="";
    }
    public RecordsItem(String curBookName,String curDate, String curTime,String curContent) {
        super();
        this.curBookName=curBookName;
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
    public String getCurBookName(){
        return curBookName;
    }
    public void setCurBookName(String curBookName){
        this.curBookName=curBookName;
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