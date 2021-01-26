package com.txxia.fivechess.game;

import com.txxia.fivechess.ResourceTable;
import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.render.PixelMapHolder;
import ohos.agp.utils.Color;
import ohos.agp.utils.Point;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.PixelFormat;
import ohos.media.image.common.Size;
import ohos.multimodalinput.event.MmiPoint;
import ohos.multimodalinput.event.TouchEvent;

import java.io.InputStream;

/**
 * 负责游戏的显示，游戏的逻辑判断在Game.java中
 * @author cuiqing
 *
 */
public class GameComponent extends Component implements Component.DrawTask, Component.TouchEventListener {

    private static final String TAG = "GameView";
    private HiLogLabel hiLogLabel = new HiLogLabel(HiLog.LOG_APP, 0x00201, TAG);

    // 棋子画笔
    private Paint chessPaint = new Paint();
    // 棋盘画笔
    private Paint boardPaint = new Paint();

    private Color boardColor = Color.BLACK;
    private float boardWidth;
    private float anchorWidth;


    private PixelMap mBlack = null;
    private PixelMap mBlackNew = null;
    private PixelMap mWhite = null;
    private PixelMap mWhiteNew = null;

    /**
     * 游戏宽度长度（可放多少个游戏子）
     */
    private int mChessboardWLen = 0;
    /**
     * 游戏高度长度（可放多少个游戏子）
     */
    private int mChessboardHLen = 0;
    private int mChessSize = 0;

    private Game mGame;

    private Coordinate focus;
    private boolean isDrawFocus;
    private PixelMap bFocus;

    public GameComponent(Context context) {
        this(context, null);
    }

    public GameComponent(Context context, AttrSet attrSet) {
        this(context,attrSet,null);
    }

    public GameComponent(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        mContext = context;

        boardWidth =1 ;
        anchorWidth = 5;
        focus = new Coordinate();
        init();
    }


    private void init(){
        chessPaint.setAntiAlias(true);
        boardPaint.setStrokeWidth(boardWidth);
        boardPaint.setColor(boardColor);

        setBindStateChangedListener(new Component.BindStateChangedListener() {
            @Override
            public void onComponentBoundToWindow(Component component) {
                int w = component.getWidth();
                int h = component.getHeight();
                if (w !=h){
                    setWidth(w);
                    setHeight(w);
//                    setComponentSize(w, w);
//                    postLayout();
//                    requestFocus();
                }
                HiLog.info(hiLogLabel, "onComponentBoundToWindow w:%{public}d, h:%{public}d,thread:%{public}s", component.getWidth(), component.getHeight(), Thread.currentThread().getName());
            }

            @Override
            public void onComponentUnboundFromWindow(Component component) {

            }
        });
        setComponentStateChangedListener((component, i) -> HiLog.info(hiLogLabel, "onComponentStateChanged w:%{public}d, h:%{public}d,", 2));
        setLayoutRefreshedListener(component -> {
            HiLog.info(hiLogLabel, "layout refresh w:%{public}d, h:%{public}d,", component.getWidth(), component.getHeight());
            int w = component.getWidth();
            int h = component.getHeight();
            mChessboardWLen = mGame.getWidth();
            mChessboardHLen = mGame.getHeight();
            mChessSize = (w) / mChessboardWLen;
            HiLog.info(hiLogLabel, "chesssize :%{public}d, mChessW:%{public}d, mChessH:%{public}d， sw:%{public}d, sh:%{public}d", mChessSize, mChessboardWLen, mChessboardHLen, w, h);

            initChess(mChessSize, mChessSize);
        });
        setTouchEventListener(this);
    }

    private void initChess(int width, int height){
        mWhite = getPixelMap(ResourceTable.Media_white,width, height);
        mBlack = getPixelMap(ResourceTable.Media_black,width, height);
        mBlackNew = getPixelMap(ResourceTable.Media_black_new,width, height);
        mWhiteNew = getPixelMap(ResourceTable.Media_white_new,width, height);
        bFocus = getPixelMap(ResourceTable.Media_focus,width, height);
    }

    /**
     * 设置游戏对象
     */
    public void setGame(Game game){
        mGame = game;
        addDrawTask(this);
        invalidate();
    }

