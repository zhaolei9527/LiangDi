package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2018/1/5
 * 功能描述：
 */
public class DdpicIndexBean {

    /**
     * code : 1
     * res : {"id":"92","belly_photo":["/Public/uploads/retreat/2018-01-05/5a4f0af3dbada.png"]}
     * msg : 大肚照查询成功
     */

    private String code;
    private ResBean res;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ResBean getRes() {
        return res;
    }

    public void setRes(ResBean res) {
        this.res = res;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ResBean {
        /**
         * id : 92
         * belly_photo : ["/Public/uploads/retreat/2018-01-05/5a4f0af3dbada.png"]
         */

        private String id;
        private List<String> belly_photo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getBelly_photo() {
            return belly_photo;
        }

        public void setBelly_photo(List<String> belly_photo) {
            this.belly_photo = belly_photo;
        }
    }
}
