package sakura.liangdinvshen.Bean;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/19
 * 功能描述：
 */
public class OrderRetreatBean {
    /**
     * code : 1
     * msg : 查询成功,返回数据
     * tkmoney : 12
     * type : 1
     */

    private int code;
    private String msg;
    private double tkmoney;
    private String type;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public double getTkmoney() {
        return tkmoney;
    }

    public void setTkmoney(double tkmoney) {
        this.tkmoney = tkmoney;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
