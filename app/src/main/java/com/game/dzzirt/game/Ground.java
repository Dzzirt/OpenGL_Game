package com.game.dzzirt.game;

import android.content.Context;

import com.game.dzzirt.game.primitives.Object3D;
import com.game.dzzirt.game.primitives.VertexData;

import org.joml.Matrix4f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TRIANGLES;
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
 * Created by Dzzirt on 02.12.2016.
 */

public class Ground extends Object3D {

    public Ground(Context context) {
        super(context);
        prepareData();
    }

    @Override
    protected void prepareData() {
        float[] vertices = {
                // тестовая земля
                -10, 0, -10,
                -10, 0, 10,
                10, 0, 10,
                10, 0, -10
        };

        short[] faces = {
                0, 1, 2,
                3, 0, 2
        };

        float[] colors = {
                0, 0, 1, 1,
                0, 1, 0, 1,
                1, 0, 0, 1,
                1, 1, 1, 1
        };

        VertexData vertexData = new VertexData(vertices, faces);
        vertexData.setColors(colors);
        setVertexData(vertexData);
    }
}
