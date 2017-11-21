[![Build Status](https://travis-ci.org/cmasson42/balanced-parentheses.svg)](https://travis-ci.org/cmasson42/balanced-parentheses)

# The project
This project aims at answering to the following question: "Count the number of expressions containing n pairs of parentheses which are correctly matched".

It computes the following recursive formula, using the stream API and dynamic programming.  
![](https://latex.codecogs.com/gif.latex?S%28n&plus;1%29%20%3D%20%5Csum_%7Bi%3D0%7D%5E%7Bn%7D%20S%28i%29.S%28n-i%29)

It also includes a jmh module that can be used to benchmark the solution.

For more details about the problem, see the following pages:  
https://en.wikipedia.org/wiki/Catalan_number  
http://www.geeksforgeeks.org/program-nth-catalan-number/
