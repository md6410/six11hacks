package org.six11.flatcad;

import org.six11.flatcad.gl.GLDebug;
import org.six11.flatcad.gl.GLApp;
import org.six11.flatcad.gl.GLImage;
import org.six11.util.Debug;

/**
 * Render 2D text using texture mapping.  Text is drawn over a simple 3D
 * scene (same as in GLApp_DemoNavigation.java).  The text stays
 * in place while the 3D scene moves.  Demonstrates functions to build a
 * character set, draw text, use 2D (Ortho) mode:
 *
 *          buildFont( <font image name>, <font width> )
 *          ...
 *          glPrint( <xpos>,  <ypos>, <upper/lower case>, <text string>);
 * <P>
 * GLApp initializes the LWJGL environment for OpenGL rendering,
 * ie. creates a window, sets the display mode, inits mouse and keyboard,
 * then runs a loop that calls render().
 * <P>
 * Special thanks to NeHe and Giuseppe D'Agata for the "2D Texture Font"
 * tutorial (http://nehe.gamedev.net).
 * <P>
 * napier at potatoland dot org
 */

import java.io.*;
import java.nio.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.glu.*;

public class TestPrintText extends GLApp {
    // Handle for texture
    int sphereTextureHandle = 0;
    int groundTextureHandle = 0;
    // Lighting colors
    float faWhite[]      = { 1.0f, 1.0f, 1.0f, 1.0f };
    float faLightBlue[]  = { 0.8f, 0.8f, .9f, 1f };
    // Light position: if last value is 0, then this describes light direction.  If 1, then light position.
    float lightPosition[]= { -2f, 2f, 2f, 1f };
    // World coordinates of sphere
    float[] spherePos = {0f, 0f, 0f};
    // Rotation of sphere
    float rotation=0f;
    // Camera position
    static float[] cameraPos = {0f,0f,25f};
    float cameraRotation = 0f;
    // A constant used in navigation
    final float piover180 = 0.0174532925f;
    // for cursor
    int cursorTextureHandle = 0;

    /**
     * Initialize the scene.  Called by GLApp.run()
     */
    public void init()
    {
        // initialize Window, Keyboard, Mouse, OpenGL environment
        initDisplay();
        initInput();
        initOpenGL();

        // setup and enable perspective
        setPerspective();

        // Create a light (diffuse light, ambient light, position)
        setLight( GL11.GL_LIGHT1, faWhite, faLightBlue, lightPosition );

        // enable lighting and texture rendering
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        // Enable alpha transparency (so text will have transparent background)
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // Create texture for spere
        GLImage textureImg = loadImage("resources/corn.jpg");
        sphereTextureHandle = makeTexture(textureImg);
        makeTextureMipMap(textureImg);

        // Create texture for ground plane
        textureImg = loadImage("resources/dirty.jpg");
        groundTextureHandle = makeTexture(textureImg);
        makeTextureMipMap(textureImg);

        // Load a cursor image and make it a texture
        textureImg = loadImage("resources/cursorCrosshair32.gif");
        cursorTextureHandle = makeTexture(textureImg);

	GLDebug states = new GLDebug();
	Debug.out("TestPrintText", "setting up font stuff. State table is:\n" + states);
        // Text settings:
        // --------------
        // prepare character set for text rendering
        buildFont("resources/font_tahoma.png", 12);
        // Enable alpha transparency (so text will have transparent background)
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        // Enable texturing, since each character is a texture drawn on a quad
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }


    /**
     * set the field of view and view depth.  Don't use gluLookAt() since this
     * will conflict with our own camera position translations that we do in render().
     */
    public static void setPerspective()
    {
        // select projection matrix (controls view on screen)
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        /* 		fovy,aspect,zNear,zFar  */
        GLU.gluPerspective(30f,         // zoom in or out of view
                           aspectRatio, // shape of viewport rectangle
                           .01f,        // Min Z: how far from eye position does view start
                           500f);       // max Z: how far from eye position does view extend
        /* don't use gluLookAt if moving camera position manually */
    }


