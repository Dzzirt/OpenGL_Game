package com.game.dzzirt.game.primitives;

import java.io.IOException;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glShaderSource;

/**
 * Created by Dzzirt on 27.11.2016.
 */

public class Shader implements IDisposable {

    private int m_shaderId;

    public Shader(EShaderType type, String shaderText) throws IOException {
        m_shaderId = glCreateShader(getShaderType(type));
        glShaderSource(m_shaderId, shaderText);
        compileShader();
    }

    private void compileShader() throws IOException {
        glCompileShader(m_shaderId);
        final int[] compileStatus = new int[1];
        glGetShaderiv(m_shaderId, GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            String log = glGetShaderInfoLog(m_shaderId);
            dispose();
            throw new IOException(log);
        }
    }

    public int getShaderId() {
        return m_shaderId;
    }

    private static int getShaderType(EShaderType type) {
        switch (type) {
            case FRAGMENT_SHADER:
                return GL_FRAGMENT_SHADER;
            case VERTEX_SHADER:
                return GL_VERTEX_SHADER;
        }
        return 0;
    }

    @Override
    public void dispose() {
        glDeleteShader(m_shaderId);
    }
}
