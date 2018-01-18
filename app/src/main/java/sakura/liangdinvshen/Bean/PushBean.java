package sakura.liangdinvshen.Bean;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2018/1/17
 * 功能描述：
 */
public class PushBean {

    public String getPush_id() {
        return push_id;
    }

    public void setPush_id(String push_id) {
        this.push_id = push_id;
    }

    /**
     * id : 12
     * img : \/Public\/uploads\/headimg\/2018-01-08\/5a52fd864aed2.jpg
     */

    private String push_id;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
