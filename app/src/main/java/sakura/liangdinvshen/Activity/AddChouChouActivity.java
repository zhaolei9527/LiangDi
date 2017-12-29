package sakura.liangdinvshen.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import sakura.liangdinvshen.Base.BaseActivity;
import sakura.liangdinvshen.Bean.BankEvent;
import sakura.liangdinvshen.Bean.StuBean;
import sakura.liangdinvshen.Fragment.RecordFragment;
import sakura.liangdinvshen.R;
import sakura.liangdinvshen.Utils.EasyToast;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.Utils.Utils;
import sakura.liangdinvshen.Volley.VolleyInterface;
import sakura.liangdinvshen.Volley.VolleyRequest;

/**
 * sakura.liangdinvshen.Activity
 *
 * @author 赵磊
 * @date 2017/12/27
 * 功能描述：
 */
public class AddChouChouActivity extends BaseActivity {

    private FrameLayout rl_back;
    private ImageView img_tongjing;
    private TextView tv_start;
    private RelativeLayout rl_start_time;
    private ImageView img_molv;
    private LinearLayout ll_molv;
    private ImageView img_huangse;
    private LinearLayout ll_huangse;
    private ImageView img_lvse;
    private LinearLayout ll_lvse;
    private ImageView img_hongse;
    private LinearLayout ll_hongse;
    private ImageView img_heise;
    private LinearLayout ll_heise;
    private ImageView img_danhuang;
    private LinearLayout ll_danhuang;
    private ImageView img_huanglv;
    private LinearLayout ll_huanglv;
    private ImageView img_anhe;
    private LinearLayout ll_anhe;
    private ImageView img_anhong;
    private LinearLayout ll_anhong;
    private ImageView img_lvhe;
    private LinearLayout ll_lvhe;
    private ImageView img_fense;
    private LinearLayout ll_fense;
    private ImageView img_huibai;
    private LinearLayout ll_huibai;
    private ImageView img_huzhuang;
    private LinearLayout ll_huzhuang;
    private ImageView img_ganchou;
    private LinearLayout ll_ganchou;
    private ImageView img_gaozhuang;
    private LinearLayout ll_gaozhuang;
    private ImageView img_fenzhuang;
    private LinearLayout ll_fenzhuang;
    private ImageView img_naiban;
    private LinearLayout ll_naiban;
    private ImageView img_shuifen;
    private LinearLayout ll_shuifen;
    private ImageView img_paomo;
    private LinearLayout ll_paomo;
    private ImageView img_nitu;
    private LinearLayout ll_nitu;
    private ImageView img_keli;
    private LinearLayout ll_keli;
    private ImageView img_boyou;
    private LinearLayout ll_boyou;
    private ImageView img_guojiang;
    private LinearLayout ll_guojiang;
    private ImageView img_doufuzha;
    private LinearLayout ll_doufuzha;
    private ImageView img_danhuatang;
    private LinearLayout ll_danhuatang;
    private ImageView img_biti;
    private LinearLayout ll_biti;
    private ImageView img_liqing;
    private LinearLayout ll_liqing;
    private Button btn_submit;


    @Override
    protected int setthislayout() {
        return R.layout.activity_addchouchou;
    }

