package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/25
 * 功能描述：
 */
public class WangCenterBean {

    /**
     * code : 1
     * list : [{"id":"2","add_time":"1501055560","img":"/Public/uploads/headimg/2017-12-23/5a3e00552dd98.jpg","ni_name":"tttttt","tel":"17629345001","last_login_time":"1514191377"},{"id":"140","add_time":"1501055560","img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt1","tel":"18638035535","last_login_time":"1514249820"},{"id":"141","add_time":"1501055560","img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt","tel":"18638035535","last_login_time":"1514009078"},{"id":"142","add_time":"1501055560","img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt","tel":"18638035535","last_login_time":"1514009078"},{"id":"143","add_time":"1501055560","img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt","tel":"18638035535","last_login_time":"1514009078"},{"id":"144","add_time":"1501055560","img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt","tel":"18638035535","last_login_time":"1514009078"},{"id":"145","add_time":"1501055560","img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt","tel":"18638035535","last_login_time":"1514009078"},{"id":"146","add_time":"1501055560","img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt","tel":"18638035535","last_login_time":"1514009078"},{"id":"147","add_time":"1501055560","img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt","tel":"18638035535","last_login_time":"1514009078"},{"id":"148","add_time":"1501055560","img":"/Public/uploads/headimg/2017-12-23/5a3df135afbb8.png","ni_name":"tttttt","tel":"18638035535","last_login_time":"1514009078"}]
     * msg : 查询数据成功，返回数据
     */

    private String code;
    private String msg;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 2
         * add_time : 1501055560
         * img : /Public/uploads/headimg/2017-12-23/5a3e00552dd98.jpg
         * ni_name : tttttt
         * tel : 17629345001
         * last_login_time : 1514191377
         */

        private String id;
        private String add_time;
        private String img;
        private String ni_name;
        private String tel;
        private String last_login_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

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

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }
    }
}
