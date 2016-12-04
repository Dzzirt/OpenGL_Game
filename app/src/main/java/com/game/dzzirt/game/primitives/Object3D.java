package com.game.dzzirt.game.primitives;

import android.content.Context;
import android.opengl.Matrix;

import com.game.dzzirt.game.R;
import com.game.dzzirt.game.common.FileUtils;

import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import static android.opengl.GLES20.glUseProgram;

/**
 * Created by Dzzirt on 28.11.2016.
 */

public abstract class Object3D extends ADrawable implements ITransformable {

    private Matrix4f m_translationMat = new Matrix4f();
    private Matrix4f m_rotationMat = new Matrix4f();

    private VertexData m_vertexData;

    private ShaderProgram m_shaderProgram;

    protected abstract void prepareData();

    protected abstract void bindData();

    protected void setVertexData(VertexData vertexData) {
        m_vertexData = vertexData;
    }

    protected VertexData getVertexData() {
        return m_vertexData;
    }

    protected int getShaderProgramId() {
        return m_shaderProgram.getProgramId();
    }

    public Object3D(Context context, int vertexShaderRes, int fragmentShaderRes) {
        createShaderProgram(context, vertexShaderRes, fragmentShaderRes);
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
}
