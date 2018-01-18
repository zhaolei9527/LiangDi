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
     * cate : [{"id":"1","cate_name":"细胞美容"},{"id":"10","cate_name":"养生"},{"id":"2","cate_name":"服饰"},{"id":"11","cate_name":"搞笑"},{"id":"8","cate_name":"情感"},{"id":"5","cate_name":"八卦"},{"id":"4","cate_name":"整形"}]
     * yun : {"id":"1","name":"月经期","yun_lv":"6%","stu_title":"安全","now_days":2,"is_yuejing":"1","stu":"1"}
     * topimg : /Public/uploads/ad/2018-01-16/5a5db9c21195d.png
     */

    private String stu;
    private YunBean yun;
    private String topimg;
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

    public String getTopimg() {
        return topimg;
    }

    public void setTopimg(String topimg) {
        this.topimg = topimg;
    }

    public List<CateBean> getCate() {
        return cate;
    }

    public void setCate(List<CateBean> cate) {
        this.cate = cate;
    }

    public static class YunBean {
        /**
         * id : 1
         * name : 月经期
         * yun_lv : 6%
         * stu_title : 安全
         * now_days : 2
         * is_yuejing : 1
         * stu : 1
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
         * id : 1
         * cate_name : 细胞美容
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
