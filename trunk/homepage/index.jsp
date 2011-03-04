<html>
  <head>
    <title>Gabe Johnson's Homepage</title>
  </head>
  <style type="text/css">
    body {
      font-family: sans-serif;
      font-padding: 6px;
      color: black;
      line-height: 120%;
      max-width: 800px;
    }

    h1 {
      font: large sans-serif;
    }

    .head {
      font-size: x-large;
      font-family: sans-serif;
      padding-left: 26px;
    }

    .indented {
      padding-left: 36px;
    }

    .urgent {
      margin: 24px;
      padding: 24px;
      color: #700;
      font-weight: bold;
      background-color: #fef;
      border: 1px dashed #088;
    }

    li {
      margin: .2em 0
    }
    
  </style>
  <body>
    <table>
      <tr>
	<td rowspan="3">
	  <img border="0" title="Gabe Johnson" style="float:right" 
	       src="img/gabe.jpg" width="240" height="300" />
	</td>
	<td valign="top">
	  <p>
	    <div class="head">Gabe Johnson's Homepage</div>
	    <br />
	    <br />
	  </p>
	  <p>
	    <div class="indented">
	      Email: johnsogg@cmu.edu <br />
	      Phone: 720-934-0491 <br />
	    </div>
	  </p>
	</td>
      </tr>
      <tr>
	<td>
	  <div class="urgent"><a href="skrui/">Try Skrui Draw version
	  3</a></div> <br /> <br />
	  <div class="indented">
	    <a href="http://johnsogg.blogspot.com/">Blog</a> - 
	    <a href="http://picasaweb.google.com/gabe.johnson">Photos</a> -
	    <a href="gabe-johnson-cv.pdf">CV (Resume)</a> -
	    <a href="#projects">Projects</a> -
	    <a href="#pubs">Publications</a> -
	    <a href="dotfiles">Dotfiles</a>
	  </div>
	</td>
      </tr>
    </table>


    <p>
      Hello. I am a PhD candidate in the <a
      href="http://code.arc.cmu.edu/">Computational Design Lab</a>
      at <a href="http://cmu.edu/">Carnegie Mellon University</a>. 


