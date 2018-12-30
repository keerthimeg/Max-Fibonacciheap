import java.util.ArrayList;
import java.util.List;



public class FibHeap 
{
	private static final double matvariable=
            1.0 / Math.log((1.0 + Math.sqrt(5.0)) / 2.0);
	    //Node class to create FibNode object
	    static  class FibNode 
	    {
	   	  FibNode parent, child, left, right;// parent , child and left+right siblings of type FibNode
	   	  int degree=0;//number of children to node
	      boolean childcut_value=false;//initial childcut value for all nodes is set to false
	   	  private String keyword;//the input keyword 
	      Long hashvalue;//frequency of input keyword
	      
	        FibNode(String keyword,Long hashvalue)//Constructor which initializes the keyword and its hashvalue
	        {
	       	 this.parent=null;//set parent of node (i.e. input keyword) to null
	       	 this.child=null; //set child of node to null
	       	 this.left=this;//set its left sibling
	       	 this.right=this;//set its right sibling
	       	 this.degree=0;//set number of children as 0
	       	 this.keyword=keyword;//assign the keyword
	       	 this.hashvalue=hashvalue;//assign the hashvalue
	       	 
	        }
	    
	         public  String  getKeyword()//Returns the keyword of the node
	         {
	   	            return this.keyword;
	   	     }
	         public  long  gethashvalue()//Returns the hashvalue associated with the keyword 
	         {
	   	            return this.hashvalue;
	   	     }
	         
	         public FibNode getParent() //Returns the parent of the node or keyword 
	         {
	             return parent;
	         }

	         public void setParent(FibNode parent) //Assign the parent to the keyword 
	         {
	             this.parent = parent;
	         }

	         public FibNode getLeft()//Returns the left sibling of the node 
	         {
	             return left;
	         }

	         public void setLeft(FibNode left) //Assign the left sibling to the node
	         {
	             this.left = left;
	         }

	         public FibNode getRight() //Returns the right sibling of the node
	         {
	             return right;
	         }

	         public void setRight(FibNode right) //Assign the right sibling to the node
	         {
	             this.right = right;
	         }

	         public FibNode getChild()//Returns the child of the node
	         {
	             return child;
	         }

	         public void setChild(FibNode child) //Assign the child to the node
	         {
	             this.child = child;
	         }

	        

	         public boolean isChildCut() //Returns the child cut value of the node
	         {
	             return childcut_value;
	         }

	         public void setChildCut(boolean childCut) //Returns the child cut value of the node
	         {
	             this.childcut_value = childCut;
	         }

	            
	         
	   }
	    
	    private FibNode maxFibnode;//indicate the maximum hashvalue node
	    private int total_nodes;//total number of nodes
	   
	    public int isEmpty(FibNode node) //This function checks whether a Node of type FibNode exists or not. 
	    {
	    	int flag=0;
			if(node != null)//if node is not null
				{	
				    flag++;//increment the flag
					return flag;//flag = 1 is node is not null
				}
			else
				return flag;
		}
	    
	    private void sibling_link(FibNode m, FibNode n)//Insert node n between node m and node m.right
	    {
	    	    n.left = m;// left sibling of n is m
	            n.right = m.right;// assign m's right sibling to n's right
	            m.right = n;//n becomes m's right sibling
	            if (isEmpty(n.right)==0)//check if n's right is null or not
	            {
	            	//if null
	                n.right = m;//assign n's right sibling as m
	                m.left = n;//assign n as m's left
	            // to maintain properties of circular doubly linked list
	            }
	            else 
	            {                    
	            	//if n's right sibling exist
	                n.right.left = n;// assign n as left sibling of n's right 
	            }
	    }
	    
	    
	    private void left_right_merge(FibNode node) //This function is used to remove the input node by connecting its siblings together
	    {
	    	node.left.right = node.right;//assign node's right to right of its left sibling
	    	node.right.left = node.left;// assign node's left to left of its right sibling
	    }
	    
	    
	    private FibNode compare_hash(FibNode node,FibNode maxnode)
	    //This function is used to compare the hashvalue (frequencies) of two nodes: node and maxnode
	    {
	    	if (node.hashvalue > maxnode.hashvalue) //if hashvalue of node is greater than the current 
	    	{
	            maxnode = node;//make node as max
	        }
	    	return maxnode;//node with highest frequency
	    }
	    
	    
	    public void increasevalue(FibNode node, long value)//This function is used to increase the hashvalue ) of keyword (FibNode node) 
	    
