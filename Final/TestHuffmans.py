'''
Unit test that tests implementation of huffmans algorithm

Created on December 6, 2016

@author: greg gagne
'''
import unittest

import Huffmans

class Test(unittest.TestCase):

    def testHuffmans(self):
        
        # a zero-length string
        self.assertEquals(0, Huffmans.huffmans(''))

        # a string of size 1
        self.assertEquals(2, Huffmans.huffmans('ab'))
        
        # more interesting strings
        self.assertEquals(4, Huffmans.huffmans('abba'))
        self.assertEquals(8, Huffmans.huffmans('hanna'))
        self.assertEquals(47, Huffmans.huffmans('barry andy barbara'))
        
if __name__ == "__main__":
    unittest.main()
