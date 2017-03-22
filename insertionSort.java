package csi403;


// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.json.*;


// Extend HttpServlet class
public class insertionSort extends HttpServlet {
	

  // Standard servlet method 
  public void init() throws ServletException
  {
      // Do any required initialization here - likely none
  }

  // Standard servlet method - we will handle a POST operation
  public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      doService(request, response); 
  }

  // Standard servlet method - we will not respond to GET
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      // Set response content type and return an error message
      response.setContentType("application/json");
      PrintWriter out = response.getWriter();
      out.println("{ 'message' : 'Use POST!'}");
  }


  // Our main worker method
  // Parses messages e.g. {"inList" : [5, 32, 3, 12]}
  // Returns the list sorted.   
  private void doService(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
	  try{
      // Get received JSON data from HTTP request
      BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
      String jsonStr = "";
      if(br != null){
          jsonStr = br.readLine();
      }
	  
      // Create JsonReader object
      StringReader strReader = new StringReader(jsonStr);
      JsonReader reader = Json.createReader(strReader);

      // Get the singular JSON object (name:value pair) in this message.    
      JsonObject obj = reader.readObject();
      // From the object get the array named "inList"
      JsonArray inArray = obj.getJsonArray("inList");

      // Sort the data in the list
      JsonArrayBuilder outArrayBuilder = Json.createArrayBuilder();
		  
	  //create array of type int 
	  int[] numbers = new int[inArray.size()];
	  
	  int num = 0;
	  
	  //store Json values into integer array
	  for(int i = 0; i < inArray.size(); i++)
	  {
		numbers[i] = inArray.getInt(i);	 
	  }
	  
	  //store current time in milliseconds
	  long start_time = System.currentTimeMillis();
	  
	  int temp;
	  
	  //insertion sort algorithm
	  for(int i = 1; i < numbers.length; i++)
	  {
		  for(int j = i; j > 0; j--)
		  {
			  //if the consecutive value is smaller than the previous, switch the values
			  if(numbers[j] < numbers[j-1])
			  {
				  temp = numbers[j];
				  numbers[j] = numbers[j-1];
				  numbers[j-1] = temp;
			  }
		  }
	  }
	  
	  long end_time = System.currentTimeMillis();
	  
	  //calculate algorithms runtime in milliseconds
	  long total_time = end_time - start_time;
	  
	  for(int i = 0; i < numbers.length; i++) 
	  {
          outArrayBuilder.add(numbers[i]); 
      }
      
      // Set response content type to be JSON
      response.setContentType("application/json");
      // Send back the response JSON message
      PrintWriter out = response.getWriter();
      out.println("{ \"outList\" : " + outArrayBuilder.build().toString() + "," + "\n" + "  \"algorithm\" : " + "\"insertion sort\"," + "\n" + "  \"timeMS\" : " + total_time + "}"); 
	  }
	  catch(JsonException e){
		  response.setContentType("application/json");
		  PrintWriter out = response.getWriter();
		  out.println("{ \"message\" : \"Malformed JSON!\"}");
	  }
	  catch(IllegalStateException e){
		  response.setContentType("application/json");
		  PrintWriter out = response.getWriter();
		  out.println("{ \"message\" : \"Malformed JSON!\"}");
	  }
	  catch(Exception e){
		  response.setContentType("application/json");
		  PrintWriter out = response.getWriter();
		  out.println("{ \"message\" : \"Malformed JSON!\"}");
	  }
	  
  }
	
  // Standard Servlet method
  public void destroy()
  {
      // Do any required tear-down here, likely nothing.
  }
}

