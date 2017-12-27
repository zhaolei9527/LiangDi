package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/22
 * 功能描述：
 */
public class CollectionGoodsBean {

    /**
     * stu : 1
     * code : 101
     * res : [{"id":"17","title":"莫兰多肉植物 E家小屋 多肉防辐射绿植盆栽办公创意花卉","img":"/Public/uploads/goods/img/2017-10-13/59e05d3ab3010.jpg","price":"10","xiaoliang":"21","type":"商品","cang":"1"},{"id":"10","title":"秋冬高领貂绒衫女短款修身纯貂绒毛衣套头百搭羊绒针织打底衫加厚","img":"/Public/uploads/goods/img/2017-09-25/59c870baa8818.jpg","price":"1","xiaoliang":"44","type":"商品","cang":"1"},{"id":"12","title":"出口外贸尾单秋冬羊绒衫女毛衣加厚高领菠萝针织衫打底外套保暖衫","img":"/Public/uploads/goods/img/2017-10-13/59e05bda67cf0.jpg","price":"12","xiaoliang":"69","type":"商品","cang":"21"}]
     */

    private int stu;
    private String code;
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

    public List<ResBean> getRes() {
        return res;
    }

    public void setRes(List<ResBean> res) {
        this.res = res;
    }

    public static class ResBean {
        /**
         * id : 17
         * title : 莫兰多肉植物 E家小屋 多肉防辐射绿植盆栽办公创意花卉
         * img : /Public/uploads/goods/img/2017-10-13/59e05d3ab3010.jpg
         * price : 10
         * xiaoliang : 21
         * type : 商品
         * cang : 1
         */

        private String id;
        private String title;
        private String img;
        private String price;
        private String xiaoliang;
        private String type;
        private String cang;

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getXiaoliang() {
            return xiaoliang;
        }

        public void setXiaoliang(String xiaoliang) {
            this.xiaoliang = xiaoliang;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCang() {
            return cang;
        }

        public void setCang(String cang) {
            this.cang = cang;
        }
    }
}
