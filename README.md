Max-Fibonacciheap
Problem Statement:

A new search engine “DuckDuckGo” is implementing a system to count the most popular keywords used in their search engine. They want to know what the 𝑛 most popular keywords are at any given time. You are required to undertake that implementation. Keywords will be given from an input file together with their frequencies. You need to use a max priority structure to find the most popular keywords. 
You must use the following data structures for the implementation.  Max Fibonacci heap: to keep track of the frequencies of keywords  Hash table: keywords should be used as keys for the hash table and value is the pointer to the corresponding node in the Fibonacci heap. 
 
You will need to perform the increase key operation many times as keywords appear in the input keywords stream. You are only required to implement the Fibonacci heap operations that are required to accomplish this task. 
 
