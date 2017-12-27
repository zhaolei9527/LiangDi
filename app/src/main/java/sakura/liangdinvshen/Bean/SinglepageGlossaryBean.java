package sakura.liangdinvshen.Bean;

import java.util.List;

/**
 * sakura.liangdinvshen.Bean
 *
 * @author 赵磊
 * @date 2017/12/27
 * 功能描述：
 */
public class SinglepageGlossaryBean {


    /**
     * stu : 1
     * data : [{"id":"1","title":"月经期","content":"潇洒的大姨妈来了，痛经，腰酸背痛，心烦气躁等症状也随之而来，这个时候要注意不能太累，不可吃生冷的食物哦，但是生理期也是丰胸和增强记忆力的最佳时段呐","add_time":null,"save_time":null},{"id":"2","title":"预测经期","content":"预测经期是指靓帝女神根据您的生理周期状况智能推算的月经预算期","add_time":null,"save_time":null},{"id":"3","title":"排卵期","content":"排卵期一般在下次大姨妈驾到之前的14天左右，排卵日期的前5天和后4天，连同排卵日在内的共10天称为排卵期，即易孕期，这个时候你身体的每一个细胞都在呼喊着：我要怀孕！巴特，如果你还没准备好做妈妈的话，爱爱时一定要采取避孕措施哦","add_time":null,"save_time":null},{"id":"4","title":"妊娠期","content":"亦称怀孕期，生理学名称，是胚胎和胎儿在母体内发育成熟的过程，从卵子受精开始至胎儿及其附属物自母体排出之间的一段时间，为了便于计算，妊娠期通常从末次月经的第一天算起，约为280天（4周）","add_time":null,"save_time":null}]
     */

    private int stu;
    private List<DataBean> data;

    public int getStu() {
        return stu;
    }

    public void setStu(int stu) {
        this.stu = stu;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * title : 月经期
         * content : 潇洒的大姨妈来了，痛经，腰酸背痛，心烦气躁等症状也随之而来，这个时候要注意不能太累，不可吃生冷的食物哦，但是生理期也是丰胸和增强记忆力的最佳时段呐
         * add_time : null
         * save_time : null
         */

        private String id;
        private String title;
        private String content;
        private Object add_time;
        private Object save_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getAdd_time() {
            return add_time;
        }

        public void setAdd_time(Object add_time) {
            this.add_time = add_time;
        }

        public Object getSave_time() {
            return save_time;
        }

        public void setSave_time(Object save_time) {
            this.save_time = save_time;
        }
    }
}
