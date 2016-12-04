package com.game.dzzirt.game;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.game.dzzirt.game.common.FileUtils;
import com.game.dzzirt.game.primitives.EShaderType;
import com.game.dzzirt.game.primitives.Shader;
import com.game.dzzirt.game.primitives.ShaderProgram;

import org.joml.Matrix3d;
import org.joml.Matrix4d;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glUseProgram;

/**
 * Created by Dzzirt on 19.11.2016.
 */

public class OpenGLRenderer implements GLSurfaceView.Renderer {

    private Context m_context;
    private Camera m_camera;
    private Player m_player;

    public OpenGLRenderer(Context context) {
        m_context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0f, 0f, 0f, 1f);
        glEnable(GL_DEPTH_TEST);
        m_camera = new Camera(new Vector2i(0, 0));
        m_camera.setPosition(new Vector3f(0, 6, 5));
        m_camera.setCenter(new Vector3f(0, 0, 0));
        m_player = new Player(m_context);
        System.out.println();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        m_camera.setViewport(new Vector2i(width, height));
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        m_player.draw(m_camera.getTransform());
    }

}
