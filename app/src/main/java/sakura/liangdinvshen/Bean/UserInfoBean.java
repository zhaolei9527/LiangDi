package sakura.liangdinvshen.Bean;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/22
 * 功能描述：
 */
public class UserInfoBean {

    /**
     * code : 1
     * msg : 查询成功,返回数据
     * list : {"ni_name":"靓帝女神","img":"/Public/uploads/headimg/default_img.png","tel":"17654321000","stu":"2","jifen":"300","money":"9855.00","leiji":"12740.00","role":"1","is_qian":"0"}
     */

    private String code;
    private String msg;
    private ListBean list;

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

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * ni_name : 靓帝女神
         * img : /Public/uploads/headimg/default_img.png
         * tel : 17654321000
         * stu : 2
         * jifen : 300
         * money : 9855.00
         * leiji : 12740.00
         * role : 1
         * is_qian : 0
         */

        private String ni_name;
        private String img;
        private String tel;
        private String stu;
        private String jifen;
        private String money;
        private String leiji;
        private String role;
        private String is_qian;

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

        public String getStu() {
            return stu;
        }

        public void setStu(String stu) {
            this.stu = stu;
        }

        public String getJifen() {
            return jifen;
        }

        public void setJifen(String jifen) {
            this.jifen = jifen;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getLeiji() {
            return leiji;
        }

        public void setLeiji(String leiji) {
            this.leiji = leiji;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getIs_qian() {
            return is_qian;
        }

        public void setIs_qian(String is_qian) {
            this.is_qian = is_qian;
        }
    }
}
