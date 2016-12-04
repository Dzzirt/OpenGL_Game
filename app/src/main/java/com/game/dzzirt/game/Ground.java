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
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * Created by Dzzirt on 02.12.2016.
 */

public class Ground extends Object3D {

    private int m_uMatrixLocation;


    public Ground(Context context, int vertexShaderRes, int fragmentShaderRes) {
        super(context, vertexShaderRes, fragmentShaderRes);
    }

    @Override
    protected void prepareData() {
        float[] vertices = {
                // тестовая земля
                -100, -100, 0,
                -100, 100, 0,
                100, 100, 0,
                100, -100, 0
        };

        setVertexData(new VertexData(vertices, new short[0]));
    }

    @Override
    protected void bindData() {
        int programId = getShaderProgramId();
        int aPositionLocation = glGetAttribLocation(programId, "a_Position");
        VertexData vertexData = getVertexData();
        glVertexAttribPointer(aPositionLocation, 3, GL_FLOAT,
                false, 0, vertexData.getVerticies());
        glEnableVertexAttribArray(aPositionLocation);

        // цвет
        int u_color = glGetUniformLocation(programId, "u_Color");
        glUniform4f(u_color, 1f, 0, 0, 1f);

        // матрица
        m_uMatrixLocation = glGetUniformLocation(programId, "u_Matrix");
    }

    @Override
    protected void draw(Matrix4f projViewMat) {
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }
}
