package org.six11.flatcad.gl;

import org.six11.util.Debug;

import java.util.*;
import java.nio.*;
import java.io.*;
import java.net.URL;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.glu.*;
import java.awt.image.*;   // for screenshot

/**
 * Collection of functions to init and run an OpenGL app using LWJGL.
 * <P>
 * Includes functions to handle:  <BR>
 *        Buffer allocation -- manage IntBuffer, ByteBuffer calls. <BR>
 *        Setup display mode, keyboard, mouse, native cursor <BR>
 *        Run main loop of application <BR>
 *        OpenGL functions -- convert screen/world coords, set modes, lights, etc. <BR>
 *        Utility functions -- load images, convert pixels, getTimeInMillis, etc. <BR>
 * <P>
 * Has a main() function to run as an application, though this class has only
 * minimal placeholder functionality.  It is meant to be subclassed,
 * and the subclass will define more elaborate render() mouseMove() functions, etc.
 * <P>
 * GLApp initializes the LWJGL environment for OpenGL rendering,
 * ie. creates a window, sets the display mode, inits mouse and keyboard,
 * then runs a loop.
 * <P>
 * Uses GLImage to load and hold image pixels.
 * <P>
 * napier a t potatoland d o t org
 */

public class GLApp {
    // Just for reference
    public static final String GLAPP_VERSION = "1.4";
    //
    // Used when allocating native buffers for data types
    public static final int SIZE_DOUBLE = 8;
    public static final int SIZE_FLOAT = 4;
    public static final int SIZE_INT = 4;
    public static final int SIZE_BYTE = 1;
    //
    // Application settings
    public static Properties settings;   // holds settings from file GLApp.cfg (see loadSettings())
    public static int finishedKey = Keyboard.KEY_ESCAPE;    // App will exit when this key is hit (set to 0 for no key exit)
    public static boolean finished;      // App will exit when finished is true (when finishedKey is hit: see mainloop())
    public static int cursorX, cursorY;
    public static long prevTime = 0;
    public static int frameCount = 0;
    public static long ticksPerSecond = 0;    // used to calc time in millis
    public static String windowTitle = "LWJGL Application";
    //
    // Default display settings (display settings in default.cfg will override these)
    // initDisplay() uses these to pick a Display and setup viewport shape.
    public static DisplayMode DM, origDM;       // hold display mode we set, and the display mode when app first executes
    public static int displayWidth = 1024;
    public static int displayHeight = 768;
    public static int displayColorBits = 32;
    public static int displayFrequency = 75;
    public static int depthBufferBits = 24;     // bits per pixel in the depth buffer
    public static boolean fullScreen = true;    // full screen or floating window
    public static float aspectRatio = 0;        // aspect ratio of Display (will default to displayWidth/displayHeight)
    public static int viewportX, viewportY;     // viewport position (will default to 0,0)
    public static int viewportW, viewportH;     // viewport size (will default to screen width, height)
    //
    // How to tile textures on mesh
    public static float tileFactorVert = 1f;      // how many times to repeat texture
    public static float tileFactorHoriz = 1f;     // how many times to repeat texture
    //
    // For copying screen image to a texture
    static int screenTextureSize = 1024;		// how large should texture be to hold screen
    //
    static float rotation = 0f;         // to rotate cubes (just to put something on screen)
    //
    // NIO Buffers to retrieve OpenGL settings.
    // For memory efficiency and performance, instantiate these once, and reuse.
    // see initBuffers(), getSetingInt(), getModelviewMatrix().
    public static IntBuffer     bufferSettingInt;
    public static IntBuffer     bufferViewport;
    public static FloatBuffer   bufferModelviewMatrix;
    public static FloatBuffer   bufferProjectionMatrix;
    public static ByteBuffer    bufferZdepth;
    //
    // Display List ID for a sphere (see makeSphere() and renderSphere())
    static int sphereID = -1;


    //========================================================================
    // Run main loop of application.  Handle mouse and keyboard input.
    //
    // These functions (main(), run(), mainLoop() and loadSettings()) start
    // and run the application.  They call the init(), render(), and mouse
    // functions.
    //
    //========================================================================

    public static void main(String args[]) {
        GLApp demo = new GLApp();
        demo.run();
    }

    /**
     * Runs the application.  Calls init() functions to setup OpenGL,
     * input and display.  Runs the main loop of the application, which handles
     * mouse and keyboard input.
     * <P>
     * Calls:  init() and mainLoop(). <BR>
     * mainLoop() calls:  render(), mouseMove(), mouseDown(), mouseUp(), keyDown(), keyUp()
     */
    public void run() {
        double delay = 0d;
        int fcount = 0;
        // load settings from config file (display size, resolution, etc.)
        loadSettings("GLApp.cfg");
        //
        initBuffers();
        try {
            // Init Display, Keyboard, Mouse, OpenGL
            init();
            // Main loop
            while (!finished) {
                if (!Display.isVisible()) {  //!!!
                    Thread.sleep(200L);
                }
                else if (Display.isCloseRequested()) {  //!!!
                    finished = true;
                }
                mainLoop();
                Display.update();  //!!!!
            }
        }
        catch (Exception e) {
            System.out.println("GLApp.run(): " + e);
            e.printStackTrace(System.out);
        }
        // prepare to exit
        cleanup();
        System.exit(0);
    }


    /**
     * Called by the run() loop.  Handles animation and input for each frame.
     * <P>
     * Currently only works with Mouse.setGrabbed(true) (see initInput())
     * This means the native cursor is hidden and app has to draw its own.
     */
    public void mainLoop() {
        int mouseDX = org.lwjgl.input.Mouse.getDX();
        int mouseDY = org.lwjgl.input.Mouse.getDY();
        int mouseDW = org.lwjgl.input.Mouse.getDWheel();
        if (mouseDX != 0 || mouseDY != 0 || mouseDW != 0) {
            cursorX += mouseDX;
            cursorY += mouseDY;
            if (cursorX < 0) {
                cursorX = 0;
            }
            else if (cursorX > DM.getWidth()) {
                cursorX = DM.getWidth();
            }
            if (cursorY < 0) {
                cursorY = 0;
            }
            else if (cursorY > DM.getHeight()) {
                cursorY = DM.getHeight();
            }
            mouseMove(cursorX,cursorY);
        }
        while ( org.lwjgl.input.Mouse.next() ) {
            if(org.lwjgl.input.Mouse.getEventButton() == 0 && org.lwjgl.input.Mouse.getEventButtonState() == true) {
              mouseDown(cursorX, cursorY);
            }
            if(org.lwjgl.input.Mouse.getEventButton() == 0 && org.lwjgl.input.Mouse.getEventButtonState() == false) {
              mouseUp(cursorX, cursorY);
            }
        }
        // Handle key hits
        while ( Keyboard.next() )  {
            if (Keyboard.getEventKey() == finishedKey) {
                finished = true;
            }
            // pass key event to handler
            if (Keyboard.getEventKeyState()) {
                keyDown(Keyboard.getEventKey());
            }
            else {
                keyUp(Keyboard.getEventKey());
            }
        }
        // Redraw the screen
        frameCount++;
        if ((Sys.getTime()-prevTime) > ticksPerSecond*1) {
            //System.out.println("==============> FramesPerSec=" + (frameCount/1) + " timeinsecs=" + getTimeInSeconds() + " timeinmillis=" + getTimeInMillis());
            prevTime = Sys.getTime();
            frameCount = 0;
        }
        render();
    }

    /**
     * Load configuration settings from optional properties file.
     * File format is:<BR>
     *
     * <PRE>
     * # Comment
     * displayWidth=1024
     * displayHeight=768
     * </PRE>
     *
     * @param configFilename
     */
    public void loadSettings(String configFilename)
    {
        settings = new Properties();
        try { settings.load(getInputStream(configFilename)); }
        catch (Exception e) {
            System.out.println("GLApp.loadSettings() warning: " + e);
            return;
        }
        // Debug: show the settings
        settings.list(System.out);
        // Check for available settings
        if (settings.getProperty("displayWidth") != null) {
            displayWidth = Integer.parseInt(settings.getProperty("displayWidth"));
        }
        if (settings.getProperty("displayHeight") != null) {
            displayHeight = Integer.parseInt(settings.getProperty("displayHeight"));
        }
        if (settings.getProperty("displayColorBits") != null) {
            displayColorBits = Integer.parseInt(settings.getProperty("displayColorBits"));
        }
        if (settings.getProperty("displayFrequency") != null) {
            displayFrequency = Integer.parseInt(settings.getProperty("displayFrequency"));
        }
        if (settings.getProperty("depthBufferBits") != null) {
            depthBufferBits = Integer.parseInt(settings.getProperty("depthBufferBits"));
        }
        if (settings.getProperty("aspectRatio") != null) {
            aspectRatio = Float.parseFloat(settings.getProperty("aspectRatio"));
        }
        if (settings.getProperty("fullScreen") != null) {
            fullScreen = settings.getProperty("fullScreen").toUpperCase().equals("YES");
        }
    }