	    {
	        node.hashvalue = value;// assign the value to node.hashvalue

	        FibNode node_parent = node.parent;//check with the parent's hashvalue
	     	if((isEmpty(node_parent)!=0) && (node.hashvalue > node_parent.hashvalue)) //if parent exists and child has higher hashvalue perform cascading cut upto root based on childcut value
	     	{
	        	Nodecut(node, node_parent);//This function  cuts the node  from node_parent, and decreases the degree of node node_parent
	            Node_CascadingCut(node_parent);//This function is used to perform cascading cut based on the childcut _value
	        }

	     	maxFibnode=compare_hash(node,maxFibnode);//This function is used to compare the hashvalue (frequencies) of two nodes: node and maxnode 
	    }
	    
	    public void insertnode(FibNode newnode)//This function is used to insert a new node of type FibNode into Fibonacci heap. 
	    {
	        //First check if the maxFibnode is null(Empty heap).If the maxFibnode is null make this new node to be inserted as max . 
	    	if(isEmpty(maxFibnode)==0)
	    	    {
	    			maxFibnode=newnode;
	    	    }
	    	else
	    		{
               //If the max exists then link this new node as right sibling to maxFibnode using sibling_link function
	    		sibling_link(maxFibnode,newnode);
	    		//System.out.println("before "+maxFibnode.keyword);
	    		maxFibnode=compare_hash(newnode,maxFibnode);//After insertion check the hashvalue of both newly inserted node and maxFibnode and make the highest hashvalue one as max.
	    		
	    		//System.out.println("after "+maxFibnode.keyword);
	    		
	    		}
	        

	    	total_nodes++;//After the insertion the total numbers of nodes are increased by 1
	    }
	    
	    public FibNode maxnode_remove()//This function is used to remove the maxFibnode.  
	    {
	    	FibNode node = maxFibnode;
	    	
	    	if(isEmpty(node)!=0)//if maxFibnode exists
	         {
	    		FibNode temp_child;
	    	    FibNode node_child = node.child;//child of max
	            int num_of_children = node.degree;//degree of max
	     
	            while (num_of_children > 0) //The children are removed one by one from the maxnode and are added to the root list of heap.
	            {
	            	temp_child = node_child.right;//the right sibling of node  

	                left_right_merge(node_child);// //This function is used to connect  siblings of node_child together before adding it to the root list of heap
		        
	                sibling_link(maxFibnode,node_child);//Insert node_child between node max and node right of max
	               
	                node_child.parent = null;//parent of node_child is set to null because it is added to the root list of heap
	                node_child = temp_child;//
	                
	                num_of_children--;//decrease number of children

	            }

	            left_right_merge(node);// remove node from root list of heap
	            
	            if (node != node.right)
	            {
	            	maxFibnode = node.right;
		               Merge_Pairwise();//after removing max node we call function degreewiseMerge() on remaining nodes (max siblings) 

	            } 
	            else
	            {
	            	maxFibnode = null;
	            }
	           
	           total_nodes--;
	           return node;
	       }
	        return null;
	    }	    

	    public void Nodecut(FibNode p, FibNode gp)
	    {
	        //This function  cuts the node p from gp, and decreases the degree of node gp
	        
	    	left_right_merge(p);//This function is used to remove the input node by connecting its siblings together
	        gp.degree--;//gp lost 1 child p , decrease degree of gp

	        if (gp.degree == 0) //if it is a leaf
	        { 
	            gp.child = null;
	        }
	        if (gp.child == p) //if p was pointed to as gp.child change it
	        {
	            gp.child = p.right;//make right sibling of p as gp.child due to removal of node p
	        }

	        
	    	sibling_link(maxFibnode,p);//insert p as right sibling of maxFibnode
	    	p.childcut_value = false;//since p is just added to root list of heap its childcut value is set to false
	        p.parent = null;//parent of p is set to null

	       
	    } 

	    
	    public void Node_CascadingCut(FibNode newnode)//This function is used to perform cascading cut based on the childcut _value. 
	    {
	    	FibNode node_parent = newnode.parent;

	        //if there is a parent
	       // if (node_parent != null)
	    	
	        	if(isEmpty(node_parent)!=0)
	        		//If the newnode has parent then perform cascading cut based on childcut value up to the root.
	        		
	       	        {
	                /* If childcut value of newnode is true ( it has previously lost a child)
	        		 then perform Nodecut() on it and perform Cascading cut on its parent
	        		 */
	        		
	        		if (newnode.childcut_value)
	        			{
	        			 Nodecut(newnode, node_parent);

	 	                // cut its parent as well
	 	                Node_CascadingCut(node_parent);
	        			}
	        		else
	        		
	        			 // If childcut value of newnode is false (it did not lose child before this) set its childcut_value to true.
	        			 
	        			{
	        			newnode.childcut_value = true;
	        			}
	        		
	       	        }
	    }
	    
	    
	    //This function is used to make node newchild  as child of the node newparent. This function is useful in Merge_Pairwise()
	    public void child_create(FibNode newchild, FibNode newparent)
	    {
	        
	    	left_right_merge(newchild);//This function is used to remove the input node by connecting its siblings together
	    	
	    	newchild.parent = newparent;// make newchild a child of node newparent

	        if (newparent.child != null) 
	        {
	        	sibling_link(newparent.child,newchild);//link this node with other children of its parent
	            
	        } 
	        else //if parent of newchild is null 
	        {
	        	
	        	newparent.child = newchild;
	        	newchild.left = newchild;
	            newchild.right = newchild;
	        	
	        }

	        
	        newparent.degree++;//due to insertion of a node increase degree of parent by 1

	        newchild.childcut_value = false;//assign false to childcut value of newly added child 
	    }
	    
