package com.android.football.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.View;

import com.android.football.R;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Created by Mihail on 23.09.14.
 */

public class Ball extends BaseGameActor implements View.OnTouchListener {

    public static final float FIXTURE_DESTINY = 0.1f;

    public static final float FIXTURE_FRICTION = 0.1f;

    public static final float FIXTURE_RESTITUTION = 0.8f;

    public static final float PI = 3.14f;

    public static final float GRAD = 180f;

    private float[][] wallDef;

    private Bitmap ball_icon;

    private CircleShape ball;

    private BodyDef bodyDef;

    Matrix matrix;

    public Ball(Context context){
        super(context);
        ball_icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.soccer_ball);
        wallDef = new float[][]{
                {0f, 0f, dpWidth, 3f},
                {0f, 0f, 0f, dpHeight},
                {0f, dpHeight, dpWidth, 3f},
                {dpWidth, 0f, 3f, dpWidth}
        };
        matrix = new Matrix();
    }


    public void prepareBody(World world, float radius, float x, float y){
        prepareCircleShape(radius);
        prepareBodyDef(x, y);
        if (bodyDef!=null)
            body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ball;
        fixtureDef.density = FIXTURE_DESTINY;
        fixtureDef.friction = FIXTURE_FRICTION;
        fixtureDef.restitution = FIXTURE_RESTITUTION;
        body.createFixture(fixtureDef);

        for (int j=0; j<wallDef.length; j++){
            BodyDef wallBodyDef = new BodyDef();
            wallBodyDef.position.set(wallDef[j][0], wallDef[j][1]);
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(wallDef[j][2], wallDef[j][3]);
            Body wall = world.createBody(wallBodyDef);
            wall.createFixture(polygonShape, 1f);
        }
    }

    public void prepareCircleShape(float radius){
        ball = new CircleShape();
        ball.setRadius(radius);
    }

    public void prepareBodyDef(float pos_x, float pos_y){
        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.x = pos_x;
        bodyDef.position.y = pos_y;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        Vec2 newPosition = new Vec2(x, y);
//        body.applyLinearImpulse(new Vec2(0.1f,0.5f), body.getPosition(), false);
        body.setTransform(newPosition, body.getAngle());
//        body.setLinearVelocity(newPosition);
        return true;
    }

    @Override
    public void draw(Canvas canvas, Body body) {
        matrix.reset();
        matrix.postRotate(body.getAngle()/PI*GRAD);
        matrix.preTranslate(-ball_icon.getWidth()>>1,-ball_icon.getHeight()>>1);
        matrix.postTranslate(body.getPosition().x, body.getPosition().y);
        canvas.drawBitmap(ball_icon, matrix, null);
    }
}