    /**
     * 通过资源ID获取位图对象
     **/
    private PixelMap getPixelMap(int resId, int width, int height) {
        InputStream drawableInputStream = null;
        try {
            drawableInputStream = getResourceManager().getResource(resId);
            ImageSource.SourceOptions sourceOptions = new ImageSource.SourceOptions();
            sourceOptions.formatHint = "image/png";
            ImageSource imageSource = ImageSource.create(drawableInputStream, null);
            ImageSource.DecodingOptions decodingOptions = new ImageSource.DecodingOptions();
            decodingOptions.desiredSize = new Size(width, height);
            decodingOptions.desiredPixelFormat = PixelFormat.ARGB_8888;
            return imageSource.createPixelmap(decodingOptions);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if (drawableInputStream != null){
                    drawableInputStream.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    public void onDraw(Component component, Canvas canvas) {
        drawGame(canvas);
    }

    /**
     * 绘制游戏界面
     */
    public void drawGame(){
        invalidate();
    }
    /**
     * 绘制游戏界面
     */
    public void drawGame(Canvas  canvas){
        long start = System.currentTimeMillis();
        drawChessBoard(canvas);
        drawChess(canvas);
        drawFocus(canvas);
        HiLog.info(hiLogLabel, "draw cost: %{public}d ms", (System.currentTimeMillis()- start));
    }

    /**
     * 增加一个棋子
     * @param x 横坐标
     * @param y 纵坐标
     */
    private void addChess(int x, int y){
        if (mGame == null){
            return;
        }
        mGame.addChess(x, y);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(Component component, TouchEvent event) {
        int action = event.getAction();
        int index = event.getIndex();
        MmiPoint point = event.getPointerPosition(index);
        MmiPoint sPoint = event.getPointerScreenPosition(index);
        float x = point.getX()-component.getContentPositionX();
        float y = point.getY() - component.getContentPositionY();
        HiLog.info(hiLogLabel, "component name:%{public}s", component.getName());
        switch (action) {
            case TouchEvent.PRIMARY_POINT_DOWN:
                focus.x = (int) (x/mChessSize);
                focus.y = (int) (y/mChessSize);
                HiLog.info(hiLogLabel, "PRIMARY_POINT_DOWN x:%{public}f, y:%{public}f", x, y);
                HiLog.info(hiLogLabel, "cp pos x:%{public}d, y:%{public}d", component.getComponentPosition().left, component.getComponentPosition().top);
                HiLog.info(hiLogLabel, "c pos x:%{public}f, y:%{public}f", getContentPositionX(), getContentPositionY());
                HiLog.info(hiLogLabel, "S PRIMARY_POINT_DOWN x:%{public}f, y:%{public}f", sPoint.getX(), sPoint.getY());
                isDrawFocus = true;
                invalidate();
                break;
            case TouchEvent.PRIMARY_POINT_UP:
                isDrawFocus = false;
                int newx = (int) (x / mChessSize);
                int newy = (int) (y / mChessSize);
                if (canAdd(newx, newy, focus)) {
                    addChess(focus.x, focus.y);
                } else {
                    invalidate();
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 判断是否取消此次下子
     * @param x x位置
     * @param y y位置
     */
    private boolean canAdd(float x, float y, Coordinate focus){
        return x < focus.x+3 && x > focus.x -3
                && y < focus.y + 3 && y > focus.y - 3;
    }


    // 画棋盘背景
    private void drawChessBoard(Canvas canvas){
        // 绘制锚点(中心点)
        int startX = mChessSize/2;
        int startY = mChessSize/2;
        int endX = startX + (mChessSize * (mChessboardWLen - 1));
        int endY = startY + (mChessSize * (mChessboardHLen - 1));
        // draw 竖直线
        for (int i = 0; i < mChessboardWLen; ++i){
            canvas.drawLine(new Point(startX+(i*mChessSize), startY), new Point(startX+(i*mChessSize), endY), boardPaint);
        }
        // draw 水平线
        for (int i = 0; i < mChessboardHLen; ++i){
            canvas.drawLine(new Point(startX, startY+(i*mChessSize)), new Point(endX, startY+(i*mChessSize)), boardPaint);
        }
        // 绘制锚点(中心点)
        int circleX = startX+mChessSize*(mChessboardWLen /2);
        int circleY = startY+mChessSize*(mChessboardHLen /2);
        canvas.drawCircle(circleX, circleY, anchorWidth, boardPaint);
    }

    // 画棋子
    private void drawChess(Canvas canvas){
        int[][] chessMap = mGame.getChessMap();
        for (int x = 0; x < chessMap.length; ++x){
            for (int y = 0; y < chessMap[0].length; ++y){
                int type = chessMap[x][y];
                if (type == Game.BLACK){
                    canvas.drawPixelMapHolder(new PixelMapHolder(mBlack), x*mChessSize, y*mChessSize, chessPaint);
                } else if (type == Game.WHITE){
                    canvas.drawPixelMapHolder(new PixelMapHolder(mWhite), x*mChessSize, y*mChessSize, chessPaint);
                }
            }
        }
        // 画最新下的一个棋子
        if (mGame.getActions() != null && mGame.getActions().size() > 0){
            Coordinate last = mGame.getActions().getLast();
            int lastType = chessMap[last.x][last.y];
            if (lastType == Game.BLACK){
                canvas.drawPixelMapHolder(new PixelMapHolder(mBlackNew), last.x*mChessSize, last.y*mChessSize, chessPaint);
            } else if (lastType == Game.WHITE){
                canvas.drawPixelMapHolder(new PixelMapHolder(mWhiteNew), last.x*mChessSize, last.y*mChessSize, chessPaint);
            }
        }
    }

    /**
     * 画手指点击的位置框，辅助显示点击位置是否准确，如点错可滑动取消本次点击
     */
    private void drawFocus(Canvas canvas){
        if (isDrawFocus){
            canvas.drawPixelMapHolder(new PixelMapHolder(bFocus), focus.x*mChessSize, focus.y*mChessSize, chessPaint);
        }
    }


}
