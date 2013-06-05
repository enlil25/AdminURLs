import java.util.Properties;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;

public class Config
{ 
	
	//representa el path de ubicacion de properties
	private static String path;
	
	//indica si el archivo properties fue cargado o no
	private static boolean properties_load=false;

    //representa el valor dentro del archivo properties obtenido
    private static String path_binario=null;
	
	static{
		try
		{	
		path=Config.class.getResource("config.properties").
			toURI().getPath();
		}catch(Exception ex){ex.printStackTrace();}	
	}
	
	public static String getPath()
	{
		FileInputStream fis=null;
		File fi=null;
	    Properties prop=null;
		try
		{  
			if(!properties_load)
			{	
			fi=new File(path);
			fis=new FileInputStream(fi);
			prop=new Properties();
			prop.load(fis);
			path_binario=prop.getProperty("path_binario");
			properties_load=true;
			return path_binario;
			}
			return path_binario;
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			return null;
		}
		finally
		{
			try
			{
				if(fis!=null) fis.close();
			}
			catch(IOException ex)
			{
				System.err.println(ex.getMessage());
				return null;
			}		
		}			
		
	}	
	
	public static void main (String[] args) {
   
   }
}