	    public void Merge_Pairwise()//This function performs degree wise merge after removing maxFibnode
	    {
	    	int num_of_root = 0;// initial number of roots 0
	        //int table_degree_size =50;
	        int table_degree_size =
	        		((int) Math.floor(Math.log(total_nodes) * matvariable)) + 1;// table to store degrees , formula given in Cormen
	        FibNode node = maxFibnode;
	        
	        List<FibNode> table_degree =new ArrayList<FibNode>(table_degree_size);//to store the removed nodes
	        
	       
	        for (int i = 0; i < table_degree_size; i++)
	        {
	        	 table_degree.add(null);// table where degrees are stored is initialized
	        }
	       
	        if (node != null) //count all the nodes until no node exists
	        {
	        	
	            node = node.right;         
	            num_of_root++;

	            while (node != maxFibnode) 
	            {
	            	num_of_root++;
	                node = node.right;
	            }
	        }
	        
	        

	        while (num_of_root > 0) 
	        {// execute for all nodes in root list 

	        	FibNode next = node.right;
	            int d = node.degree;
	           
	            for (;;) 
	            {
	            	FibNode table_node =  table_degree.get(d);//use method to get value of degree
	                if (table_node == null)
	                {
	                    break;//if no nodes left in table
	                }

	               
	                if (table_node.hashvalue>node.hashvalue)
	                	//Compare the hashvalue and make one of the nodes a child of the other. For this make table_node as parent and node as child after swapping(if swapping is needed)
	                 
	                {
	                	FibNode temp = table_node;
	                	table_node = node;
	                    node = temp;
	                }
	                	/*
	                	
	                	System.out.println("before tablenode"+table_node.keyword);
	                	System.out.println("before node"+node.keyword);
	                	
	                	System.out.println("after tablenode"+table_node.keyword);
	                	System.out.println("after node"+node.keyword);
	                	*/
	                
	                child_create(table_node, node);//make table_node the child of node as node.hashvalue is greater
	                table_degree.set(d, null);//degree is set to null after combining
	                d++;
	            }

	            
	            table_degree.set(d, node);//store the new degree in the respective postion
	            num_of_root--;//go through the remaining list
	            node = next;
	           
	        }

	        maxFibnode = null;

	        for (int i = 0; i < table_degree_size; i++)// combine nodes of same degree 
	        {
	            FibNode node_nxt = table_degree.get(i);
	            if (node_nxt == null) //if node doesnt exist
	            {
	                continue;
	            }

	           
	            if (isEmpty(maxFibnode)==0)//if maxFibnode is null
	            {
	            	maxFibnode = node_nxt;
	            }
	            else
	            	//if maxFibnode is not null
	            {
	            	left_right_merge(node_nxt);//remove node_nxt from root list.
	            	sibling_link(maxFibnode,node_nxt);//add node_nxt root list
	            	maxFibnode=compare_hash(node_nxt,maxFibnode);//Check if node_nxt is new maximum
	               /*
	                if (node_nxt.hashvalue > maxFibnode.hashvalue) 
	                {
	                	maxFibnode = node_nxt;
	                }
	                
	                */   
	        	    
	            }
	            
	        }
	    }
	    
}
