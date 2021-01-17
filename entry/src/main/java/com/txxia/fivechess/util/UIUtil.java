package com.txxia.fivechess.util;

import ohos.agp.components.AttrHelper;
import ohos.app.Context;

public class UIUtil {
    /**
     * 获取屏幕宽度, 单位：px
     */
    public static int getScreenWidth(Context context) {
        return vp2px(context, context.getResourceManager().getDeviceCapability().width);
    }

    /**
     * 获取屏幕高度, 单位：px
     */
    public static int getScreenHeight(Context context) {
        return vp2px(context, context.getResourceManager().getDeviceCapability().height);
    }

    /**
     * 单位转换：vp->px
     */
    public static int vp2px(Context context, float vp) {
        return  AttrHelper.vp2px(vp, context);
    }

}
