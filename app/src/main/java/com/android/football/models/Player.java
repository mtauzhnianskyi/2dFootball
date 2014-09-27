package com.android.football.models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Created by Mihail on 23.09.14.
 */

public class Player extends BaseGameActor implements View.OnTouchListener {

    public static final float FIXTURE_DESTINY = 1;

    public static final float FIXTURE_FRICTION = 0.3f;

    public static final float FIXTURE_RESTITUTION = 0.8f;

    public static final float PI = 3.14f;

    public static final float GRAD = 180f;

    private PlayerBodyFixtures bodyFixtures;

    private Paint paintTorso;

    private Paint paintHead;

    private Paint paintHands;

    public Player(Context context) {
        super(context);

        paintTorso = new Paint();
        paintTorso.setStyle(Paint.Style.FILL);
        paintTorso.setColor(Color.RED);
        paintHead = new Paint();
        paintHead.setStyle(Paint.Style.FILL);
        paintHead.setColor(Color.BLACK);
        paintHands = new Paint();
        paintHands.setStyle(Paint.Style.FILL);
        paintHands.setColor(Color.YELLOW);
    }


    public void preparePlayerBody(World world, float x, float y, float width, float height) {
        setDefaultBodyFixtures(bodyFixtures, width, height);

        // head shape
        CircleShape head = new CircleShape();
        head.m_radius = bodyFixtures.getHeadRadius();
        Vec2 offset = new Vec2(0, -height - head.m_radius);
        head.m_p.set(offset.x, offset.y);

        // torso shape
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(bodyFixtures.getTorso().getEndWidth(),
                bodyFixtures.getTorso().getEndHeight());

        //left hand shape
        PolygonShape leftPolygon = new PolygonShape();
        leftPolygon.setAsBox(bodyFixtures.getLeftHand().getEndWidth(),
                bodyFixtures.getLeftHand().getEndHeight(),
                new Vec2(-width * 2, -45), 0);

        // right hand shape
        PolygonShape rightPolygon = new PolygonShape();
        rightPolygon.setAsBox(bodyFixtures.getRightHand().getEndWidth(),
                bodyFixtures.getRightHand().getEndHeight(),
                new Vec2(width * 2, -45), 0);

        //left leg shape
        PolygonShape leftLegPolygon = new PolygonShape();
        leftLegPolygon.setAsBox(bodyFixtures.getLeftLeg().getEndWidth(),
                bodyFixtures.getLeftLeg().getEndHeight(),
                new Vec2(-width, height * 2), 0);

        PolygonShape rightLegPolygon = new PolygonShape();
        rightLegPolygon.setAsBox(bodyFixtures.getRightLeg().getEndWidth(),
                bodyFixtures.getRightLeg().getEndHeight(),
                new Vec2(width, height * 2), 0);

        BodyDef headBodyDef = new BodyDef();
        headBodyDef.type = BodyType.STATIC;
        headBodyDef.position.x = x;
        headBodyDef.position.y = y;
        if (headBodyDef != null) {
            body = world.createBody(headBodyDef);

        }
        FixtureDef fixtureDef = new FixtureDef();
        setupFixture(fixtureDef, polygonShape);

        FixtureDef fixtureDef1 = new FixtureDef();
        setupFixture(fixtureDef1, leftPolygon);

        FixtureDef fixtureDef2 = new FixtureDef();
        setupFixture(fixtureDef2, rightPolygon);

        FixtureDef fixtureDef3 = new FixtureDef();
        setupFixture(fixtureDef3, leftLegPolygon);

        FixtureDef fixtureDef4 = new FixtureDef();
        setupFixture(fixtureDef4, rightLegPolygon);

        body = world.createBody(headBodyDef);
        body.createFixture(fixtureDef);

        body.createFixture(fixtureDef1);
        body.createFixture(fixtureDef2);
        body.createFixture(fixtureDef3);
        body.createFixture(fixtureDef4);
        body.createFixture(head, 1.0f);
    }

    private void setupFixture(FixtureDef fixtureDef, PolygonShape ps) {
        fixtureDef.shape = ps;
        fixtureDef.density = FIXTURE_DESTINY;
        fixtureDef.friction = FIXTURE_FRICTION;
        fixtureDef.restitution = FIXTURE_RESTITUTION;
    }

    private void drawHead(Canvas canvas, Vec2 position, CircleShape shape) {

        canvas.drawCircle(position.x + shape.m_p.x, position.y + shape.m_p.y,
                bodyFixtures.getHeadRadius(), bodyFixtures.getPaintHead());
    }

