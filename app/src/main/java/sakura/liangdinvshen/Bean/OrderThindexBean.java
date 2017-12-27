package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/22
 * 功能描述：
 */
public class OrderThindexBean {


    /**
     * stu : 1
     * code : 111
     * res : [{"id":"12","gid":"12","ogid":"22","addtime":"1513922610","status":"1","number":"1","type":"2","gimg":"/Public/uploads/goods/img/2017-10-13/59e05bda67cf0.jpg","price":"12.00","title":"出口外贸尾单秋冬羊绒衫女毛衣加厚高领菠萝针织衫打底外套保暖衫","gstu":"1"},{"id":"11","gid":"12","ogid":"23","addtime":"1513922482","status":"1","number":"1","type":"2","gimg":"/Public/uploads/goods/img/2017-10-13/59e05bda67cf0.jpg","price":"12.00","title":"出口外贸尾单秋冬羊绒衫女毛衣加厚高领菠萝针织衫打底外套保暖衫","gstu":"1"},{"id":"10","gid":"10","ogid":"20","addtime":"1513922110","status":"1","number":"1","type":"2","gimg":"/Public/uploads/goods/img/2017-09-25/59c870baa8818.jpg","price":"1.00","title":"秋冬高领貂绒衫女短款修身纯貂绒毛衣套头百搭羊绒针织打底衫加厚","gstu":"1"},{"id":"9","gid":"12","ogid":"18","addtime":"1513921622","status":"1","number":"1","type":"2","gimg":"/Public/uploads/goods/img/2017-10-13/59e05bda67cf0.jpg","price":"12.00","title":"出口外贸尾单秋冬羊绒衫女毛衣加厚高领菠萝针织衫打底外套保暖衫","gstu":"1"},{"id":"8","gid":"12","ogid":"19","addtime":"1513921583","status":"1","number":"1","type":"2","gimg":"/Public/uploads/goods/img/2017-10-13/59e05bda67cf0.jpg","price":"12.00","title":"出口外贸尾单秋冬羊绒衫女毛衣加厚高领菠萝针织衫打底外套保暖衫","gstu":"1"},{"id":"7","gid":"12","ogid":"15","addtime":"1513920780","status":"1","number":"1","type":"2","gimg":"/Public/uploads/goods/img/2017-10-13/59e05bda67cf0.jpg","price":"12.00","title":"出口外贸尾单秋冬羊绒衫女毛衣加厚高领菠萝针织衫打底外套保暖衫","gstu":"1"},{"id":"6","gid":"12","ogid":"16","addtime":"1513920473","status":"1","number":"1","type":"2","gimg":"/Public/uploads/goods/img/2017-10-13/59e05bda67cf0.jpg","price":"12.00","title":"出口外贸尾单秋冬羊绒衫女毛衣加厚高领菠萝针织衫打底外套保暖衫","gstu":"1"},{"id":"5","gid":"12","ogid":"17","addtime":"1513920336","status":"1","number":"1","type":"2","gimg":"/Public/uploads/goods/img/2017-10-13/59e05bda67cf0.jpg","price":"12.00","title":"出口外贸尾单秋冬羊绒衫女毛衣加厚高领菠萝针织衫打底外套保暖衫","gstu":"1"},{"id":"4","gid":"12","ogid":"12","addtime":"1513920140","status":"1","number":"1","type":"2","gimg":"/Public/uploads/goods/img/2017-10-13/59e05bda67cf0.jpg","price":"12.00","title":"出口外贸尾单秋冬羊绒衫女毛衣加厚高领菠萝针织衫打底外套保暖衫","gstu":"1"},{"id":"3","gid":"12","ogid":"13","addtime":"1513919973","status":"1","number":"1","type":"2","gimg":"/Public/uploads/goods/img/2017-10-13/59e05bda67cf0.jpg","price":"12.00","title":"出口外贸尾单秋冬羊绒衫女毛衣加厚高领菠萝针织衫打底外套保暖衫","gstu":"1"}]
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
         * id : 12
         * gid : 12
         * ogid : 22
         * addtime : 1513922610
         * status : 1
         * number : 1
         * type : 2
         * gimg : /Public/uploads/goods/img/2017-10-13/59e05bda67cf0.jpg
         * price : 12.00
         * title : 出口外贸尾单秋冬羊绒衫女毛衣加厚高领菠萝针织衫打底外套保暖衫
         * gstu : 1
         */

        private String id;
        private String gid;
        private String ogid;
        private String addtime;
        private String status;
        private String number;
        private String type;
        private String gimg;
        private String price;
        private String title;
        private String gstu;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getOgid() {
            return ogid;
        }

        public void setOgid(String ogid) {
            this.ogid = ogid;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getGimg() {
            return gimg;
        }

        public void setGimg(String gimg) {
            this.gimg = gimg;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGstu() {
            return gstu;
        }

        public void setGstu(String gstu) {
            this.gstu = gstu;
        }
    }
}
