package sakura.liangdinvshen.Bean;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/24
 * 功能描述：
 */
public class StuBean {

    /**
     * stu : 1
     */

    private int stu;
    /**
     * code : 100
     * error_msg : no uid or bank_id or bank_num
     */

    private int code;
    private String error_msg;

    public int getStu() {
        return stu;
    }

    public void setStu(int stu) {
        this.stu = stu;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
