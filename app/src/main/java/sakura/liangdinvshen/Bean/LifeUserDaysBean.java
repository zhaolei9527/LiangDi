package sakura.liangdinvshen.Bean;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/29
 * 功能描述：
 */
public class LifeUserDaysBean {

    /**
     * data : {"live_stage":"1","is_yuejing":"1","yuejing_detail":"未记录","love_love":"1","ti_wen":"36.5°C","ti_zhong":"45.0Kg","xin_qing":"郁闷","zheng_zhuang":",腹痛难忍,冷汗淋漓,肛门坠胀","ri_ji":"fycUchhh","tai_dong":"1","question":"未记录","bu_ru":"未记录","chou_chou":"02:58","cz_quxian":"未记录","baby_bushufu":"未记录","da_du_pic":"未记录"}
     * stu : 1
     */

    private DataBean data;
    private int stu;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getStu() {
        return stu;
    }

    public void setStu(int stu) {
        this.stu = stu;
    }

    public static class DataBean {
        /**
         * live_stage : 1
         * is_yuejing : 1
         * yuejing_detail : 未记录
         * love_love : 1
         * ti_wen : 36.5°C
         * ti_zhong : 45.0Kg
         * xin_qing : 郁闷
         * zheng_zhuang : ,腹痛难忍,冷汗淋漓,肛门坠胀
         * ri_ji : fycUchhh
         * tai_dong : 1
         * question : 未记录
         * bu_ru : 未记录
         * chou_chou : 02:58
         * cz_quxian : 未记录
         * baby_bushufu : 未记录
         * da_du_pic : 未记录
         */

        private String live_stage;
        private String is_yuejing;
        private String yuejing_detail;
        private String love_love;
        private String ti_wen;
        private String ti_zhong;
        private String xin_qing;
        private String zheng_zhuang;
        private String ri_ji;
        private String tai_dong;
        private String question;
        private String bu_ru;
        private String chou_chou;
        private String cz_quxian;
        private String baby_bushufu;
        private String da_du_pic;

        public String getLive_stage() {
            return live_stage;
        }

        public void setLive_stage(String live_stage) {
            this.live_stage = live_stage;
        }

        public String getIs_yuejing() {
            return is_yuejing;
        }

        public void setIs_yuejing(String is_yuejing) {
            this.is_yuejing = is_yuejing;
        }

        public String getYuejing_detail() {
            return yuejing_detail;
        }

        public void setYuejing_detail(String yuejing_detail) {
            this.yuejing_detail = yuejing_detail;
        }

        public String getLove_love() {
            return love_love;
        }

        public void setLove_love(String love_love) {
            this.love_love = love_love;
        }

        public String getTi_wen() {
            return ti_wen;
        }

        public void setTi_wen(String ti_wen) {
            this.ti_wen = ti_wen;
        }

        public String getTi_zhong() {
            return ti_zhong;
        }

        public void setTi_zhong(String ti_zhong) {
            this.ti_zhong = ti_zhong;
        }

        public String getXin_qing() {
            return xin_qing;
        }

        public void setXin_qing(String xin_qing) {
            this.xin_qing = xin_qing;
        }

        public String getZheng_zhuang() {
            return zheng_zhuang;
        }

        public void setZheng_zhuang(String zheng_zhuang) {
            this.zheng_zhuang = zheng_zhuang;
        }

        public String getRi_ji() {
            return ri_ji;
        }

        public void setRi_ji(String ri_ji) {
            this.ri_ji = ri_ji;
        }

        public String getTai_dong() {
            return tai_dong;
        }

        public void setTai_dong(String tai_dong) {
            this.tai_dong = tai_dong;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getBu_ru() {
            return bu_ru;
        }

        public void setBu_ru(String bu_ru) {
            this.bu_ru = bu_ru;
        }

        public String getChou_chou() {
            return chou_chou;
        }

        public void setChou_chou(String chou_chou) {
            this.chou_chou = chou_chou;
        }

        public String getCz_quxian() {
            return cz_quxian;
        }

        public void setCz_quxian(String cz_quxian) {
            this.cz_quxian = cz_quxian;
        }

        public String getBaby_bushufu() {
            return baby_bushufu;
        }

        public void setBaby_bushufu(String baby_bushufu) {
            this.baby_bushufu = baby_bushufu;
        }

        public String getDa_du_pic() {
            return da_du_pic;
        }

        public void setDa_du_pic(String da_du_pic) {
            this.da_du_pic = da_du_pic;
        }
    }
}
