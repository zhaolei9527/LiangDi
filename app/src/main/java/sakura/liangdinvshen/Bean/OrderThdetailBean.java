package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/22
 * 功能描述：
 */
public class OrderThdetailBean {

    /**
     * stu : 1
     * info : {"id":"12","gid":"12","ogid":"22","img":["/Public/uploads/retreat/2017-12-22/5a3ca032e7400.png"],"addtime":"1513922610","number":"1","type":"2","status":"-1","thbeizhu":"啊啊啊吧","money":"12.00","gtype":"1","t_money":null,"editbeizhu":null}
     * goods : {"id":"12","title":"出口外贸尾单秋冬羊绒衫女毛衣加厚高领菠萝针织衫打底外套保暖衫","img":"/Public/uploads/goods/img/2017-10-13/59e05bda67cf0.jpg","price":"12","type":"1","is_show":"1"}
     */

    private int stu;
    private InfoBean info;
    private GoodsBean goods;

    public int getStu() {
        return stu;
    }

    public void setStu(int stu) {
        this.stu = stu;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public static class InfoBean {
        /**
         * id : 12
         * gid : 12
         * ogid : 22
         * img : ["/Public/uploads/retreat/2017-12-22/5a3ca032e7400.png"]
         * addtime : 1513922610
         * number : 1
         * type : 2
         * status : -1
         * thbeizhu : 啊啊啊吧
         * money : 12.00
         * gtype : 1
         * t_money : null
         * editbeizhu : null
         */

        private String id;
        private String gid;
        private String ogid;
        private String addtime;
        private String number;
        private String type;
        private String status;
        private String thbeizhu;
        private String money;
        private String gtype;
        private Object t_money;
        private Object editbeizhu;
        private List<String> img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getOgid() {
            return ogid;
        }

        public void setOgid(String ogid) {
            this.ogid = ogid;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getThbeizhu() {
            return thbeizhu;
        }

        public void setThbeizhu(String thbeizhu) {
            this.thbeizhu = thbeizhu;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getGtype() {
            return gtype;
        }

        public void setGtype(String gtype) {
            this.gtype = gtype;
        }

        public Object getT_money() {
            return t_money;
        }

        public void setT_money(Object t_money) {
            this.t_money = t_money;
        }

        public Object getEditbeizhu() {
            return editbeizhu;
        }

        public void setEditbeizhu(Object editbeizhu) {
            this.editbeizhu = editbeizhu;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }
    }

    public static class GoodsBean {
        /**
         * id : 12
         * title : 出口外贸尾单秋冬羊绒衫女毛衣加厚高领菠萝针织衫打底外套保暖衫
         * img : /Public/uploads/goods/img/2017-10-13/59e05bda67cf0.jpg
         * price : 12
         * type : 1
         * is_show : 1
         */

        private String id;
        private String title;
        private String img;
        private String price;
        private String type;
        private String is_show;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }
    }
}
