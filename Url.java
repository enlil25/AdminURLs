import java.io.Serializable;

public class Url implements Serializable
{
	private String direccion;
	private String autor;
	private String categoria;
	private String tiempo;
	private String popularidad;

   public Url()
   {
   	 this.direccion=null;
   	 this.autor=null;
   	 this.categoria=null;
   	 this.tiempo=null;
   	 this.popularidad=null;
   }	
	
	public void setDireccion(String direccion) {
		this.direccion = direccion; 
	}

	public void setAutor(String autor) {
		this.autor = autor; 
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria; 
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo; 
	}

	public void setPopularidad(String popularidad) {
		this.popularidad = popularidad; 
	}

	public String getDireccion() {
		return (this.direccion); 
	}

	public String getAutor() {
		return (this.autor); 
	}

	public String getCategoria() {
		return (this.categoria); 
	}

	public String getTiempo() {
		return (this.tiempo); 
	}

	public String getPopularidad() {
		return (this.popularidad); 
	}
	
	
}