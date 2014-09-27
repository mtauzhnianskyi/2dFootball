package com.android.football.models;

import android.graphics.Paint;

/**
 * Created by Mihail on 25.09.14.
 */

public class PlayerBodyFixtures {

    private PlayerTorso torso;

    private PlayerHand leftHand;

    private PlayerHand rightHand;

    private PlayerLeg leftLeg;

    private PlayerLeg rightLeg;

    private Paint paintHead;

    private float headRadius;


    public PlayerBodyFixtures(PlayerTorso torso, PlayerHand leftHand, PlayerHand rightHand,
                              PlayerLeg leftLeg, PlayerLeg rightLeg,
                              Paint paintHead, float headRadius){
        this.torso = torso;

        this.leftHand = leftHand;

        this.rightHand = rightHand;

        this.leftLeg = leftLeg;

        this.rightLeg = rightLeg;

        this.paintHead = paintHead;

        this.headRadius = headRadius;
    }

    public PlayerBodyFixtures(PlayerTorso torso, PlayerHand hand,
                              PlayerLeg leg,
                              Paint paintHead, float headRadius){
        this.torso = torso;

        this.leftHand = hand;

        this.rightHand = hand;

        this.leftLeg = leg;

        this.rightLeg = leg;

        this.paintHead = paintHead;

        this.headRadius = headRadius;
    }

    public PlayerBodyFixtures(float torsoW, float torsoH, Paint paintTorso,
                              float leftHandW, float leftHandH, Paint paintHands,
                              float rightHandW, float rightHandH,
                              float leftLegW, float leftLegH,
                              float rightLegW, float rightLegH, Paint paintLegs,
                              Paint paintHead, float headRadius){
        torso = new PlayerTorso(torsoW, torsoH, paintTorso);

        leftHand = new PlayerHand(leftHandW, leftHandH, paintHands);

        rightHand = new PlayerHand(rightHandW, rightHandH, paintHands);

        leftLeg = new PlayerLeg(leftLegW, leftLegH, paintLegs);

        rightLeg = new PlayerLeg(rightLegW, rightLegH, paintLegs);

        this.paintHead = paintHead;

        this.headRadius = headRadius;
    }

    public Paint getPaintHead() {
        return paintHead;
    }

    public void setPaintHead(Paint paintHead) {
        this.paintHead = paintHead;
    }

    public float getHeadRadius() {
        return headRadius;
    }

    public void setHeadRadius(float headRadius) {
        this.headRadius = headRadius;
    }

    public PlayerTorso getTorso() {
        return torso;
    }

    public void setTorso(PlayerTorso torso) {
        this.torso = torso;
    }

    public PlayerHand getLeftHand() {
        return leftHand;
    }

    public void setLeftHand(PlayerHand leftHand) {
        this.leftHand = leftHand;
    }

    public PlayerHand getRightHand() {
        return rightHand;
    }

    public void setRightHand(PlayerHand rightHand) {
        this.rightHand = rightHand;
    }

    public PlayerLeg getLeftLeg() {
        return leftLeg;
    }

    public void setLeftLeg(PlayerLeg leftLeg) {
        this.leftLeg = leftLeg;
    }

    public PlayerLeg getRightLeg() {
        return rightLeg;
    }

    public void setRightLeg(PlayerLeg rightLeg) {
        this.rightLeg = rightLeg;
    }
}
