package sakura.liangdinvshen;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.ChatManager;
import com.hyphenate.chat.Conversation;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.easeui.Notifier;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.hyphenate.helpdesk.easeui.ui.BaseChatActivity;
import com.hyphenate.helpdesk.easeui.util.CommonUtils;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.hyphenate.helpdesk.model.AgentInfo;
import com.hyphenate.helpdesk.model.MessageHelper;
import com.hyphenate.helpdesk.util.Log;
import com.mob.MobApplication;
import com.tencent.smtt.sdk.QbSdk;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.base.UpdateParser;
import org.lzh.framework.updatepluginlib.model.CheckEntity;
import org.lzh.framework.updatepluginlib.model.Update;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import sakura.liangdinvshen.Bean.GetVersionCode;
import sakura.liangdinvshen.Utils.PausableThreadPoolExecutor;
import sakura.liangdinvshen.Utils.SpUtil;
import sakura.liangdinvshen.Utils.UrlUtils;
import sakura.liangdinvshen.other.CallReceiver;

/**
 * Created by 赵磊 on 2017/7/12.
 */

public class App extends MobApplication {
    /**
     * 先创建一个请求队列，因为这个队列是全局的，所以在Application中声明这个队列
     */
    public static RequestQueue queues;
    public static PausableThreadPoolExecutor pausableThreadPoolExecutor;
    /**
     * kefuChat.MessageListener
     */
    protected ChatManager.MessageListener messageListener = null;

    private CallReceiver callReceiver;


    @Override
    public void onCreate() {
        super.onCreate();


        //后面可以设置其他属性
        queues = Volley.newRequestQueue(getApplicationContext());
        QbSdk.initX5Environment(this, null);
        Fresco.initialize(this);
        pausableThreadPoolExecutor = new PausableThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());

        // Kefu SDK 初始化
        ChatClient.Options options = new ChatClient.Options();
        options.setAppkey("1186171221178397#liangdinvshen");//必填项，appkey获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“AppKey”
        options.setTenantId("51178");//必填项，tenantId获取地址：kefu.easemob.com，“管理员模式 > 设置 > 企业信息”页面的“租户ID”
        if (!ChatClient.getInstance().init(getApplicationContext(), options)) {
            return;
        }
        // Kefu EaseUI的初始化
        UIProvider.getInstance().init(getApplicationContext());
        ChatClient.getInstance().init(getApplicationContext(), new ChatClient.Options().setConsoleLog(true));
        setGlobalListeners();

