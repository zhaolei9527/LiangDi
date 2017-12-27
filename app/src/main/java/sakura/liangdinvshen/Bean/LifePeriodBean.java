package sakura.liangdinvshen.Bean;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/26
 * 功能描述：
 */
public class LifePeriodBean {


    /**
     * stu : 1
     * res : {"period_length":"5","period_cycle":"30"}
     */

    private int stu;
    private ResBean res;

    /**
     * res : {"pregnant_expected":"1500319607"}
     */

    public int getStu() {
        return stu;
    }

    public void setStu(int stu) {
        this.stu = stu;
    }

    public ResBean getRes() {
        return res;
    }

    public void setRes(ResBean res) {
        this.res = res;
    }


    public static class ResBean {
        /**
         * period_length : 5
         * period_cycle : 30
         */

        private String period_length;
        private String period_cycle;

        public String getPeriod_length() {
            return period_length;
        }

        public void setPeriod_length(String period_length) {
            this.period_length = period_length;
        }

        public String getPeriod_cycle() {
            return period_cycle;
        }

        public void setPeriod_cycle(String period_cycle) {
            this.period_cycle = period_cycle;
        }

        /**
         * pregnant_expected : 1500319607
         */

        private String pregnant_expected;

        public String getPregnant_expected() {
            return pregnant_expected;
        }

        public void setPregnant_expected(String pregnant_expected) {
            this.pregnant_expected = pregnant_expected;
        }

        /**
         * baby_sex : 1
         * baby_birthday : 1500319607
         * period_length : 5
         * period_cycle : 30
         */

        private String baby_sex;
        private String baby_birthday;

        public String getBaby_sex() {
            return baby_sex;
        }

        public void setBaby_sex(String baby_sex) {
            this.baby_sex = baby_sex;
        }

        public String getBaby_birthday() {
            return baby_birthday;
        }

        public void setBaby_birthday(String baby_birthday) {
            this.baby_birthday = baby_birthday;
        }


    }

}