    /**
     *  Return a property from the properties file.  This file is optional,
     * so properties may be empty.
     * @see loadSettings()
     */
    public static String getProperty(String propertyName) {
        return settings.getProperty(propertyName);
    }


    /**
     * Open a file InputStream and trap errors.
     * @param filenameOrURL
     * @return
     */
    public static InputStream getInputStream(String filename) {
        InputStream in = null;
        try {
            in = new FileInputStream(filename);
        }
        catch (IOException ioe) {
            System.out.println("GLApp.getInputStream (" + filename + "): " + ioe);
            if (in != null) {
                try {
                    in.close();
                    in = null;
                }
                catch (Exception e) {}
            }
        }
        catch (Exception e) {
            System.out.println("GLApp.getInputStream (" + filename + "): " + e);
        }
        if (in == null) {
            // Couldn't open file: try looking in jar.
            // NOTE: this will look only in the folder that this class is in.
            //System.out.println("GLApp.getInputStream (" +filename+ "): in == null, try jar");
            //URL u = getClass().getResource(filename);   // for non-static class
            URL u = GLApp.class.getResource(filename);
            if (u != null) {
                //System.out.println("GLApp.getInputStream (" +filename+ "): trying jar, got url=" + u);
                try {
                    in = u.openStream();
                }
                catch (Exception e) {
                    System.out.println("GLApp.getInputStream (" +filename+ "): Can't load from jar: " + e);
                }
            }
        }
        return in;
    }

    public static InputStream getInputStream_OLD(String filename) {
        InputStream in = null;
        try {
            in = new FileInputStream(filename);
        }
        catch (IOException ioe) {
            System.out.println("GLApp.getInputStream (" + filename + "): " + ioe);
            if (in != null) {
                try {
                    in.close();
                    in = null;
                }
                catch (Exception e) {}
            }
        }
        catch (Exception e) {
            System.out.println("GLApp.getInputStream (" + filename + "): " + e);
        }
        return in;
    }


    //========================================================================
    // Custom Application functionality: can be overriden by subclass.
    //
    // Functions to initialize OpenGL, set the viewing mode, render the scene,
    // respond to mouse actions, and initialize the app.  These functions
    // are overridden in the subclass to create custom behavior.
    //
    //========================================================================

    /**
     * The three init functions below must be called to get the display, mouse
     * and OpenGL context ready for use. Override init() in a subclass to
     * customize behavior.
     */
    public void init()
    {
        // initialize Window, Keyboard, Mouse, OpenGL environment
        initDisplay();
        initInput();
        initOpenGL();
    }

    /**
     *  For memory efficiency and performance, it's best to init NIO buffers
     *  once and reuse, particularly for calls that may be repeated often.
     *  Allocate NIO buffers here, use them in getSetingInt(),
     *  getModelviewMatrix(), etc.
     */
    public static void initBuffers()
    {
        bufferSettingInt = allocInts(16);
        bufferModelviewMatrix = allocFloats(16);
        bufferProjectionMatrix = allocFloats(16);
        bufferViewport = allocInts(16);
        bufferZdepth = allocBytes(SIZE_FLOAT);
    }

