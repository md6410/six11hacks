#!/usr/bin/env python

from gnuradio import gr
from gnuradio import bbn
from math import pi
import Numeric

#hello

class bbn_80211b_mod(gr.hier_block):

    def __init__(self, fg, spb, alpha, gain, use_barker=0):
        if not isinstance(spb, int) or spb < 2:
            raise TypeError, "sbp must be an integer >= 2"
        self.spb = spb
        self.bits_per_chunk = 1

	ntaps = 2 * spb - 1
        alpha = 0.5

        self.bytes2chunks = gr.packed_to_unpacked_bb(self.bits_per_chunk,
                                                     gr.GR_MSB_FIRST)

        constellation = ( (), ( -1-0j,1+0j ), ( 0.707+0.707j,-0.707-0.707j ),
                          ( 0.707+0j,-0.707-0.707j ), ( -1+0j,-1j, 1j, 1+0j ),
                          ( 1+0j,0+1j,-1+0j,0-1j ), ( 0+0j,1+0j ) )

        self.chunks2symbols = gr.chunks_to_symbols_bc(constellation[2])
        self.scrambler = bbn.scrambler_bb(True)
        self.diff_encode = gr.diff_encoder_bb(2);

        self.barker_taps = bbn.firdes_barker(spb)

	self.rrc_taps = gr.firdes.root_raised_cosine(4 * gain, spb,
		1.0, alpha, ntaps)

        if use_barker:
            self.tx_filter = gr.interp_fir_filter_ccf(spb, self.barker_taps)
        else:
            self.tx_filter = gr.interp_fir_filter_ccf(spb, self.rrc_taps)

        fg.connect(self.scrambler, self.bytes2chunks)
        fg.connect(self.bytes2chunks, self.diff_encode)
        fg.connect(self.diff_encode, self.chunks2symbols)
	fg.connect(self.chunks2symbols,self.tx_filter)

        gr.hier_block.__init__(self, fg, self.scrambler, self.tx_filter)
        bbn.crc16_init()
