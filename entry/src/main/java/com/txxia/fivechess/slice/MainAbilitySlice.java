package com.txxia.fivechess.slice;

import com.txxia.fivechess.AboutAbility;
import com.txxia.fivechess.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Button button = (Button) findComponentById(ResourceTable.Id_btnStartSingleGame);
        button.setClickedListener(component -> {
            Intent secondIntent = new Intent();
            Operation operation = new Intent.OperationBuilder()
                    .withDeviceId("")
                    .withBundleName(getBundleName())
                    .withAbilityName("com.txxia.fivechess.SingleGameActivity")
                    .build();
            secondIntent.setOperation(operation);
            startAbility(secondIntent);
        });
        findComponentById(ResourceTable.Id_btnAbout).setClickedListener(component ->{
            Intent secondIntent = new Intent();
            Operation operation = new Intent.OperationBuilder()
                    .withDeviceId("")
                    .withBundleName(getBundleName())
                    .withAbilityName(AboutAbility.class.getName())
                    .build();
            secondIntent.setOperation(operation);
            startAbility(secondIntent);
        });
        findComponentById(ResourceTable.Id_btnExit).setClickedListener(component ->{
            terminateAbility();
        });
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