    /**
     * Called by GLApp.run() to initialize OpenGL rendering.
     * @throws java.lang.Exception
     */
    public void initOpenGL() {
        try {
            // Depth tests mess up alpha drawing.  When layering up many transluscent
            // shapes, you have to turn off Depth tests or GL will throw out "hidden"
            // layers, when you actually want to draw the lower layers under the
            // topmost (transluscent) layers.
            GL11.glClearDepth(1.0f); // Depth Buffer Setup
            GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing  // Need this ON if using textures
            GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Testing To Do

            // Needed?
            GL11.glShadeModel(GL11.GL_SMOOTH); // Enable Smooth Shading
            GL11.glClearColor(0f, 0f, 0f, 0f); // Black Background
            GL11.glEnable(GL11.GL_NORMALIZE);
            GL11.glEnable(GL11.GL_CULL_FACE);

            // Perspective quality
            GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);

            // Set the size and shape of the screen area
            //GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
            GL11.glViewport(viewportX, viewportY, viewportW, viewportH);

            // setup perspective (see setOrtho for 2D)
            setPerspective();

            // select model view for subsequent transforms
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glLoadIdentity();
        }
        catch (Exception e) {
            System.out.println("GLApp.initOpenGL(): " + e);
        }
    }


    /**
     * Called by mainLoop() to render one frame.  Subclass will override this.
     */
    public void render() {
        // select model view for subsequent transforms
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        // clear depth buffer and color
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // rotate, scale and draw cube
        rotation += .3;
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glRotatef(rotation, (float)0, (float)1, (float)0);
        GL11.glColor4f(0f, .5f, 1f, 1f);
        renderCube();   // draw a cube
        GL11.glPopMatrix();

        // draw another overlapping cube
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glRotatef(rotation, (float)1, (float)1, (float)1);
        GL11.glColor4f(.7f, .5f, 0f, 1f);
        renderCube();
        GL11.glPopMatrix();
    }

    /**
     * Called by mainLoop() when mouse moves
     */
    public void mouseMove(int x, int y) {
    }


    public void mouseDown(int x, int y) {
        System.out.println("Mouse down");
    }


    public void mouseUp(int x, int y) {
        System.out.println("Mouse UP");
    }


    public void keyDown(int keycode) {
        System.out.println("KEYDOWN " + keycode);
    }


    public void keyUp(int keycode) {
        System.out.println("KEYUP " + keycode);
    }


    /**
     * Run() calls this right before exit.
     */
    public void cleanup() {
        destroyFont();
        Keyboard.destroy();
        Display.destroy();  // will call Mouse.destroy()
    }


    public GLApp() {
    }

    //========================================================================
    // Setup display mode
    //
    // Initialize Display, Mouse, Keyboard.
    // These functions are specific to LWJGL.
    //
    //========================================================================

    /**
     * Initialize the Display mode, viewport size, and open a Window.
     * By default the window is fullscreen, the viewport is the same dimensions
     * as the window.
     */
    public static boolean initDisplay () {
        origDM = Display.getDisplayMode();
        System.out.println("GLApp.initDisplay(): Current display mode is " + origDM);
        // Set display mode
        try {
            if ( (DM = getDisplayMode(displayWidth, displayHeight, displayColorBits, displayFrequency)) == null) {
                if ( (DM = getDisplayMode(1024, 768, 32, 60)) == null) {
                    if ( (DM = getDisplayMode(1024, 768, 16, 60)) == null) {
                        if ( (DM = getDisplayMode(origDM.getWidth(), origDM.getHeight(), origDM.getBitsPerPixel(), origDM.getFrequency())) == null) {
                            System.out.println("GLApp.initDisplay(): Can't find a compatible Display Mode!!!");
                        }
                    }
                }
            }
            System.out.println("GLApp.initDisplay(): Setting display mode to " + DM + " with pixel depth = " + depthBufferBits);
            Display.setDisplayMode(DM);
        }
        catch (Exception exception) {
            System.err.println("GLApp.initDisplay(): Failed to create display: " + exception);
            System.exit(1);
        }
        // Initialize the Window
        try {
            Display.create(new PixelFormat(0, depthBufferBits, 0));  // set bits per buffer: alpha, depth, stencil
            Display.setTitle(windowTitle);
            Display.setFullscreen(fullScreen);
            Display.setVSyncEnabled(true);
            System.out.println("GLApp.initDisplay(): Created OpenGL window.");
        }
        catch (Exception exception1) {
            System.err.println("GLApp.initDisplay(): Failed to create OpenGL window: " + exception1);
            System.exit(1);
        }
        // Set viewport width/height and Aspect ratio.
        if (aspectRatio == 0f) {
            // no aspect ratio was set in GLApp.cfg: default to full screen.
            aspectRatio = (float)DM.getWidth() / (float)DM.getHeight(); // calc aspect ratio of display
        }
        // viewport may not match shape of screen.  Adjust to fit.
        viewportH = DM.getHeight();                        // viewport Height
        viewportW = (int) (DM.getHeight() * aspectRatio);  // Width
        if (viewportW > DM.getWidth()) {
            viewportW = DM.getWidth();
            viewportH = (int) (DM.getWidth() * (1 / aspectRatio));
        }
        // center viewport in screen
        viewportX = (int) ((DM.getWidth()-viewportW) / 2);
        viewportY = (int) ((DM.getHeight()-viewportH) / 2);
        return true;
    }

    /**
     * Initialize the Keyboard and Mouse.
     * <P>
     * Set Mouse.setGrabbed(true).  This hides the native cursor and enables
     * the Mouse.getDX() and Mouse.getDY() functions so we can track mouse
     * motion ourselves.
     *
     * @see mainLoop()  for mouse motion tracking
     * @see drawCursor()  to draw our own cursor on screen
     */
    public static void initInput() {
        try {
            // init keyboard
            Keyboard.create();

            // init mouse: this will hide the native cursor (see drawCursor())
            org.lwjgl.input.Mouse.setGrabbed(true);

            // set initial cursor pos to center screen
            cursorX = (int) DM.getWidth() / 2;
            cursorY = (int) DM.getHeight() / 2;

            // Init hi-res timer
            ticksPerSecond = Sys.getTimerResolution();
        }
        catch (Exception e) {
            System.out.println("GLApp.initInput(): " + e);
        }
    }


    /**
     * Retrieve a DisplayMode object with the given params
     */
    public static DisplayMode getDisplayMode(int w, int h, int colorBits, int freq) {
        try {
            DisplayMode adisplaymode[] = Display.getAvailableDisplayModes();
            DisplayMode dm = null;
            for (int j = 0; j < adisplaymode.length; j++) {
                dm = adisplaymode[j];
                if (dm.getWidth() == w && dm.getHeight() == h && dm.getBitsPerPixel() == colorBits &&
                    dm.getFrequency() == freq) {
                    return dm;
                }
            }
        }
        catch (LWJGLException e) {
            System.out.println("GLApp.getDisplayMode(): Exception " + e);
        }
        return null;
    }


    //========================================================================
    // OpenGL functions
    //
    // Typical OpenGL functionality: get settings, get matrices, convert
    // screen to world coords, define textures and lights.
    //
    //========================================================================

    public static int getSettingInt(int whichSetting)
    {
        //IntBuffer ibuffer = allocInts(16);
        bufferSettingInt.clear();
        GL11.glGetInteger(whichSetting, bufferSettingInt);
        return bufferSettingInt.get(0);
    }

    public static FloatBuffer getModelviewMatrix()
    {
      // /*FloatBuffer*/ bufferModelviewMatrix = allocFloats(16);
      bufferModelviewMatrix.clear();
      GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, bufferModelviewMatrix);
      return bufferModelviewMatrix;
    }

    public static FloatBuffer getProjectionMatrix()
    {
        //FloatBuffer projection = allocFloats(16);
        bufferProjectionMatrix.clear();
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, bufferProjectionMatrix);
        return bufferProjectionMatrix;
    }

    public static IntBuffer getViewport()
    {
        //IntBuffer viewport = allocInts(16);
        bufferViewport.clear();
        GL11.glGetInteger(GL11.GL_VIEWPORT, bufferViewport);
        return bufferViewport;
    }


    /**
     * Convert a FloatBuffer matrix to a 4x4 float array.
     * @param fb   FloatBuffer containing 16 values of 4x4 matrix
     * @return     2 dimensional float array
     */
    public static float[][] getMatrixAsArray(FloatBuffer fb) {
        float[][] fa = new float[4][4];
        fa[0][0] = fb.get();
        fa[0][1] = fb.get();
        fa[0][2] = fb.get();
        fa[0][3] = fb.get();
        fa[1][0] = fb.get();
        fa[1][1] = fb.get();
        fa[1][2] = fb.get();
        fa[1][3] = fb.get();
        fa[2][0] = fb.get();
        fa[2][1] = fb.get();
        fa[2][2] = fb.get();
        fa[2][3] = fb.get();
        fa[3][0] = fb.get();
        fa[3][1] = fb.get();
        fa[3][2] = fb.get();
        fa[3][3] = fb.get();
        return fa;
    }

    /**
     * Return the modelview matrix as a 4x4 float array
     */
    public static float[][] getModelviewMatrixA()
    {
        FloatBuffer b = getModelviewMatrix();
        return getMatrixAsArray(b);
    }

    /**
     * Return the projection matrix as a 4x4 float array
     */
    public static float[][] getProjectionMatrixA()
    {
        FloatBuffer b = getProjectionMatrix();
        return getMatrixAsArray(b);
    }

    /**
     * Return the Viewport data as array of 4 floats
     */
    public static int[] getViewportA()
    {
        IntBuffer ib = getViewport();
        int[] ia = new int[4];
        ia[0] = ib.get(0);
        ia[1] = ib.get(1);
        ia[2] = ib.get(2);
        ia[3] = ib.get(3);
        return ia;
    }


    /**
     * Return the Z depth of the single pixel at the given screen position.
     */
    public static float getZDepth(int x, int y)
    {
        //ByteBuffer zdepth = allocBytes(SIZE_FLOAT); //ByteBuffer.allocateDirect(1 * SIZE_FLOAT).order(ByteOrder.nativeOrder());
        bufferZdepth.clear();
        GL11.glReadPixels(x, y, 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, bufferZdepth);
        return ( (float) (bufferZdepth.getFloat(0)));
    }


    /**
     * Find the Z depth of the origin in the projected world view. Used by getWorldCoordsAtScreen()
     * Projection matrix  must be active for this to return correct results (GL.glMatrixMode(GL.GL_PROJECTION)).
     * For some reason I have to chop this to four decimals or I get bizarre
     * results when I use the value in project().
     */
    public static float getZDepthAtOrigin()
    {
        float[] resultf = new float[3];
        project( 0, 0, 0, resultf);
        return ((int)(resultf[2] * 10000F)) / 10000f;  // truncate to 4 decimals
    }

    /**
     * Return screen coordinates for a given point in world space.  The world
     * point xyz is 'projected' into screen coordinates using the current model
     * and projection matrices, and the current viewport settings.
     *
     * @param x         world coordinates
     * @param y
     * @param z
     * @param resultf    the screen coordinate as an array of 3 floats
     */
    public static void project(float x, float y, float z, float[] resultf)
    {
        float[] result = new float[3];
        GLU.gluProject( x, y, z, getModelviewMatrixA(), getProjectionMatrixA(), getViewportA(), result);
        resultf[0] = result[0];
        resultf[1] = result[1];
        resultf[2] = result[2];
    }


    /**
     * Return world coordinates for a given point on the screen.  The screen
     * point xyz is 'un-projected' back into world coordinates using the
     * current model and projection matrices, and the current viewport settings.
     *
     * @param x         screen x position
     * @param y         screen y position
     * @param z         z-buffer depth position
     * @param resultf   the world coordinate as an array of 3 floats
     * @see             getWorldCoordsAtScreen()
     */
    public static void unProject(float x, float y, float z, float[] resultf)
    {
        float[] result = new float[3];  // v.9
        GLU.gluUnProject( x, y, z, getModelviewMatrixA(), getProjectionMatrixA(), getViewportA(), result);
        resultf[0] = result[0];
        resultf[1] = result[1];
        resultf[2] = result[2];
    }

    /**
     * For given screen xy, return the world xyz coords in a float array.  Assume
     * Z position is 0.
     */
    public static float[] getWorldCoordsAtScreen(int x, int y) {
        float z = getZDepthAtOrigin();
        float[] resultf = new float[3];
        unProject( (float)x, (float)y, (float)z, resultf);
        return resultf;
    }

    /**
     * For given screen xy and z depth, return the world xyz coords in a float array.
     */
    public static float[] getWorldCoordsAtScreen(int x, int y, float z) {
        float[] resultf = new float[3];
        unProject( (float)x, (float)y, (float)z, resultf);
        return resultf;
    }

    /**
     * Allocate a texture (glGenTextures) and return the handle to it.
     */
    public static int allocateTexture()
    {
        IntBuffer textureHandle = allocInts(1);
        GL11.glGenTextures(textureHandle);
        return textureHandle.get(0);
    }

    /**
     * Create a texture from the given image.
     */
    public static int makeTexture(GLImage textureImg)
    {
        if ( textureImg == null ) {
            return 0;
        }
        else {
            return makeTexture(textureImg.pixelBuffer, textureImg.w, textureImg.h);
        }
    }

    /**
     * How many times to repeat texture horizontally and vertically.
     */
    public static void setTextureTile(float horizontalTile, float verticalTile) {
        tileFactorHoriz = horizontalTile;
        tileFactorVert = verticalTile;
    }

    /**
     * Create a texture from the given pixels in RGBA format.  Set the texture
     * to repeat in both directions and use LINEAR for magnification.
     * @return  the texture handle
     */
    public static int makeTexture(ByteBuffer pixels, int w, int h)
    {
        // get a new empty texture
        int textureHandle = allocateTexture();
        // 'select' the new texture by it's handle
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,textureHandle);
        // set texture parameters
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR); //GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR); //GL11.GL_NEAREST);
        // Create the texture from pixels
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, w, h, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
        return textureHandle;
    }

    /**
     * Build Mipmaps for texture (builds different versions of the picture for
     * distances - looks better)
     *
     * @param textureImg  the texture image
     * @return   error code of buildMipMap call
     */
    public static int makeTextureMipMap(GLImage textureImg)
    {
        int ret = GLU.gluBuild2DMipmaps(GL11.GL_TEXTURE_2D, 4, textureImg.w,
                          textureImg.h, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, textureImg.getPixelsRGBA());
        if (ret != 0) {
            System.out.println("GLApp.makeTextureMipMap(): Error occured while building mip map, ret=" + ret);
        }
        //Assign the mip map levels and texture info
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
        GL11.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
        return ret;
    }

    /**
     * Create a texture large enough to hold the screen image.  Use RGBA format
     * to insure colors are copied exactly.  Use GL_NEAREST for magnification
     * to prevent slight blurring of image when screen is drawn back.
     *
     * @see frameSave()
     * @see frameDraw()
     */
    public static int makeTextureForScreen(int screenSize)
    {
        // get a texture size big enough to hold screen (512, 1024, 2048 etc.)
        screenTextureSize = getPowerOfTwoBiggerThan(screenSize);
        System.out.println("GLApp.makeTextureForScreen(): made texture for screen with size " + screenTextureSize);
        // get a new empty texture
        int textureHandle = allocateTexture();
        ByteBuffer pixels = allocBytes(screenTextureSize*screenTextureSize*SIZE_INT);
        // 'select' the new texture by it's handle
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,textureHandle);
        // set texture parameters
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        // use GL_NEAREST to prevent blurring during frequent screen restores
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        // Create the texture from pixels: use GL_RGBA8 to insure exact color copy
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, screenTextureSize, screenTextureSize, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
        return textureHandle;
    }

    /**
     * Find a power of two big enough to hold the given dimension.  Ie.
     * to make a texture big enough to hold a screen image for an 800x600
     * screen getPowerOfTwoBiggerThan(800) will return 1024.
     * <P>
     * @see makeTextureForScreen()
     * @param dimension
     * @return a power of two bigger than the given dimension
     */
    public static int getPowerOfTwoBiggerThan(int dimension) {
        for (int exp=1; exp <= 32; exp++) {
            if (dimension <= Math.pow(2, exp)) {
                return (int)Math.pow(2, exp);
            }
        }
        return 0;
    }

    /**
     * Set OpenGL to render in 3D perspective.
     */
    public static void setPerspective()
    {
        // select projection matrix (controls view on screen)
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(30f, aspectRatio, 1f, 30f);
        GLU.gluLookAt(0f, 0f, 15f, 0f, 0f, 0f, 0f, 1f, 0f);
    }

    /**
     * Set OpenGL to render in flat 2D (no perspective).  This creates a
     * one-to-one relation between screen pixel and rendered pixel, ie. if
     * you draw a 10x10 quad at 100,100 it will appear as a 10x10 pixel
     * square on screen at pixel position 100,100.
     * <P>
     * ABOUT Ortho and Viewport:<BR>
     * --------------------------<BR>
     * Let's say we're drawing in 2D and want to have a cinema proportioned
     * viewport (16x9), and want to bound our 2D rendering into that area ie.
       <PRE>
          ___________1024,576
         |           |
         |  Scene    |      Set the bounds on the scene geometry
         |___________|      to the viewport size and shape
      0,0

          ___________1024,576
         |           |
         |  Ortho    |      Set the projection to cover the same
         |___________|      area as the scene
      0,0

          ___________ 1024,768
         |___________|
         |           |1024,672
         |  Viewport |      Set the viewport to the same shape
     0,96|___________|      as scene and ortho, but centered on
         |___________|      screen.
      0,0
     </PRE>
     *
     */
    public static void setOrtho()
    {
        // select projection matrix (controls view on screen)
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        // set ortho to same size as viewport, positioned at 0,0
        GL11.glOrtho(0,viewportW,0,viewportH,-1,1);
    }

    /**
     * Set OpenGL to render in flat 2D (no perspective) on top of current scene.
     * Preserve current projection and model views, and disable depth testing.
     * Once Ortho is On, glTranslate() will take pixel coords as arguments,
     * with the lower left corner 0,0 and the upper right corner 1024,768 (or
     * whatever your screen size is).
     *
     * NOTE: this depends on the static variables viewportX,
     * viewportY, viewportW, and viewportH being set to the correct
     * values for your display.
     *
     * @see setOrthoOff()
     */
    public static void setOrthoOn()
    {
        // prepare to render in 2D
        GL11.glDisable(GL11.GL_DEPTH_TEST);                   // so text stays on top of scene
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();                                  // preserve perspective view
        GL11.glLoadIdentity();                                // clear the perspective matrix
        GL11.glOrtho(viewportX,viewportX+viewportW,viewportY,viewportY+viewportH,-1,1);  // turn on 2D mode
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();				// Preserve the Modelview Matrix
        GL11.glLoadIdentity();				// clear the Modelview Matrix
    }

    /**
     * Turn 2D mode off.  Return the projection and model views to their
     * preserved state that was saved when setOrthoOn() was called, and
     * enable depth testing.
     *
     * @see setOrthoOn()
     */
    public static void setOrthoOff()
    {
        // restore the original positions and views
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_DEPTH_TEST);		// turn Depth Testing back on
    }

    /**
     * Set the color of a 'positional' light (a light that has a specific
     * position within the scene).  <BR>
     *
     * Pass in an OpenGL light number (GL11.GL_LIGHT1),
     * the 'Diffuse' and 'Ambient' colors (direct light and reflected light),
     * and the position.<BR>
     *
     * @param GLLightHandle
     * @param diffuseLightColor
     * @param ambientLightColor
     * @param position
     */
    public static void setLight( int GLLightHandle,
        float[] diffuseLightColor, float[] ambientLightColor, float[] position )
    {
        FloatBuffer ltDiffuse = allocFloats(diffuseLightColor);
        FloatBuffer ltAmbient = allocFloats(ambientLightColor);
        FloatBuffer ltPosition = allocFloats(position);
        GL11.glLight(GLLightHandle, GL11.GL_DIFFUSE, ltDiffuse);   // color of the direct illumination
        GL11.glLight(GLLightHandle, GL11.GL_SPECULAR, ltDiffuse);  // color of the highlight
        GL11.glLight(GLLightHandle, GL11.GL_AMBIENT, ltAmbient);   // color of the reflected light
        GL11.glLight(GLLightHandle, GL11.GL_POSITION, ltPosition);
        GL11.glEnable(GLLightHandle);	// Enable the light (GL_LIGHT1 - 7)
        //GL11.glLightf(GLLightHandle, GL11.GL_QUADRATIC_ATTENUATION, .005F);    // how light beam drops off
    }


    public static void setSpotLight( int GLLightHandle,
        float[] diffuseLightColor, float[] ambientLightColor,
        float[] position, float[] direction, float cutoffAngle )
    {
        FloatBuffer ltDirection = allocFloats(direction);
        setLight(GLLightHandle, diffuseLightColor, ambientLightColor, position);
        GL11.glLightf(GLLightHandle, GL11.GL_SPOT_CUTOFF, cutoffAngle);   // width of the beam
        GL11.glLight(GLLightHandle, GL11.GL_SPOT_DIRECTION, ltDirection);    // which way it points
        GL11.glLightf(GLLightHandle, GL11.GL_CONSTANT_ATTENUATION, 2F);    // how light beam drops off
        //GL11.glLightf(GLLightHandle, GL11.GL_LINEAR_ATTENUATION, .5F);    // how light beam drops off
        //GL11.glLightf(GLLightHandle, GL11.GL_QUADRATIC_ATTENUATION, .5F);    // how light beam drops off
    }

    /**
     * Set the color of the Global Ambient Light.  Affects all objects in
     * scene regardless of their placement.
     */
    public static void setAmbientLight(float[] ambientLightColor)
    {
        FloatBuffer ltAmbient = allocFloats(ambientLightColor);
        GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, ltAmbient);
    }


    //========================================================================
    // Utility functions
    //
    // Functions to load images, convert byte order, make cursors,
    // grab framebuffer pixels, draw pixels into framebuffer.
    //
    //========================================================================

    public static double getTimeInSeconds()
    {
        if (ticksPerSecond == 0) {
            ticksPerSecond = Sys.getTimerResolution();
        }
        return (((double)Sys.getTime())/(double)ticksPerSecond);
    }

    public static double getTimeInMillis()
    {
        if (ticksPerSecond == 0) {
            ticksPerSecond = Sys.getTimerResolution();
        }
        return (double) ((((double)Sys.getTime())/(double)ticksPerSecond) * 1000.0);
    }

    /**
     * Load an image from the given file and return a GLImage object.
     * @param imgFilename
     * @return
     */
    public static GLImage loadImage(String imgFilename) {
        GLImage img = new GLImage(imgFilename);
        if (img.isLoaded()) {
            return img;
        }
        return null;
    }

    /**
     * Load an image from the given file and return a ByteBuffer containing RGBA pixels.<BR>
     * Can be used to create textures. <BR>
     * @param imgFilename
     * @return
     */
    public static ByteBuffer loadImagePixels(String imgFilename) {
        GLImage img = new GLImage(imgFilename);
        return img.pixelBuffer;
    }

    /**
     * Load an image from the given file and return an IntBuffer containing ARGB pixels.<BR>
     * Can be used to create Native Cursors. <BR>
     * @param imgFilename
     * @return IntBuffer
     */
    public static IntBuffer loadImageInt(String imgFilename) {
        GLImage img = new GLImage(imgFilename);
        int[] jpixels = img.getImagePixels();      // pixels in Java int format
        return allocInts(jpixels);  // convert to IntBuffer and return
    }

    /**
     * Draw an image at the given x,y. If scale values are not 1, then scale
     * the image.  See loadImage().<BR>
     */
    public static void drawImageInt(GLImage img, int x, int y, float scaleW, float scaleY) {
        if (img != null) {
            GL11.glRasterPos2i(x, y);
            GL11.glDrawPixels(img.w, img.h, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, img.pixelBuffer);
            if (scaleW != 1f || scaleY != 1f) {
                GL11.glPixelZoom(scaleW, scaleY);
            }
        }
    }

    /**
     * Draw a cursor image textured onto a quad at cursor position.  The cursor
     * image must be loaded into a texture, then this function can be called
     * after scene is drawn.  Uses glPushAttrib() to preserve the current
     * drawing state.  glPushAttrib() may slow performance down, so in your
     * app you may want to set the states yourself before calling drawCursor()
     * and take the push/pop out of here.
     * <P>
     * See mainLoop() for cursorX cursorY and mouse motion handling.
     * <P>
     * Example:
     * <PRE>
     *    GLImage cursorImg = loadImage("images/cursorCrosshair32.gif");  // cursor image must be 32x32
     *    int cursorCrosshairTxtr = makeTexture(cursorImg);
     *
     *    public void render() {
     *        // render scene
     *        ...
     *        drawCursor(cursorCrosshairTxtr);
     *    }
     * </PRE>
     *
     * @param cursorTextureHandle  handle to texture containing  32x32 cursor image
     */
    public void drawCursor(int cursorTextureHandle) {
        setOrthoOn();
        //GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_TEXTURE_BIT | GL11.GL_LIGHTING_BIT);
        {
            GL11.glDisable(GL11.GL_LIGHTING);    // so cursor will draw as-is
            GL11.glEnable(GL11.GL_BLEND);        // enable transparency
            GL11.glEnable(GL11.GL_TEXTURE_2D);   // so texture image will show
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);  // overlay cursor onto scene
            drawImageQuad(cursorTextureHandle, cursorX-16, cursorY-16, 32, 32);  // assumes 32x32 pixels
        }
        GL11.glPopAttrib();
        setOrthoOff();
    }


    /**
     * Draw an image at the given x,y. Scale the image to the given w,h.
     * The image must be loaded into the texture pointed to by textureHandle.
     * It will be drawn in 2D on top of the current scene.
     * <BR>
     * @ee loadImage().
     */
    public static void drawImageQuad(int textureHandle, int x, int y, float w, float h) {
        // activate the specified texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,textureHandle);
        // prepare to render in 2D
        setOrthoOn();
        // draw the image textured onto a quad
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0f, 0f);
        GL11.glVertex3f( (float)x, (float)y, (float)0);
        GL11.glTexCoord2f(1f, 0f);
        GL11.glVertex3f( (float)x+w, (float)y, (float)0);
        GL11.glTexCoord2f(1f, 1f);
        GL11.glVertex3f( (float)x+w, (float)y+h, (float)0);
        GL11.glTexCoord2f(0f, 1f);
        GL11.glVertex3f( (float)x, (float)y+h, (float)0);
        GL11.glEnd();
        // restore the previous perspective and model views
        setOrthoOff();
    }

    /**
     * Draw an image (loaded into a texture) onto a quad, in 3D model space.
     */
    public static void drawQuad(int textureHandle, float x, float y, float z, float w, float h) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,textureHandle);
        // draw  textured quad
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0f, 0f);
        GL11.glVertex3f( x, y, z);
        GL11.glTexCoord2f(1f, 0f);
        GL11.glVertex3f( x+w, y, z);
        GL11.glTexCoord2f(1f, 1f);
        GL11.glVertex3f( x+w, y+h, z);
        GL11.glTexCoord2f(0f, 1f);
        GL11.glVertex3f( x, y+h, z);
        GL11.glEnd();
    }


    /**
     * Create a native cursor from the given image file.<BR>
     * My cursor motion doesn't work with native cursor since verion .95
     * I'll have to fix someday. I prefer drawing cursor as a quad anyway, so
     * this is low priority.
     *
     * @param imgFilename
     * @return Cursor
     * @see drawCursor()
     */
    public static Cursor makeNativeCursor(String imgFilename) {
        // check for hw cursor !!!
        if ( (Cursor.getCapabilities() & Cursor.CURSOR_ONE_BIT_TRANSPARENCY) == 0) {
            System.out.println("GLApp.makeNativeCursor(): No hardware cursor support!");
            return null;
        }
        // load the image
        GLImage img = new GLImage(imgFilename);
        int[] jpixels = img.getImagePixels();      // pixels in Java int format
        int w = img.w;
        int h = img.h;
        // check for legal image size  !!!
        if ( w < Cursor.getMinCursorSize() || w > Cursor.getMaxCursorSize()
             || h < Cursor.getMinCursorSize() || h > Cursor.getMaxCursorSize() ) {
            System.out.println("GLApp.makeNativeCursor(): Cursor image is not correct size, should be " + Cursor.getMinCursorSize() + "-" + Cursor.getMaxCursorSize());
            return null;
        }
        // correct transparent pixels: if a pixel has partial alpha (anything less
        // than opaque) set entire pixel to 0.  Otherwise transparent area looks funky.
        for (int i=0; i < jpixels.length; i++) {
            if ( (jpixels[i] >> 24 & 0xff) != 0xff) {
                jpixels[i] = 0;
            }
        }
        // make the cursor
        IntBuffer intpixels =  allocInts(jpixels);  // convert to IntBuffer
        Cursor C = null;
        try {
            C = new Cursor(w, h,        // cursor size
                           w/2, h/2,    // hotspot at center
                           1,           // one cursor image will be passed in
                           intpixels,   // image pixels
                           null);       // no delays (timing delays for animated cursors)
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return C;
    }


    /**
     * Get pixels from frame buffer.<BR>
     */
    public static GLImage getFramePixels(int x, int y, int w, int h) {
        ByteBuffer pixels = allocBytes(w*h);
        //GL11.glReadBuffer(GL11.GL_BACK);
        GL11.glReadPixels(x,y,w,h,GL11.GL_RGBA,GL11.GL_UNSIGNED_BYTE, pixels);
        return new GLImage(pixels,w,h);
    }

    /**
     * Get pixels from frame buffer into an existing image.<BR>
     */
    public static void getFramePixels(int x, int y, GLImage img) {
        GL11.glReadPixels(x,y, img.w, img.h, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, img.pixelBuffer);
    }

    /**
     * Write pixels to frame buffer from an existing image.
     * <BR>
     * Need to switch Projection matrix to ortho (2D) since Raster Position is
     * transformed in space just like a vertex.  Steps: switch to 2D, set the
     * draw position to lower left, draw the image into Back and Front buffers.
     * <BR>
     * Draw into both buffers so they both have same image (otherwise you may
     * get flicker as front and back buffers have different images).  This is
     * an issue only when you're not clearing the screen before drawing each
     * frame, ie. when leaving trails as objects move.
     */
    public static void setFramePixels(GLImage img) {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GLU.gluOrtho2D(0f, DM.getWidth(), 0f, DM.getHeight());
        GL11.glRasterPos2i(0,0);
        GL11.glDrawBuffer(GL11.GL_FRONT);
        GL11.glDrawPixels(img.w, img.h, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, img.pixelBuffer);
        GL11.glDrawBuffer(GL11.GL_BACK);
        GL11.glDrawPixels(img.w, img.h, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, img.pixelBuffer);
        GL11.glPopMatrix();
    }


    /**
     * Save entire screen image to a texture.  Will copy entire screen even
     * if a viewport is in use.  Texture param must be large enough to hold
     * screen image (see makeTextureForScreen()).
     *
     * @param txtrHandle   texture where screen image will be stored
     * @see frameDraw()
     * @see makeTextureForScreen()
     */
    public static void frameSave(int txtrHandle)
    {
        GL11.glColor4f(1,1,1,1);                // turn off alpha and color tints
        GL11.glReadBuffer(GL11.GL_BACK);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,txtrHandle);
        // Copy screen to texture
        GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0,0, 0,0, DM.getWidth(),DM.getHeight());   //!!! whole frame
    }


    /**
     * Draw the screen-sized image over entire screen area.  The screen image
     * is stored in the given texture at 0,0 (see frameSave()) and has the
     * same dimensions as the current display mode (DM.getWidth(), DM.getHeight()).
     * <P>
     * Reset the viewport and ortho mode to full screen (viewport may be
     * different proportion than screen if custom aspectRatio is set).  Draw the
     * quad the same size as texture so no stretching or compression of image.
     *
     * @param txtrHandle
     */
    public static void frameDraw(int txtrHandle)
    {
        // keep it opaque
        GL11.glDisable(GL11.GL_BLEND);
        // set viewport to full screen
        GL11.glViewport(0, 0, DM.getWidth(), DM.getHeight());
        // set ortho view to full screen and draw quad
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();                      // preserve current projection
        {
            GL11.glLoadIdentity();                // clear the projection matrix
            GL11.glOrtho(0, DM.getWidth(), 0, DM.getHeight(), -1, 1); // turn on 2D mode full screen
            drawQuad(txtrHandle, 0, 0, 0, screenTextureSize, screenTextureSize); // draw the full screen image
        }
        GL11.glPopMatrix();                       // restore projection view
        // restore viewport to custom aspect ratio
        GL11.glViewport(viewportX, viewportY, viewportW, viewportH);
    }


    /**
     * Make a blank image of the given size<BR>
     */
    public static GLImage makeImage(int w, int h) {
        ByteBuffer pixels = allocBytes(w*h*4);
        return new GLImage(pixels,w,h);
    }

    //========================================================================
    // Functions to render meshes.  Specific to IDX3D libe.
    //
    //========================================================================
