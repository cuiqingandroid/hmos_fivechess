package com.txxia.fivechess;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class AboutAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_ability_about);
        findComponentById(ResourceTable.Id_back).setClickedListener(v -> {
            terminateAbility();
        });
    }

}