    @Override
    protected void initview() {
        btn_submit = (Button) findViewById(R.id.btn_submit);
        rl_back = (FrameLayout) findViewById(R.id.rl_back);
        img_tongjing = (ImageView) findViewById(R.id.img_tongjing);
        tv_start = (TextView) findViewById(R.id.tv_start);
        rl_start_time = (RelativeLayout) findViewById(R.id.rl_start_time);
        img_molv = (ImageView) findViewById(R.id.img_molv);
        ll_molv = (LinearLayout) findViewById(R.id.ll_molv);
        img_huangse = (ImageView) findViewById(R.id.img_huangse);
        ll_huangse = (LinearLayout) findViewById(R.id.ll_huangse);
        img_lvse = (ImageView) findViewById(R.id.img_lvse);
        ll_lvse = (LinearLayout) findViewById(R.id.ll_lvse);
        img_hongse = (ImageView) findViewById(R.id.img_hongse);
        ll_hongse = (LinearLayout) findViewById(R.id.ll_hongse);
        img_heise = (ImageView) findViewById(R.id.img_heise);
        ll_heise = (LinearLayout) findViewById(R.id.ll_heise);
        img_danhuang = (ImageView) findViewById(R.id.img_danhuang);
        ll_danhuang = (LinearLayout) findViewById(R.id.ll_danhuang);
        img_huanglv = (ImageView) findViewById(R.id.img_huanglv);
        ll_huanglv = (LinearLayout) findViewById(R.id.ll_huanglv);
        img_anhe = (ImageView) findViewById(R.id.img_anhe);
        ll_anhe = (LinearLayout) findViewById(R.id.ll_anhe);
        img_anhong = (ImageView) findViewById(R.id.img_anhong);
        ll_anhong = (LinearLayout) findViewById(R.id.ll_anhong);
        img_lvhe = (ImageView) findViewById(R.id.img_lvhe);
        ll_lvhe = (LinearLayout) findViewById(R.id.ll_lvhe);
        img_fense = (ImageView) findViewById(R.id.img_fense);
        ll_fense = (LinearLayout) findViewById(R.id.ll_fense);
        img_huibai = (ImageView) findViewById(R.id.img_huibai);
        ll_huibai = (LinearLayout) findViewById(R.id.ll_huibai);
        img_huzhuang = (ImageView) findViewById(R.id.img_huzhuang);
        ll_huzhuang = (LinearLayout) findViewById(R.id.ll_huzhuang);
        img_ganchou = (ImageView) findViewById(R.id.img_ganchou);
        ll_ganchou = (LinearLayout) findViewById(R.id.ll_ganchou);
        img_gaozhuang = (ImageView) findViewById(R.id.img_gaozhuang);
        ll_gaozhuang = (LinearLayout) findViewById(R.id.ll_gaozhuang);
        img_fenzhuang = (ImageView) findViewById(R.id.img_fenzhuang);
        ll_fenzhuang = (LinearLayout) findViewById(R.id.ll_fenzhuang);
        img_naiban = (ImageView) findViewById(R.id.img_naiban);
        ll_naiban = (LinearLayout) findViewById(R.id.ll_naiban);
        img_shuifen = (ImageView) findViewById(R.id.img_shuifen);
        ll_shuifen = (LinearLayout) findViewById(R.id.ll_shuifen);
        img_paomo = (ImageView) findViewById(R.id.img_paomo);
        ll_paomo = (LinearLayout) findViewById(R.id.ll_paomo);
        img_nitu = (ImageView) findViewById(R.id.img_nitu);
        ll_nitu = (LinearLayout) findViewById(R.id.ll_nitu);
        img_keli = (ImageView) findViewById(R.id.img_keli);
        ll_keli = (LinearLayout) findViewById(R.id.ll_keli);
        img_boyou = (ImageView) findViewById(R.id.img_boyou);
        ll_boyou = (LinearLayout) findViewById(R.id.ll_boyou);
        img_guojiang = (ImageView) findViewById(R.id.img_guojiang);
        ll_guojiang = (LinearLayout) findViewById(R.id.ll_guojiang);
        img_doufuzha = (ImageView) findViewById(R.id.img_doufuzha);
        ll_doufuzha = (LinearLayout) findViewById(R.id.ll_doufuzha);
        img_danhuatang = (ImageView) findViewById(R.id.img_danhuatang);
        ll_danhuatang = (LinearLayout) findViewById(R.id.ll_danhuatang);
        img_biti = (ImageView) findViewById(R.id.img_biti);
        ll_biti = (LinearLayout) findViewById(R.id.ll_biti);
        img_liqing = (ImageView) findViewById(R.id.img_liqing);
        ll_liqing = (LinearLayout) findViewById(R.id.ll_liqing);
    }