    private void drawTorsoShape(Canvas canvas, Vec2 position, PolygonShape ps, int typeShapeDeep) {
        switch (typeShapeDeep) {
            case 0:
                drawShapePart(canvas, position, ps,
                        bodyFixtures.getRightLeg().getEndWidth(),
                        bodyFixtures.getRightLeg().getEndHeight(),
                        bodyFixtures.getRightLeg().getEndColor());
                break;
            case 1:
                drawShapePart(canvas, position, ps,
                        bodyFixtures.getLeftLeg().getEndWidth(),
                        bodyFixtures.getLeftLeg().getEndHeight(),
                        bodyFixtures.getLeftLeg().getEndColor());
                break;
            case 2:
                drawShapePart(canvas, position, ps,
                        bodyFixtures.getRightHand().getEndWidth(),
                        bodyFixtures.getRightHand().getEndHeight(),
                        bodyFixtures.getRightHand().getEndColor());
                break;
            case 3:
                drawShapePart(canvas, position, ps,
                        bodyFixtures.getLeftHand().getEndWidth(),
                        bodyFixtures.getLeftHand().getEndHeight(),
                        bodyFixtures.getLeftHand().getEndColor());
                break;
            case 4:
                drawShapePart(canvas, position, ps,
                        bodyFixtures.getTorso().getEndWidth(),
                        bodyFixtures.getTorso().getEndHeight(),
                        bodyFixtures.getTorso().getEndColor());
                break;
        }
    }

    private void drawShapePart(Canvas canvas, Vec2 position, PolygonShape ps,
                               float shapeWidth, float shapeHeight, Paint paint) {

        Path p = new Path();
        p.moveTo(position.x + ps.m_centroid.x + shapeWidth,
                position.y + ps.m_centroid.y + shapeHeight);
        p.lineTo(position.x + ps.m_centroid.x + shapeWidth,
                position.y + ps.m_centroid.y - shapeHeight);
        p.lineTo(position.x + ps.m_centroid.x - shapeWidth,
                position.y + ps.m_centroid.y - shapeHeight);
        p.lineTo(position.x + ps.m_centroid.x - shapeWidth,
                position.y + ps.m_centroid.y + shapeHeight);
        p.lineTo(position.x + ps.m_centroid.x + shapeWidth,
                position.y + ps.m_centroid.y + shapeHeight);
        p.close();
        canvas.drawPath(p, paint);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        Vec2 newPosition = new Vec2(x, y);
        goTo(newPosition, 5);
//        body.applyLinearImpulse(new Vec2(0.1f, 0.5f), body.getPosition(), false);
//        body.setTransform(newPosition, body.getAngle());
//        body.setLinearVelocity(newPosition);
        return true;
    }

    private void goTo(Vec2 wayPoint, int speed) {
        float x1 = body.getPosition().x;
        float y1 = body.getPosition().y;

        float x2 = wayPoint.x;
        float y2 = wayPoint.y;

        double angleToPoint = Math.atan2(y2 - y1, x2 - x1);


        if (x1 > x2 & y1 > y2) {
            while ((x2 <= x1) && (y2 <= y1)) {
                x1 -= speed;
                y1 -= speed;
                Vec2 tmpPos = new Vec2(x1, y1);
                //body.setLinearVelocity(tmpPos);
                body.setTransform(tmpPos, (float) angleToPoint);
            }
        } else if (x1 < x2 & y1 > y2) {
            while ((x2 >= x1) && (y2 <= y1)) {
                x1 += speed;
                y1 -= speed;
                Vec2 tmpPos = new Vec2(x1, y1);
//                    body.setLinearVelocity(tmpPos);
                body.setTransform(tmpPos, (float) angleToPoint);
            }
        } else if (x1 > x2 & y1 < y2) {
            while ((x2 <= x1) && (y2 >= y1)) {
                x1 -= speed;
                y1 += speed;
                Vec2 tmpPos = new Vec2(x1, y1);
//                    body.setLinearVelocity(tmpPos);
                body.setTransform(tmpPos, (float) angleToPoint);
            }
        } else {
            while ((x2 >= x1) && (y2 >= y1)) {
                x1 += speed;
                y1 += speed;
                Vec2 tmpPos = new Vec2(x1, y1);
//                    body.setLinearVelocity(tmpPos);
                body.setTransform(tmpPos, (float) angleToPoint);
            }
        }
    }

    @Override
    public void draw(Canvas canvas, Body body) {
        int shapeDeep = 0;
        Fixture f = body.getFixtureList();
        Vec2 position = body.getPosition();
        while (f != null) {
            ShapeType type = f.getType();
            if (type == ShapeType.POLYGON) {
                PolygonShape ps = (PolygonShape) f.getShape();
                drawTorsoShape(canvas, position, ps, shapeDeep);
                shapeDeep++;
            } else if (type == ShapeType.CIRCLE) {
                CircleShape shape = (CircleShape) f.getShape();
                drawHead(canvas, position, shape);
            }
            f = f.getNext();
        }
    }

    public PlayerBodyFixtures getBodyFixtures() {
        return bodyFixtures;
    }

    public void setBodyFixtures(PlayerBodyFixtures bodyFixtures) {
        this.bodyFixtures = bodyFixtures;
    }

    public void setDefaultBodyFixtures(PlayerBodyFixtures bodyFixtures, float width, float height) {

//        this.bodyFixtures = new PlayerBodyFixtures(
//                width,
//                height,
//                paintTorso,
//                50.0f, 10.0f,
//                paintHands,
//                15.0f, 70.0f,
//                paintHead, 30.0f);

        this.bodyFixtures = new PlayerBodyFixtures(new PlayerTorso(width, height, paintTorso),
                new PlayerHand(50f, 10f, paintHands),
                new PlayerLeg(15f, 70f, paintHands),
                paintHead, 30.0f);
    }
}
