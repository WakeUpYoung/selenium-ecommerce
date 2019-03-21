package cn.wakeupeidolon.entity.taobao;

import java.util.List;

/**
 * @author Wang Yu
 */
public class RateDetail {
    private List<RateList> rateList;
    private String searchinfo;
    private String from;
    
    public List<RateList> getRateList() {
        return rateList;
    }
    
    public void setRateList(List<RateList> rateList) {
        this.rateList = rateList;
    }
    
    public String getSearchinfo() {
        return searchinfo;
    }
    
    public void setSearchinfo(String searchinfo) {
        this.searchinfo = searchinfo;
    }
    
    public String getFrom() {
        return from;
    }
    
    public void setFrom(String from) {
        this.from = from;
    }
    
    
    @Override
    public String toString() {
        return "rateList.size = " + rateList.size()
                + "\n" + "searchinfo = " + searchinfo + "\n"
                + "from = " + from + "\n";
    }
}
