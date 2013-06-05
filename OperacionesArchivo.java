import java.util.List;
import java.util.ArrayList;
public class OperacionesArchivo implements Operaciones<Url>
{
	private List <Url>listUrl;
	
	public OperacionesArchivo()
	{
		listUrl=new ArrayList<Url>();
	}
	
	
    public void insertRegister(Url reg)
    {
       if(!listUrl.contains(reg))
       	listUrl.add(reg);
    }
	public  Url deleteRegister(String... args){
	
	Url delete=null;
	Url aux=null;
	int nreg=args.length;
	
	for(int i=0; i<listUrl.size(); i++)
	{
		aux=listUrl.get(i); 
		boolean w=false;
		//busca los registros antes de eliminarlos;
		//si uno de ellos no coincide se termina la busqueda y devuelve null
		//los registros de busqueda deben coincidir todos
		int coincidencias=0;
		int cont=0;
		for(int j=0; j<nreg; j++)
		{
		   	if(j==0)
	   	     if(args[j]!=null && args[j].equals(aux.getAutor())) 
	   	     {
	   	     	coincidencias++;
	   	     	w=true;	   	     	
	   	     }
	   	   	 else {w=false; cont++;}
	   	 if(j==1)
	   	 	if(args[j]!=null && args[j].equals(aux.getCategoria())) 
	   	 	{
	   	 	   coincidencias++;
	   	     	w=true;	
	   	 	}	
	   	    else {w=false; cont++;}
	   	
	   	 if(j==2)
	   	 	if(args[j]!=null && args[j].equals(aux.getDireccion()))
	   	 	{
	   	 		coincidencias++;
	   	     	w=true;
	   	 	}	
	   	    else {w=false; cont++;}
	   	 	
	   	 if(j==3)
	   	 	if(args[j]!=null && args[j].equals(aux.getPopularidad()))
	   	 	{
	   	 		coincidencias++;
	   	     	w=true;
	   	 	}	
	   	    else {w=false; cont++;}		
	   	 if(j==4)
	   	 	if(args[j]!=null && args[j].equals(aux.getTiempo()))
	   	 	{
	   	 		coincidencias++;
	   	     	w=true;
	   	 	}	
	   	 	else {w=false; cont++;}		
		}
		
		 //si es falso y se llego al final de la lista
	   if(!w && i==listUrl.size()-1) return null;	
	   
	   else if(w && coincidencias==nreg-cont)
	   {	 
	     //borrado de registro
	     Url aux2=aux;
	     listUrl.remove(aux);
	     return aux2;
	   }
	   	
	}		
		return null;
		}
	public Url updateRegister(Url reg,String...args){
		
		Url aux=null;
		int numreg=args.length;
	
	for(int i=0; i<listUrl.size(); i++)
	{   
		aux=listUrl.get(i); 
	    boolean w=false;
	    int coincidencias=0;
		int cont=0;
	   for(int j=0; j<numreg; j++)
	   {	   		 
	   	   if(j==0)
	   	     if(args[j]!=null && args[j].equals(aux.getAutor())) 
	   	     {
	   	     	coincidencias++;
	   	     	w=true;
	   	     }		
	   	   	else {w=false; cont++;}
	   	 if(j==1)
	   	 	if(args[j]!=null && args[j].equals(aux.getCategoria()))
	   	 	{
	   	 		coincidencias++;
	   	     	w=true;	
	   	 	}	
	   	 	else {w=false; cont++;}
	   	
	   	 if(j==2)
	   	 	if(args[j]!=null && args[j].equals(aux.getDireccion()))
	   	 	{
	   	 		coincidencias++;
	   	     	w=true;
	   	 	}	
	   	 	else {w=false; cont++;}
	   	 	
	   	 if(j==3)
	   	 	if(args[j]!=null && args[j].equals(aux.getPopularidad()))
	   	 	{
	   	 		coincidencias++;
	   	     	w=true;
	   	 	}	
	   	 	else {w=false; cont++;}		
	   	 if(j==4)
	   	 	if(args[j]!=null && args[j].equals(aux.getTiempo()))
	   	 	{
	   	 		coincidencias++;
	   	     	w=true;
	   	 	}	
	   	 	else {w=false; cont++;}			
	   		   	  	   	 	  	
	   }
	   //System.out.println("w="+w);
	   //si es falso y se llego al final de la lista
	   if(!w && i==listUrl.size()-1) return null;
	   
	   else if(w && coincidencias==numreg-cont)
	   {	 
	   //seteo con las nuevas propiedades   	 
	   	aux.setAutor(reg.getAutor());
        aux.setCategoria(reg.getCategoria());
	   	aux.setDireccion(reg.getDireccion());
        aux.setPopularidad(reg.getPopularidad());
        aux.setTiempo(reg.getTiempo());  	  	   	 	 
	    return aux;
	   }
	     
	}
		return null;
		}
	//consulta por web  o direccion
    public List<Url> consult(String prefix){
    	
    	String web=null;
    	List<Url> lista=new ArrayList<Url>();
    	
    	for(Url url :listUrl)
    	{
    		web=url.getDireccion();
    		
    		if(web.indexOf(prefix)>=0)
    			lista.add(url);
    	}
    	return lista;
    	
    	}
    public Url busc(Url reg){
    	
    	Url busc=null;
    	int n=-1;
    	//si se encuentra el registro
    	if((n=listUrl.indexOf(reg))>=0)
    	{
    		busc=listUrl.get(n);
    	}	
 	
    return busc;
    
    }
    
