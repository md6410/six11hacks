#!/usr/bin/env python

# warble.py - GNU Radio script demonstrating how to dynamically update block
#             parameters. Every second it calculates two pitches (constrained
#             by the command-line parameters, e.g. 200 and 800) and plays them.
#             It produces a science-fiction-like "warble" sound.
#
# Example:
#
# $ ./warble.py 350 440

from gnuradio import gr
from gnuradio import audio
from time import sleep
import sys
import random

class warble(gr.top_block):
    def __init__(self, frq0, frq1):
        gr.top_block.__init__(self)

        sample_rate = 44100
        ampl = 0.3
        self.frq0 = frq0
        self.frq1 = frq1
        self.src0 = gr.sig_source_f (sample_rate, gr.GR_SIN_WAVE, self.frq0, ampl)
        self.src1 = gr.sig_source_f (sample_rate, gr.GR_SIN_WAVE, self.frq1, ampl)
        self.adder = gr.add_vff(1)
        
        waveout = gr.wavfile_sink("warble.wav", 1, sample_rate, 8)
        dst = audio.sink (sample_rate, "", True)
        self.connect (self.src0, (self.adder, 0))
        self.connect (self.src1, (self.adder, 1))
        self.connect ((self.adder, 0), (dst, 0))
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
        thing = warble(f_low, f_high)
        thing.start()
        while (1):
            sleep(1)
            f0 = random.randint(f_low, f1)
            f1 = random.randint(f0, f_high)
            thing.set_tone0(f0)
            thing.set_tone1(f1)
            print (str(f0) + " " + str(f1))
    except KeyboardInterrupt:
        pass

