package sakura.printersakura.Activity;

import sakura.printersakura.R;
import sakura.printersakura.base.BaseActivity;

/**
 * sakura.printersakura.Activity
 *
 * @author 赵磊
 * @date 2017/11/22
 * 功能描述：
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected void ready() {
        super.ready();
        fullScreen();
    }

    @Override
    protected int setthislayout() {
        return R.layout.activcity_login;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
