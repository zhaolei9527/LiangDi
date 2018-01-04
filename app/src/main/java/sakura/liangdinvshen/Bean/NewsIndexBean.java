package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/11/29
 * 功能描述：新闻index
 */
public class NewsIndexBean {

    /**
     * stu : 1
     * cate : [{"id":"2","cate_name":"小说"},{"id":"5","cate_name":"大姨妈"},{"id":"4","cate_name":"育儿"},{"id":"1","cate_name":"情感"}]
     * yun : {"id":"4","name":"妊娠期","yun_lv":"8%","stu_title":"极低","now_days":133,"is_yuejing":"2","stu":"3"}
     */

    private String stu;
    private YunBean yun;
    private List<CateBean> cate;

    public String getStu() {
        return stu;
    }

    public void setStu(String stu) {
        this.stu = stu;
    }

    public YunBean getYun() {
        return yun;
    }

    public void setYun(YunBean yun) {
        this.yun = yun;
    }

    public List<CateBean> getCate() {
        return cate;
    }

    public void setCate(List<CateBean> cate) {
        this.cate = cate;
    }

    public static class YunBean {
        /**
         * id : 4
         * name : 妊娠期
         * yun_lv : 8%
         * stu_title : 极低
         * now_days : 133
         * is_yuejing : 2
         * stu : 3
         */

        private String id;
        private String name;
        private String yun_lv;
        private String stu_title;
        private int now_days;
        private String is_yuejing;
        private String stu;

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

        public String getYun_lv() {
            return yun_lv;
        }

        public void setYun_lv(String yun_lv) {
            this.yun_lv = yun_lv;
        }

        public String getStu_title() {
            return stu_title;
        }

        public void setStu_title(String stu_title) {
            this.stu_title = stu_title;
        }

        public int getNow_days() {
            return now_days;
        }

        public void setNow_days(int now_days) {
            this.now_days = now_days;
        }

        public String getIs_yuejing() {
            return is_yuejing;
        }

        public void setIs_yuejing(String is_yuejing) {
            this.is_yuejing = is_yuejing;
        }

        public String getStu() {
            return stu;
        }

        public void setStu(String stu) {
            this.stu = stu;
        }
    }

    public static class CateBean {
        /**
         * id : 2
         * cate_name : 小说
         */

        private String id;
        private String cate_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCate_name() {
            return cate_name;
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
        }
    }
}
