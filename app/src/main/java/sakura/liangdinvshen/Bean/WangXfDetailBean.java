package sakura.liangdinvshen.Bean;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/25
 * 功能描述：
 */
public class WangXfDetailBean {


    /**
     * code : 1
     * list : {"id":"11","ogid":"51","gid":"6","uid":"2","addtime":"1514183520","yid":"138","fworder":"151418347743368586","ni_name":"tttttt","img":"/Public/uploads/headimg/2017-12-23/5a3e00552dd98.jpg","tel":"17629345001","ygname":"呀呀呀","ygimg":"/Public/uploads/headimg/2017-12-23/5a3dc56d10428.jpg","ygtel":"17654321000","price":"100.00","number":"1","funame":"小蓝屏","fuimg":"/Public/uploads/goods/img/2017-09-25/59c87060df700.jpg"}
     * msg : 查询数据成功，返回数据
     */

    private String code;
    private ListBean list;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ListBean {
        /**
         * id : 11
         * ogid : 51
         * gid : 6
         * uid : 2
         * addtime : 1514183520
         * yid : 138
         * fworder : 151418347743368586
         * ni_name : tttttt
         * img : /Public/uploads/headimg/2017-12-23/5a3e00552dd98.jpg
         * tel : 17629345001
         * ygname : 呀呀呀
         * ygimg : /Public/uploads/headimg/2017-12-23/5a3dc56d10428.jpg
         * ygtel : 17654321000
         * price : 100.00
         * number : 1
         * funame : 小蓝屏
         * fuimg : /Public/uploads/goods/img/2017-09-25/59c87060df700.jpg
         */

        private String id;
        private String ogid;
        private String gid;
        private String uid;
        private String addtime;
        private String yid;
        private String fworder;
        private String ni_name;
        private String img;
        private String tel;
        private String ygname;
        private String ygimg;
        private String ygtel;
        private String price;
        private String number;
        private String funame;
        private String fuimg;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOgid() {
            return ogid;
        }

        public void setOgid(String ogid) {
            this.ogid = ogid;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getYid() {
            return yid;
        }

        public void setYid(String yid) {
            this.yid = yid;
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

        public String getYgname() {
            return ygname;
        }

        public void setYgname(String ygname) {
            this.ygname = ygname;
        }

        public String getYgimg() {
            return ygimg;
        }

        public void setYgimg(String ygimg) {
            this.ygimg = ygimg;
        }

        public String getYgtel() {
            return ygtel;
        }

        public void setYgtel(String ygtel) {
            this.ygtel = ygtel;
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

        public String getFuname() {
            return funame;
        }

        public void setFuname(String funame) {
            this.funame = funame;
        }

        public String getFuimg() {
            return fuimg;
        }

        public void setFuimg(String fuimg) {
            this.fuimg = fuimg;
        }
    }
}
