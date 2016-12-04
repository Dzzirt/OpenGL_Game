package com.game.dzzirt.game.primitives;

import android.opengl.GLES20;
import android.util.Log;

import java.util.List;

import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glLinkProgram;

/**
 * Created by Dzzirt on 28.11.2016.
 */

public class ShaderProgram {

    private int m_programId;

    public ShaderProgram(List<Shader> shaders) throws Exception {
        m_programId = createProgram();
        attachShaders(shaders);
        if (!shaders.isEmpty()) {
            linkProgram();
        }
        disposeShaders(shaders);
    }

    public int getProgramId() {
        return m_programId;
    }

    private void disposeShaders(List<Shader> shaders) {
        for (Shader s : shaders) {
            s.dispose();
        }
    }

    private int createProgram() throws Exception {
        int programId = glCreateProgram();
        if (programId == 0) {
            throw new Exception("Error create shader program");
        }
        return programId;
    }

    private void linkProgram() throws Exception {
        glLinkProgram(m_programId);
        final int[] linkStatus = new int[1];
        glGetProgramiv(m_programId, GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            glDeleteProgram(m_programId);
            throw new Exception(glGetProgramInfoLog(m_programId));
        }
    }

    private void attachShaders(List<Shader> shaders) {
        for (Shader s : shaders) {
            glAttachShader(m_programId, s.getShaderId());
        }
    }
}
