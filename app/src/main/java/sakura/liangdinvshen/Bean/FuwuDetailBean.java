package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/25
 * 功能描述：
 */
public class FuwuDetailBean {

    /**
     * stu : 1
     * goods : {"title":"小蓝屏","img":"/Public/uploads/goods/img/2017-09-25/59c87060df700.jpg"}
     * info : {"fworder":"151418347743368586","yfwnum":"1","number":"1","pjnum":"10","price":"100.00","gid":"6","orderid":"15141834776109397","addtime":"1514183477"}
     * fuwu : [{"id":"11","ogid":"51","gid":"6","uid":"2","addtime":"1514183520","wid":"2","yid":"138","is_ping":"1","fworder":"151418347743368586","ping":{"id":"2","fwid":"11","gid":"6","ogid":"51","uid":"2","content":"浩浩荡荡你简简单单各个环节警方称此次版本疯疯癫癫许某某","xing":"5","addtime":"1514184836","stu":"1","hf":null,"admin":null,"hftime":null},"wang":{"id":"2","bian_num":"0","title":"安徽省合肥市市辖区","province":"吉林省","city":"长春市","country":"市辖区","address":"黄河南路2单元1102ggggiu 哈哈哈宝宝","add_time":"1514121093","is_open":"1","lx_name":"呀呀呀","lx_tel":"17654321000","yuan":"呀呀呀"}}]
     * msg : 查询成功，返回数据
     */

    private String stu;
    private GoodsBean goods;
    private InfoBean info;
    private String msg;
    private List<FuwuBean> fuwu;

    public String getStu() {
        return stu;
    }

    public void setStu(String stu) {
        this.stu = stu;
    }

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<FuwuBean> getFuwu() {
        return fuwu;
    }

    public void setFuwu(List<FuwuBean> fuwu) {
        this.fuwu = fuwu;
    }

    public static class GoodsBean {
        /**
         * title : 小蓝屏
         * img : /Public/uploads/goods/img/2017-09-25/59c87060df700.jpg
         */

        private String title;
        private String img;

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
    }

    public static class InfoBean {
        /**
         * fworder : 151418347743368586
         * yfwnum : 1
         * number : 1
         * pjnum : 10
         * price : 100.00
         * gid : 6
         * orderid : 15141834776109397
         * addtime : 1514183477
         */

        private String fworder;
        private String yfwnum;
        private String number;
        private String pjnum;
        private String price;
        private String gid;
        private String orderid;
        private String addtime;

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

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPjnum() {
            return pjnum;
        }

        public void setPjnum(String pjnum) {
            this.pjnum = pjnum;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }

    public static class FuwuBean {
        /**
         * id : 11
         * ogid : 51
         * gid : 6
         * uid : 2
         * addtime : 1514183520
         * wid : 2
         * yid : 138
         * is_ping : 1
         * fworder : 151418347743368586
         * ping : {"id":"2","fwid":"11","gid":"6","ogid":"51","uid":"2","content":"浩浩荡荡你简简单单各个环节警方称此次版本疯疯癫癫许某某","xing":"5","addtime":"1514184836","stu":"1","hf":null,"admin":null,"hftime":null}
         * wang : {"id":"2","bian_num":"0","title":"安徽省合肥市市辖区","province":"吉林省","city":"长春市","country":"市辖区","address":"黄河南路2单元1102ggggiu 哈哈哈宝宝","add_time":"1514121093","is_open":"1","lx_name":"呀呀呀","lx_tel":"17654321000","yuan":"呀呀呀"}
         */

        private String id;
        private String ogid;
        private String gid;
        private String uid;
        private String addtime;
        private String wid;
        private String yid;
        private String is_ping;
        private String fworder;
        private PingBean ping;
        private WangBean wang;

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

        public String getWid() {
            return wid;
        }

        public void setWid(String wid) {
            this.wid = wid;
        }

        public String getYid() {
            return yid;
        }

        public void setYid(String yid) {
            this.yid = yid;
        }

        public String getIs_ping() {
            return is_ping;
        }

        public void setIs_ping(String is_ping) {
            this.is_ping = is_ping;
        }

        public String getFworder() {
            return fworder;
        }

        public void setFworder(String fworder) {
            this.fworder = fworder;
        }

        public PingBean getPing() {
            return ping;
        }

        public void setPing(PingBean ping) {
            this.ping = ping;
        }

        public WangBean getWang() {
            return wang;
        }

        public void setWang(WangBean wang) {
            this.wang = wang;
        }

        public static class PingBean {
            /**
             * id : 2
             * fwid : 11
             * gid : 6
             * ogid : 51
             * uid : 2
             * content : 浩浩荡荡你简简单单各个环节警方称此次版本疯疯癫癫许某某
             * xing : 5
             * addtime : 1514184836
             * stu : 1
             * hf : null
             * admin : null
             * hftime : null
             */

            private String id;
            private String fwid;
            private String gid;
            private String ogid;
            private String uid;
            private String content;
            private String xing;
            private String addtime;
            private String stu;
            private Object hf;
            private Object admin;
            private Object hftime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFwid() {
                return fwid;
            }

            public void setFwid(String fwid) {
                this.fwid = fwid;
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

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getXing() {
                return xing;
            }

            public void setXing(String xing) {
                this.xing = xing;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getStu() {
                return stu;
            }

            public void setStu(String stu) {
                this.stu = stu;
            }

            public Object getHf() {
                return hf;
            }

            public void setHf(Object hf) {
                this.hf = hf;
            }

            public Object getAdmin() {
                return admin;
            }

            public void setAdmin(Object admin) {
                this.admin = admin;
            }

            public Object getHftime() {
                return hftime;
            }

            public void setHftime(Object hftime) {
                this.hftime = hftime;
            }
        }

        public static class WangBean {
            /**
             * id : 2
             * bian_num : 0
             * title : 安徽省合肥市市辖区
             * province : 吉林省
             * city : 长春市
             * country : 市辖区
             * address : 黄河南路2单元1102ggggiu 哈哈哈宝宝
             * add_time : 1514121093
             * is_open : 1
             * lx_name : 呀呀呀
             * lx_tel : 17654321000
             * yuan : 呀呀呀
             */

            private String id;
            private String bian_num;
            private String title;
            private String province;
            private String city;
            private String country;
            private String address;
            private String add_time;
            private String is_open;
            private String lx_name;
            private String lx_tel;
            private String yuan;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getIs_open() {
                return is_open;
            }

            public void setIs_open(String is_open) {
                this.is_open = is_open;
            }

            public String getLx_name() {
                return lx_name;
            }

            public void setLx_name(String lx_name) {
                this.lx_name = lx_name;
            }

            public String getLx_tel() {
                return lx_tel;
            }

            public void setLx_tel(String lx_tel) {
                this.lx_tel = lx_tel;
            }

            public String getYuan() {
                return yuan;
            }

            public void setYuan(String yuan) {
                this.yuan = yuan;
            }
        }
    }
}
