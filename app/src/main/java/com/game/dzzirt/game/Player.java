package com.game.dzzirt.game;

import android.content.Context;
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

    private int m_uMatrixLocation;

    public Player(Context context) {
        super(context, R.raw.vertex_shader, R.raw.fragment_shader);
        prepareData();
        bindData();
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
                0.5f, 1f, 1f, 1f,
                0.5f, 1f, 1f, 1f,
                0, 0, 0, 1f,
                0, 0, 0, 1f,
                0.5f, 0.5f, 0, 1f,
                0.5f, 0.5f, 0, 1f
        };

        short[] faces = {
                0, 1, 2,
                0, 2, 3,
                2, 5, 1,
                2, 6, 5,
                0, 1, 5,
                5, 4, 0,
                4, 0, 3,
                3, 7, 4,
                3, 2, 6,
                6, 7, 3,
                4, 5, 6,
                6, 4, 7,
        };
        VertexData vertexData = new VertexData(vertices, faces);
        vertexData.setColors(colors);
        setVertexData(vertexData);
    }

    @Override
    protected void bindData() {
        int programId = getShaderProgramId();
//        getVertexData().setPosition(0);
        int aPositionLocation = glGetAttribLocation(programId, "a_Position");
        glVertexAttribPointer(aPositionLocation, 3, GL_FLOAT,
                false, 0, getVertexData().getVerticies());

        glEnableVertexAttribArray(aPositionLocation);

        // цвет
        int a_color = glGetAttribLocation(programId, "a_Color");
        glVertexAttribPointer(a_color, 4, GL_FLOAT, false, 0, getVertexData().getColors());
        glEnableVertexAttribArray(a_color);
//        int u_color = glGetUniformLocation(programId, "u_Color");
//        glUniform4f(u_color, 1f, 0, 0, 1f);

        // матрица
        m_uMatrixLocation = glGetUniformLocation(programId, "u_Matrix");
    }

    @Override
    protected void draw(Matrix4f projViewMat) {
        updateResultMatrix(projViewMat);
        glDrawElements(GL_TRIANGLES, getVertexData().getFaces().capacity(), GL_UNSIGNED_SHORT, getVertexData().getFaces());
    }
    private void updateResultMatrix(Matrix4f projViewMat) {
        Matrix4f resultMat = new Matrix4f();
        projViewMat.mul(getTransform(), resultMat);
        float[] temp = new float[16];
        resultMat.get(temp);
        glUniformMatrix4fv(m_uMatrixLocation, 1, false, temp, 0);
    }
}
