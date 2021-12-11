package com.mega.tank.collider;

import com.mega.tank.role.GameRole;
import com.mega.tank.util.PropertyMgr;

import java.util.LinkedList;
import java.util.List;

public class ColliderChain extends Collider {

    private static final String PROP_KEY_NAME = "colliders";

    private List<Collider> colliders = new LinkedList<>();

    public ColliderChain() {
        init();
    }

    private void init() {
        String value = PropertyMgr.getInstance().getString(PROP_KEY_NAME);
        for (String clzName : value.split(",")) {
            try {
                Collider collider = (Collider) Class.forName(clzName).getDeclaredConstructor().newInstance();
                add(collider);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void add(Collider collider) {
        colliders.add(collider);
    }

    @Override
    public void collide(GameRole gr1, GameRole gr2) {
        for (Collider collider : colliders) {
            collider.collide(gr1, gr2);
            if (!collider.isContinue) {
                break;
            }
        }
    }
}
