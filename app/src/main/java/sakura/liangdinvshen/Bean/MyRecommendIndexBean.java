package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/23
 * 功能描述：
 */
public class MyRecommendIndexBean {

    /**
     * stu : 1
     * res : [{"img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt1","add_time":"1501055560"},{"img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt","add_time":"1501055560"},{"img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt","add_time":"1501055560"},{"img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt","add_time":"1501055560"},{"img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt","add_time":"1501055560"},{"img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt","add_time":"1501055560"},{"img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt","add_time":"1501055560"},{"img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt","add_time":"1501055560"},{"img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt","add_time":"1501055560"},{"img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt","add_time":"1501055560"}]
     */

    private int stu;
    private List<ResBean> res;

    public int getStu() {
        return stu;
    }

    public void setStu(int stu) {
        this.stu = stu;
    }

    public List<ResBean> getRes() {
        return res;
    }

    public void setRes(List<ResBean> res) {
        this.res = res;
    }

    public static class ResBean {
        /**
         * img : /Public/uploads/headimg/2017-12-23/5a3df135afbb8.png
         * ni_name : tttttt1
         * add_time : 1501055560
         */

        private String img;
        private String ni_name;
        private String add_time;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getNi_name() {
            return ni_name;
        }

        public void setNi_name(String ni_name) {
            this.ni_name = ni_name;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
