package sakura.liangdinvshen.Bean;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/23
 * 功能描述：
 */
public class MyNewsNewsDetailBean {
    /**
     * stu : 1
     * res : {"id":"31","uid":"2","title":"订单发货","content":"1513950129991196订单已发货，快递公司：申通快递，快递单号：10000，请注意近期内快递信息！","status":"2","type":"1","addtime":"1513950837"}
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
         * id : 31
         * uid : 2
         * title : 订单发货
         * content : 1513950129991196订单已发货，快递公司：申通快递，快递单号：10000，请注意近期内快递信息！
         * status : 2
         * type : 1
         * addtime : 1513950837
         */

        private String id;
        private String uid;
        private String title;
        private String content;
        private String status;
        private String type;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
