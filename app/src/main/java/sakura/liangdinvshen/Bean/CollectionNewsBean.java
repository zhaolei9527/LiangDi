package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/22
 * 功能描述：
 */
public class CollectionNewsBean {


    /**
     * stu : 1
     * code : 101
     * res : [{"id":"6","title":"国际油价反弹 化工品价格跟随原油走强","img":"/Public/uploads/news/2017-09-29/59cdae249893f.jpg","view":"110","cate_name":"小说"},{"id":"8","title":"廉价药价格飙涨 还得从产业链上游求解","img":"/Public/uploads/news/2017-09-29/59cdaecedc62a.jpg","view":"193","cate_name":"小说"}]
     * error_msg :
     */

    private int stu;
    private String code;
    private String error_msg;
    private List<ResBean> res;

    public int getStu() {
        return stu;
    }

    public void setStu(int stu) {
        this.stu = stu;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public List<ResBean> getRes() {
        return res;
    }

    public void setRes(List<ResBean> res) {
        this.res = res;
    }

    public static class ResBean {
        /**
         * id : 6
         * title : 国际油价反弹 化工品价格跟随原油走强
         * img : /Public/uploads/news/2017-09-29/59cdae249893f.jpg
         * view : 110
         * cate_name : 小说
         */

        private String id;
        private String title;
        private String img;
        private String view;
        private String cate_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public String getCate_name() {
            return cate_name;
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
        }
    }
}
