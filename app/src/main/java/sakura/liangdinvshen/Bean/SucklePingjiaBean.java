package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/9
 * 功能描述：
 */
public class SucklePingjiaBean {

    /**
     * stu : 1
     * res : [{"content":"一次就见效，真是好","xing":"5","hf":"谢谢您的支持","addtime":"1507877859","headimg":"/Public/uploads/headimg/default_img.png","nickname":"靓帝女神"}]
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
         * content : 一次就见效，真是好
         * xing : 5
         * hf : 谢谢您的支持
         * addtime : 1507877859
         * headimg : /Public/uploads/headimg/default_img.png
         * nickname : 靓帝女神
         */

        private String content;
        private String xing;
        private String hf;
        private String addtime;
        private String headimg;
        private String nickname;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getXing() {
            return xing;
        }

        public void setXing(String xing) {
            this.xing = xing;
        }

        public String getHf() {
            return hf;
        }

        public void setHf(String hf) {
            this.hf = hf;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
