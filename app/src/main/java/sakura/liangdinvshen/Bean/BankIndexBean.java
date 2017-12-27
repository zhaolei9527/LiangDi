package sakura.liangdinvshen.Bean;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/23
 * 功能描述：
 */
public class BankIndexBean {


    /**
     * stu : 1
     * res : {"id":"2","bank":"工商","no":"1234567890123456788","name":"邮政","add_time":"1507875716","uid":"2","status":"1","rand":"12345"}
     */

    private int stu;
    private ResBean res;

    public int getStu() {
        return stu;
    }

    public void setStu(int stu) {
        this.stu = stu;
    }

    public ResBean getRes() {
        return res;
    }

    public void setRes(ResBean res) {
        this.res = res;
    }

    public static class ResBean {
        /**
         * id : 2
         * bank : 工商
         * no : 1234567890123456788
         * name : 邮政
         * add_time : 1507875716
         * uid : 2
         * status : 1
         * rand : 12345
         */

        private String id;
        private String bank;
        private String no;
        private String name;
        private String add_time;
        private String uid;
        private String status;
        private String rand;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRand() {
            return rand;
        }

        public void setRand(String rand) {
            this.rand = rand;
        }
    }
}
