package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/28
 * 功能描述：
 */
public class SuckleSuckleBean {

    /**
     * month : 0
     * day : 2
     * time : 2017-12-28
     * stu : 1
     * res : [{"start_time":"10:27","type":"奶粉","amount":"10mL"},{"start_time":"10:27","type":"亲喂母乳","amount":"3分10秒"},{"start_time":"10:27","type":"亲喂母乳","amount":"0分0秒"},{"start_time":"10:27","type":"亲喂母乳","amount":"0分0秒"},{"start_time":"10:31","type":"奶粉","amount":"10mL"}]
     */

    private int month;
    private int day;
    private String time;
    private int stu;
    private List<ResBean> res;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStu() {
        return stu;
    }

    public void setStu(int stu) {
        this.stu = stu;
    }

    public List<ResBean> getRes() {
        return res;
    }

    public void setRes(List<ResBean> res) {
        this.res = res;
    }

    public static class ResBean {
        /**
         * start_time : 10:27
         * type : 奶粉
         * amount : 10mL
         */

        private String start_time;

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        private String end_time;

        private String type;
        private String amount;

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
