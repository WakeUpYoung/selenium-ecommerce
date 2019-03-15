package cn.wakeupeidolon.entity.taobao;

import java.util.List;

/**
 * @author Wang Yu
 */
public class RateDetail {
    private List<RateList> rateList;
    private String searchinfo;
    private String from;
    private List<String> tags;
    
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
    
    public List<String> getTags() {
        return tags;
    }
    
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    
    @Override
    public String toString() {
        return "rateList=" + rateList
                + "\n" + "searchinfo=" + searchinfo + "\n"
                + "from=" + from + "\n"
                + "tags=" + String.join(",", tags);
    }
}
