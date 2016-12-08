package com.game.dzzirt.game;

import android.content.Context;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import com.game.dzzirt.game.primitives.Object3D;
import com.game.dzzirt.game.primitives.VertexData;

import org.joml.Matrix4f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_INT;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_INT;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * Created by Dzzirt on 29.11.2016.
 */

public class Player extends Object3D {

    public Player(Context context) {
        super(context);
        prepareData();
    }

    @Override
    protected void prepareData() {

        float[] vertices = {
                // тестовый игрок
                -1f, +1f, -1f,
                +1f, +1f, -1f,
                +1f, -1f, -1f,
                -1f, -1f, -1f,
                -1f, +1f, +1f,
                +1f, +1f, +1f,
                +1f, -1f, +1f,
                -1f, -1f, +1f
        };

        float[] colors = {
                1f, 0, 1f, 1f,
                1f, 0, 1f, 1f,
                0, 1, 0, 1,
                0, 1, 0, 1,
                1, 0, 0, 1,
                1, 0, 0, 1,
                1, 1, 0, 1,
                1, 1, 0, 1
        };

        short[] faces = {
                0, 1, 2,
                0, 2, 3,
                2, 1, 5,
                2, 5, 6,
                3, 2, 6,
                3, 6, 7,
                0, 3, 7,
                0, 7, 4,
                1, 0, 4,
                1, 4, 5,
                6, 5, 4,
                6, 4, 7,
        };
        VertexData vertexData = new VertexData(vertices, faces);
        vertexData.setColors(colors);
        setVertexData(vertexData);
    }
}
