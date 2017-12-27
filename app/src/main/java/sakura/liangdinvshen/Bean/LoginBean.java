package sakura.liangdinvshen.Bean;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/11/29
 * 功能描述：
 */
public class LoginBean {


    /**
     * code : 1
     * msg : 登陆成功！
     * res : {"id":"10","ni_name":"靓帝女神","img":"/Public/uploads/headimg/default_img.png","tel":"17629345001","password":"5d015f65dc4c84b23767cd0cfc8e5133","add_time":"1511934451","status":"1","rand":"57421","is_del":"1","last_login_time":"1511934751","stu":"1","jifen":"","money":"0.00","total_push_money":"0.00","pid1":"","pid2":"","pid3":"","role":"1","wd_id":"","sheng_li_qi":"","now_days":"","is_yuejing":"2","shengao":"","shengri":"","hunyin":"未婚","city":"","mother_start":"","pregnant_start":"","pregnant_expected":"","period_start":"","period_end":"","period_length":"","period_cycle":"","baby_sex":"","baby_birthday":"","erweima":"","is_tuan":"","tuan":"","check_in_time":""}
     */


    private String code;
    private String msg;
    private ResBean res;

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

    public ResBean getRes() {
        return res;
    }

    public void setRes(ResBean res) {
        this.res = res;
    }

    public static class ResBean {
        //生日
        //(nonatomic, copy) NSString *baby_birthday;
//人生阶段人生阶段，1第一经期，2第二备孕，3，第三怀孕，4第四辣妈
        //(nonatomic, copy) NSString *stu;
//1小王子2小公主
        //(nonatomic, copy) NSString *baby_sex;
//城市
        //(nonatomic, copy) NSString *city;
//婚姻未婚
        //(nonatomic, copy) NSString *hunyin;
//头像
        //(nonatomic, copy) NSString *img;
//是否是月经期1是2不是
        //(nonatomic, copy) NSString *is_yuejing;
//积分
        //(nonatomic, copy) NSString *jifen;
//余额
        //(nonatomic, copy) NSString *money;
//辣妈开始时间
        //(nonatomic, copy) NSString *mother_start;
//昵称
        //(nonatomic, copy) NSString *ni_name;
//经期的第几天
        //(nonatomic, copy) NSString *now_days;
//月经周期长度
        //(nonatomic, copy) NSString *period_cycle;
//月经结束
        //(nonatomic, copy) NSString *period_end;
//月经长度
        //(nonatomic, copy) NSString *period_length;
//月经开始
        //(nonatomic, copy) NSString *period_start;
//怀孕预产期
        //(nonatomic, copy) NSString *pregnant_expected;
//怀孕开始时间
        //(nonatomic, copy) NSString *pregnant_start;
//1;角色 1 会员 2员工 3.负责人
        //(nonatomic, copy) NSString *role;
//生理期
        //(nonatomic, copy) NSString *sheng_li_qi;
//身高
        //(nonatomic, copy) NSString *shengao;
//生日
        //(nonatomic, copy) NSString *shengri;
//消费累计总额
        //(nonatomic, copy) NSString *total_push_money;


        /**
         * id : 10
         * ni_name : 靓帝女神
         * img : /Public/uploads/headimg/default_img.png
         * tel : 17629345001
         * password : 5d015f65dc4c84b23767cd0cfc8e5133
         * add_time : 1511934451
         * status : 1
         * rand : 57421
         * is_del : 1
         * last_login_time : 1511934751
         * stu : 1
         * jifen :
         * money : 0.00
         * total_push_money : 0.00
         * pid1 :
         * pid2 :
         * pid3 :
         * role : 1
         * wd_id :
         * sheng_li_qi :
         * now_days :
         * is_yuejing : 2
         * shengao :
         * shengri :
         * hunyin : 未婚
         * city :
         * mother_start :
         * pregnant_start :
         * pregnant_expected :
         * period_start :
         * period_end :
         * period_length :
         * period_cycle :
         * baby_sex :
         * baby_birthday :
         * erweima :
         * is_tuan :
         * tuan :
         * check_in_time :
         */