    @Override
    protected void initListener() {
        ll_molv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_id = "墨绿色";
                img_molv.setVisibility(View.VISIBLE);
                img_huangse.setVisibility(View.INVISIBLE);
                img_lvse.setVisibility(View.INVISIBLE);
                img_hongse.setVisibility(View.INVISIBLE);
                img_heise.setVisibility(View.INVISIBLE);
                img_danhuang.setVisibility(View.INVISIBLE);
                img_huanglv.setVisibility(View.INVISIBLE);
                img_anhe.setVisibility(View.INVISIBLE);
                img_anhong.setVisibility(View.INVISIBLE);
                img_lvhe.setVisibility(View.INVISIBLE);
                img_fense.setVisibility(View.INVISIBLE);
                img_huibai.setVisibility(View.INVISIBLE);
            }
        });

        ll_huangse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_id = "黄色";
                img_molv.setVisibility(View.INVISIBLE);
                img_huangse.setVisibility(View.VISIBLE);
                img_lvse.setVisibility(View.INVISIBLE);
                img_hongse.setVisibility(View.INVISIBLE);
                img_heise.setVisibility(View.INVISIBLE);
                img_danhuang.setVisibility(View.INVISIBLE);
                img_huanglv.setVisibility(View.INVISIBLE);
                img_anhe.setVisibility(View.INVISIBLE);
                img_anhong.setVisibility(View.INVISIBLE);
                img_lvhe.setVisibility(View.INVISIBLE);
                img_fense.setVisibility(View.INVISIBLE);
                img_huibai.setVisibility(View.INVISIBLE);
            }
        });

        ll_lvse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_id = "绿色";
                img_molv.setVisibility(View.INVISIBLE);
                img_huangse.setVisibility(View.INVISIBLE);
                img_lvse.setVisibility(View.VISIBLE);
                img_hongse.setVisibility(View.INVISIBLE);
                img_heise.setVisibility(View.INVISIBLE);
                img_danhuang.setVisibility(View.INVISIBLE);
                img_huanglv.setVisibility(View.INVISIBLE);
                img_anhe.setVisibility(View.INVISIBLE);
                img_anhong.setVisibility(View.INVISIBLE);
                img_lvhe.setVisibility(View.INVISIBLE);
                img_fense.setVisibility(View.INVISIBLE);
                img_huibai.setVisibility(View.INVISIBLE);
            }
        });


        ll_hongse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_id = "红色";
                img_molv.setVisibility(View.INVISIBLE);
                img_huangse.setVisibility(View.INVISIBLE);
                img_lvse.setVisibility(View.INVISIBLE);
                img_hongse.setVisibility(View.VISIBLE);
                img_heise.setVisibility(View.INVISIBLE);
                img_danhuang.setVisibility(View.INVISIBLE);
                img_huanglv.setVisibility(View.INVISIBLE);
                img_anhe.setVisibility(View.INVISIBLE);
                img_anhong.setVisibility(View.INVISIBLE);
                img_lvhe.setVisibility(View.INVISIBLE);
                img_fense.setVisibility(View.INVISIBLE);
                img_huibai.setVisibility(View.INVISIBLE);
            }
        });

        ll_heise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_id = "黑色";
                img_molv.setVisibility(View.INVISIBLE);
                img_huangse.setVisibility(View.INVISIBLE);
                img_lvse.setVisibility(View.INVISIBLE);
                img_hongse.setVisibility(View.INVISIBLE);
                img_heise.setVisibility(View.VISIBLE);
                img_danhuang.setVisibility(View.INVISIBLE);
                img_huanglv.setVisibility(View.INVISIBLE);
                img_anhe.setVisibility(View.INVISIBLE);
                img_anhong.setVisibility(View.INVISIBLE);
                img_lvhe.setVisibility(View.INVISIBLE);
                img_fense.setVisibility(View.INVISIBLE);
                img_huibai.setVisibility(View.INVISIBLE);
            }
        });


        ll_danhuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_id = "蛋黄色";
                img_molv.setVisibility(View.INVISIBLE);
                img_huangse.setVisibility(View.INVISIBLE);
                img_lvse.setVisibility(View.INVISIBLE);
                img_hongse.setVisibility(View.INVISIBLE);
                img_heise.setVisibility(View.INVISIBLE);
                img_danhuang.setVisibility(View.VISIBLE);
                img_huanglv.setVisibility(View.INVISIBLE);
                img_anhe.setVisibility(View.INVISIBLE);
                img_anhong.setVisibility(View.INVISIBLE);
                img_lvhe.setVisibility(View.INVISIBLE);
                img_fense.setVisibility(View.INVISIBLE);
                img_huibai.setVisibility(View.INVISIBLE);
            }
        });

        ll_huanglv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_id = "黄绿色";
                img_molv.setVisibility(View.INVISIBLE);
                img_huangse.setVisibility(View.INVISIBLE);
                img_lvse.setVisibility(View.INVISIBLE);
                img_hongse.setVisibility(View.INVISIBLE);
                img_heise.setVisibility(View.INVISIBLE);
                img_danhuang.setVisibility(View.INVISIBLE);
                img_huanglv.setVisibility(View.VISIBLE);
                img_anhe.setVisibility(View.INVISIBLE);
                img_anhong.setVisibility(View.INVISIBLE);
                img_lvhe.setVisibility(View.INVISIBLE);
                img_fense.setVisibility(View.INVISIBLE);
                img_huibai.setVisibility(View.INVISIBLE);
            }
        });


        ll_anhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_id = "暗褐色";
                img_molv.setVisibility(View.INVISIBLE);
                img_huangse.setVisibility(View.INVISIBLE);
                img_lvse.setVisibility(View.INVISIBLE);
                img_hongse.setVisibility(View.INVISIBLE);
                img_heise.setVisibility(View.INVISIBLE);
                img_danhuang.setVisibility(View.INVISIBLE);
                img_huanglv.setVisibility(View.INVISIBLE);
                img_anhe.setVisibility(View.VISIBLE);
                img_anhong.setVisibility(View.INVISIBLE);
                img_lvhe.setVisibility(View.INVISIBLE);
                img_fense.setVisibility(View.INVISIBLE);
                img_huibai.setVisibility(View.INVISIBLE);
            }
        });


        ll_anhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_id = "暗红色";
                img_molv.setVisibility(View.INVISIBLE);
                img_huangse.setVisibility(View.INVISIBLE);
                img_lvse.setVisibility(View.INVISIBLE);
                img_hongse.setVisibility(View.INVISIBLE);
                img_heise.setVisibility(View.INVISIBLE);
                img_danhuang.setVisibility(View.INVISIBLE);
                img_huanglv.setVisibility(View.INVISIBLE);
                img_anhe.setVisibility(View.INVISIBLE);
                img_anhong.setVisibility(View.VISIBLE);
                img_lvhe.setVisibility(View.INVISIBLE);
                img_fense.setVisibility(View.INVISIBLE);
                img_huibai.setVisibility(View.INVISIBLE);
            }
        });


        ll_lvhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_id = "绿褐色";
                img_molv.setVisibility(View.INVISIBLE);
                img_huangse.setVisibility(View.INVISIBLE);
                img_lvse.setVisibility(View.INVISIBLE);
                img_hongse.setVisibility(View.INVISIBLE);
                img_heise.setVisibility(View.INVISIBLE);
                img_danhuang.setVisibility(View.INVISIBLE);
                img_huanglv.setVisibility(View.INVISIBLE);
                img_anhe.setVisibility(View.INVISIBLE);
                img_anhong.setVisibility(View.INVISIBLE);
                img_lvhe.setVisibility(View.VISIBLE);
                img_fense.setVisibility(View.INVISIBLE);
                img_huibai.setVisibility(View.INVISIBLE);
            }
        });


        ll_fense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_id = "粉红色";
                img_molv.setVisibility(View.INVISIBLE);
                img_huangse.setVisibility(View.INVISIBLE);
                img_lvse.setVisibility(View.INVISIBLE);
                img_hongse.setVisibility(View.INVISIBLE);
                img_heise.setVisibility(View.INVISIBLE);
                img_danhuang.setVisibility(View.INVISIBLE);
                img_huanglv.setVisibility(View.INVISIBLE);
                img_anhe.setVisibility(View.INVISIBLE);
                img_anhong.setVisibility(View.INVISIBLE);
                img_lvhe.setVisibility(View.INVISIBLE);
                img_fense.setVisibility(View.VISIBLE);
                img_huibai.setVisibility(View.INVISIBLE);
            }
        });


        ll_huibai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_id = "灰白色";
                img_molv.setVisibility(View.INVISIBLE);
                img_huangse.setVisibility(View.INVISIBLE);
                img_lvse.setVisibility(View.INVISIBLE);
                img_hongse.setVisibility(View.INVISIBLE);
                img_heise.setVisibility(View.INVISIBLE);
                img_danhuang.setVisibility(View.INVISIBLE);
                img_huanglv.setVisibility(View.INVISIBLE);
                img_anhe.setVisibility(View.INVISIBLE);
                img_anhong.setVisibility(View.INVISIBLE);
                img_lvhe.setVisibility(View.INVISIBLE);
                img_fense.setVisibility(View.INVISIBLE);
                img_huibai.setVisibility(View.VISIBLE);
            }
        });

        img_huzhuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shape_id = "糊状";
                img_huzhuang.setBackgroundResource(R.drawable.bianbian_icon_huzhuang_checked);
                img_ganchou.setBackgroundResource(R.drawable.bianbian_icon_ganchou);
                img_gaozhuang.setBackgroundResource(R.drawable.bianbian_icon_gaozhuang);
                img_fenzhuang.setBackgroundResource(R.drawable.bianbian_icon_fenzhuang);
                img_naiban.setBackgroundResource(R.drawable.bianbian_icon_naiban);
                img_shuifen.setBackgroundResource(R.drawable.bianbian_icon_shuifen);
                img_paomo.setBackgroundResource(R.drawable.bianbian_icon_paomo);
                img_nitu.setBackgroundResource(R.drawable.bianbian_icon_liti);
                img_keli.setBackgroundResource(R.drawable.bianbian_icon_yangbian);
                img_boyou.setBackgroundResource(R.drawable.bianbian_icon_boyou);
                img_guojiang.setBackgroundResource(R.drawable.bianbian_icon_guojiang);
                img_doufuzha.setBackgroundResource(R.drawable.bianbian_icon_doufuzha);
                img_danhuatang.setBackgroundResource(R.drawable.bianbian_icon_danhuatang);
                img_biti.setBackgroundResource(R.drawable.bianbian_icon_biti);
                img_liqing.setBackgroundResource(R.drawable.bianbian_icon_liqing);

            }
        });
        img_ganchou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shape_id = "干稠";
                img_huzhuang.setBackgroundResource(R.drawable.bianbian_icon_huzhuang);
                img_ganchou.setBackgroundResource(R.drawable.bianbian_icon_ganchou_checked);
                img_gaozhuang.setBackgroundResource(R.drawable.bianbian_icon_gaozhuang);
                img_fenzhuang.setBackgroundResource(R.drawable.bianbian_icon_fenzhuang);
                img_naiban.setBackgroundResource(R.drawable.bianbian_icon_naiban);
                img_shuifen.setBackgroundResource(R.drawable.bianbian_icon_shuifen);
                img_paomo.setBackgroundResource(R.drawable.bianbian_icon_paomo);
                img_nitu.setBackgroundResource(R.drawable.bianbian_icon_liti);
                img_keli.setBackgroundResource(R.drawable.bianbian_icon_yangbian);
                img_boyou.setBackgroundResource(R.drawable.bianbian_icon_boyou);
                img_guojiang.setBackgroundResource(R.drawable.bianbian_icon_guojiang);
                img_doufuzha.setBackgroundResource(R.drawable.bianbian_icon_doufuzha);
                img_danhuatang.setBackgroundResource(R.drawable.bianbian_icon_danhuatang);
                img_biti.setBackgroundResource(R.drawable.bianbian_icon_biti);
                img_liqing.setBackgroundResource(R.drawable.bianbian_icon_liqing);

            }
        });

        img_gaozhuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shape_id = "膏状";
                img_huzhuang.setBackgroundResource(R.drawable.bianbian_icon_huzhuang);
                img_ganchou.setBackgroundResource(R.drawable.bianbian_icon_ganchou);
                img_gaozhuang.setBackgroundResource(R.drawable.bianbian_icon_gaozhuang_checked);
                img_fenzhuang.setBackgroundResource(R.drawable.bianbian_icon_fenzhuang);
                img_naiban.setBackgroundResource(R.drawable.bianbian_icon_naiban);
                img_shuifen.setBackgroundResource(R.drawable.bianbian_icon_shuifen);
                img_paomo.setBackgroundResource(R.drawable.bianbian_icon_paomo);
                img_nitu.setBackgroundResource(R.drawable.bianbian_icon_liti);
                img_keli.setBackgroundResource(R.drawable.bianbian_icon_yangbian);
                img_boyou.setBackgroundResource(R.drawable.bianbian_icon_boyou);
                img_guojiang.setBackgroundResource(R.drawable.bianbian_icon_guojiang);
                img_doufuzha.setBackgroundResource(R.drawable.bianbian_icon_doufuzha);
                img_danhuatang.setBackgroundResource(R.drawable.bianbian_icon_danhuatang);
                img_biti.setBackgroundResource(R.drawable.bianbian_icon_biti);
                img_liqing.setBackgroundResource(R.drawable.bianbian_icon_liqing);

            }
        });


        img_fenzhuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shape_id = "粉状";
                img_huzhuang.setBackgroundResource(R.drawable.bianbian_icon_huzhuang);
                img_ganchou.setBackgroundResource(R.drawable.bianbian_icon_ganchou);
                img_gaozhuang.setBackgroundResource(R.drawable.bianbian_icon_gaozhuang);
                img_fenzhuang.setBackgroundResource(R.drawable.bianbian_icon_fenzhuang_checked);
                img_naiban.setBackgroundResource(R.drawable.bianbian_icon_naiban);
                img_shuifen.setBackgroundResource(R.drawable.bianbian_icon_shuifen);
                img_paomo.setBackgroundResource(R.drawable.bianbian_icon_paomo);
                img_nitu.setBackgroundResource(R.drawable.bianbian_icon_liti);
                img_keli.setBackgroundResource(R.drawable.bianbian_icon_yangbian);
                img_boyou.setBackgroundResource(R.drawable.bianbian_icon_boyou);
                img_guojiang.setBackgroundResource(R.drawable.bianbian_icon_guojiang);
                img_doufuzha.setBackgroundResource(R.drawable.bianbian_icon_doufuzha);
                img_danhuatang.setBackgroundResource(R.drawable.bianbian_icon_danhuatang);
                img_biti.setBackgroundResource(R.drawable.bianbian_icon_biti);
                img_liqing.setBackgroundResource(R.drawable.bianbian_icon_liqing);

            }
        });

        img_naiban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shape_id = "奶扳";
                img_huzhuang.setBackgroundResource(R.drawable.bianbian_icon_huzhuang);
                img_ganchou.setBackgroundResource(R.drawable.bianbian_icon_ganchou);
                img_gaozhuang.setBackgroundResource(R.drawable.bianbian_icon_gaozhuang);
                img_fenzhuang.setBackgroundResource(R.drawable.bianbian_icon_fenzhuang);
                img_naiban.setBackgroundResource(R.drawable.bianbian_icon_naiban_hover);
                img_shuifen.setBackgroundResource(R.drawable.bianbian_icon_shuifen);
                img_paomo.setBackgroundResource(R.drawable.bianbian_icon_paomo);
                img_nitu.setBackgroundResource(R.drawable.bianbian_icon_liti);
                img_keli.setBackgroundResource(R.drawable.bianbian_icon_yangbian);
                img_boyou.setBackgroundResource(R.drawable.bianbian_icon_boyou);
                img_guojiang.setBackgroundResource(R.drawable.bianbian_icon_guojiang);
                img_doufuzha.setBackgroundResource(R.drawable.bianbian_icon_doufuzha);
                img_danhuatang.setBackgroundResource(R.drawable.bianbian_icon_danhuatang);
                img_biti.setBackgroundResource(R.drawable.bianbian_icon_biti);
                img_liqing.setBackgroundResource(R.drawable.bianbian_icon_liqing);

            }
        });

        img_shuifen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shape_id = "稀水样";
                img_huzhuang.setBackgroundResource(R.drawable.bianbian_icon_huzhuang);
                img_ganchou.setBackgroundResource(R.drawable.bianbian_icon_ganchou);
                img_gaozhuang.setBackgroundResource(R.drawable.bianbian_icon_gaozhuang);
                img_fenzhuang.setBackgroundResource(R.drawable.bianbian_icon_fenzhuang);
                img_naiban.setBackgroundResource(R.drawable.bianbian_icon_naiban);
                img_shuifen.setBackgroundResource(R.drawable.bianbian_icon_shuifen_checked);
                img_paomo.setBackgroundResource(R.drawable.bianbian_icon_paomo);
                img_nitu.setBackgroundResource(R.drawable.bianbian_icon_liti);
                img_keli.setBackgroundResource(R.drawable.bianbian_icon_yangbian);
                img_boyou.setBackgroundResource(R.drawable.bianbian_icon_boyou);
                img_guojiang.setBackgroundResource(R.drawable.bianbian_icon_guojiang);
                img_doufuzha.setBackgroundResource(R.drawable.bianbian_icon_doufuzha);
                img_danhuatang.setBackgroundResource(R.drawable.bianbian_icon_danhuatang);
                img_biti.setBackgroundResource(R.drawable.bianbian_icon_biti);
                img_liqing.setBackgroundResource(R.drawable.bianbian_icon_liqing);

            }
        });

        img_paomo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shape_id = "泡沫状";
                img_huzhuang.setBackgroundResource(R.drawable.bianbian_icon_huzhuang);
                img_ganchou.setBackgroundResource(R.drawable.bianbian_icon_ganchou);
                img_gaozhuang.setBackgroundResource(R.drawable.bianbian_icon_gaozhuang);
                img_fenzhuang.setBackgroundResource(R.drawable.bianbian_icon_fenzhuang);
                img_naiban.setBackgroundResource(R.drawable.bianbian_icon_naiban);
                img_shuifen.setBackgroundResource(R.drawable.bianbian_icon_shuifen);
                img_paomo.setBackgroundResource(R.drawable.bianbian_icon_paomo_checked);
                img_nitu.setBackgroundResource(R.drawable.bianbian_icon_liti);
                img_keli.setBackgroundResource(R.drawable.bianbian_icon_yangbian);
                img_boyou.setBackgroundResource(R.drawable.bianbian_icon_boyou);
                img_guojiang.setBackgroundResource(R.drawable.bianbian_icon_guojiang);
                img_doufuzha.setBackgroundResource(R.drawable.bianbian_icon_doufuzha);
                img_danhuatang.setBackgroundResource(R.drawable.bianbian_icon_danhuatang);
                img_biti.setBackgroundResource(R.drawable.bianbian_icon_biti);
                img_liqing.setBackgroundResource(R.drawable.bianbian_icon_liqing);

            }
        });

        img_nitu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shape_id = "泥土状";
                img_huzhuang.setBackgroundResource(R.drawable.bianbian_icon_huzhuang);
                img_ganchou.setBackgroundResource(R.drawable.bianbian_icon_ganchou);
                img_gaozhuang.setBackgroundResource(R.drawable.bianbian_icon_gaozhuang);
                img_fenzhuang.setBackgroundResource(R.drawable.bianbian_icon_fenzhuang);
                img_naiban.setBackgroundResource(R.drawable.bianbian_icon_naiban);
                img_shuifen.setBackgroundResource(R.drawable.bianbian_icon_shuifen);
                img_paomo.setBackgroundResource(R.drawable.bianbian_icon_paomo);
                img_nitu.setBackgroundResource(R.drawable.bianbian_icon_liti_checked);
                img_keli.setBackgroundResource(R.drawable.bianbian_icon_yangbian);
                img_boyou.setBackgroundResource(R.drawable.bianbian_icon_boyou);
                img_guojiang.setBackgroundResource(R.drawable.bianbian_icon_guojiang);
                img_doufuzha.setBackgroundResource(R.drawable.bianbian_icon_doufuzha);
                img_danhuatang.setBackgroundResource(R.drawable.bianbian_icon_danhuatang);
                img_biti.setBackgroundResource(R.drawable.bianbian_icon_biti);
                img_liqing.setBackgroundResource(R.drawable.bianbian_icon_liqing);

            }
        });

        img_keli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shape_id = "颗粒状";
                img_huzhuang.setBackgroundResource(R.drawable.bianbian_icon_huzhuang);
                img_ganchou.setBackgroundResource(R.drawable.bianbian_icon_ganchou);
                img_gaozhuang.setBackgroundResource(R.drawable.bianbian_icon_gaozhuang);
                img_fenzhuang.setBackgroundResource(R.drawable.bianbian_icon_fenzhuang);
                img_naiban.setBackgroundResource(R.drawable.bianbian_icon_naiban);
                img_shuifen.setBackgroundResource(R.drawable.bianbian_icon_shuifen);
                img_paomo.setBackgroundResource(R.drawable.bianbian_icon_paomo);
                img_nitu.setBackgroundResource(R.drawable.bianbian_icon_liti);
                img_keli.setBackgroundResource(R.drawable.bianbian_icon_yangbian_checked);
                img_boyou.setBackgroundResource(R.drawable.bianbian_icon_boyou);
                img_guojiang.setBackgroundResource(R.drawable.bianbian_icon_guojiang);
                img_doufuzha.setBackgroundResource(R.drawable.bianbian_icon_doufuzha);
                img_danhuatang.setBackgroundResource(R.drawable.bianbian_icon_danhuatang);
                img_biti.setBackgroundResource(R.drawable.bianbian_icon_biti);
                img_liqing.setBackgroundResource(R.drawable.bianbian_icon_liqing);

            }
        });


        img_boyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shape_id = "柏油状";
                img_huzhuang.setBackgroundResource(R.drawable.bianbian_icon_huzhuang);
                img_ganchou.setBackgroundResource(R.drawable.bianbian_icon_ganchou);
                img_gaozhuang.setBackgroundResource(R.drawable.bianbian_icon_gaozhuang);
                img_fenzhuang.setBackgroundResource(R.drawable.bianbian_icon_fenzhuang);
                img_naiban.setBackgroundResource(R.drawable.bianbian_icon_naiban);
                img_shuifen.setBackgroundResource(R.drawable.bianbian_icon_shuifen);
                img_paomo.setBackgroundResource(R.drawable.bianbian_icon_paomo);
                img_nitu.setBackgroundResource(R.drawable.bianbian_icon_liti);
                img_keli.setBackgroundResource(R.drawable.bianbian_icon_yangbian);
                img_boyou.setBackgroundResource(R.drawable.bianbian_icon_boyou_checked);
                img_guojiang.setBackgroundResource(R.drawable.bianbian_icon_guojiang);
                img_doufuzha.setBackgroundResource(R.drawable.bianbian_icon_doufuzha);
                img_danhuatang.setBackgroundResource(R.drawable.bianbian_icon_danhuatang);
                img_biti.setBackgroundResource(R.drawable.bianbian_icon_biti);
                img_liqing.setBackgroundResource(R.drawable.bianbian_icon_liqing);

            }
        });

        img_guojiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shape_id = "果酱样";
                img_huzhuang.setBackgroundResource(R.drawable.bianbian_icon_huzhuang);
                img_ganchou.setBackgroundResource(R.drawable.bianbian_icon_ganchou);
                img_gaozhuang.setBackgroundResource(R.drawable.bianbian_icon_gaozhuang);
                img_fenzhuang.setBackgroundResource(R.drawable.bianbian_icon_fenzhuang);
                img_naiban.setBackgroundResource(R.drawable.bianbian_icon_naiban);
                img_shuifen.setBackgroundResource(R.drawable.bianbian_icon_shuifen);
                img_paomo.setBackgroundResource(R.drawable.bianbian_icon_paomo);
                img_nitu.setBackgroundResource(R.drawable.bianbian_icon_liti);
                img_keli.setBackgroundResource(R.drawable.bianbian_icon_yangbian);
                img_boyou.setBackgroundResource(R.drawable.bianbian_icon_boyou);
                img_guojiang.setBackgroundResource(R.drawable.bianbian_icon_guojiang_checked);
                img_doufuzha.setBackgroundResource(R.drawable.bianbian_icon_doufuzha);
                img_danhuatang.setBackgroundResource(R.drawable.bianbian_icon_danhuatang);
                img_biti.setBackgroundResource(R.drawable.bianbian_icon_biti);
                img_liqing.setBackgroundResource(R.drawable.bianbian_icon_liqing);

            }
        });


        img_doufuzha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shape_id = "豆腐渣";
                img_huzhuang.setBackgroundResource(R.drawable.bianbian_icon_huzhuang);
                img_ganchou.setBackgroundResource(R.drawable.bianbian_icon_ganchou);
                img_gaozhuang.setBackgroundResource(R.drawable.bianbian_icon_gaozhuang);
                img_fenzhuang.setBackgroundResource(R.drawable.bianbian_icon_fenzhuang);
                img_naiban.setBackgroundResource(R.drawable.bianbian_icon_naiban);
                img_shuifen.setBackgroundResource(R.drawable.bianbian_icon_shuifen);
                img_paomo.setBackgroundResource(R.drawable.bianbian_icon_paomo);
                img_nitu.setBackgroundResource(R.drawable.bianbian_icon_liti);
                img_keli.setBackgroundResource(R.drawable.bianbian_icon_yangbian);
                img_boyou.setBackgroundResource(R.drawable.bianbian_icon_boyou);
                img_guojiang.setBackgroundResource(R.drawable.bianbian_icon_guojiang);
                img_doufuzha.setBackgroundResource(R.drawable.bianbian_icon_doufuzha_checked);
                img_danhuatang.setBackgroundResource(R.drawable.bianbian_icon_danhuatang);
                img_biti.setBackgroundResource(R.drawable.bianbian_icon_biti);
                img_liqing.setBackgroundResource(R.drawable.bianbian_icon_liqing);

            }
        });


        img_danhuatang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shape_id = "蛋花汤";
                img_huzhuang.setBackgroundResource(R.drawable.bianbian_icon_huzhuang);
                img_ganchou.setBackgroundResource(R.drawable.bianbian_icon_ganchou);
                img_gaozhuang.setBackgroundResource(R.drawable.bianbian_icon_gaozhuang);
                img_fenzhuang.setBackgroundResource(R.drawable.bianbian_icon_fenzhuang);
                img_naiban.setBackgroundResource(R.drawable.bianbian_icon_naiban);
                img_shuifen.setBackgroundResource(R.drawable.bianbian_icon_shuifen);
                img_paomo.setBackgroundResource(R.drawable.bianbian_icon_paomo);
                img_nitu.setBackgroundResource(R.drawable.bianbian_icon_liti);
                img_keli.setBackgroundResource(R.drawable.bianbian_icon_yangbian);
                img_boyou.setBackgroundResource(R.drawable.bianbian_icon_boyou);
                img_guojiang.setBackgroundResource(R.drawable.bianbian_icon_guojiang);
                img_doufuzha.setBackgroundResource(R.drawable.bianbian_icon_doufuzha);
                img_danhuatang.setBackgroundResource(R.drawable.bianbian_icon_danhuatang_checked);
                img_biti.setBackgroundResource(R.drawable.bianbian_icon_biti);
                img_liqing.setBackgroundResource(R.drawable.bianbian_icon_liqing);

            }
        });

        img_biti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shape_id = "鼻涕样粘液";
                img_huzhuang.setBackgroundResource(R.drawable.bianbian_icon_huzhuang);
                img_ganchou.setBackgroundResource(R.drawable.bianbian_icon_ganchou);
                img_gaozhuang.setBackgroundResource(R.drawable.bianbian_icon_gaozhuang);
                img_fenzhuang.setBackgroundResource(R.drawable.bianbian_icon_fenzhuang);
                img_naiban.setBackgroundResource(R.drawable.bianbian_icon_naiban);
                img_shuifen.setBackgroundResource(R.drawable.bianbian_icon_shuifen);
                img_paomo.setBackgroundResource(R.drawable.bianbian_icon_paomo);
                img_nitu.setBackgroundResource(R.drawable.bianbian_icon_liti);
                img_keli.setBackgroundResource(R.drawable.bianbian_icon_yangbian);
                img_boyou.setBackgroundResource(R.drawable.bianbian_icon_boyou);
                img_guojiang.setBackgroundResource(R.drawable.bianbian_icon_guojiang);
                img_doufuzha.setBackgroundResource(R.drawable.bianbian_icon_doufuzha);
                img_danhuatang.setBackgroundResource(R.drawable.bianbian_icon_danhuatang);
                img_biti.setBackgroundResource(R.drawable.bianbian_icon_biti_checked);
                img_liqing.setBackgroundResource(R.drawable.bianbian_icon_liqing);

            }
        });


        img_liqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shape_id = "粘稠像沥青";
                img_huzhuang.setBackgroundResource(R.drawable.bianbian_icon_huzhuang);
                img_ganchou.setBackgroundResource(R.drawable.bianbian_icon_ganchou);
                img_gaozhuang.setBackgroundResource(R.drawable.bianbian_icon_gaozhuang);
                img_fenzhuang.setBackgroundResource(R.drawable.bianbian_icon_fenzhuang);
                img_naiban.setBackgroundResource(R.drawable.bianbian_icon_naiban);
                img_shuifen.setBackgroundResource(R.drawable.bianbian_icon_shuifen);
                img_paomo.setBackgroundResource(R.drawable.bianbian_icon_paomo);
                img_nitu.setBackgroundResource(R.drawable.bianbian_icon_liti);
                img_keli.setBackgroundResource(R.drawable.bianbian_icon_yangbian);
                img_boyou.setBackgroundResource(R.drawable.bianbian_icon_boyou);
                img_guojiang.setBackgroundResource(R.drawable.bianbian_icon_guojiang);
                img_doufuzha.setBackgroundResource(R.drawable.bianbian_icon_doufuzha);
                img_danhuatang.setBackgroundResource(R.drawable.bianbian_icon_danhuatang);
                img_biti.setBackgroundResource(R.drawable.bianbian_icon_biti);
                img_liqing.setBackgroundResource(R.drawable.bianbian_icon_liqing_checked);

            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(color_id)) {
                    EasyToast.showShort(context, "请选择颜色");
                    return;
                }

                if (TextUtils.isEmpty(shape_id)) {
                    EasyToast.showShort(context, "请选择形状");
                    return;
                }

                if (Utils.isConnected(context)) {
                    dialog = Utils.showLoadingDialog(context);
                    dialog.show();
                    suckleSmellyDoadd(color_id, shape_id, tv_start.getText().toString());
                } else {
                    EasyToast.showShort(context, "网络未连接");
                }

            }
        });


        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rl_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTimePicker();
            }
        });

        tv_start.setText(getMillon(System.currentTimeMillis()));

    }

    @Override
    protected void initData() {

    }

    /**
     * 格式到秒
     *
     * @return time -> yyyy-MM-dd-HH-mm-ss
     */
    public String getMillon(long time) {
        return new SimpleDateFormat("HH:mm").format(time);
    }

    private void initTimePicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1972, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 11, 28);
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tv_start.setText(getMillon(date.getTime()));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{false, false, false, true, true, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setTitleBgColor(getResources().getColor(R.color.pressedColor))
                .setCancelColor(getResources().getColor(R.color.text))
                .setSubmitColor(getResources().getColor(R.color.text))
                .setTitleText("开始时间")
                .setTitleColor(getResources().getColor(R.color.text))
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
        pvTime.show();
    }

    private Dialog dialog;

    private String color_id = "";
    private String shape_id = "";

    /**
     * 添加臭臭
     */
    private void suckleSmellyDoadd(String color_id, String shape_id, final String start_time) {
        HashMap<String, String> params = new HashMap<>(6);
        params.put("key", UrlUtils.KEY);
        params.put("uid", String.valueOf(SpUtil.get(context, "uid", "")));
        params.put("time", RecordFragment.currentDate.toString());
        params.put("color_id", color_id);
        params.put("shape_id", shape_id);
        params.put("start_time", start_time);
        Log.e("RegisterActivity", params.toString());
        VolleyRequest.RequestPost(context, UrlUtils.BASE_URL + "suckle/smelly_doadd", "suckle/smelly_doadd", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                dialog.dismiss();
                Log.e("RegisterActivity", result);
                try {
                    StuBean stuBean = new Gson().fromJson(result, StuBean.class);
                    if ("1".equals(String.valueOf(stuBean.getStu()))) {
                        EventBus.getDefault().post(
                                new BankEvent(start_time, "chouchou"));
                        finish();
                    } else {
                        EasyToast.showShort(context, "提交失败");
                    }
                    stuBean = null;
                    result = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                Toast.makeText(context, getString(R.string.Abnormalserver), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
