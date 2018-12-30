import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.io.*;
import java.util.regex.*;



public class keywordcounter
{


    public static void main(String[] args)
    {
    	
	    	long start_time = System.currentTimeMillis();
	    	System.out.println(" Implementing a system to count the most popular keywords used in the search engine DuckDuckGo");
	    	int flag=0;
	    	
	    	String filePath = args[0];//path of the input file
	    	
	    	String line_input; 
	    	File output_file =null;
	    	BufferedWriter buff_writer =null;
	        BufferedReader buff_reader = null;
	        FileWriter file_writer = null;
	        FileInputStream input_file =null;
	        FibHeap heap_fib = new FibHeap(); //Create an object of the fibonacci Heap
	        HashMap<String,FibHeap.FibNode> hashmap_fib = new HashMap();//Hash Map for Storing the keyword and its frequency
	         //HASHMAP<STRING KEY, INTEGER HASH> NAME= NEW HASHMAP , input keyword is key and hashvalue is integer hash
         
         //Pattern.compile () is used to match the regular expression against input text. 
         Pattern pattern_1 = Pattern.compile("(\\d+)");//query
         Pattern pattern_2 = Pattern.compile("^([$])([^ ]+)(\\s)(\\d+)");//input keyword
         Pattern pattern_3 = Pattern.compile("^stop$");//stop
         
        
      try {
    	   //input_file = new FileInputStream("C:\\Users\\DELL\\Desktop\\OM ads project\\OMtest\\input_1000000.txt");
    	  
    	   input_file = new FileInputStream(filePath);
            buff_reader = new BufferedReader(new InputStreamReader(input_file));
            
           //output_file = new File("C:\\Users\\DELL\\Desktop\\OM ads project\\OMtest\\output_1000000.txt");
            
            output_file = new File("output_file.txt");
            file_writer=new FileWriter(output_file);
            buff_writer = new BufferedWriter(file_writer);
           
           

        	
        while ((line_input= buff_reader.readLine()) != null) //until lines in inputfile exists
        {
        	
           //The Matcher instance is used to find matches of the pattern in the input text
        	// creating matcher object 
            Matcher match_1 = pattern_1.matcher(line_input);// for query
            Matcher match_2 = pattern_2.matcher(line_input);//for input keyword
            Matcher match_3 = pattern_3.matcher(line_input);//for stop
            
            
            
            if (match_3.matches()) //if the input is stop , then break
            {
                break;
            } 
            
            else if (match_2.find()) //if input is keyword
            {
            	//Separate keyword and its frequency
                String keyword = match_2.group(2);//the keyword
                long hashvalue = Long.parseLong(match_2.group(4));//frequency of keyword

                if ( hashmap_fib.containsKey(keyword))//if the keyword is already present in the hashtable then call increase key in fibonacci heap
                {
                     long val=hashmap_fib.get(keyword).hashvalue;
                	 long increasevalue = val + hashvalue;
                     heap_fib.increasevalue(hashmap_fib.get(keyword),increasevalue);

                }
                else//add this keyword
                {

                	FibHeap.FibNode node = new FibHeap.FibNode(keyword,hashvalue);
                	heap_fib.insertnode(node);
                    hashmap_fib.put(keyword,node);  
                   
                }
                
            }
            else if (match_1.find())//if input is query
            {

                int query_num = Integer.valueOf(match_1.group(1));

                ArrayList<FibHeap.FibNode> removedNodes = new ArrayList<FibHeap.FibNode>(query_num);//all the removed nodes in put in array
                

                for ( int i=0;i<query_num;i++)//remove "n" (query number of nodes)
                {
                	if(flag==0) 
                	{
                		if(!hashmap_fib.isEmpty())//if hashmap is not empty then remove
                		{
		                	FibHeap.FibNode node = heap_fib.maxnode_remove();
		                	hashmap_fib.remove(node.getKeyword());
		                    FibHeap.FibNode newNode= new FibHeap.FibNode(node.getKeyword(),node.hashvalue);//same word and its hash value or frequency
		                    removedNodes.add(newNode);
                  
	                    if ( i ==query_num-1) //if it is the last word to be written in output dont put ","
	                    	{
	                    		buff_writer.write(node.getKeyword());
                    		}
	                    else 
	                    	{
	                    		buff_writer.write(node.getKeyword() + ",");//for all others put ","
	                    	}
                    
                		}
                		else//if hashmap is empty
                		{
	                	   System.out.println(" Query number is greater than input available. Hence printing available input in descending order of hashvalues ");
	                	   flag=1;
	                	   break;
                		}
                  
                	}
              }
                
                for ( FibHeap.FibNode iterate : removedNodes)//add all the removed nodes back into hash table
                {
                	heap_fib.insertnode(iterate);
                    hashmap_fib.put(iterate.getKeyword(),iterate);
                }
                
                buff_writer.newLine();
                
            }  
           
        }
        buff_writer.close();
        buff_reader.close();
    }//try 
      catch (IOException e) 
      	{
    	  System.out.println("IOException encountered\n\n" + e.toString());
        }
      
	      long end_time   = System.currentTimeMillis();//to keep track of time
	      long total_time = end_time - start_time;
	      System.out.println(" Time to run in milli seconds: "+ total_time);
    
    	}

    }
