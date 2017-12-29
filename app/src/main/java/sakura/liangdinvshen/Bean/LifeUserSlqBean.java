package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/29
 * 功能描述：
 */
public class LifeUserSlqBean {

    /**
     * data : [{"stu":"月经期","time":"2017-12-1"},{"stu":"月经期","time":"2017-12-2"},{"stu":"月经期","time":"2017-12-3"},{"stu":"月经期","time":"2017-12-4"},{"stu":"月经期","time":"2017-12-5"},{"stu":"月经期","time":"2017-12-6"},{"stu":"月经期","time":"2017-12-7"},{"stu":"月经期","time":"2017-12-8"},{"stu":"安全期","time":"2017-12-9"},{"stu":"安全期","time":"2017-12-10"},{"stu":"排卵期","time":"2017-12-11"},{"stu":"排卵期","time":"2017-12-12"},{"stu":"排卵期","time":"2017-12-13"},{"stu":"排卵期","time":"2017-12-14"},{"stu":"排卵期","time":"2017-12-15"},{"stu":"排卵期","time":"2017-12-16"},{"stu":"排卵期","time":"2017-12-17"},{"stu":"排卵期","time":"2017-12-18"},{"stu":"排卵期","time":"2017-12-19"},{"stu":"排卵期","time":"2017-12-20"},{"stu":"安全期","time":"2017-12-21"},{"stu":"安全期","time":"2017-12-22"},{"stu":"安全期","time":"2017-12-23"},{"stu":"安全期","time":"2017-12-24"},{"stu":"安全期","time":"2017-12-25"},{"stu":"安全期","time":"2017-12-26"},{"stu":"安全期","time":"2017-12-27"},{"stu":"月经期","time":"2017-12-28"},{"stu":"月经期","time":"2017-12-29"},{"stu":"月经期","time":"2017-12-30"},{"stu":"月经期","time":"2017-12-31"}]
     * stu : 1
     */

    private int stu;
    private List<DataBean> data;

    public int getStu() {
        return stu;
    }

    public void setStu(int stu) {
        this.stu = stu;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * stu : 月经期
         * time : 2017-12-1
         */

        private String stu;
        private String time;

        public String getStu() {
            return stu;
        }

        public void setStu(String stu) {
            this.stu = stu;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
