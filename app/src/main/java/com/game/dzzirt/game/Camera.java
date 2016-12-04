package com.game.dzzirt.game;

import android.opengl.Matrix;

import com.game.dzzirt.game.primitives.ITransformable;

import org.joml.Matrix3d;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;

import static android.opengl.GLES20.glViewport;

/**
 * Created by Dzzirt on 30.11.2016.
 */

public class Camera implements ITransformable {

    private final static Vector3f UP = new Vector3f(0, 1, 0);

    private Vector3f m_center = new Vector3f(0, 0, -100);

    private Vector3f m_position = new Vector3f();

    private Matrix4f m_projMat = new Matrix4f();

    private Matrix4f m_viewMat = new Matrix4f();

    public Camera(Vector2i size) {
        setViewport(size);
        updateLook();
    }

    public void setViewport(Vector2i size) {
        glViewport(0, 0, size.x, size.y);
        updateProjectionMatrix(size.x, size.y);
    }

    @Override
    public void setPosition(Vector3f pos) {
        m_position = pos;
        updateLook();
    }

    @Override
    public void setRotation(Vector3f degree) {
        //Coming soon
    }

    @Override
    public Vector3f getPosition() {
        return m_position;
    }

    @Override
    public Vector3f getRotation() {
        //Coming soon
        return null;
    }

    public void setCenter(Vector3f center) {
        m_center = center;
        updateLook();
    }

    @Override
    public Matrix4f getTransform() {
        Matrix4f result = new Matrix4f();
        m_projMat.mul(m_viewMat, result);
        return result;
    }

    private void updateProjectionMatrix(int width, int height) {
        float ratio;
        float left = -1;
        float right = 1;
        float bottom = -1;
        float top = 1;
        float near = 2;
        float far = 100;
        if (width > height) {
            ratio = (float) width / height;
            left *= ratio;
            right *= ratio;
        } else {
            ratio = (float) height / width;
            bottom *= ratio;
            top *= ratio;
        }
        m_projMat.identity();
        m_projMat.frustum(left, right, bottom, top, near, far);
    }

    private void updateLook() {
        m_viewMat.identity();
        m_viewMat.setLookAt(m_position, m_center, UP);
    }

}
