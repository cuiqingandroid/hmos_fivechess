package com.txxia.fivechess;

import com.txxia.fivechess.slice.SingleGameSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class SingleGameActivity extends Ability {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(SingleGameSlice.class.getName());
    }
}
