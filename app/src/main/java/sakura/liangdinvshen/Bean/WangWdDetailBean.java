package sakura.liangdinvshen.Bean;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/26
 * 功能描述：
 */
public class WangWdDetailBean {

    /**
     * code : 1
     * res : {"id":"2","bian_num":"0","title":"安徽省合肥市市辖区","province":"吉林省","city":"长春市","country":"市辖区","address":"黄河南路2单元1102ggggiu 哈哈哈宝宝","add_time":"1514121093","is_open":"1","lx_name":"呀呀呀","lx_tel":"17654321000","fz_name":"呀呀呀","fz_tel":"17654321000"}
     */

    private String code;
    private ResBean res;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
         * fz_name : 呀呀呀
         * fz_tel : 17654321000
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
        private String fz_name;
        private String fz_tel;

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

        public String getFz_name() {
            return fz_name;
        }

        public void setFz_name(String fz_name) {
            this.fz_name = fz_name;
        }

        public String getFz_tel() {
            return fz_tel;
        }

        public void setFz_tel(String fz_tel) {
            this.fz_tel = fz_tel;
        }
    }
}
