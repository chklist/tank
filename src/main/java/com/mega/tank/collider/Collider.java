package com.mega.tank.collider;

import com.mega.tank.role.GameRole;

abstract class Collider {

    boolean isContinue = true;

    abstract void collide(GameRole gr1, GameRole gr2);
}
