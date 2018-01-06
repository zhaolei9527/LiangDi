package sakura.liangdinvshen.Bean;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2018/1/6
 * 功能描述：
 */
public class SuckleGrowBean {

    /**
     * stu : 1
     * res : {"id":"7","uid":"77","height":"50.5","weight":"25.5","head":"25.5","addtime":"1515168000"}
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
         * id : 7
         * uid : 77
         * height : 50.5
         * weight : 25.5
         * head : 25.5
         * addtime : 1515168000
         */

        private String id;
        private String uid;
        private String height;
        private String weight;
        private String head;
        private String addtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
