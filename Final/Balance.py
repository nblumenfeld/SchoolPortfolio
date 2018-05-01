'''
Balance Function and unit test for question 4
Noah Blumenfeld
Decemeber 12, 2016
'''
import unittest

'''
This function balances a list according to the instruction on question 4
'''
def balance(values):
    """
    >>> balance([5,2,7,19,15,18,4])
    [2, 19, 4, 18, 5, 15, 7]

    """

    # instance variables
    results = []
    values_copy = values[:]

    # loop while the result list isn't filled with all numbers
    while len(results) < len(values):
        # if numbers left to copy into result, copy according to instructions
        if len(values_copy) > 0:
            results.append(min(values_copy))
            values_copy.remove(min(values_copy))
        if len(values_copy) > 0:
            results.append(max(values_copy))
            values_copy.remove(max(values_copy))

    # return the list
    return results



'''
Unit test for Balance function

1) tests the example list from the test
2) tests a list with an even amount of numbers in it
3) tests an empty list being passed to the function

'''
class BalanceTest(unittest.TestCase):
    values = [5, 2, 7, 19, 15, 18, 4]
    result = [2, 19, 4, 18, 5, 15, 7]

    def testExample(self):
        self.assertEquals(balance(self.values), self.result, 'failed example')

    def testEven(self):
        self.assertEquals(balance([1,2,3,4]), [1,4,2,3], 'failed even')

    def testEmpty(self):
        self.assertEquals(balance([]),[],'failed empty')

'''
Main method to run all the tests on balance funtion
'''

if __name__ == '__main__':
    import doctest
    doctest.testmod()
    unittest.main(exit=False)