    public Url busc(String...args){
    	
    	Url busc=null; 
    	Url aux=null;
    	int nreg=args.length;
       //recorrido de lista
    	for(int i=0; i<listUrl.size(); i++)	
    	{  
    	  aux=listUrl.get(i);
    	  boolean w=false;
    	  int cont=0;
    	  int coincidencias=0;
    	  
    	  for(int j=0; j<nreg; j++)
    	  {
    		 if(j==0)
	   	       if(args[j]!=null && args[j].equalsIgnoreCase(aux.getAutor())) {w=true; coincidencias++;}
	   	   	   else {w=false; cont++;}
	   	     if(j==1)
	   	 	   if(args[j]!=null && args[j].equalsIgnoreCase(aux.getCategoria())){w=true; coincidencias++;}
	   	 	   else {w=false; cont++;}	   	
	   	     if(j==2)
	   	 	   if(args[j]!=null && args[j].equalsIgnoreCase(aux.getDireccion())) {w=true; coincidencias++;}
	   	 	   else {w=false; cont++;}	   	 	
	   	     if(j==3)
	   	 	   if(args[j]!=null && args[j].equalsIgnoreCase(aux.getPopularidad())) {w=true; coincidencias++;}
	   	 	   else {w=false; cont++;}		
	   	     if(j==4)
	   	       if(args[j]!=null && args[j].equals(aux.getTiempo())) {w=true; coincidencias++;}
	   	 	   else {w=false; cont++;}			
    		}
    		//System.out.println("w="+w);

    		if(!w && i==listUrl.size()-1) 
    		   return null;
    		else if(w && coincidencias==nreg-cont)
    		{ 
    			//System.out.printf("registro encontrado");
    			return aux;  
    		}			
    		  				
    	}
    return null;
    
    }
    
    
    
    public void ListRecords()
    {   
    	for(Url url:listUrl)
    	{
    		System.out.printf("%s%n",url.getAutor());
    		System.out.printf("%s%n",url.getCategoria());
    		System.out.printf("%s%n",url.getDireccion());
    		System.out.printf("%s%n",url.getPopularidad());
    		System.out.printf("%s%n%n",url.getTiempo());				
    	}
    }	
	
	
	public static void main(String[]args)
	{
		Url url=new Url();
		url.setAutor("jose");
		url.setCategoria("informatica");
		url.setDireccion("http://www.java2s.com");
		url.setPopularidad("50%");
		url.setTiempo("1 año");
		
	    Url url2=new Url();
		url2.setAutor("andres");
		url2.setCategoria("programacion");
		url2.setDireccion("http://www.oracle.com");
		url2.setPopularidad("90%");
		url2.setTiempo("5 años");
		
		Url url3=new Url();
		url3.setAutor("anonimo");
		url3.setCategoria("Juegos");
		url3.setDireccion("http://www.javazone.com.pe");
		url3.setPopularidad("40%");
		url3.setTiempo("1.5 año");
		
		OperacionesArchivo obj=new OperacionesArchivo();
		obj.insertRegister(url);
		obj.insertRegister(url2);
		obj.insertRegister(url3);
		obj.ListRecords();
		
		/*//consulta
		List <Url>list=obj.consult(".javaZone");
	
		for(Url ur:list)
    	{
    		System.out.printf("%s%n",ur.getAutor());
    		System.out.printf("%s%n",ur.getCategoria());
    		System.out.printf("%s%n",ur.getDireccion());
    		System.out.printf("%s%n",ur.getPopularidad());
    		System.out.printf("%s%n%n",ur.getTiempo());				
    	}*/
    	
    	//busqueda
    	/*Url enc=obj.busc("andres","programacion","http://www.oracle.com");
    
    	if(enc==null) System.err.println("url no encontrada");
    	else
    	{
    		System.out.printf("%s%n",enc.getAutor());
    		System.out.printf("%s%n",enc.getCategoria());
    		System.out.printf("%s%n",enc.getDireccion());
    		System.out.printf("%s%n",enc.getPopularidad());
    		System.out.printf("%s%n%n",enc.getTiempo());		
    	}	*/
   /* //eliminar	
      Url del=obj.deleteRegister("jose","informatica","http://www.java2s.com");
	if(del==null)System.err.println("registro no borrado");
	else System.err.println("registro  borrado");
	obj.ListRecords();*/
	
	//editado
	Url nuevo=new Url();
	nuevo.setAutor("damian");
	nuevo.setCategoria("ocio");
	nuevo.setDireccion("http://www.fullocio.net");
	nuevo.setPopularidad("60%");
	nuevo.setTiempo("2.0 año");
	Url edit=obj.updateRegister(nuevo,"andres","programacion","http://www.oracle.com");
	if(edit==null)System.out.printf("registro no fue editado");
	else{
		System.err.println("registro editado");
		obj.ListRecords();
	}
	}
	
	public int CountRegisters()
	{
		return listUrl.size();
	}	
    
    public void deleteRegisterAll()
    {
    	listUrl.clear();
    }	
	
	public void setListUrl(List<Url> listUrl) {
		this.listUrl = listUrl; 
	}

	public List<Url> getListUrl() {
		return (this.listUrl); 
	}		
}

