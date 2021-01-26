package com.txxia.fivechess;

import com.txxia.fivechess.slice.AIGameSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class AIGameActivity extends Ability {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(AIGameSlice.class.getName());
    }
}
