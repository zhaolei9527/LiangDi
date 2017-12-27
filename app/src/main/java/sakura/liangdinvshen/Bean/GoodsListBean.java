package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/7
 * 功能描述：
 */
public class GoodsListBean {

    /**
     * status : 1
     * res : [{"id":"12","xiaoliang":"21","img":"/Public/uploads/goods/img/2017-10-13/59e05bd6f3f20.jpg","title":"出口外贸尾单秋冬羊绒衫女毛衣加厚高领菠萝针织衫打底外套保暖衫","type":"1","price":"12"},{"id":"10","xiaoliang":"19","img":"/Public/uploads/goods/img/2017-09-25/59c870baa8818.jpg","title":"秋冬高领貂绒衫女短款修身纯貂绒毛衣套头百搭羊绒针织打底衫加厚","type":"1","price":"1"},{"id":"17","xiaoliang":"15","img":"/Public/uploads/goods/img/2017-10-13/59e05d3ab3010.jpg","title":"莫兰多肉植物 E家小屋 多肉防辐射绿植盆栽办公创意花卉","type":"1","price":"10"},{"id":"1","xiaoliang":"73","img":"/Public/uploads/goods/img/2017-10-09/59dac6417a8f0.jpg","title":"测试新增商品","type":"1","price":"100"},{"id":"2","xiaoliang":"76","img":"/Public/uploads/goods/img/2017-10-09/59dac6602d690.jpg","title":"化工布料","type":"1","price":"199"},{"id":"3","xiaoliang":"73","img":"/Public/uploads/goods/img/2017-10-09/59dac67e86470.jpg","title":"心血康2","type":"1","price":"33"},{"id":"4","xiaoliang":"70","img":"/Public/uploads/goods/img/2017-10-09/59dac625aaa78.jpg","title":"硫化锌","type":"1","price":"199"},{"id":"5","xiaoliang":"67","img":"/Public/uploads/goods/img/2017-10-09/59dac45e6ed70.jpg","title":"贝莱投资标志设计作品","type":"1","price":"100"},{"id":"6","xiaoliang":"74","img":"/Public/uploads/goods/img/2017-09-25/59c87060df700.jpg","title":"小蓝屏","type":"2","price":"100"},{"id":"7","xiaoliang":"68","img":"/Public/uploads/goods/img/2017-10-09/59dac609c96a8.jpg","title":"小黑瓶","type":"1","price":"100"}]
     * topimg : {"id":"2","title":"商品街头部图片","img":"/Public/uploads/ad/2017-10-13/59e0287a99db8.jpg","url":"","edittime":"1493893356","sort":"0"}
     * code : 302
     */

    private String status;
    private TopimgBean topimg;
    private String code;
    private List<ResBean> res;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TopimgBean getTopimg() {
        return topimg;
    }

    public void setTopimg(TopimgBean topimg) {
        this.topimg = topimg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ResBean> getRes() {
        return res;
    }

    public void setRes(List<ResBean> res) {
        this.res = res;
    }

    public static class TopimgBean {
        /**
         * id : 2
         * title : 商品街头部图片
         * img : /Public/uploads/ad/2017-10-13/59e0287a99db8.jpg
         * url :
         * edittime : 1493893356
         * sort : 0
         */

        private String id;
        private String title;
        private String img;
        private String url;
        private String edittime;
        private String sort;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getEdittime() {
            return edittime;
        }

        public void setEdittime(String edittime) {
            this.edittime = edittime;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }
    }

    public static class ResBean {
        /**
         * id : 12
         * xiaoliang : 21
         * img : /Public/uploads/goods/img/2017-10-13/59e05bd6f3f20.jpg
         * title : 出口外贸尾单秋冬羊绒衫女毛衣加厚高领菠萝针织衫打底外套保暖衫
         * type : 1
         * price : 12
         */

        private String id;
        private String xiaoliang;
        private String img;
        private String title;
        private String type;
        private String price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getXiaoliang() {
            return xiaoliang;
        }

        public void setXiaoliang(String xiaoliang) {
            this.xiaoliang = xiaoliang;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
