package com.android.football.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.android.football.models.Ball;
import com.android.football.models.Player;
import com.android.football.threads.FootballThread;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

public class MainActivity extends Activity implements ContactListener{

    private SurfaceView view;
    private SurfaceHolder holder;

    protected float dpHeight;

    protected float dpWidth;

    protected float density;

    private FootballThread gameThread;

    private World world;
    private Vec2 gravity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        dpHeight = displayMetrics.heightPixels ;
        dpWidth = displayMetrics.widthPixels ;
        density = displayMetrics.density;
        view  = new SurfaceView(this);
        holder = view.getHolder();
        fullScreen(this);
        setContentView(view);

        gravity = new Vec2(0,10f);
        world  = new World(gravity);
        world.setAutoClearForces(true);
        world.setContactListener(this);
        startNewThread2PlayGame();


    }

    @Override
    protected void onDestroy() {
        if (gameThread != null){
            gameThread.setThreadRunning(false);
        }
        super.onDestroy();

    }

    private final void fullScreen(Activity activity){
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().setBackgroundDrawable(null);

    }

    private void startNewThread2PlayGame(){

        gameThread = new FootballThread(this, holder, world);
        Ball ball1 = new Ball(this);
        Ball ball2 = new Ball(this);
        Ball ball3 = new Ball(this);
        Ball ball4 = new Ball(this);
        ball1.prepareBody(world, 70, 10, 10);
        ball2.prepareBody(world, 70, dpWidth/3+30, 10);
        ball3.prepareBody(world, 70, dpWidth/3*2-30, 150);
        ball4.prepareBody(world, 70, dpWidth-70, 50);

        Player player = new Player(this);
        player.preparePlayerBody(world, dpWidth/2, dpHeight/2, 40, 80);
        view.setOnTouchListener(player);
        gameThread.setBall(ball1);
        gameThread.setBall(ball2);
        gameThread.setBall(ball3);
        gameThread.setBall(ball4);
        gameThread.setPlayer(player);
        gameThread.setGameActor(player);
        gameThread.setGameActor(ball1);
        gameThread.setGameActor(ball2);
        gameThread.setGameActor(ball3);
        gameThread.setGameActor(ball4);

        gameThread.setThreadRunning(true);
        gameThread.start();
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
