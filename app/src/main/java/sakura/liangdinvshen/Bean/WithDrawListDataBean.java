package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/25
 * 功能描述：
 */
public class WithDrawListDataBean {

    /**
     * stu : 1
     * res : [{"id":"67","bian_num":"123456","tixian_num":"88","add_time":"1514163549","stu":"审核中","uid":"2","sort":"0","is_show":"1","bid":null,"bank":"邮政银行","kaid":"4228481111111111111111"},{"id":"66","bian_num":"123456","tixian_num":"10","add_time":"1514162768","stu":"审核中","uid":"2","sort":"0","is_show":"1","bid":null,"bank":"邮政银行","kaid":"4228481111111111111111"}]
     */

    private int stu;
    private List<ResBean> res;

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
         * id : 67
         * bian_num : 123456
         * tixian_num : 88
         * add_time : 1514163549
         * stu : 审核中
         * uid : 2
         * sort : 0
         * is_show : 1
         * bid : null
         * bank : 邮政银行
         * kaid : 4228481111111111111111
         */

        private String id;
        private String bian_num;
        private String tixian_num;
        private String add_time;
        private String stu;
        private String uid;
        private String sort;
        private String is_show;
        private Object bid;
        private String bank;
        private String kaid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBian_num() {
            return bian_num;
        }

        public void setBian_num(String bian_num) {
            this.bian_num = bian_num;
        }

        public String getTixian_num() {
            return tixian_num;
        }

        public void setTixian_num(String tixian_num) {
            this.tixian_num = tixian_num;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getStu() {
            return stu;
        }

        public void setStu(String stu) {
            this.stu = stu;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public Object getBid() {
            return bid;
        }

        public void setBid(Object bid) {
            this.bid = bid;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getKaid() {
            return kaid;
        }

        public void setKaid(String kaid) {
            this.kaid = kaid;
        }
    }
}
