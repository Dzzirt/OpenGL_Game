package com.game.dzzirt.game.primitives;

import android.content.Context;

import com.game.dzzirt.game.R;
import com.game.dzzirt.game.common.FileUtils;

import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * Created by Dzzirt on 28.11.2016.
 */

public abstract class Object3D extends ADrawable implements ITransformable {

    private Matrix4f m_translationMat = new Matrix4f();
    private Matrix4f m_rotationMat = new Matrix4f();

    private VertexData m_vertexData;

    private ShaderProgram m_shaderProgram;

    private int m_uMatrixLocation;
    private int m_aPositionLocation;
    private int m_aColorLocation;

    protected abstract void prepareData();

    protected void bindData() {
        int programId = getShaderProgramId();
        m_aPositionLocation = glGetAttribLocation(programId, "a_Position");
        glVertexAttribPointer(m_aPositionLocation, 3, GL_FLOAT,
                false, 0, getVertexData().getVerticies());

        if (getVertexData().getColors() != null) {
            m_aColorLocation = glGetAttribLocation(programId, "a_Color");
            glVertexAttribPointer(m_aColorLocation, 4, GL_FLOAT, false, 0, getVertexData().getColors());
        }

        m_uMatrixLocation = glGetUniformLocation(programId, "u_Matrix");
    } ;

    private void updateModelMatrix(Matrix4f projViewMat) {
        Matrix4f resultMat = new Matrix4f();
        projViewMat.mul(getTransform(), resultMat);
        float[] temp = new float[16];
        resultMat.get(temp);
        glUniformMatrix4fv(m_uMatrixLocation, 1, false, temp, 0);
    }

    protected void setVertexData(VertexData vertexData) {
        m_vertexData = vertexData;
    }

    protected VertexData getVertexData() {
        return m_vertexData;
    }

    protected int getShaderProgramId() {
        return m_shaderProgram.getProgramId();
    }

    public Object3D(Context context) {
        createShaderProgram(context, R.raw.vertex_shader, R.raw.fragment_shader);
    }

    @Override
    public void setPosition(Vector3f pos) {
        m_translationMat.identity();
        m_translationMat.translate(pos.x, pos.y, pos.z);
    }

    @Override
    public void setRotation(Vector3f degree) {
        m_rotationMat.identity();
        m_rotationMat.rotateXYZ(degree);
    }

    @Override
    public Vector3f getPosition() {
        return m_translationMat.getTranslation(new Vector3f());
    }

    @Override
    public Vector3f getRotation() {
        AxisAngle4f rotation = m_rotationMat.getRotation(new AxisAngle4f());
        return new Vector3f();
    }

    private void createShaderProgram(Context context, int vertexShaderRes, int fragmentShaderRes) {
        try {
            ArrayList<Shader> shaders = new ArrayList<>();
            shaders.add(new Shader(EShaderType.VERTEX_SHADER,
                    FileUtils.readTextFromRaw(context, vertexShaderRes)));
            shaders.add(new Shader(EShaderType.FRAGMENT_SHADER,
                    FileUtils.readTextFromRaw(context, fragmentShaderRes)));
            m_shaderProgram = new ShaderProgram(shaders);
            glUseProgram(m_shaderProgram.getProgramId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Matrix4f getTransform() {
        Matrix4f result = new Matrix4f();
        m_translationMat.mul(m_rotationMat, result);
        return result;
    }

    @Override
    public void draw(Matrix4f projViewMat) {
        bindData();

        updateModelMatrix(projViewMat);

        glEnableVertexAttribArray(m_aPositionLocation);
        glEnableVertexAttribArray(m_aColorLocation);

        glDrawElements(GL_TRIANGLES, getVertexData().getFaces().capacity()
                , GL_UNSIGNED_SHORT, getVertexData().getFaces());

        glDisableVertexAttribArray(m_aColorLocation);
        glDisableVertexAttribArray(m_aPositionLocation);
    }
}
