package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/24
 * 功能描述：
 */
public class FuwuIndexBean {

    /**
     * code : 1
     * list : [{"id":"47","orderid":"15140269172961214","price":"100.00","number":"1","status":"1","gid":"6","pjnum":"10","type":"2","uid":"2","fworder":"151402691719317707","yfwnum":"1","addtime":"1514026917","img":"/Public/uploads/goods/img/2017-09-25/59c87060df700.jpg","title":"小蓝屏","is_fw":"1"},{"id":"46","orderid":"15140267807618341","price":"100.00","number":"1","status":"0","gid":"6","pjnum":"10","type":"2","uid":"2","fworder":"151402678024218825","yfwnum":"0","addtime":"1514026780","img":"/Public/uploads/goods/img/2017-09-25/59c87060df700.jpg","title":"小蓝屏","is_fw":"0"},{"id":"44","orderid":"15140126393725603","price":"0.00","number":"1","status":"0","gid":"8","pjnum":"3","type":"2","uid":"2","fworder":"15140126391022437","yfwnum":"0","addtime":"1514012639","img":"/Public/uploads/goods/img/2017-09-23/59c5fb696f608.jpg","title":"第一纪念品","is_fw":"0"},{"id":"40","orderid":"15140005204749975","price":"100.00","number":"1","status":"0","gid":"6","pjnum":"10","type":"2","uid":"2","fworder":"151400052084887710","yfwnum":"0","addtime":"1514000520","img":"/Public/uploads/goods/img/2017-09-25/59c87060df700.jpg","title":"小蓝屏","is_fw":"0"},{"id":"34","orderid":"1513950129991196","price":"100.00","number":"1","status":"1","gid":"6","pjnum":"10","type":"2","uid":"2","fworder":"151395012959283487","yfwnum":"0","addtime":"1513950129","img":"/Public/uploads/goods/img/2017-09-25/59c87060df700.jpg","title":"小蓝屏","is_fw":"0"},{"id":"25","orderid":"15139359171015302","price":"100.00","number":"1","status":"1","gid":"13","pjnum":"10","type":"2","uid":"2","fworder":"151393591799624634","yfwnum":"1","addtime":"1513935917","img":"/Public/uploads/goods/img/2017-09-25/59c87060df700.jpg","title":"小蓝屏","is_fw":"1"},{"id":"24","orderid":"15139359017228365","price":"0.00","number":"1","status":"0","gid":"8","pjnum":"3","type":"2","uid":"2","fworder":"151393590120977862","yfwnum":"0","addtime":"1513935901","img":"/Public/uploads/goods/img/2017-09-23/59c5fb696f608.jpg","title":"第一纪念品","is_fw":"0"}]
     * msg : 服务信息查询成功
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
         * id : 47
         * orderid : 15140269172961214
         * price : 100.00
         * number : 1
         * status : 1
         * gid : 6
         * pjnum : 10
         * type : 2
         * uid : 2
         * fworder : 151402691719317707
         * yfwnum : 1
         * addtime : 1514026917
         * img : /Public/uploads/goods/img/2017-09-25/59c87060df700.jpg
         * title : 小蓝屏
         * is_fw : 1
         */

        private String id;
        private String orderid;
        private String price;
        private String number;
        private String status;
        private String gid;
        private String pjnum;
        private String type;
        private String uid;
        private String fworder;
        private String yfwnum;
        private String addtime;
        private String img;
        private String title;
        private String is_fw;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getPjnum() {
            return pjnum;
        }

        public void setPjnum(String pjnum) {
            this.pjnum = pjnum;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getYfwnum() {
            return yfwnum;
        }

        public void setYfwnum(String yfwnum) {
            this.yfwnum = yfwnum;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIs_fw() {
            return is_fw;
        }

        public void setIs_fw(String is_fw) {
            this.is_fw = is_fw;
        }
    }
}
