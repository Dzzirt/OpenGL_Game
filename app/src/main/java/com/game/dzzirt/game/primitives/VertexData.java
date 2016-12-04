package com.game.dzzirt.game.primitives;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Dzzirt on 03.12.2016.
 */

public class VertexData {

    private FloatBuffer m_verticies;
    private ShortBuffer m_faces;
    private FloatBuffer m_colors;

    public VertexData(float[] verticies, short[] faces) {
        m_verticies = ByteBuffer
                .allocateDirect(verticies.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(verticies);
        m_faces = ByteBuffer
                .allocateDirect(faces.length * 4)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(faces);
        m_verticies.position(0);
        m_faces.position(0);
    }


    public FloatBuffer getVerticies() {
        return m_verticies;
    }

    public ShortBuffer getFaces() {
        return m_faces;
    }

    public FloatBuffer getColors() {
        return m_colors;
    }

    public void setVerticies(float[] verticies) {
        m_verticies = ByteBuffer
                .allocateDirect(verticies.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(verticies);
        m_verticies.position(0);
    }

    public void setFaces(short[] faces) {
        m_faces = ByteBuffer
                .allocateDirect(faces.length * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(faces);
        m_faces.position(0);
    }

    public void setColors(float[] colors) {
        m_colors = ByteBuffer
                .allocateDirect(colors.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(colors);
        m_colors.position(0);
    }
}
