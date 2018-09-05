import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.shoppingcart.Controller.ProductsController;


public class ClassToTry {

	public static void main(String[] args) throws UnsupportedEncodingException {

		new ClassToTry().getpath();
	}
	
	public void getpath() throws UnsupportedEncodingException {
		/*WebApplicationContext context = (WebApplicationContext)getContext();
		File webinfFolder = new File ( context.getHttpSession().getServletContext().getRealPath("/WEB-INF") );
		*/
		String classPath = ClassToTry.class.getResource("").getPath();
		System.out.println("pppppppp: "+ classPath);
		System.out.println("Split path: "+classPath.split("target")[0]);
		
		System.out.println(" User Home: "+ System.getProperty("user.home"));
	}

}