package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/29
 * 功能描述：
 */
public class ProblemGetProblemBean {


    /**
     * stu : 1
     * status : 1
     * res : [{"id":"15","problem_title":"叶酸片今天是否有按时吃","pregnancy_time":"1","add_time":"1508980575","save_time":"1508980575","status":"1"},{"id":"16","problem_title":"今天是否去医院做产检了","pregnancy_time":"1","add_time":"1508980764","save_time":"1508980839","status":"1"}]
     */

    private int stu;
    private int status;
    private List<ResBean> res;

    public int getStu() {
        return stu;
    }

    public void setStu(int stu) {
        this.stu = stu;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ResBean> getRes() {
        return res;
    }

    public void setRes(List<ResBean> res) {
        this.res = res;
    }

    public static class ResBean {
        /**
         * id : 15
         * problem_title : 叶酸片今天是否有按时吃
         * pregnancy_time : 1
         * add_time : 1508980575
         * save_time : 1508980575
         * status : 1
         */

        private String id;
        private String problem_title;
        private String pregnancy_time;
        private String add_time;
        private String save_time;
        private String status;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        private String type;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProblem_title() {
            return problem_title;
        }

        public void setProblem_title(String problem_title) {
            this.problem_title = problem_title;
        }

        public String getPregnancy_time() {
            return pregnancy_time;
        }

        public void setPregnancy_time(String pregnancy_time) {
            this.pregnancy_time = pregnancy_time;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getSave_time() {
            return save_time;
        }

        public void setSave_time(String save_time) {
            this.save_time = save_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}