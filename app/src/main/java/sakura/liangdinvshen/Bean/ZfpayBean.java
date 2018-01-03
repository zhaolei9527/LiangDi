package sakura.liangdinvshen.Bean;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2018/1/3
 * 功能描述：
 */
public class ZfpayBean {

    /**
     * code : 1
     * msg : 请求成功返回
     * res : app_id=2017122201058462&biz_content=%7B%22body%22%3A%22%5Cu9753%5Cu5e1d%5Cu5973%5Cu795e-1514966382552087%22%2C%22subject%22%3A%22%5Cu9753%5Cu5e1d%5Cu5973%5Cu795e-1514966382552087%22%2C%22out_trade_no%22%3A%221514966382552087%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A0.01%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&method=alipay.trade.app.pay&sign_type=RSA2&timestamp=2018-01-03+15%3A59%3A42&version=1.0&sign=XA2e%2Ba7BcMDfzyR%2BHGt1Ro6JjiTm%2BviIide%2FLEpdsJ0eSCkWayLNJh4iRYdMIp3llc8nxe81sKm0k0QaOrnWPUdisGClluG7tE%2BLdPn9BuBN1d5hHJNGG4H4VkYF8JTicGRxTH%2BaYl3QD6pJ9m2j1qS1qNBXFvo0hRnrg42%2FI3ZNqsu6Ybmmt5Cfps0xxNHiSTtTd0uLVYOySoveuKrfEDexyhWWGTWHkUdZEegaU5WjXTojIbBVaB0d7PsFNwFdeKrYSzHA02LgIqAL82Soa6JXk8Wl6gCHOh1bQPq25fj7i0pJOkxkdeRW7k5nG%2BghZO2xL293nm%2BKMzQDnd5Ydg%3D%3D
     */

    private String code;
    private String msg;
    private String res;

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

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }
}
