#!/usr/bin/env python

# warble.py - GNU Radio script demonstrating how to dynamically update block
#             parameters. Every second it calculates two pitches (constrained
#             by the command-line parameters, e.g. 200 and 800) and plays them.
#             It produces a science-fiction-like "warble" sound. Give it a
#             filename if you like, and if you do that you can also give it a
#             number of iterations to stop after.
#
# Syntax: warble.py freq0 freq1 [filename [num_iterations]]
#
# Examples:
#
# $ ./warble.py 350 440
# $ ./warble.py 350 440 warble_output.wav
# $ ./warble.py 350 400 warble_output.wav 20

from gnuradio import gr
from gnuradio import audio
from time import sleep
import sys
import random

class warble(gr.top_block):
    def __init__(self, frq0, frq1, filename):
        gr.top_block.__init__(self)

        sample_rate = 44100
        ampl = 0.3
        self.frq0 = frq0
        self.frq1 = frq1
        self.src0 = gr.sig_source_f (sample_rate, gr.GR_SIN_WAVE, self.frq0, ampl)
        self.src1 = gr.sig_source_f (sample_rate, gr.GR_SIN_WAVE, self.frq1, ampl)
        self.adder = gr.add_vff(1)
        dst = audio.sink (sample_rate, "", True)
        self.connect (self.src0, (self.adder, 0))
        self.connect (self.src1, (self.adder, 1))
        self.connect ((self.adder, 0), (dst, 0))

        if (filename):
            waveout = gr.wavfile_sink(filename, 1, sample_rate, 8)
            self.connect ((self.adder, 0), (waveout, 0))
    
    def set_tone0(self, v):
        self.src0.set_frequency(v)

    def set_tone1(self, v):
        self.src1.set_frequency(v)

# The following will run the warble block
if __name__ == '__main__':
    try:
        f_low = f0 = int(sys.argv[1])
        f_high = f1 = int(sys.argv[2])
        filename = None
        num_iterations = -1
        if (len(sys.argv) > 3):
            filename = sys.argv[3]
        if (len(sys.argv) > 4):
            num_iterations = int(sys.argv[4])

        thing = warble(f_low, f_high, filename)
        thing.start()
        while (num_iterations != 0):
            sleep(1)
            f0 = random.randint(f_low, f1)
            f1 = random.randint(f0, f_high)
            thing.set_tone0(f0)
            thing.set_tone1(f1)
            print (str(f0) + " " + str(f1))
            num_iterations = num_iterations - 1

    except KeyboardInterrupt:
        pass