/*************
    / **
     * Render a mesh with the given texture. <BR>
     * Must include these lines in init() for textures to appear: <BR>
     *     GL11.glEnable(GL11.GL_TEXTURE_2D); <BR>
     *     GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_REPLACE);<BR>
     *
     * @param o
     * @param textureHandle
     * /
    public static void renderMesh(idx3d_Object o, int textureHandle)
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,textureHandle);
        renderMesh(o);
    }

    / **
     * Basic mesh render function, using current color, material and texture.
     * @param o
     * /
    public static void renderMesh(idx3d_Object o)
    {
        idx3d_Triangle t;
        GL11.glBegin(GL11.GL_TRIANGLES);
        for (int j = 0; j < o.triangle.length; j++) { // draw all triangles in object
            t = o.triangle[j];

            GL11.glTexCoord2f(tileFactorHoriz*t.p1.u, tileFactorVert*t.p1.v);
            GL11.glNormal3f(t.p1.n.x, t.p1.n.y, t.p1.n.z);
            GL11.glVertex3f( (float)t.p1.pos.x, (float)t.p1.pos.y, (float)t.p1.pos.z);

            GL11.glTexCoord2f(tileFactorHoriz*t.p2.u, tileFactorVert*t.p2.v);
            GL11.glNormal3f(t.p2.n.x, t.p2.n.y, t.p2.n.z);
            GL11.glVertex3f( (float)t.p2.pos.x, (float)t.p2.pos.y, (float)t.p2.pos.z);

            GL11.glTexCoord2f(tileFactorHoriz*t.p3.u, tileFactorVert*t.p3.v);
            GL11.glNormal3f(t.p3.n.x, t.p3.n.y, t.p3.n.z);
            GL11.glVertex3f( (float)t.p3.pos.x, (float)t.p3.pos.y, (float)t.p3.pos.z);
        }
        GL11.glEnd();
    }


    / **
     * Render OBJ mesh (may have multiple texture coords per vert!!??!!)
     * @param o
     * /
    public static void renderMeshOBJ(idx3d_Object o, int textureHandle)
    {
        idx3d_Triangle t;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,textureHandle);
        GL11.glBegin(GL11.GL_TRIANGLES);
        for (int j = 0; j < o.triangle.length; j++) { // draw all triangles in object
            t = o.triangle[j];

            GL11.glTexCoord2f(tileFactorHoriz*t.uvw1.x, tileFactorVert*t.uvw1.y);
            GL11.glNormal3f(t.p1.n.x, t.p1.n.y, t.p1.n.z);
            GL11.glVertex3f( (float)t.p1.pos.x, (float)t.p1.pos.y, (float)t.p1.pos.z);

            GL11.glTexCoord2f(tileFactorHoriz*t.uvw2.x, tileFactorVert*t.uvw2.y);
            GL11.glNormal3f(t.p2.n.x, t.p2.n.y, t.p2.n.z);
            GL11.glVertex3f( (float)t.p2.pos.x, (float)t.p2.pos.y, (float)t.p2.pos.z);

            GL11.glTexCoord2f(tileFactorHoriz*t.uvw3.x, tileFactorVert*t.uvw3.y);
            GL11.glNormal3f(t.p3.n.x, t.p3.n.y, t.p3.n.z);
            GL11.glVertex3f( (float)t.p3.pos.x, (float)t.p3.pos.y, (float)t.p3.pos.z);
        }
        GL11.glEnd();
    }

    / ***
     *   in projectVerts() have to set triangle.dist (average Z depth of triangle)
     *   return sortTriangles(tri,0,tri.length-1);
     * /
    public idx3d_Triangle[] sortTriangles(idx3d_Triangle[] tri, int L, int R)
    {
        float m=(tri[L].dist+tri[R].dist)/2;
        int i=L;
        int j=R;
        idx3d_Triangle temp;

        do
        {
            while (tri[i].dist>m) i++;
            while (tri[j].dist<m) j--;

            if (i<=j)
            {
                temp=tri[i];
                tri[i]=tri[j];
                tri[j]=temp;
                i++;
                j--;
            }
        }
        while (j>=i);

        if (L<j) sortTriangles(tri,L,j);
        if (R>i) sortTriangles(tri,i,R);

        return tri;
    }
************/

    /**
     * Render a unit cube, using current color, texture and material.
     * @param o
     */
    public static void renderCube()
    {
        GL11.glBegin(GL11.GL_QUADS);
        // Front Face
        GL11.glNormal3f( 0.0f, 0.0f, 1.0f);
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f(-1.0f, -1.0f,  1.0f);	// Bottom Left Of The Texture and Quad
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( 1.0f, -1.0f,  1.0f);	// Bottom Right Of The Texture and Quad
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( 1.0f,  1.0f,  1.0f);	// Top Right Of The Texture and Quad
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f(-1.0f,  1.0f,  1.0f);	// Top Left Of The Texture and Quad
        // Back Face
        GL11.glNormal3f( 0.0f, 0.0f, -1.0f);
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f(-1.0f, -1.0f, -1.0f);	// Bottom Right Of The Texture and Quad
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f(-1.0f,  1.0f, -1.0f);	// Top Right Of The Texture and Quad
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f( 1.0f,  1.0f, -1.0f);	// Top Left Of The Texture and Quad
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f( 1.0f, -1.0f, -1.0f);	// Bottom Left Of The Texture and Quad
        // Top Face
        GL11.glNormal3f( 0.0f, 1.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f(-1.0f,  1.0f, -1.0f);	// Top Left Of The Texture and Quad
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f(-1.0f,  1.0f,  1.0f);	// Bottom Left Of The Texture and Quad
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( 1.0f,  1.0f,  1.0f);	// Bottom Right Of The Texture and Quad
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( 1.0f,  1.0f, -1.0f);	// Top Right Of The Texture and Quad
        // Bottom Face
        GL11.glNormal3f( 0.0f, -1.0f, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f(-1.0f, -1.0f, -1.0f);	// Top Right Of The Texture and Quad
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f( 1.0f, -1.0f, -1.0f);	// Top Left Of The Texture and Quad
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f( 1.0f, -1.0f,  1.0f);	// Bottom Left Of The Texture and Quad
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f(-1.0f, -1.0f,  1.0f);	// Bottom Right Of The Texture and Quad
        // Right face
        GL11.glNormal3f( 1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( 1.0f, -1.0f, -1.0f);	// Bottom Right Of The Texture and Quad
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( 1.0f,  1.0f, -1.0f);	// Top Right Of The Texture and Quad
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f( 1.0f,  1.0f,  1.0f);	// Top Left Of The Texture and Quad
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f( 1.0f, -1.0f,  1.0f);	// Bottom Left Of The Texture and Quad
        // Left Face
        GL11.glNormal3f( -1.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f(-1.0f, -1.0f, -1.0f);	// Bottom Left Of The Texture and Quad
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f(-1.0f, -1.0f,  1.0f);	// Bottom Right Of The Texture and Quad
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f(-1.0f,  1.0f,  1.0f);	// Top Right Of The Texture and Quad
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f(-1.0f,  1.0f, -1.0f);	// Top Left Of The Texture and Quad
        GL11.glEnd();
    }


    /**
     * Render a unit sphere at the origin, using current color,
     * texture and material.  This function calls makeSphere() to create the
     * sphere in a display list.
     */
    public static void renderSphere() {
        if (sphereID < 0) {
            makeSphere();
        }
        GL11.glCallList(sphereID);
    }


    /**
      * Create a Display List of a sphere.  Sphere will be at origin,
      * will have radius of 1 unit, and will have 24 segments x 24 slices.
      * <P>
      * The LWJGL sphere has it's poles aligned horizontally.  I rotate
      * 90 degrees around the X axis to get the poles of the sphere vertical.
      * Just a preference, not critical.
      * <P>
      * Display Lists store opengl rendering commands.
      * In this example the opengl commands to render a sphere
      * are stored into a display list for future drawing.  This approach
      * is often used to increase performance.
      * <P>
      * @see renderSphere() to draw the display list
      */
    public static void makeSphere()
    {
        Sphere s = new Sphere();       // an LWJGL class for drawing sphere
        sphereID = GL11.glGenLists(1); // Create a Display List
        s.setTextureFlag(true);        // generate texture coords
        GL11.glNewList(sphereID, GL11.GL_COMPILE); // Start building a list
        {
            GL11.glRotatef( -90, 1, 0, 0);  // rotate around X axis
            s.draw(1, 24, 24);              // run GL commands to draw sphere
        }
        GL11.glEndList();             // Done building the display list
    }

    //========================================================================
    // Functions to build a character set and draw text strings.
    //
    // Example:
    //           buildFont("Font_tahoma.png");
    //           ...
    //           glPrint(100, 100, 0, "Here's some text");
    //           ...
    //           destroyFont();   // cleanup
    //========================================================================

    static int fontListBase = -1;           // Base Display List For The character set
    static int fontTextureHandle = -1;      // Texture handle for character set image

    /**
     * Build a character set from the given texture image.
     *
     * @param charSetImage   texture image containing 256 characters in a 16x16 grid
     * @param fontWidth      how many pixels to allow per character on screen
     *
     * @see       destroyFont()
     */
    public static void buildFont(String charSetImage, int fontWidth)
    {
        // make texture from image
        GLImage textureImg = loadImage(charSetImage);
        fontTextureHandle = makeTexture(textureImg);
        // build character set as call list of 256 textured quads
        buildFont(fontTextureHandle, fontWidth);
    }

    /**
      * Build the character set display list from the given texture.  Creates
      * one quad for each character, with one letter textured onto each quad.
      * Assumes the texture is a 256x256 image containing every
      * character of the charset arranged in a 16x16 grid.  Each character
      * is 16x16 pixels.  Call destroyFont() to release the display list memory.
      *
      * Should be in ORTHO (2D) mode to render text (see setOrtho()).
      *
      * Special thanks to NeHe and Giuseppe D'Agata for the "2D Texture Font"
      * tutorial (http://nehe.gamedev.net).
      *
      * @param charSetImage   texture image containing 256 characters in a 16x16 grid
      * @param fontWidth      how many pixels to allow per character on screen
      *
      * @see       destroyFont()
      */
    public static void buildFont(int fontTxtrHandle, int fontWidth)
    {
        float factor = 1f/16f;
        float cx, cy;
        fontListBase = GL11.glGenLists(256); // Creating 256 Display Lists
        for (int i = 0; i < 256; i++) {
            cx = (float) (i % 16) / 16f;              // X Texture Coord Of Character (0 - 1.0)
            cy = (float) (i / 16) / 16f;              // Y Texture Coord Of Character (0 - 1.0)
            GL11.glNewList(fontListBase + i, GL11.GL_COMPILE); // Start Building A List
            GL11.glBegin(GL11.GL_QUADS);              // Use A 16x16 pixel Quad For Each Character
            GL11.glTexCoord2f(cx, 1 - cy - factor);  // Texture Coord (Bottom Left)
            GL11.glVertex2i(0, 0);
            GL11.glTexCoord2f(cx + factor, 1 - cy - factor); // Texture Coord (Bottom Right)
            GL11.glVertex2i(16, 0);
            GL11.glTexCoord2f(cx + factor, 1 - cy);   // Texture Coord (Top Right)
            GL11.glVertex2i(16, 16);
            GL11.glTexCoord2f(cx, 1 - cy);             // Texture Coord (Top Left)
            GL11.glVertex2i(0, 16);
            GL11.glEnd();                              // Done Building Our Quad (Character)
            GL11.glTranslatef(fontWidth, 0, 0);        // Move To The Right Of The Character
            GL11.glEndList();                          // Done Building The Display List
        } // Loop Until All 256 Are Built
    }

    /**
     * Clean up the allocated display lists for the character set.
     */
    public static void destroyFont()
    {
        if (fontListBase != -1) {
            GL11.glDeleteLists(fontListBase,256);
            fontListBase = -1;
        }
    }

    /**
      * Render a text string onto the screen, using the character set created
      * by buildCharSet().
      */
     public static void glPrint(int x, int y, int set, String msg)
     {
         int offset = fontListBase - 32 + (128 * set);
         if (fontListBase == -1 || fontTextureHandle == -1) {
             System.out.println("GLApp.glPrint(): character set has not been created -- run buildCharSet() first");
             return;
         }
         if (msg != null) {
             // enable the charset texture
             GL11.glBindTexture(GL11.GL_TEXTURE_2D, fontTextureHandle);
             // prepare to render in 2D
             setOrthoOn();
             // draw the text
             GL11.glTranslatef(x, y, 0);        // Position The Text (in pixels coords)
             for(int i=0; i<msg.length(); i++) {
                 GL11.glCallList(offset + msg.charAt(i));
             }
             // restore the original positions and views
             setOrthoOff();
         }
     }


     /**
       * Render a text string in model space, using the character set created
       * by buildCharSet().
       */
      public static void glText(float x, float y, float z, int set, float scale, String msg)
      {
          int offset = fontListBase - 32 + (128 * set);
          if (fontListBase == -1 || fontTextureHandle == -1) {
              System.out.println("GLApp.glPrint(): character set has not been created -- run buildCharSet() first");
              return;
          }
          if (msg != null) {
              // enable the charset texture
              GL11.glBindTexture(GL11.GL_TEXTURE_2D, fontTextureHandle);
              // draw the text
              GL11.glPushMatrix();
              {
                  GL11.glTranslatef(x, y, z); // Position The Text (in pixels coords)
                  GL11.glScalef(scale,scale,scale);  // make it smaller (arbitrary kludge!!!!)
                  for (int i = 0; i < msg.length(); i++) {
                      GL11.glCallList(offset + msg.charAt(i));
                  }
              }
              GL11.glPopMatrix();
          }
      }


     //========================================================================
     // PBuffer functions
     //
     // Pbuffers are offscreen buffers that can be rendered into just like
     // the regular framebuffer.  A pbuffer can be larger than the screen,
     // which allows for the creation of higher resolution images.
     //
     //========================================================================

     /**
      * Create a Pbuffer for use as an offscreen buffer, with the given
      * width and height.  Use selectPbuffer() to make the pbuffer the
      * context for all subsequent opengl commands.  Use selectDisplay() to
      * make the Display the context for opengl commands.
      * <P>
      * @param width
      * @param height
      * @return Pbuffer
      * @see selectPbuffer(), selectDisplay()
      */
     public Pbuffer makePbuffer(final int width, final int height) {
         Pbuffer pbuffer = null;
         try {
             pbuffer = new Pbuffer(width, height,
                                   new PixelFormat(24, //bitsperpixel
                                                   8,  //alpha
                                                   24, //depth
                                                   0,  //stencil
                                                   0), //samples
                                   null,
                                   null);
          } catch (LWJGLException e) {
             System.out.println("GLApp.makePbuffer(): exception " + e);
         }
         return pbuffer;
     }

     /**
      * Make the pbuffer the current context for opengl commands.  All following
      * gl functions will operate on this buffer instead of the display.
      * <P>
      * NOTE: the Pbuffer may be recreated if it was lost since last used.  It's
      * a good idea to use:
      * <PRE>
      *         pbuff = selectPbuffer(pbuff);
      * </PRE>
      * to hold onto the new Pbuffer reference if Pbuffer was recreated.
      *
      * @param pb  pbuffer to make current
      * @return    Pbuffer
      * @see       selectDisplay(), makePbuffer()
      */
     public Pbuffer selectPbuffer(Pbuffer pb) {
         if (pb != null) {
             try {
                 // re-create the buffer if necessary
                 if (pb.isBufferLost()) {
                     int w = pb.getWidth();
                     int h = pb.getHeight();
                     System.out.println("GLApp.selectPbuffer(): Buffer contents lost - recreating the pbuffer");
                     pb.destroy();
                     pb = makePbuffer(w, h);
                 }
                 // select the pbuffer for rendering
                 pb.makeCurrent();
             }
             catch (LWJGLException e) {
                 System.out.println("GLApp.selectPbuffer(): exception " + e);
             }
         }
         return pb;
     }

     /**
      * Make the Display the current context for OpenGL commands.  Subsequent
      * gl functions will operate on the Display.
      *
      * @see selectPbuffer()
      */
     public void selectDisplay()
     {
         try {
             Display.makeCurrent();
         } catch (LWJGLException e) {
             System.out.println("GLApp.selectDisplay(): exception " + e);
         }
     }

     /**
      * Copy the pbuffer contents to a texture.  (Should this use glCopyTexSubImage2D()?
      * Is RGB the fastest format?)
      */
     public static void frameSave(Pbuffer pbuff, int textureHandle) {
         GL11.glBindTexture(GL11.GL_TEXTURE_2D,textureHandle);
         GL11.glCopyTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, 0, 0, pbuff.getWidth(), pbuff.getHeight(), 0);
     }


     /**
      * Save the contents of the current render buffer to a PNG image.  If the current
      * buffer is the framebuffer then this will work as a screen capture.  Can
      * also be used with the PBuffer class to copy large images or textures that
      * have been rendered into the offscreen pbuffer.
      * <P>
      * WARNING: this function hogs memory!  Call java with more memory
      * (java -Xms360m -Xmx360)
      * <P>
      * @see   selectPbuffer(), selectDisplay()
      */
     public static void screenShot(int width, int height, String saveFilename) {
         // allocate space for RBG pixels
         ByteBuffer framebytes = GLApp.allocBytes(width * height * 3);
         int[] pixels = new int[width * height];
         int bindex;
         // grab a copy of the current frame contents as RGB (has to be UNSIGNED_BYTE or colors come out too dark)
         GL11.glReadPixels(0, 0, width, height, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, framebytes);
         // copy RGB data from ByteBuffer to integer array
         for (int i = 0; i < pixels.length; i++) {
             bindex = i * 3;
             pixels[i] =
                 0xFF000000                                          // A
                 | ((framebytes.get(bindex) & 0x000000FF) << 16)     // R
                 | ((framebytes.get(bindex+1) & 0x000000FF) << 8)    // G
                 | ((framebytes.get(bindex+2) & 0x000000FF) << 0);   // B
         }
         // free up this memory
         framebytes = null;
         // flip the pixels vertically (opengl has 0,0 at lower left, java is upper left)
         pixels = GLImage.flipPixels(pixels, width, height);
         try {
             // Create a BufferedImage with the RGB pixels then save as PNG
             BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
             image.setRGB(0, 0, width, height, pixels, 0, width);
             javax.imageio.ImageIO.write(image, "png", new File(saveFilename));
         }
         catch (Exception e) {
             System.out.println("GLApp.screenShot(): exception " + e);
         }
     }


     //========================================================================
     // Buffer allocation functions
     //
     // These functions create and populate the native buffers used by LWJGL.
     //========================================================================

     public static ByteBuffer allocBytes(int howmany) {
         return ByteBuffer.allocateDirect(howmany * SIZE_BYTE).order(ByteOrder.nativeOrder());
     }

     public static IntBuffer allocInts(int howmany) {
         return ByteBuffer.allocateDirect(howmany * SIZE_INT).order(ByteOrder.nativeOrder()).asIntBuffer();
     }

     public static FloatBuffer allocFloats(int howmany) {
         return ByteBuffer.allocateDirect(howmany * SIZE_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
     }

     public static DoubleBuffer allocDoubles(int howmany) {
         return ByteBuffer.allocateDirect(howmany * SIZE_DOUBLE).order(ByteOrder.nativeOrder()).asDoubleBuffer();
     }

     public static ByteBuffer allocBytes(byte[] bytearray) {
         ByteBuffer bb = ByteBuffer.allocateDirect(bytearray.length * SIZE_BYTE).order(ByteOrder.nativeOrder());
         bb.put(bytearray).flip();
         return bb;
     }

     public static IntBuffer allocInts(int[] intarray) {
         IntBuffer ib = ByteBuffer.allocateDirect(intarray.length * SIZE_FLOAT).order(ByteOrder.nativeOrder()).asIntBuffer();
         ib.put(intarray).flip();
         return ib;
     }

     public static FloatBuffer allocFloats(float[] floatarray) {
         FloatBuffer fb = ByteBuffer.allocateDirect(floatarray.length * SIZE_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
         fb.put(floatarray).flip();
         return fb;
     }


     /**
      * make a time stamp for filename
      * @return
      */
     public String makeTimestamp()
     {
         Calendar now = Calendar.getInstance();
         int year = now.get(Calendar.YEAR);
         int month = now.get(Calendar.MONTH) + 1;
         int day = now.get(Calendar.DAY_OF_MONTH);
         int hours = now.get(Calendar.HOUR_OF_DAY);
         int minutes = now.get(Calendar.MINUTE);
         int seconds = now.get(Calendar.SECOND);
         String datetime =  ""
                            + year
                            + (month < 10 ? "0" : "") + month
                            + (day < 10 ? "0" : "") + day
                            + "-"
                            + (hours < 10 ? "0" : "") + hours
                            + (minutes < 10 ? "0" : "") + minutes
                            + (seconds < 10 ? "0" : "")  + seconds;
         return datetime;
     }

}
