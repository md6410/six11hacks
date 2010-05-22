<html>
  <head>
    <title>Skrui Draw Help and Moral Support</title>
  </head>
  
  <body>

    <p>Skrui Draw is a drawing program with a sketch recognition-based
    user interface. My goal in making all of this is to explore what
    is possible with pen-centric widgets and interaction techniques.
    </p>

    <p><a href="skrui-draw.zip">Download Skrui Draw here</a>.
    <a href="skrui-draw.zip"><img border="0" align="right"
    src="skrui-draw-icon.png" width="128" height="128" /></a>

    <p><b><i>NOTE</i>: I haven't implemented pen widgets for
    everything. In order to save and print you have to use the
    keyboard until I have implemented pen-centric widgets for that
    stuff.</b></p>

    <p>Commands include:</p>
    <ul>
      <li>^S: Save</li>
      <li>^Shift-S: Save as</li>
      <li>^O: Open</li>
      <li>^P: Print to PDF (this is pretty cool)</li>
    </ul>

    <p>Here is the Skrui Draw UI:</p>

    <img src="skrui-draw.png" width="601" height="441" />

    <p>Here's some neat stuff you can do with it.</p>

    <p><b>Thickness chooser</b>: This is based on the CrossY widget by
    Georg Apitz and François Guimbretière. To use, just press the pen
    down inside the widget and slide it over the red line. The pen
    thickness depends on how far up the red line you cross.</p>

    <p><b>Color chooser</b>: Colors are 'mixed' together by stirring
    the pen in a color square. Unlike normal swatches that let you
    click, these widgets let you nudge the pen color in a direction by
    dragging the pen through the color area. The resulting color is
    left on the color square so you can see what you've mixed (but
    fades over time).</p>

    <p><b>Alpha chooser</b>: You can also make the ink more or less
    transparent by stirring your pen in this widget. While color
    chosers do not pay attention to what direction you are stirring,
    the alpha chooser makes the ink more clear if you move up, and
    more opaque if you move it down.</p>

    <p><b>Undo/redo</b>: Stir the pen to the left to undo, stir to the
    right to redo. This widget lets users trigger discrete events
    (e.g. undo/redo), rather than continuous events (e.g. color
    change).</p>

    <p><b>Flow selection</b>: After you've drawn something, you might
    want to correct errors. You could do this by undoing and trying
    over, or you could modify what you've drawn. You can select
    portions of a stroke by setting the pen down and holding it
    still. A selection will begin to grow outward. If you move the pen
    while selecting, you will modify the selected region. If you lift
    up and then draw near the selection, the selected region will try
    to move towards the new line you've drawn. You can make multiple
    selections by tapping twice (or more) before beginning a
    selection.</p>

    <p><b>Scribble fill</b>: If you want to fill a region, just pick a
    color and start scribbling back and forth. The system recognizes
    this as a fill gesture and automatically switches input mode to
    fill. It will revert back to line drawing mode afterwards.</p>

    <p>Questions, comments should be redirected to Gabe Johnson:
    johnsogg@cmu.edu</p>

  </body>
</html>