In the past I have done various software development or research gigs
      for Google, Ricoh Research, Stevens Tech (in the <a
      href="http://howe.stevens.edu/research/research-centers/decision-technologies/">Center
      for Decision Technologies</a>) and <a
      href="http://readytalk.com/">ReadyTalk</a>. Before that I was a
      computer science major at <a
      href="http://www.cs.colorado.edu/">the University of Colorado,
      Boulder</a> where I was involved with the <a
      href="http://www.cs.colorado.edu/~l3d/">Center for LifeLong
      Learning and Design</a>. Before that I mostly just played
      guitar.
    </p>

    <p>
      My research is on design and how it might be supported with
      computation. In particular I work on sketch-recognition based
      user interfaces and calligraphic interaction. I am driven by an
      almost obsessive desire to find ways of using computers with
      pens and other yet-to-be-invented hardware. Some of my projects
      and publications below describe this in more detail.
    </p>

    <a name="projects" />
    <h2>Projects (and neat hacks)</h2>


    <p>
      <img border="0" src="skrui/skrui-draw-icon-60px.png" width="60"
      height="60">&nbsp; &nbsp; <b>Skrui Draw</b> is a simple painting
      application that is based on pen-centric interaction techniques,
      often involving sketch recognition. It is a vector-based
      painting program and lets you save images as PDFs. I made the
      icon in the application itself (and if you can make a better
      one, and I am sure you can, make one in Skrui Draw and send it
      to me). <a href="skrui/">Download and read more about the
      current status of Skrui Draw over yonder</a>.</p>

    <p>
      <img border="0" src="img/olive-thumb.png" width="60" height="60"
      />&nbsp; &nbsp; <b>Olive</b>: Olive is a web-based development
      environment for prototyping sketch recognition algorithms or
      interaction techniques. It features a domain-specific language
      called Slippy, which allows developers to quickly try out
      ideas. <a href="slippy/">You may try out Olive here</a>.
    </p>

    <p><img border="0" src="img/sketching-games.png" width="60"
    height="60" /> &nbsp; &nbsp; <b>Sketching Games</b>: This is a
    series of games where players draw or describe pictures. This
    benefits researchers of sketching interfaces by providing lots of
    useful data on how people sketch (in the spirit of von Ahn's
    wonderful <a href="http://www.gwap.com/gwap/">ESP Game</a>). It
    also provides me with a platform for exploring calligraphic
    interaction methods. You can play two of the games here on my
    site: <i><a href="picturephone" style="font-weight: bold; color:
    #700">Picturephone</a></i> and <i><a href="ss"
    style="font-weight:bold; color: #700">Stellasketch</a></i>
    (Stellasketch is better).  (<b>Paper:
	<i>
	  <a href="papers/sketching-games.pdf">PDF</a>, 
	  <a href="papers/sketching-games.bib">Bib</a>,
          <a href="papers/sketching-games-sbim-slides.pdf">Annotated
          Slides</a></i></b>)
    </p>

    <p><img border="0" src="img/sketch-review.png" width="60"
    height="60" /> &nbsp; &nbsp;<b>Computational Support for Sketching
    in Design: A Review</b>. I worked with my co-authors (Mark Gross,
    Ellen Do, and Jason Hong) for quite a long time on a fairly
    comprehensive review of the literature on sketch-based design
    systems. This includes a discussion of the role of sketching in
    design, hardware, various aspects of sketch recognition, and
    interaction methods for calligraphic interfaces. This is my only
    project that hasn't involved me building software. On the outset
    of this project we wanted to write something that a fresh PhD
    student interested in this area could read and have a very good
    sense of the field. I think we did a pretty good job.  (<b>Paper:
	<i>
	  <a href="papers/sketch-lit-review.pdf">PDF</a>, 
	  <a href="papers/sketch-lit-review.bib">Bib</a></i></b>)
    </p>


    <p><img border="0" src="img/flatcad.png" width="60" height="60" />
    &nbsp; &nbsp;<b>FlatCAD and FlatLang</b>: FlatCAD is a 3D modeling
    environment for designing objects that are produced on a laser
    cutter. Instead of the traditional WIMP (Windows, Icons, Menus,
    Pointers) paradigm, FlatCAD users <i>program</i> models using a
    domain-specific language called FlatLang. You can think of
    FlatLang as a sort of 3D LOGO, but the turtle has a high power
    laser attached to it. There is more information on <a
    href="http://flatcad.org/">the FlatCAD web site</a> including
    source code and binaries. Try it out!  (<b>Paper:
	<i>
	  <a href="papers/flatcad-vlhcc.pdf">PDF</a>, 
	  <a href="papers/flatcad-vlhcc.bib">Bib</a></i></b>)
    </p>

    <p><img border="0" src="img/flow-selection.png" width="60"
    height="60" /> &nbsp; &nbsp;<b>Flow Selection</b> is a time-based,
    modeless selection and operation technique for calligraphic
    tools. It allows users to alternately draw and make corrections
    without explicitly changing pen tools. I had the idea for this
    mostly because I am lazy. While making corrections to
    computer-based freehand drawings, I was annoyed that I had to
    change from a draw mode to a select mode to an edit mode and
    (finally) back to a draw mode again before continuing to work.
      <a href="flow-selection/">Click here to try the Flow Selection
    Java Applet demo.</a>  (<b>Paper:
	<i>
	  <a href="papers/flow-selection-avi.pdf">PDF</a>, 
	  <a href="papers/flow-selection-avi.bib">Bib</a>,
	  <a href="papers/flow-selection-avi-slides.pdf">Annotated
	  Slides</a></i></b>)
    </p>

    <p><img border="0" src="img/designosaur.png" width="60"
    height="60" /> &nbsp; &nbsp;<b>Designosaur</b>: The Designosaur
    was a sketch-based design environment (ostensibly for kids) for
    designing your own dinosaur (or monster) skeleton which could be
    manufactured with a laser cutter. This was my first grad school
    project, and while it was a 'bad pancake', it served as the basis
    for my future work on design tools (like FlatCAD) and sketch-based
    interaction (like Flow Selection). (<b>Paper:
	<i>
	  <a href="papers/designosaur-ff-dcc.pdf">PDF</a>, 
	  <a href="papers/designosaur-ff-dcc.bib">Bib</a></i></b>)
    </p>

    <p><img border="0" src="img/frontend.png" width="60" height="60"
    /> &nbsp; &nbsp;<b>FrontEnd Java Layout Manager</b>: If you have
    ever had to program a user interface in Java you know that <a
    href="http://madbean.com/anim/totallygridbag/">it can sometimes be
    a daunting task</a>.  Continuing on my theory that laziness is
    superior to stress, I developed a very easy to use layout manager
    for Java. The FrontEnd layout manager is based on
    constraints---you simply state where various elements should be
    relative to one another, optionally indicating their alignment and
    inter-component spacing. I use this layout in basically all of my
    Java UI projects. (<b>Download:
	<i>
	  <a href="hacks/frontend-src.tar.gz">Source</a>, 
	  <a href="hacks/frontend.jar">Java Executable Example</a>,
	  <a href="hacks/Example.java.txt">Java Example</a></i></b>)
    </p>

    <p><img border="0" src="img/driftroom.png" width="60" height="60"
    /> &nbsp; &nbsp;<b>Drift Room</b>: Inspired by the Royal College
    of Art's Drift Table, the Drift Room provides a slowly 'drifting'
    projection of an aerial scene on the floor. The direction and
    speed of the drift is manipulated by people standing in different
    locations. While this was just a class project, it was one of the
    cooler things I've been involved in: it was partly a physical hack
    (involving distance sensors and many heavy-duty springs) and a
    software hack (involving low-level Handyboard programming that
    provided input to a Google Maps mashup). The Drift Room was
    designed and built by Tajin Biswas, Chang Zhang, and
    myself. (<b>Paper:
	<i>
	  <a href="papers/drift-room.pdf">PDF</a></i></b>)
    </p>

    <a name="pubs" />
    <h2>Publications</h2>

    <p><i>(<a href="papers/gabe-johnson.bib">Complete BibTeX
    Listing</a>)</i></p>

    <ul>
      <li>G. Johnson and E. Y.-L. Do. <a
      href="papers/sketching-games.pdf">Games for sketch data
      collection</a>. In C. Grimm and J. J. L. Jr., editors,
      EUROGRAPHICS Symposium on Sketch-Based Interfaces and Modeling
      (SBIM 2009), 2009. <a
      href="papers/sketching-games.bib"></a></li>

      <li>G. Johnson, M. D. Gross, J. Hong, and E. Y.-L. Do. <a
      href="papers/sketch-lit-review.pdf">Computational support for
      sketching in design: a review</a>. Foundations and Trends in
      Human-Computer Interaction, Vol. 2: No 1, pp
      1--93. http://dx.doi.org/10.1561/1100000013, 2009. <i><a
      href="papers/sketch-lit-review.bib">Bib</a></i></li>

      <li>G. Johnson. <a
      href="papers/sketching-games-iui-workshop.pdf">Picturephone: A
      game for sketch data capture</a>. In IUI '09 Workshop on Sketch
      Recognition, 2009. <i><a
      href="papers/sketching-games-iui-workshop.bib">Bib</a></i></li>

      <li>G. Johnson. <a href="papers/flatcad-vlhcc.pdf">FlatCAD and
      FlatLang: Kits by code</a>. In Proceedings of IEEE Symposium on
      Visual Languages and Human-Centric Computing, 2008. <i> <a
      href="papers/flatcad-vlhcc.bib">Bib</a></i> </li>

      <li>G. Johnson, M. D. Gross, and E. Y.-L. Do. <a
      href="papers/flow-selection-avi.pdf">Flow selection: A
      time-based selection and operation technique for sketching
      tools</a>. In 2006 Conference on Advanced Visual Interfaces,
      pages 83--86, Venice, Italy, 2006. <i> <a
      href="papers/flow-selection-avi.bib">Bib</a></i></li>

      <li>Y. Oh, G. Johnson, M. D. Gross, and E. Y.-L. Do. <a
      href="papers/designosaur-ff-dcc.pdf">The Designosaur and
      the Furniture Factory: Simple software for fast
      fabrication</a>. In 2nd Int. Conf. on Design Computing and
      Cognition (DCC06), 2006. <i> <a
      href="papers/designosaur-ff-dcc.bib">Bib</a></i></li>

    </ul>
    
    <p>
      <i>Generated on: <%= new java.util.Date() %></i>
    </p>

  </body>
</html>
