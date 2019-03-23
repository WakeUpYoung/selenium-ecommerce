package cn.wakeupeidolon.enums;

/**
 * @Author Wang Yu
 * @Description
 * @Date 13:08 2019/3/21
 */
public enum WebType {
    TAO_BAO(0), // 淘宝
    TMALL(1),  // 天猫
    JD(2),  // 京东
    UNKNOWN(-1)
    ;
    private int type;
    
    WebType(int type) {
        this.type = type;
    }
    
    public int getType(){
        return this.type;
    }
    
}
