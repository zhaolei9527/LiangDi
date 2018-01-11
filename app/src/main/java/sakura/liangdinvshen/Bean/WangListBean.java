package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2018/1/11
 * 功能描述：
 */
public class WangListBean {

    /**
     * code : 1
     * list : [{"id":"3","bian_num":"96001","title":"七七网络科技","province":"北京","city":"崇文区","country":"","address":"北京","add_time":"1515656344","is_open":"1","lx_name":"18638035535","lx_tel":"18638035535","img":"/Public/uploads/img/2018-01-11/5a57208676d0e.jpg","wd_id":"3","uname":"18638035535","tel":"18638035535"}]
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
         * id : 3
         * bian_num : 96001
         * title : 七七网络科技
         * province : 北京
         * city : 崇文区
         * country :
         * address : 北京
         * add_time : 1515656344
         * is_open : 1
         * lx_name : 18638035535
         * lx_tel : 18638035535
         * img : /Public/uploads/img/2018-01-11/5a57208676d0e.jpg
         * wd_id : 3
         * uname : 18638035535
         * tel : 18638035535
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
        private String img;
        private String wd_id;
        private String uname;
        private String tel;

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getWd_id() {
            return wd_id;
        }

        public void setWd_id(String wd_id) {
            this.wd_id = wd_id;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    }
}
