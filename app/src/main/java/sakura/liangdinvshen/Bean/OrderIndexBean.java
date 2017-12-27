package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/13
 * 功能描述：
 */
public class OrderIndexBean {

    /**
     * is_addr : 2
     * stu : 1
     * jfdk : 0
     * yunfei : 0.00
     * jf_tj : 100.00
     * jf_bili : 0
     * total : 0
     * list : []
     * address : {"id":"124","name":"闲了说","province":"河南省","city":"郑州市","country":"中原区","address":"哈哈哈哈哈","tel":"17629345001","is_default":"1","uid":"10","add_time":"1513148232"}
     * money : 0.00
     * sy_jifen : 100.00
     */

    private String is_addr;
    private String stu;
    private int jfdk;
    private String yunfei;
    private String jf_tj;
    private String jf_bili;
    private int total;
    private AddressBean address;
    private String money;
    private String sy_jifen;
    private List<?> list;

    public String getIs_addr() {
        return is_addr;
    }

    public void setIs_addr(String is_addr) {
        this.is_addr = is_addr;
    }

    public String getStu() {
        return stu;
    }

    public void setStu(String stu) {
        this.stu = stu;
    }

    public int getJfdk() {
        return jfdk;
    }

    public void setJfdk(int jfdk) {
        this.jfdk = jfdk;
    }

    public String getYunfei() {
        return yunfei;
    }

    public void setYunfei(String yunfei) {
        this.yunfei = yunfei;
    }

    public String getJf_tj() {
        return jf_tj;
    }

    public void setJf_tj(String jf_tj) {
        this.jf_tj = jf_tj;
    }

    public String getJf_bili() {
        return jf_bili;
    }

    public void setJf_bili(String jf_bili) {
        this.jf_bili = jf_bili;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getSy_jifen() {
        return sy_jifen;
    }

    public void setSy_jifen(String sy_jifen) {
        this.sy_jifen = sy_jifen;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public static class AddressBean {
        /**
         * id : 124
         * name : 闲了说
         * province : 河南省
         * city : 郑州市
         * country : 中原区
         * address : 哈哈哈哈哈
         * tel : 17629345001
         * is_default : 1
         * uid : 10
         * add_time : 1513148232
         */

        private String id;
        private String name;
        private String province;
        private String city;
        private String country;
        private String address;
        private String tel;
        private String is_default;
        private String uid;
        private String add_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
