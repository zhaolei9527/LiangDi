package sakura.liangdinvshen.Bean;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2018/1/6
 * 功能描述：
 */
public class GetVersionCode {
    /**
     * code : 1
     * res : {"az":{"version":"11","url":"HTTP://192.168.1.199/android/2017-10-25/59efd965af261.apk","content":"修改了部分bug,更新了部分内容"},"ios":{"version":"11","url":"www.cn77.cn","content":"修改了部分bug,更新了部分内容"}}
     */

    private String code;
    private ResBean res;

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

    public static class ResBean {
        /**
         * az : {"version":"11","url":"HTTP://192.168.1.199/android/2017-10-25/59efd965af261.apk","content":"修改了部分bug,更新了部分内容"}
         * ios : {"version":"11","url":"www.cn77.cn","content":"修改了部分bug,更新了部分内容"}
         */

        private AzBean az;
        private IosBean ios;

        public AzBean getAz() {
            return az;
        }

        public void setAz(AzBean az) {
            this.az = az;
        }

        public IosBean getIos() {
            return ios;
        }

        public void setIos(IosBean ios) {
            this.ios = ios;
        }

        public static class AzBean {
            /**
             * version : 11
             * url : HTTP://192.168.1.199/android/2017-10-25/59efd965af261.apk
             * content : 修改了部分bug,更新了部分内容
             */

            private String version;
            private String url;
            private String content;

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class IosBean {
            /**
             * version : 11
             * url : www.cn77.cn
             * content : 修改了部分bug,更新了部分内容
             */

            private String version;
            private String url;
            private String content;

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
