package dynamic_programming

import kotlin.test.assertEquals

interface Fibonacci {
    fun find(n: Int): Int
}

class BasicFibonacci : Fibonacci {
    override fun find(n: Int): Int {
        if (n == 0 || n == 1) {
            return n
        }
        return find(n - 1) + find(n - 2)
    }
}

class MemoizationFibonacci : Fibonacci {
    override fun find(n: Int): Int {
        // init memoization array of size n + 1 with -1
        val memoizationArray = Array(n + 1) { -1 }
        return helper(n, memoizationArray)
    }

    private fun helper(n: Int, memoizationArray: Array<Int>): Int {
        // three possible cases as we recurse down and back up
        return if (n == 0 || n == 1) {
            // reach the bottom where we know the values
            // store the known values and return it
            saveToMemoizationArray(n, n, memoizationArray)
            n
        } else if (memoizationArray[n] != -1) {
            // we reached a value that has been saved, no need to compute again
            memoizationArray[n]
        } else {
            // we reached a value that need to be computed
            // recurse down until meet base and come back up
            val fibonacciValue = helper(n - 1, memoizationArray) + helper(n - 2, memoizationArray)
            saveToMemoizationArray(n, fibonacciValue, memoizationArray)
            return fibonacciValue
        }
    }

    private fun saveToMemoizationArray(n: Int, fibonacciValue: Int, memoizationArray: Array<Int>) {
        memoizationArray[n] = fibonacciValue
    }
}

class TabulationFibonacci : Fibonacci {
    override fun find(n: Int): Int {
        // init tabulation array of size n + 1 with -1
        val tabulationArray = Array(n + 1) { -1 }
        // set the known values
        tabulationArray[0] = 0
        tabulationArray[1] = 1
        // iterate from unknown till n
        for (i in 2..n) {
            // calculate based on known values set in previous step
            val fibonacciValue = tabulationArray[i - 1] + tabulationArray[i - 2]
            saveToTabulationArray(i, fibonacciValue, tabulationArray)
        }
        return tabulationArray[n]
    }

    private fun saveToTabulationArray(n: Int, fibonacciValue: Int, tabulationArray: Array<Int>) {
        tabulationArray[n] = fibonacciValue
    }
}

fun main() {
    val fibonaccis = listOf(BasicFibonacci(), MemoizationFibonacci(), TabulationFibonacci())
    for (f in fibonaccis){
        assertEquals(f.find(5), 5)
        assertEquals(f.find(10), 55)
        assertEquals(f.find(11), 89)
    }
}