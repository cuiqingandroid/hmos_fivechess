package com.txxia.fivechess.slice;

import com.txxia.fivechess.ResourceTable;
import com.txxia.fivechess.game.*;
import com.txxia.fivechess.util.UIUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.dialog.IDialog;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class SingleGameSlice extends AbilitySlice {
    private static final String TAG = "SingleGameSlice";
    private HiLogLabel hiLogLabel = new HiLogLabel(HiLog.LOG_APP, 0x00201, TAG);
    
    GameComponent mGameComponent = null;
    
    Game mGame;
    Player me;
    Player computer;
    
    ComputerAI ai;
    
    // 胜局
    private Text mBlackWin;
    private Text mWhiteWin;
    
    // 当前落子方
    private Image mBlackActive;
    private Image mWhiteActive;
    
    // Control Button
    private Button restart;
    private Button rollback;

    private boolean isRollback;
    
    /**
     * 处理游戏回调信息，刷新界面
     */
    private EventHandler mComputerHandler ;

    /**
     * 处理游戏回调信息，刷新界面
     */
    private EventHandler mRefreshHandler = new EventHandler(EventRunner.getMainEventRunner()){

        protected void processEvent(InnerEvent event) {
//            Log.d(TAG, "refresh action="+ event.what);
            HiLog.info(hiLogLabel, "EventHandler processEvent id:%{public}d", event.eventId);
            switch (event.eventId) {
                case GameConstants.GAME_OVER:
                    if ((int)event.object == Game.BLACK){
                        showWinDialog("你赢了！");
                        me.win();
                    } else if ((int)event.object == Game.WHITE) {
                        showWinDialog("你输给了电脑！");
                        computer.win();
                    }
                    updateScore(me, computer);
                    break;
                case GameConstants.ACTIVE_CHANGE:
                    updateActive(mGame);
                    break;
                case GameConstants.ADD_CHESS:
                    updateActive(mGame);
                    mComputerHandler.sendEvent(0);
                    break;
                default:
                    break;
            }
        }
    };
    
    
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_game_single);
        initViews();
        initGame();
        initComputer();
    }
    

    private void initViews(){
        mGameComponent = (GameComponent) findComponentById(ResourceTable.Id_game_view);
        mBlackWin = (Text) findComponentById(ResourceTable.Id_black_win);
        mBlackActive = (Image) findComponentById(ResourceTable.Id_black_active);
        mWhiteWin = (Text) findComponentById(ResourceTable.Id_white_win);
        mWhiteActive = (Image) findComponentById(ResourceTable.Id_white_active);
        restart = (Button) findComponentById(ResourceTable.Id_restart);
        rollback = (Button) findComponentById(ResourceTable.Id_rollback);
        restart.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                mGame.reset();
                updateActive(mGame);
                updateScore(me, computer);
                mGameComponent.drawGame();
            }
        });
        rollback.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if(mGame.getActive() != me.getType()){
                    isRollback = true;
                } else {
                    rollback();
                }
            }
        });
        findComponentById(ResourceTable.Id_exit).setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                terminateAbility();
            }
        });
    }
    
    private void initGame(){
        me = new Player("自己",Game.BLACK);
        computer = new Player("电脑",Game.WHITE);
        mGame = new Game(mRefreshHandler, me, computer);
        mGame.setMode(GameConstants.MODE_SINGLE);
        mGameComponent.setGame(mGame);
        updateActive(mGame);
        updateScore(me, computer);
        ai = new ComputerAI(mGame.getWidth(), mGame.getHeight());
    }
    
    private void initComputer(){
        mComputerHandler = new ComputerHandler();
    }
    
    private void updateActive(Game game){
        if (game.getActive() == Game.BLACK){
            mBlackActive.setVisibility(Component.VISIBLE);
            mWhiteActive.setVisibility(Component.INVISIBLE);
        } else {
            mBlackActive.setVisibility(Component.INVISIBLE);
            mWhiteActive.setVisibility(Component.VISIBLE);
        }
    }
    
    private void updateScore(Player black, Player white){
        mBlackWin.setText(black.getWin());
        mWhiteWin.setText(white.getWin());
    }

    private void showWinDialog(String message){
        CommonDialog b = new CommonDialog(this);
        b.setSize(UIUtil.getScreenWidth(this)*2/3, UIUtil.vp2px(this, 120));
        b.setContentText(message);
        b.setButton(1,"继续", new IDialog.ClickedListener() {
            @Override
            public void onClick(IDialog dialog, int var2) {
                mGame.reset();
                mGameComponent.invalidate();
                dialog.destroy();
            }
        });
        b.setButton(0,"退出", new IDialog.ClickedListener() {
            @Override
            public void onClick(IDialog dialog, int var2) {
                terminateAbility();
            }
        });
        b.show();
    }

    private void rollback(){
        mGame.rollback();
        mGame.rollback();
        updateActive(mGame);
        mGameComponent.drawGame();
    }
    
    private class ComputerHandler extends EventHandler{


        public ComputerHandler() {
            super(EventRunner.getMainEventRunner());
        }

        protected void processEvent(InnerEvent event) {
            ai.updateValue(mGame.getChessMap());
            Coordinate c = ai.getNextCoordinate(mGame.getChessMap());
            mGame.addChess(c, computer);
            mGameComponent.drawGame();
            if (isRollback){
                rollback();
                isRollback = false;
            }
        }

    }
}
