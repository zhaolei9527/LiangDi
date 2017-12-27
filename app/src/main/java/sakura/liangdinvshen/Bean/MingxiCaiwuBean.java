package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/23
 * 功能描述：
 */
public class MingxiCaiwuBean {

    /**
     * code : 1
     * list : [{"id":"97","add_time":"1514012639","change_num":"0.00","over_num":"9254.00","stu":"1"},{"id":"96","add_time":"1514012512","change_num":"12.00","over_num":"9254.00","stu":"1"},{"id":"95","add_time":"1514012387","change_num":"1.00","over_num":"9266.00","stu":"1"},{"id":"94","add_time":"1514010571","change_num":"1.00","over_num":"9267.00","stu":"1"},{"id":"93","add_time":"1514000520","change_num":"100.00","over_num":"9268.00","stu":"1"},{"id":"92","add_time":"1513997566","change_num":"1.00","over_num":"9368.00","stu":"1"},{"id":"91","add_time":"1513996739","change_num":"12.00","over_num":"9369.00","stu":"1"},{"id":"90","add_time":"1513995757","change_num":"100.00","over_num":"9381.00","stu":"1"},{"id":"89","add_time":"1513993512","change_num":"100.00","over_num":"9481.00","stu":"1"},{"id":"88","add_time":"1513993105","change_num":"0.00","over_num":"9581.00","stu":"1"},{"id":"87","add_time":"1513950129","change_num":"100.00","over_num":"9581.00","stu":"1"},{"id":"86","add_time":"1513950067","change_num":"1.00","over_num":"9681.00","stu":"1"}]
     * msg : 查询成功，返回数据
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
         * id : 97
         * add_time : 1514012639
         * change_num : 0.00
         * over_num : 9254.00
         * stu : 1
         */

        private String id;
        private String add_time;
        private String change_num;
        private String over_num;
        private String stu;

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

        public String getChange_num() {
            return change_num;
        }

        public void setChange_num(String change_num) {
            this.change_num = change_num;
        }

        public String getOver_num() {
            return over_num;
        }

        public void setOver_num(String over_num) {
            this.over_num = over_num;
        }

        public String getStu() {
            return stu;
        }

        public void setStu(String stu) {
            this.stu = stu;
        }
    }
}
