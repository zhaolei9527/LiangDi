package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/22
 * 功能描述：
 */
public class MynewsNewsListBean {

    /**
     * stu : 1
     * res : [{"id":"30","uid":"2","title":"订单发货","content":"15139464591959727订单已发货，快递公司：asdasd，快递单号：1744551，请注意近期内快递信息！","status":"1","type":"系统消息","addtime":"1513946715"},{"id":"29","uid":"2","title":"订单发货","content":"15139464648383950订单已发货，快递公司：申通快递，快递单号：147852963，请注意近期内快递信息！","status":"1","type":"系统消息","addtime":"1513946575"},{"id":"28","uid":"2","title":"订单发货","content":"15139464703071981订单已发货，快递公司：申通快递，快递单号：147852963，请注意近期内快递信息！","status":"1","type":"系统消息","addtime":"1513946557"},{"id":"27","uid":"2","title":"订单发货","content":"15139459913068015订单已发货，快递公司：申通快递，快递单号：4125794616，请注意近期内快递信息！","status":"1","type":"系统消息","addtime":"1513946082"},{"id":"26","uid":"2","title":"订单发货","content":"15139459986189040订单已发货，快递公司：天天快递，快递单号：741852963，请注意近期内快递信息！","status":"1","type":"系统消息","addtime":"1513946058"},{"id":"25","uid":"2","title":"订单发货","content":"15139460036387080订单已发货，快递公司：申通快递，快递单号：962870，请注意近期内快递信息！","status":"1","type":"系统消息","addtime":"1513946036"},{"id":"24","uid":"2","title":"订单发货","content":"15139224605950722订单已发货，快递公司：申通快递，快递单号：123753951，请注意近期内快递信息！","status":"1","type":"系统消息","addtime":"1513945538"},{"id":"23","uid":"2","title":"订单发货","content":"15139224657829196订单已发货，快递公司：申通快递，快递单号：741852，请注意近期内快递信息！","status":"1","type":"系统消息","addtime":"1513945291"},{"id":"22","uid":"2","title":"订单发货","content":"15139359171015302订单已发货，快递公司：申通快递，快递单号：123456，请注意近期内快递信息！","status":"1","type":"系统消息","addtime":"1513945264"},{"id":"21","uid":"2","title":"订单发货","content":"15139359398532556订单已发货，快递公司：圆通速递，快递单号：123456789，请注意近期内快递信息！","status":"1","type":"系统消息","addtime":"1513936928"}]
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
         * id : 30
         * uid : 2
         * title : 订单发货
         * content : 15139464591959727订单已发货，快递公司：asdasd，快递单号：1744551，请注意近期内快递信息！
         * status : 1
         * type : 系统消息
         * addtime : 1513946715
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