        HashMap<String, String> params = new HashMap<>(1);
        params.put("key", UrlUtils.KEY);
        UpdateConfig.getConfig()
                // 必填：数据更新接口,url与checkEntity两种方式任选一种填写
                //.setUrl(UrlUtils.BASE_URL + "user/app_version")
                .setCheckEntity(new CheckEntity().setMethod("POST").setParams(params).setUrl(UrlUtils.BASE_URL + "user/app_version"))
                // 必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理
                .setUpdateParser(new UpdateParser() {
                    @Override
                    public Update parse(String response) throws Exception {
                        /* 此处根据上面url或者checkEntity设置的检查更新接口的返回数据response解析出
                         * 一个update对象返回即可。更新启动时框架内部即可根据update对象的数据进行处理
                         */
                        GetVersionCode getVersionCode = new Gson().fromJson(response, GetVersionCode.class);
                        Update update = new Update();
                        Log.e("App", response);
                        if ("1".equals(String.valueOf(getVersionCode.getCode()))) {
                            // 此apk包的下载地址
                            update.setUpdateUrl(getVersionCode.getRes().getAz().getUrl());
                            // 此apk包的版本号
                            update.setVersionCode(Integer.parseInt(getVersionCode.getRes().getAz().getVersion()));
                            // 此apk包的版本名称
                            //update.setVersionName(object.optString("update_ver_name"));
                            // 此apk包的更新内容
                            update.setUpdateContent(getVersionCode.getRes().getAz().getContent());
                            // 此apk包是否为强制更新
                            //  update.setForced(false);
                            // 是否显示忽略此次版本更新按钮
                            //  update.setIgnore(object.optBoolean("ignore_able",false));
                        } else {
                            // 此apk包的下载地址
                            update.setUpdateUrl("");
                            // 此apk包的版本号
                            update.setVersionCode(1);
                            // 此apk包的版本名称
                            update.setVersionName("1.1.1");
                            // 此apk包的更新内容
                            update.setUpdateContent("");
                        }
                        return update;
                    }
                });


    }

    private void setGlobalListeners() {
        //注册消息事件监听
        registerEventListener();

        IntentFilter callFilter = new IntentFilter(ChatClient.getInstance().callManager()
                .getIncomingCallBroadcastAction());
        if (callReceiver == null) {
            callReceiver = new CallReceiver();
        }
        // register incoming call receiver
        getApplicationContext().registerReceiver(callReceiver, callFilter);
    }

    /**
     * 全局事件监听
     * 因为可能会有UI页面先处理到这个消息，所以一般如果UI页面已经处理，这里就不需要再次处理
     * activityList.size() <= 0 意味着所有页面都已经在后台运行，或者已经离开Activity Stack
     */
    protected void registerEventListener() {
        messageListener = new ChatManager.MessageListener() {
            @Override
            public void onMessage(List<Message> msgs) {
                for (Message message : msgs) {
                    // 如果消息不是和当前聊天ID的消息
                    Log.e("notify全局事件监听", message.getBody().toString());
                    UIProvider.getInstance().getNotifier().onNewMsg(message);
                }
            }

            @Override
            public void onCmdMessage(List<Message> msgs) {
            }

            @Override
            public void onMessageStatusUpdate() {
            }

            @Override
            public void onMessageSent() {
            }
        };

        ChatClient.getInstance().chatManager().addMessageListener(messageListener);
        setEaseUIProvider(getApplicationContext());
    }

    private void setEaseUIProvider(final Context context) {
        //设置头像和昵称 某些控件可能没有头像和昵称，需要注意
        UIProvider.getInstance().setUserProfileProvider(new UIProvider.UserProfileProvider() {
            @Override
            public void setNickAndAvatar(Context context, Message message, ImageView userAvatarView, TextView usernickView) {
                if (message.direct() == Message.Direct.RECEIVE) {
                    //设置接收方的昵称和头像
                    // UserUtil.setAgentNickAndAvatar(context, message, userAvatarView, usernickView);
                    AgentInfo agentInfo = MessageHelper.getAgentInfo(message);
                    if (usernickView != null) {
                        usernickView.setText(message.from());
                        if (agentInfo != null) {
                            if (!TextUtils.isEmpty(agentInfo.getNickname())) {
                                usernickView.setText(agentInfo.getNickname());
                            }
                        }
                    }
                    if (userAvatarView != null) {
                        if (agentInfo != null) {
                            if (!TextUtils.isEmpty(agentInfo.getAvatar())) {
                                String strUrl = agentInfo.getAvatar();
                                // 设置客服头像
                                if (!TextUtils.isEmpty(strUrl)) {
                                    if (!strUrl.startsWith("http")) {
                                        strUrl = "http:" + strUrl;
                                    }
                                    //正常的string路径
                                    Glide.with(context).load(strUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(userAvatarView);
                                }
                            }
                        }
                    }
                } else {
                    //此处设置当前登录用户的头像，
                    if (userAvatarView != null) {
                        String img = String.valueOf(SpUtil.get(context, "img", ""));
                        if (img.startsWith("http://")) {
                            Glide.with(context).load(img).diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(userAvatarView);
                        } else {
                            Glide.with(context).load(UrlUtils.URL + img).diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(userAvatarView);
                        }
                    }
                }
            }
        });

        //设置通知栏样式
        UIProvider.getInstance().getNotifier().setNotificationInfoProvider(new Notifier.NotificationInfoProvider() {
            @Override
            public String getTitle(Message message) {
                //修改标题,这里使用默认
                return null;
            }

            @Override
            public int getSmallIcon(Message message) {
                //设置小图标，这里为默认
                return 0;
            }

            @Override
            public String getDisplayedText(Message message) {
                // 设置状态栏的消息提示，可以根据message的类型做相应提示
                String ticker = CommonUtils.getMessageDigest(message, context);
                if (message.getType() == Message.Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", context.getString(R.string.noti_text_expression));
                }
                return "客服" + ": " + ticker;
            }

            @Override
            public String getLatestText(Message message, int fromUsersNum, int messageNum) {
                // return null;
                return "客服发来一条新消息~~";
            }

            @Override
            public Intent getLaunchIntent(Message message) {
                Intent intent;
                //设置点击通知栏跳转事件
                Conversation conversation = ChatClient.getInstance().chatManager().getConversation(message.from());
                String titleName = null;
                if (conversation.officialAccount() != null) {
                    titleName = conversation.officialAccount().getName();
                }
                intent = new IntentBuilder(context)
                        .setTargetClass(BaseChatActivity.class)
                        .setServiceIMNumber(conversation.conversationId())
                        .setTitleName(titleName)
                        .setShowUserNick(true)
                        .build();

                return intent;
            }
        });
    }


    public static RequestQueue getQueues() {
        return queues;
    }
}
