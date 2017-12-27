package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/11/30
 * 功能描述：
 */
public class NewsListBean {

    /**
     * code : 1
     * res : [{"id":"8","title":"廉价药价格飙涨 还得从产业链上游求解","add_time":"1502418676","keywords":"家庭常用药开始涨价","img":"/Public/uploads/news/2017-09-29/59cdaecedc62a.jpg","view":"40","cid":"2","cname":"小说"},{"id":"6","title":"国际油价反弹 化工品价格跟随原油走强","add_time":"1502414064","keywords":"国际油价反弹","img":"/Public/uploads/news/2017-09-29/59cdae249893f.jpg","view":"69","cid":"2","cname":"小说"},{"id":"2","title":"化工园区大项目建设再获突破","add_time":"1496481828","keywords":"化工园区大项目建设再获突破","img":"/Public/uploads/news/2017-09-29/59cdaed7c9f49.jpg","view":"50","cid":"2","cname":"小说"}]
     * cate : [{"id":"2","cate_name":"小说"},{"id":"5","cate_name":"大姨妈"},{"id":"4","cate_name":"育儿"},{"id":"1","cate_name":"情感"}]
     * msg : 查询数据成功，返回数据
     */

    private String code;
    private String msg;
    private List<ResBean> res;
    private List<CateBean> cate;

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

    public List<ResBean> getRes() {
        return res;
    }

    public void setRes(List<ResBean> res) {
        this.res = res;
    }

    public List<CateBean> getCate() {
        return cate;
    }

    public void setCate(List<CateBean> cate) {
        this.cate = cate;
    }

    public static class ResBean {
        /**
         * id : 8
         * title : 廉价药价格飙涨 还得从产业链上游求解
         * add_time : 1502418676
         * keywords : 家庭常用药开始涨价
         * img : /Public/uploads/news/2017-09-29/59cdaecedc62a.jpg
         * view : 40
         * cid : 2
         * cname : 小说
         */

        private String id;
        private String title;
        private String add_time;
        private String keywords;
        private String img;
        private String view;
        private String cid;
        private String cname;

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

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
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

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
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
