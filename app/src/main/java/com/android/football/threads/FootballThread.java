package com.android.football.threads;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.android.football.interfaces.IFigureDrawable;
import com.android.football.models.Ball;
import com.android.football.models.Player;

import org.jbox2d.dynamics.World;

import java.util.ArrayList;

/**
 * Created by Mihail on 23.09.14.
 */

public class FootballThread extends Thread {

    private boolean isThreadRunning;

    private SurfaceHolder holder;

    private int colorBackground;

    private Paint paint;



    private World world;

    private Context context;

    private Ball ball;

    ArrayList<IFigureDrawable> allGameActors = new ArrayList<>();
//    ArrayList<Body> allBalls = new ArrayList<>();
//
//    ArrayList<Body> allPlayers = new ArrayList<>();
    Player player;

    public FootballThread(Context context, SurfaceHolder holder, World world){
        super();
        this.context = context;
        this.holder = holder;
        this.world = world;
        paint = new Paint();


        paint.setColor(Color.BLUE);

    }

    @Override
    public void run() {
        while (isThreadRunning){
            Canvas canvas = null;
            try{
                world.step(1f/16f, 6, 2);
                canvas  = holder.lockCanvas();
                if(canvas != null){
                    canvas.drawPaint(paint);
                    for (IFigureDrawable b : allGameActors){
                        b.draw(canvas, b.getFigureBody());
                    }
//                    for (Body b: allBalls){
//                        ball.draw(canvas, b);
//                    }
//                    for (Body b:allPlayers){
//                        player.draw(canvas, b);
//                    }

                //holder.unlockCanvasAndPost(canvas);
                }

                try {
                    //holder.unlockCanvasAndPost(canvas);
                    Thread.sleep(8);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }catch (Exception exc){
                exc.printStackTrace();
            }
            finally {
                if (canvas != null)
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public boolean isThreadRunning() {
        return isThreadRunning;
    }

    public void setThreadRunning(boolean isThreadRunning) {
        this.isThreadRunning = isThreadRunning;
    }

    public int getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(int colorBackground) {
        this.colorBackground = colorBackground;
    }
    public void setBall(Ball f){
        ball = f;
    }
    public void setPlayer(Player p){
        player = p;
    }

    public void setGameActor(IFigureDrawable body){
        allGameActors.add(body);
    }

//    public void setBallBody(Body body){
//        allBalls.add(body);
//    }
//
//    public void setPlayer (Body playerBody){
//        allPlayers.add(playerBody);
//    }
}