        private String id;
        private String ni_name;
        private String img;
        private String tel;
        private String password;
        private String add_time;
        private String status;
        private String rand;
        private String is_del;
        private String last_login_time;
        private String stu;
        private String jifen;
        private String money;
        private String total_push_money;
        private String pid1;
        private String pid2;
        private String pid3;
        private String role;
        private String wd_id;
        private String sheng_li_qi;
        private String now_days;
        private String is_yuejing;
        private String shengao;
        private String shengri;
        private String hunyin;
        private String city;
        private String mother_start;
        private String pregnant_start;
        private String pregnant_expected;
        private String period_start;
        private String period_end;
        private String period_length;
        private String period_cycle;
        private String baby_sex;
        private String baby_birthday;
        private String erweima;
        private String is_tuan;
        private String tuan;
        private String check_in_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRand() {
            return rand;
        }

        public void setRand(String rand) {
            this.rand = rand;
        }

        public String getIs_del() {
            return is_del;
        }

        public void setIs_del(String is_del) {
            this.is_del = is_del;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
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

        public String getTotal_push_money() {
            return total_push_money;
        }

        public void setTotal_push_money(String total_push_money) {
            this.total_push_money = total_push_money;
        }

        public String getPid1() {
            return pid1;
        }

        public void setPid1(String pid1) {
            this.pid1 = pid1;
        }

        public String getPid2() {
            return pid2;
        }

        public void setPid2(String pid2) {
            this.pid2 = pid2;
        }

        public String getPid3() {
            return pid3;
        }

        public void setPid3(String pid3) {
            this.pid3 = pid3;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getWd_id() {
            return wd_id;
        }

        public void setWd_id(String wd_id) {
            this.wd_id = wd_id;
        }

        public String getSheng_li_qi() {
            return sheng_li_qi;
        }

        public void setSheng_li_qi(String sheng_li_qi) {
            this.sheng_li_qi = sheng_li_qi;
        }

        public String getNow_days() {
            return now_days;
        }

        public void setNow_days(String now_days) {
            this.now_days = now_days;
        }

        public String getIs_yuejing() {
            return is_yuejing;
        }

        public void setIs_yuejing(String is_yuejing) {
            this.is_yuejing = is_yuejing;
        }

        public String getShengao() {
            return shengao;
        }

        public void setShengao(String shengao) {
            this.shengao = shengao;
        }

        public String getShengri() {
            return shengri;
        }

        public void setShengri(String shengri) {
            this.shengri = shengri;
        }

        public String getHunyin() {
            return hunyin;
        }

        public void setHunyin(String hunyin) {
            this.hunyin = hunyin;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getMother_start() {
            return mother_start;
        }

        public void setMother_start(String mother_start) {
            this.mother_start = mother_start;
        }

        public String getPregnant_start() {
            return pregnant_start;
        }

        public void setPregnant_start(String pregnant_start) {
            this.pregnant_start = pregnant_start;
        }

        public String getPregnant_expected() {
            return pregnant_expected;
        }

        public void setPregnant_expected(String pregnant_expected) {
            this.pregnant_expected = pregnant_expected;
        }

        public String getPeriod_start() {
            return period_start;
        }

        public void setPeriod_start(String period_start) {
            this.period_start = period_start;
        }

        public String getPeriod_end() {
            return period_end;
        }

        public void setPeriod_end(String period_end) {
            this.period_end = period_end;
        }

        public String getPeriod_length() {
            return period_length;
        }

        public void setPeriod_length(String period_length) {
            this.period_length = period_length;
        }

        public String getPeriod_cycle() {
            return period_cycle;
        }

        public void setPeriod_cycle(String period_cycle) {
            this.period_cycle = period_cycle;
        }

        public String getBaby_sex() {
            return baby_sex;
        }

        public void setBaby_sex(String baby_sex) {
            this.baby_sex = baby_sex;
        }

        public String getBaby_birthday() {
            return baby_birthday;
        }

        public void setBaby_birthday(String baby_birthday) {
            this.baby_birthday = baby_birthday;
        }

        public String getErweima() {
            return erweima;
        }

        public void setErweima(String erweima) {
            this.erweima = erweima;
        }

        public String getIs_tuan() {
            return is_tuan;
        }

        public void setIs_tuan(String is_tuan) {
            this.is_tuan = is_tuan;
        }

        public String getTuan() {
            return tuan;
        }

        public void setTuan(String tuan) {
            this.tuan = tuan;
        }

        public String getCheck_in_time() {
            return check_in_time;
        }

        public void setCheck_in_time(String check_in_time) {
            this.check_in_time = check_in_time;
        }
    }
}
