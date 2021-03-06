import java.util.*;
import java.io.*;
import java.net.*;
	public class Server{



public static void main(String[] args) throws IOException, ClassNotFoundException {
		
	
	Scanner input = new Scanner(System.in);
	
	//Data structure to save the list of services filled by administrator  
	ArrayList <item> items = new ArrayList<item>();
	
		String name ="";
		double price =0.0;
		int quant =0;
		
		String option="" ;
		
		//server administrator enters the products info before 
		//launching the server
		
		do {
			
			
			System.out.println("enter name: ");
			//InputMismatchException handling using the general exception because if the administrator enters a number or symbols 
            // we must warn him to renter the name 
			while(true){
				
				try{name = input.next();
				break;
				}
				catch(Exception e){System.out.println("invalid... reEnter:");
				input.nextLine(); 
				}
				
				  }
			
/////////////////////////////////////////////////////
			
			
			
			
			System.out.println("enter price: ");
			//InputMismatchException handling using the general exception because if the administrator enters a character or symbols 
            // we must warn him to renter the price
			while(true){
			
			try{price = input.nextDouble();
			break;
			}
			catch(Exception e){System.out.println("invalid... reEnter:");
			input.nextLine(); 
			}
			
			  }
			
/////////////////////////////////////////////////
			
			
			System.out.println("enter quantity: ");
			//InputMismatchException handling using the general exception because if the administrator enters a character or symbols 
            // we must warn him to renter the quantity 
			while(true){
				
				try{quant = input.nextInt();
				break;
				}
				catch(Exception e){System.out.println("invalid... reEnter:");
				input.nextLine(); 
				} }
			
     ///////////////////////////////////////////////////////           
                
                
			// put the information of the item that the administrator enters in the server in an object to save it in the arraylist data structure
			item i = new item(name,price,quant);
			items.add(i);
			
			
			
	////////////////////////////////////////////////////////		
			System.out.println(" if done => exit, else => add");
			//InputMismatchException handling using the general exception because if the administrator enters a wrong word other the add,exit
            // we must warn him to rewrite his option again
			while(true){
				
				try{option = input.next();
				
				
				//logical error handling  if the client enters a wrong option we throw the exception 
				if(option.equalsIgnoreCase("add") ||option.equalsIgnoreCase("exit"))
			    break;
				throw new Exception();
				}//end try
				
				catch(Exception e){System.out.println("invalid... reEnter (add or exit):");
				input.nextLine(); 
				}
				
				  }
			
			
			
		
			
		}	while(!option.equalsIgnoreCase("exit")); 
		
		
		
		
		//create the server
		ServerSocket ss=new ServerSocket(8080);  
		System.out.println("Server waiting for conection..");
		Socket s = ss.accept();  //waits for client request 
		System.out.println("Conection established");
		
		//read objects
		ObjectInputStream ois=new   ObjectInputStream (s.getInputStream());
		
		//read strings
		InputStreamReader ins = new InputStreamReader(s.getInputStream()); 
		BufferedReader br = new BufferedReader(ins);
		
		//writes strings
		OutputStreamWriter outs = new OutputStreamWriter(s.getOutputStream()); // write to client
		BufferedWriter bw = new BufferedWriter(outs);
		
			
		
		  //put all products in a string to send it
	     //to client
		String result = "";
		for(int x = 0 ; x <items.size() ; x++) {
			
			result = result  + items.get(x).getName()+": "+ items.get(x).getPrice()+"$"+"  ";
				
		}
		
		
		//now send the list of services to client	
		bw.write(result);
		bw.newLine();
		bw.flush();
		
		
		
		//Data structure to save the list of client?s orders to be compared with the item list to implement the processing easily 
		ArrayList<item> orders=new ArrayList<item>();
		String add="";	
			
			while(!add.equalsIgnoreCase("no")) {
				
				//read the order
				item ord=(item)ois.readObject();
				//store it
					orders.add(ord);
					
		// declare two Strings to send the processing response to the client	
		 String d =" "; 
		  String r=" ";
		  // boolean flag to handle the error that might occur
		 boolean ItemNameExitFlag=false;
		  
		int j=0;// second loop counter
		int newQuantity=0; // stores the new quantity after the client's order (reducing the inventory)
	
		for(int i=0;i<orders.size();i++) {// loop for the client's requests array 
			for(j=0;j<items.size();j++) { // loop for the server's original array (the promoted items)
				
		// comparing the name of the item that the client order from the server and see if it's has a match the original array (the promoted items)
				// and also checks if client order has an equal or less quantity than the quantity in the server array (so the server can provide)
				
		 if(orders.get(i).getName().equalsIgnoreCase(items.get(j).getName())&&orders.get(i).getQuantity()<= items.get(j).getQuantity()) {
			 
			 ItemNameExitFlag=true;
				 //store the total cost in string to send it to the client
		 d= "Quantity "+orders.get(i).getQuantity()+" of item "+orders.get(i).getName()+" is available." + 
				 "Total cost= "+items.get(j).getPrice()*orders.get(i).getQuantity() + " SR";
		 
					 //send the total cost to the client 
		               bw.write(d);
		            	bw.newLine();
						bw.flush();
						
					// reducing the quantity from the inventory after serving the client
				 newQuantity= items.get(j).getQuantity()-orders.get(i).getQuantity();
				 items.get(j).setQuantity(newQuantity);
		 }
		 //checks if the client enters an item that the server has but with a large amount of quantity so the server can't provide
		 else if(orders.get(i).getName().equalsIgnoreCase(items.get(j).getName())&&orders.get(i).getQuantity()> items.get(j).getQuantity()) {
			 ItemNameExitFlag=true;
			//store the response in string to send it to the client
						r= "we don't have enough quantity ";
	
						 //send the response to the client
						bw.write(r);
						bw.newLine();
						bw.flush(); 
				
			}
 
		 }//end inner for
			//logical error handling using a boolean flag if the client enters item that the server doesn't have
			if( !ItemNameExitFlag){
				//send message to inform that the item doesn't exit
				bw.write(orders.get(i).getName() +": invalid item name");
				bw.newLine();
				bw.flush(); 
		
			}
			
			
			}//end outer for
		
				
	// after the server provides what the client request it removes his request from the array 
		orders.clear();
		
		//check if user entered "no" to stop 
		//taking his/her orders AKA terminating 
		//the loop .
		//else this loop will continue 

			 add=br.readLine();
			 
		}//end do while
			// sending a bye message when the connection is terminated
			 String n= "Bye";
			   bw.write(n);
				bw.newLine();
				bw.flush();
	}
}