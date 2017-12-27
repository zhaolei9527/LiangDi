package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/25
 * 功能描述：
 */
public class XflistBean {

    /**
     * code : 1
     * list : [{"id":"11","addtime":"1514183520","ogid":"51","uid":"2","fworder":"151418347743368586","ni_name":"tttttt","img":"/Public/uploads/headimg/2017-12-23/5a3e00552dd98.jpg","tel":"17629345001","price":"100.00","number":"1"},{"id":"10","addtime":"1514026995","ogid":"47","uid":"2","fworder":"151402691719317707","ni_name":"tttttt","img":"/Public/uploads/headimg/2017-12-23/5a3e00552dd98.jpg","tel":"17629345001","price":"100.00","number":"1"},{"id":"9","addtime":"1513949286","ogid":"25","uid":"2","fworder":"151393591799624634","ni_name":"tttttt","img":"/Public/uploads/headimg/2017-12-23/5a3e00552dd98.jpg","tel":"17629345001","price":"100.00","number":"1"}]
     * msg : 查询数据成功，返回数据
     */

    private String code;
    private String msg;
    private List<ListBean> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 11
         * addtime : 1514183520
         * ogid : 51
         * uid : 2
         * fworder : 151418347743368586
         * ni_name : tttttt
         * img : /Public/uploads/headimg/2017-12-23/5a3e00552dd98.jpg
         * tel : 17629345001
         * price : 100.00
         * number : 1
         */

        private String id;
        private String addtime;
        private String ogid;
        private String uid;
        private String fworder;
        private String ni_name;
        private String img;
        private String tel;
        private String price;
        private String number;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getOgid() {
            return ogid;
        }

        public void setOgid(String ogid) {
            this.ogid = ogid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getFworder() {
            return fworder;
        }

        public void setFworder(String fworder) {
            this.fworder = fworder;
        }

        public String getNi_name() {
            return ni_name;
        }

        public void setNi_name(String ni_name) {
            this.ni_name = ni_name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
