package com.game.dzzirt.game.primitives;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Created by Dzzirt on 28.11.2016.
 */

public interface ITransformable {
    void setPosition(Vector3f pos);
    void setRotation(Vector3f degree);
    Vector3f getPosition();
    Vector3f getRotation();
    Matrix4f getTransform();
}