    /**
     * Render one frame.  Called by GLApp.run().
     */
    public void render() {
        // select model view for subsequent transforms
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

//         // clear depth buffer and color
      GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

//         // adjust camera position according to arrow key events
        setCameraPosition();

//         // shift and rotate entire scene (opposite to  "camera" position)
        GL11.glRotatef((360.0f-cameraRotation), 0, 1f, 0);   // first rotate around y axis
        GL11.glTranslatef( -cameraPos[0], -cameraPos[1], -cameraPos[2]);  // then move forward on x,z axis (staying on ground, so no Y motion)

        // Don't 'loadIdentiy' below to clear the model view matrix.  Model
        // translations are added onto the translate we did above that
        // shifted and rotated the entire scene.

        // draw the ground plane
        GL11.glPushMatrix();
        {
            GL11.glTranslatef(0f, -2.1f, 0f); // down a bit
            GL11.glScalef(10f, .01f, 10f);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, groundTextureHandle);
            renderCube();
        }
        GL11.glPopMatrix();

        // draw sphere at center
        rotation += .5f;
//         GL11.glPushMatrix();
//         {
//             GL11.glTranslatef(spherePos[0], spherePos[1], spherePos[2]); // move
//             GL11.glRotatef(rotation, 0, 1, 0);  // rotate around Y axis
//             GL11.glScalef(2f, 2f, 2f); // scale up
//             GL11.glBindTexture(GL11.GL_TEXTURE_2D, sphereTextureHandle);
//             renderSphere();
//         }
//         GL11.glPopMatrix();

        // Place the light.  Light will move with the rest of the scene
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, allocFloats(lightPosition));

        // Turn off lighting so text will draw as-is, not lit
        GL11.glDisable(GL11.GL_LIGHTING);

        // render some text relative to viewport edges
	int i = viewportX + 10;
	int j = viewportH - 40;
	GLDebug states = new GLDebug();
	Debug.out("TestPrintExt", "Drawing Text. State table at " + System.currentTimeMillis() + "\n" + states);
        glPrint( i, j, 0, "What the fuck is the matter with my code? " + i + ", " + j);
        glPrint( viewportX+100, viewportY+125, 0, "Use arrow keys to navigate:");
        glPrint( viewportX+100, viewportY+100, 1, "Left-Right arrows rotate camera");
        glPrint( viewportX+100, viewportY+ 80, 1, "Up-Down arrows move camera forward and back");
        glPrint( viewportX+100, viewportY+ 60, 1, "PageUp-PageDown move vertically");

        // turn lighting back on
        GL11.glEnable(GL11.GL_LIGHTING);

        // draw the cursor on top of scene, in 2D
        drawCursor(cursorTextureHandle);
    }


    /**
     * Adjust the Camera position based on keyboard arrow key input.
     */
    public void setCameraPosition()
    {
        // Turn left
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            cameraRotation += 1.0f;
        }
        // Turn right
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            cameraRotation -= 1.0f;
        }
        // move forward in current direction
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            cameraPos[0] -= (float) Math.sin(cameraRotation * piover180) * .3f;
            cameraPos[2] -= (float) Math.cos(cameraRotation * piover180) * .3f;
        }
        // move backward in current direction
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            cameraPos[0] += (float) Math.sin(cameraRotation * piover180) * .3f;
            cameraPos[2] += (float) Math.cos(cameraRotation * piover180) * .3f;
        }
        // move camera down
        if (Keyboard.isKeyDown(Keyboard.KEY_PRIOR)) {
            cameraPos[1] +=  .3f;
        }
        // move camera up
        if (Keyboard.isKeyDown(Keyboard.KEY_NEXT)) {
            cameraPos[1] -=  .3f;
        }
    }


    public void mouseMove(int x, int y) {
    }


    /**
     * Just for yacks, move sphere to mouse click position.
     */
    public void mouseDown(int x, int y) {
        spherePos = getWorldCoordsAtScreen(x,y);
    }


    public void mouseUp(int x, int y) {
    }


    public void keyDown(int keycode) {
    }


    public void keyUp(int keycode) {
    }

    //------------------------------------------------------------------------
    // Run main loop of application.  Handle mouse and keyboard input.
    //------------------------------------------------------------------------

    private TestPrintText() {
    }

    public static void main(String args[]) {
        TestPrintText demo = new TestPrintText();
        demo.run();  // will call init(), render(), mouse functions
    }

}
