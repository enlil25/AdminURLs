import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.util.List;
import java.util.ArrayList;
public class LecturaEscritura
{
	private OperacionesArchivo operationFile;
	private String path;
	private String estado;
		
	public LecturaEscritura(OperacionesArchivo operationFile)
	{
		this.operationFile=operationFile;
		//cargar el path del archivo properties y lo obtiene
		this.path=Config.getPath();
		//representa el estado de la lectura o escritura
		this.estado="";
		loadRegisters();
	}
	
	private void loadRegisters()
	{
		List<Url> listUrl=null;
		listUrl=operationFile.getListUrl();
		
		ObjectInputStream ois=null;
		FileInputStream fis=null;
		File fi=null;
		Object readed=null;
		try
		{
		   fi=new File(this.path);
		   fis=new FileInputStream(fi);
		   ois=new ObjectInputStream(fis);
		   while( (readed=ois.readObject())!=null )
		   {
		   	    listUrl.add( (Url)readed );
		   }	   	
		}
		catch(ClassNotFoundException ex){
			 ex.printStackTrace(); return;
			}
		catch(EOFException ex){ System.err.println(ex.getMessage()); return;}
		catch(IOException ex){ex.printStackTrace(); return;}
		finally
		{
			try
			{
				if(fis!=null) fis.close();
				if(ois!=null) ois.close();
				
			}catch(IOException ex)
			{  ex.printStackTrace();
				return;
			}		
		}
	}	
		
	public void writeFile(Url reg)
	{
		ObjectOutputStream ous=null;
		FileOutputStream fos=null;
		File fi=null;
		MiObjectOutputStream mos=null;
		
		try
		{
		    fi=new File(this.path);
		    fos=new FileOutputStream(fi,true);
		    //si es un archivo recienCreado con 0 bytes			   
		    if(fi.length()==0)
		    { 
		    	ous=new ObjectOutputStream(fos);
		    	ous.writeObject(reg);
		    	estado="ok";
		    }
		    //si el archivo pesa mas de 0 bytes	       
		    else
		    { 
		    	mos=new MiObjectOutputStream(fos);
		    	mos.writeObject(reg);
		        estado="ok";	
		    }	
		    		   		  		
		}
		catch(IOException ex)
		{
			System.err.printf(ex.getMessage());
			estado="error";
			return;
		}
		//cierre de flujos
		finally
		{
			try
			{
				if(fos!=null) fos.close();
				if(ous!=null) ous.close();
				if(mos!=null) mos.close();
				
			}catch(IOException ex)
		    { System.err.println(ex.getMessage());
		      estado="error";
		      return;
		    }	
		}			
	
	}
	
	//escribe todos los registros que hayan en la lista
	//que contiene operationFile
	public void writeFile()
	{
	   int numreg=-1;
	   //se obtiene el numero de registros
	   numreg=operationFile.getListUrl().size();		
	  //se recorre cada registro para poder escribirlos
	  for(int i=0; i<numreg; i++)
	   writeFile(operationFile.getListUrl().get(i));
		 		   		  		
	}
	
	public void CleanFile()
	{
	    File fi=null;	  
		File newfile=null;
		try
		{
			fi=new File(this.path);			
			fi.delete();
			newfile=new File(this.path);
			newfile.createNewFile();
			//operationFile.getListUrl().clear();
			estado="ok";
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			estado="error";
			return;
		}		
	}
	
	//guarda archivo en pahtdestino
	public void saveFile(String FolderDestination,String fileOrigen)
	{
		File origen=null;
		File destination=null;
		FileInputStream fis=null;
		FileOutputStream fos=null;
		byte content[]=new byte[512];
		int readed=0;
		try
		{
			origen=new File(fileOrigen);
			destination=new File(FolderDestination);
			fis=new FileInputStream(origen);
			fos=new FileOutputStream(destination+File.separator+
				origen.getName());
			while( (readed=fis.read(content))!=-1)
				fos.write(content,0,readed);
			estado="ok";
		}	
		catch(IOException ex)
		{
			System.err.println(ex.getMessage());
			estado="error";
			return;
		}
		finally
		{
		  	try
			{
				if(fis!=null) fis.close();
				if(fos!=null) fos.close();
				
			}catch(IOException ex)
			{  ex.printStackTrace();
				estado="error";
				return;
			}		
		}		
	}
	
	public List<Url> readFile(String fileOrigen)
	{
		List <Url>listurl=null;
		ObjectInputStream ois=null;
		FileInputStream fis=null;
		File fi=null;
		Object readed=null;
		try
		{
		   fi=new File(fileOrigen);
		   fis=new FileInputStream(fi);
		   ois=new ObjectInputStream(fis);
		   listurl=new ArrayList<Url>();
		   while( (readed=ois.readObject())!=null )
		   {
		   	     listurl.add( (Url)readed );
		   }
		   estado="ok";
		   return listurl;	   	
		}
		catch(ClassNotFoundException ex)
		{
			System.err.println(ex.getMessage());
			estado="error";
			return null;
		}
		catch(EOFException ex)
		{
			System.err.println(ex.getMessage());
			estado="ok";
			return listurl;
		}
		catch(IOException ex){
		ex.printStackTrace(); 
		estado="error";	
		return null;}
		finally
		{
			try
			{
				if(fis!=null) fis.close();
				if(ois!=null) ois.close();
				
			}catch(IOException ex)
			{  ex.printStackTrace();
			    estado="error";
				return null;
			}		
		}				
	}		
	
	public void update()
	{  
		//limpia o vacia totalmente el archivo
		CleanFile();
		//escribe todos los registros que hayan en la lista
		writeFile();
	}		
	
	//clase interna que extiende a ObjectOutputStream 
	//sobreescribiendo a writeStreamHeader() con implementacion 
	//vacia
	private class MiObjectOutputStream extends ObjectOutputStream
	{
		
		public MiObjectOutputStream(OutputStream ous) throws IOException
		{
			super(ous);
		}
		
		public void writeStreamHeader()
		{
		  //sobreescritura vacia
		  	
		}				
	}
	

	
	public static void leerArchivo(String path)
	{
		ObjectInputStream ois=null;
		FileInputStream fis=null;
		File fi=null;
		Object readed=null;
		try
		{
		   fi=new File(path);
		   fis=new FileInputStream(fi);
		   ois=new ObjectInputStream(fis);
		   while( (readed=ois.readObject())!=null )
		   {		   	 
		   	    System.out.println( ((Url)readed).getDireccion());
		   }
		  
		   	
		}
		catch(ClassNotFoundException ex){
			 ex.printStackTrace(); 
			}
		catch(EOFException ex){ System.err.println(ex.getMessage()); }
		catch(IOException ex){ex.printStackTrace(); }
		finally
		{
			try
			{
				if(fis!=null) fis.close();
				if(ois!=null) ois.close();
				
			}catch(IOException ex)
			{  ex.printStackTrace();
			
			}		
		}	
	}
	

	public void setOperationFile(OperacionesArchivo operationFile) {
		this.operationFile = operationFile; 
	}

	public void setEstado(String estado) {
		this.estado = estado; 
	}

	public OperacionesArchivo getOperationFile() {
		return (this.operationFile); 
	}

	public String getPath() {
		return (this.path); 
	}

	public String getEstado() {
		return (this.estado); 
	}				
}
