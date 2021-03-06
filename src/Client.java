import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.*
;public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {	
		
		
		
		Scanner input =new Scanner(System.in);
		
		try {
			
			//create socket to connect to server
		Socket s = new Socket("localhost",8080);
		
		//writes objects
		ObjectOutputStream oos=new ObjectOutputStream (s.getOutputStream());
		
		//read strings
		InputStreamReader ins= new InputStreamReader(s.getInputStream());
		BufferedReader br = new BufferedReader(ins);
		
		//write strings
		OutputStreamWriter outs = new OutputStreamWriter(s.getOutputStream());
		BufferedWriter bw = new BufferedWriter(outs);
		
		
		
		
	//read the list of services that the server sent
		String list="";
	   	list=br.readLine();
		
	
		
       
	 // this outer do while is to force the client to
		//choose either display or exit
	      String option="";
	do {
		
	 System.out.println("for services => display , to exit => exit.");
	 option=input.next();
	
	
	 if(option.equalsIgnoreCase("display")) {
           
		
	   //inner do while takes client orders
			 String op="";
	          do {
	             
	        	  //print the list of services
	        	  System.out.println(list);
	              String name="";
	        	  int quant=0;
	        	  
	        	  
	        	  
	        	  
	              System.out.println("enter name of item you want: ");
	            //InputMismatchException handling using the general exception because if the client enters a number or symbols 
	             // we must warn him to renter the name 
	              while(true){
	  				
	  				try{name = input.next();
	  				break;
	  				}
	  				catch(Exception e){System.out.println("invalid... reEnter:");
	  				input.nextLine(); 
	  				}
	  				
	  				  }
	
	              
	              
	      
	              
	               System.out.println("enter its quantity: ");
	             //InputMismatchException handling using the general exception because if the client enters a character or symbols 
		             // we must warn him to renter the quantity 
	               while(true){
		  				
		  				try{quant=input.nextInt();
		  				break;
		  				}
		  				catch(Exception e){System.out.println("invalid... reEnter:");
		  				input.nextLine(); 
		  				}
		  				
		  				  }
	        
	               
	             //save the client order in object to make it ready for storing in the arraylist
	              item ord=new item(name,quant);

	              //send the order to the server
	               oos.writeObject(ord);
	               oos.flush();
	           
	               // string store the response from the server and print it
	               String re =" ";
	               re=br.readLine();
	               System.out.println(re);
	               
	 
	               System.out.println("another order ? yes, else; no");
	           
	               
	             //InputMismatchException handling using the general exception because if the client enters a wrong word other the yes,no
	               // we must warn to him rewrite his option again
	               while(true){
	   				
	   				try{op = input.next();
	   				if(op.equalsIgnoreCase("yes") ||
	   						op.equalsIgnoreCase("no")){
	   					break;
	   				}
	   				
	   				throw new Exception();
	   				}
	   				catch(Exception e){System.out.println("invalid... reEnter(yes or no):");
	   				input.nextLine(); 
	   				}
	   				
	   				  }
	               
	               
	               //sends the value of op to server, either no or yes
	               //if yes this loop and also the corresponding  
	               //loop on the server side both will continue
	               //if no then both will terminate  
	               
	               bw.write(op);
	   	           bw.newLine();
	   	           bw.flush();
	   	           
	   	      
	   		
	        
               }while(!op.equalsIgnoreCase("no"));
	             //end of inner loop
	   }  // end the if statement for display
	         
	          
	          //the rest of code of the outer loop
	      
	
		
		else if(option.equalsIgnoreCase("exit")) {
			System.exit(0);
		}
	 //logical error handling using else option if the client doesn't enters display nor exit
		else {option="repeat";}
		
    }while(option.equals("repeat"));
	
	// Receiving a bye message when the connection is terminated
	 String e =" ";
     e=br.readLine();
     System.out.println(e);
		       
		}//end try
		
	
	
catch(IOException e) {
			e.printStackTrace();
		}

		
	}

}
