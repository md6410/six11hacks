	    +=-+=-+=-+=-+=-+=-+=-+=-+=-+=-+=-+=-+=-+=-+=-
			    FlatCAD README
	    =+-=+-=+-=+-=+-=+-=+-=+-=+-=+-=+-=+-=+-=+-=+-

		     Last updated: March 06, 2007


Instructions for the demo:

You need Java 1.5. I am pretty sure this is standard on new Macs. If
you don't have it, java.com has installers.

You'll have to use a terminal. Unpacking the zip file, you should see:

~/tmp $ ls -l flatcad
total 1776
-rwxr-xr-x   1 johnsogg  johnsogg      67 Mar  5 21:47 flatcad
drwxr-xr-x   5 johnsogg  johnsogg     170 Mar  5 21:43 flatcad-examples
drwxr-xr-x   6 johnsogg  johnsogg     204 Feb  5 16:22 flatcad-native
-rw-r--r--   1 johnsogg  johnsogg  903292 Mar  5 21:53 flatcad.jar
    

Make the 'flatcad' file executable:

~/tmp $ chmod +x flatcad

If you aren't on a mac, you'll have to edit that file so that the
native library path points to the right place. It's right next to the
macosx directory.

Now you can run the demo by running that script. Note that the
dot-slash in front of the command is (probably) necessary.

~/tmp $ ./flatcad
    
Once you are inside the environment, you can use C-n, C-o, C-s, and
C-SHIFT-s to start a new file, open an existing file, save your file,
and save your file with a new name respectively. There are a few
example programs in the flatcad-examples directory.

The most important command is C-ENTER. This causes FlatCAD to
interpret the FlatLang program (in its entirety).

It is recommended that you look at the 'syntax.fl' file. It gives you
all sorts of guidance. Also, 'boxcad.fl' and 'jessica/jessica.fl' also
have some nifty things.
