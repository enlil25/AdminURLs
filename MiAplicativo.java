import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Desktop;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import javax.swing.filechooser.FileFilter;

public class MiAplicativo extends JFrame implements ActionListener,KeyListener
{
	private Container contenedor;
	private JPanel panel_left;
	private JPanel panel_center;
	private JPanel panel_rigth;
	private JPanel panel_buttons;
	private JLabel lbl_title;
	private JLabel lbl_busc;
	private JTextField txt_busc;
	private JList list_urls;
	private JScrollPane scroll_list_urls;
	private JButton button_busc;
	private JButton button_addurl;
	private JButton button_removeUrl;
	private JButton button_updateUrl;
	private JButton button_removeallUrl;
	private JEditorPane txteditor_result;
	private JScrollPane scroll_txteditor_result;
	
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem item_Open;
	private JMenuItem item_Import;
	private JMenuItem item_exit;
	private JMenuItem item_saveURLs;
	private JMenuItem item_openBinReg;
	
	//panel de entrada de datos y
	//panel para jlist
	private JPanel panel_data;
	private JPanel panel_list;
    
    private OperacionesArchivo operationfile;
    private LecturaEscritura readwrite;
    private DefaultListModel listUrlModel;
    
	public MiAplicativo()
	{
	 super("Mi Aplicativo");
	 
	  try
	 { 
	 	UIManager.LookAndFeelInfo[]info=UIManager.getInstalledLookAndFeels();
	 	UIManager.setLookAndFeel( info[4].getClassName() );
	 	SwingUtilities.updateComponentTreeUI(this);
	 	
	 }
	 catch(Exception ex){ex.printStackTrace();}	
	 
	 this.contenedor=this.getContentPane();
	 panel_left=new JPanel();
	 panel_center=new JPanel();
	 panel_rigth=new JPanel();
	 panel_buttons=new JPanel();
	 lbl_title=new JLabel("Registros URL");
	 lbl_busc=new JLabel("Ingresa direccion web");
	 txt_busc=new JTextField(22);
	 button_busc=new JButton("Buscar");
	 list_urls=new JList();
	 scroll_list_urls=new JScrollPane(list_urls);
	 button_addurl=new JButton("Add URL");
	 button_removeUrl=new JButton("Remove URL");
	 button_updateUrl=new JButton("Edit URL");
	 button_removeallUrl=new JButton("Remove All URL");
	 txteditor_result=new JEditorPane();
	 scroll_txteditor_result=new JScrollPane(txteditor_result);
	 
	 panel_data=new JPanel();
	 panel_list=new JPanel();
	 
	 addDetails();
	 	
	 operationfile=new OperacionesArchivo();
	 readwrite=new LecturaEscritura(operationfile);
	 listUrlModel =new DefaultListModel();
	 list_urls.setModel(listUrlModel);
	 
	 menuBar=new JMenuBar();
	 menuFile=new JMenu("Archivo");
	 item_Open=new JMenuItem("Abrir carpeta con htmls");
	 item_Import=new JMenuItem("Importar carpeta con htmls");
	 item_saveURLs=new JMenuItem("exportar archivo RegBinario.binreg");
	 item_openBinReg=new JMenuItem("importar archivo RegBinario.binreg");
	 item_exit=new JMenuItem("Salir");
	 menuFile.add(item_Open);
	 menuFile.add(item_Import);
	 menuFile.addSeparator();
	 menuFile.add(item_saveURLs);
	 menuFile.add(item_openBinReg);
	 menuFile.addSeparator();
	 menuFile.add(item_exit);
	 menuBar.add(menuFile);
	 this.setJMenuBar(menuBar);
	 cargarRegistros();
	 

	 	

}

private File directorioActualAux=null;
private class actionMenuItems implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		Object component=e.getSource();
		
		if(component==item_exit)
		{
			MiAplicativo.this.dispose();
			System.exit(0);
		}
		else if(component==item_Import)
		{
		  JFileChooser jfc=new JFileChooser();
		   jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		     if(directorioActualAux!=null)
		       jfc.setCurrentDirectory(directorioActualAux);
		  
		    int c=jfc.showOpenDialog(item_Import);		  
		    if(c==JFileChooser.APPROVE_OPTION)
		    {
		       File file=jfc.getSelectedFile();
		        directorioActualAux=file;		   
		       //se lista los archivos;
		       File[] files=file.listFiles();
		       for(File f:files)
		       {
		       	if( f.getName().lastIndexOf(".url")!=-1 || f.getName().lastIndexOf(".Url")!=-1 )
		       	{
		  
		       	  Url url=new Url();
		       	  url.setAutor("");
		       	  url.setCategoria("");
		       	  url.setPopularidad("");
		       	  url.setTiempo("");
		       	  url.setDireccion(f.getName());
		       	  operationfile.insertRegister(url);
		       	  System.err.println(f.getName());
		       	}
		       }
		       //se escribe en archivo los nuevos registros
		       readwrite.update();
		       //carga los registros de nuevo
		       cargarRegistros();
		       //si hay registros se activa los botones
		       if(!operationfile.getListUrl().isEmpty())
		       {
		       	 button_removeUrl.setEnabled(true);
		       	 button_removeallUrl.setEnabled(true);
		       	 button_updateUrl.setEnabled(true);
		       }		       	
		    }	
		}
		else if(component==item_Open)
		{
		   JFileChooser jfc=new JFileChooser();
		   jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		     if(directorioActualAux!=null)
		       jfc.setCurrentDirectory(directorioActualAux);
		  
		    int c=jfc.showOpenDialog(item_Open);		  
		    if(c==JFileChooser.APPROVE_OPTION)
		    {
		       File file=jfc.getSelectedFile();
		        directorioActualAux=file;		   
		       //se lista los archivos;
		       File[] files=file.listFiles();
		       DefaultListModel modelListAux=new DefaultListModel();
		       for(File f:files)
		       {
		       	if( f.getName().lastIndexOf(".url")!=-1 || f.getName().lastIndexOf(".Url")!=-1 )
		       	{	 
		       	  modelListAux.addElement(f.getName());
		       	}
		       }		    		    
               
               list_urls.setModel(modelListAux);		   
		       //si hay registros se activa los botones
		       if(!operationfile.getListUrl().isEmpty())
		       {
		       	 button_removeUrl.setEnabled(true);
		       	 button_removeallUrl.setEnabled(true);
		       	 button_updateUrl.setEnabled(true);
		       }		       	
		    }		
			
			
		}
	    else if(component==item_saveURLs)
		{
			 JFileChooser jfc=new JFileChooser();
		   jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		     if(directorioActualAux!=null)
		       jfc.setCurrentDirectory(directorioActualAux);
		  
		    int c=jfc.showSaveDialog(item_saveURLs);		  
		    if(c==JFileChooser.APPROVE_OPTION)
		    {
		       File file=jfc.getSelectedFile();
		        directorioActualAux=file;		   
		       //se copia archivo binario a lugar especificado
		     	    		    
               readwrite.saveFile(file.getAbsolutePath(),
                                 readwrite.getPath());
            	if(readwrite.getEstado().equals("ok"))
            	{JOptionPane.showMessageDialog(item_saveURLs,"Archivo RegBinario.binreg\n"+
            		"guardado en:"+file.getAbsolutePath());
            		return;
            	}
            	else
            	{
            	JOptionPane.showMessageDialog(item_saveURLs,"Archivo RegBinario.binreg\n"+
            		"no puedo ser guardado,vuelva a intentar");	
            		return;
            	}			   		      	       	
		    }								
		}
		else if(component==item_openBinReg)
		{
			 JFileChooser jfc=new JFileChooser();
		   jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		   jfc.setFileFilter(new FileFilter(){
		   	
		   	public boolean accept(File f)
		   	{
		   		return f.getName().lastIndexOf(".binreg")!=-1;
		   	}
		   	
		   public String getDescription()
		   {
		   	return new String(".binreg");
		   }			
		   	
		   	});
		     if(directorioActualAux!=null)
		       jfc.setCurrentDirectory(directorioActualAux);
		  
		    int c=jfc.showOpenDialog(item_openBinReg);		  
		    if(c==JFileChooser.APPROVE_OPTION)
		    {
		       File file=jfc.getSelectedFile();   
		       List <Url>listimportes=readwrite.readFile(file.getAbsolutePath());
		       if(readwrite.getEstado().equals("ok"))
		       {
		       	 for(Url url:listimportes)
		       	 operationfile.insertRegister(url);
		       	  readwrite.update();
		       	  cargarRegistros();
		       	  if(!operationfile.getListUrl().isEmpty())
		       	  {
		       	  	    button_removeUrl.setEnabled(true);
					    button_updateUrl.setEnabled(true);
					    button_removeallUrl.setEnabled(true);
					    button_addurl.setEnabled(true);	
		       	  }	
		       }
		       else
		       {
		       	JOptionPane.showMessageDialog(item_openBinReg,"No se puedo copiar de:\n"
		       		+file.getAbsolutePath()+" ,intentelo de nuevo");
		       		return;
		       }	
		       	   		      	       	
		    }				
		}
		else	
		return;				
	}	
}
	 	 

	
	private void addDetails()
	{
		scroll_list_urls.setHorizontalScrollBarPolicy(
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll_list_urls.setVerticalScrollBarPolicy(
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
		scroll_txteditor_result.setVerticalScrollBarPolicy(
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll_txteditor_result.setHorizontalScrollBarPolicy(
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		
		txt_busc.setMaximumSize(new Dimension(160,20));
		txt_busc.setMinimumSize(new Dimension(160,20));
		txt_busc.setPreferredSize(new Dimension(160,20));
		
		list_urls.setLayoutOrientation(JList.VERTICAL_WRAP);
		list_urls.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_urls.setVisibleRowCount(-1);
	
		//panel_data.setMaximumSize(new Dimension(128,10));
		//panel_data.setMinimumSize(new Dimension(22,10));
	}	
	
	public void addInContainers()
	{
		//en panel entrada
		panel_data.setLayout(new BoxLayout(panel_data,BoxLayout.X_AXIS));
		panel_data.setBorder(BorderFactory.createEtchedBorder(1));
		panel_data.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(5,5,5,5),panel_data.getBorder()));

		//panel_data.add(lbl_title,cons);	
		panel_data.add(lbl_busc);	
		panel_data.add(txt_busc);	
		panel_data.add(button_busc);
		
		//en panel list
		panel_list.setLayout(new BorderLayout());
		panel_list.setBorder(BorderFactory.createEtchedBorder(1));
		panel_list.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(5,5,5,5),panel_list.getBorder()));
	    panel_list.add(scroll_list_urls,BorderLayout.CENTER);
	    
	    //en panelbutton
	    panel_buttons.setLayout(new GridLayout(2,2,5,5));
		panel_buttons.setBorder(BorderFactory.createEtchedBorder(1));
		panel_buttons.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(5,5,5,5),panel_buttons.getBorder()));
	    panel_buttons.add(button_addurl);
	    panel_buttons.add(button_removeUrl);
	    panel_buttons.add(button_updateUrl);
	    panel_buttons.add(button_removeallUrl);
	    
	    //en panelcentral
	    panel_center.setLayout(new BorderLayout());
		panel_center.setBorder(BorderFactory.createEtchedBorder(1));
		panel_center.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(10,10,10,10),panel_center.getBorder()));
	    panel_center.add(scroll_txteditor_result,BorderLayout.CENTER);
	    
	    //en panellef
	    panel_left.setLayout(new BoxLayout(panel_left,BoxLayout.Y_AXIS));
		panel_left.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));	
	    panel_left.add(panel_data);
	    panel_left.add(panel_list);
	    panel_left.add(panel_buttons);
	    
	    //en contenedor
	    contenedor.setLayout(new BorderLayout());
	    contenedor.add(panel_left,BorderLayout.WEST);
	    contenedor.add(panel_center,BorderLayout.CENTER);
	    
	    //en clase actual
	    this.setVisible(true);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(500,500);
	    
	    agregarEventos();
		
	}
	
	public void agregarEventos()
	{
		button_busc.setActionCommand("button_busc:action_bus");
	    button_busc.addActionListener(this);
	    button_addurl.setActionCommand("button_addurl:action_addurl");
	    button_addurl.addActionListener(this);
	    button_removeUrl.setActionCommand("button_removeUrl:action_removeUrl");
	    button_removeUrl.addActionListener(this);
	    button_updateUrl.setActionCommand("button_updateUrl:action_updateUrl");
	    button_updateUrl.addActionListener(this);
	    button_removeallUrl.setActionCommand("button_removeallUrl:action_removeAllUrl");
	    button_removeallUrl.addActionListener(this); 
	    txt_busc.addKeyListener(new ListenerKey());	
	    list_urls.addMouseListener(new ListenerMouse());
	    
	    //eventos de menuitems
	    item_exit.addActionListener(new actionMenuItems());
	    item_Import.addActionListener(new actionMenuItems()); 	
	    item_Open.addActionListener(new actionMenuItems());	  
	    item_saveURLs.addActionListener(new actionMenuItems()); 
	    item_openBinReg.addActionListener(new actionMenuItems());  	
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String comando=e.getActionCommand().trim();
		
		if(comando.equals("button_busc:action_bus"))
		{
			action_bus();
		}
		else if(comando.equals("button_addurl:action_addurl"))
		{
			action_addurl();
		}
		else if(comando.equals("button_removeUrl:action_removeUrl"))
		{
			int cf=JOptionPane.showConfirmDialog(button_removeUrl,"¿estas seguro que deseas eliminar"
				+"este registro\n\n?"+list_urls.getSelectedValue().toString());
			if(cf==-1) return;
			else if(cf==JOptionPane.CANCEL_OPTION)return;
			else if(cf==JOptionPane.YES_OPTION)
			{
				operationfile.deleteRegister(null,null,(String)list_urls.getSelectedValue());
				readwrite.update();
				if(readwrite.getEstado().equals("ok"))
				{
					listUrlModel.remove(list_urls.getSelectedIndex());
					//si una vez eliminado el registro la lista se vacia 
					//completamente
					if(operationfile.getListUrl().isEmpty())
					{
					    button_removeUrl.setEnabled(false);
					    button_updateUrl.setEnabled(false);
					    button_removeallUrl.setEnabled(false);
					    button_addurl.setEnabled(true);	
					}	
				}					 
					
					
					
			}	
				
		}
		else if(comando.equals("button_removeallUrl:action_removeAllUrl"))
		{
			int cf=JOptionPane.showConfirmDialog(button_removeallUrl,"¿estas seguro que deseas eliminar"
				+"Todos los registros?\n"+"Total registros:"+operationfile.CountRegisters()+"\n");
			if(cf==-1) return;
			else if(cf==JOptionPane.CANCEL_OPTION)return;
			else if(cf==JOptionPane.YES_OPTION)
			{
				operationfile.deleteRegisterAll();
				readwrite.update();
				if(readwrite.getEstado().equals("ok"))
				{
					listUrlModel.clear();
					
				  //si una vez eliminado el registro la lista se vacia 
				  //completamente
					if(operationfile.getListUrl().isEmpty())
					{
					    button_removeUrl.setEnabled(false);
					    button_updateUrl.setEnabled(false);
					    button_removeallUrl.setEnabled(false);
					    button_addurl.setEnabled(true);	
					}	
					
					
				}
				else
				{
					System.err.println("estado:error");
				}		
					
			}	
		}
		else if(comando.equals("button_updateUrl:action_updateUrl"))
		{
			action_updateUrl();
		}
		else return;
			 				
	}
	

	private JDialog createDialogAddUrl(int width,int heigt)
	{
		final JDialog output=new JDialog();
		
		JPanel panel=new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		JLabel lbl0=new JLabel("Nuevo Registro");
		lbl0.setFont(new Font(Font.SANS_SERIF,Font.BOLD,33));
		JLabel lbl1=new JLabel("Autor");
		JLabel lbl2=new JLabel("Categoria");
		JLabel lbl3=new JLabel("Direccion:(http)");
		JLabel lbl4=new JLabel("Popularidad");
		JLabel lbl5=new JLabel("Tiempo");			
		
		final JTextField txt1=new JTextField(22);
	    final JTextField txt2=new JTextField(22);
	    final JTextField txt3=new JTextField(22);
		final JTextField txt4=new JTextField(22);
		final JTextField txt5=new JTextField(22);
		
		JButton button1=new JButton("Guardar");
		button1.addActionListener(new ActionListener(){
			
		public void actionPerformed(ActionEvent e)
		{
				
		   String aut=txt1.getText().trim();
		   String cat=txt2.getText().trim();
	       String dir=txt3.getText().trim();
		   String pop=txt4.getText().trim();	   
		   String tie=txt5.getText().trim();
		   
		   Url url1=new Url();
		   url1.setAutor(aut);
		   url1.setCategoria(cat);
		   url1.setDireccion(dir);
		   url1.setPopularidad(pop);
		   url1.setTiempo(tie);
		   
		   operationfile.insertRegister(url1);
		   //actualiza en fichero con escritura	   		   
		   readwrite.update();
		   
		   String estado=readwrite.getEstado();
		   if(estado.equals("ok"))
		   {
		   	MiAplicativo.this.listUrlModel.addElement(dir);
		   	list_urls.setSelectedIndex(0);
		   	output.setVisible(false);
		   	output.dispose();
		   	
		   	//se activa los botones de edicion y eliminado
		   	button_removeUrl.setEnabled(true);
		   	button_removeallUrl.setEnabled(true);
		   	button_updateUrl.setEnabled(true);
		   }
		   else
		   {
		   	//mensaje de no se pudo
		   }	
 
		}	 
			});
		JButton button2=new JButton("Cancelar");
		button2.addActionListener(new ActionListener(){
			
		public void actionPerformed(ActionEvent e)
		{	
			output.setVisible(false);
			output.dispose();
		}
			});
		
		
		//a panel
		GridBagConstraints cons=new GridBagConstraints();
		cons.insets=new Insets(5,5,5,5);
		cons.gridx=0;
		cons.gridy=0;
		cons.gridwidth=2;
		cons.gridheight=1;
		cons.anchor=GridBagConstraints.CENTER;
		panel.add(lbl0,cons);
		
		cons.gridx=0;
		cons.gridy=1;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(lbl1,cons);
		
		cons.gridx=0;
		cons.gridy=2;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(lbl2,cons);
		
		cons.gridx=0;
		cons.gridy=3;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(lbl3,cons);
		
		cons.gridx=0;
		cons.gridy=4;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(lbl4,cons);
		
		cons.gridx=0;
		cons.gridy=5;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(lbl5,cons);	
			
		cons.gridx=1;
		cons.gridy=1;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(txt1,cons);
		
		cons.gridx=1;
		cons.gridy=2;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(txt2,cons);
		
		cons.gridx=1;
		cons.gridy=3;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(txt3,cons);
		
		cons.gridx=1;
		cons.gridy=4;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(txt4,cons);
		
		cons.gridx=1;
		cons.gridy=5;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(txt5,cons);
		
		cons.gridx=0;
		cons.gridy=6;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(button1,cons);
		
		cons.gridx=1;
		cons.gridy=6;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(button2,cons);
			

		output.setTitle("New Register");							
		output.setSize(width,heigt);
		output.getContentPane().setLayout(new BorderLayout());
		output.getContentPane().add(panel,BorderLayout.CENTER);
		output.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		output.setVisible(true);
		output.setModal(false);
				
		return output;
	}
	
	private JDialog createDialogEditUrl(int width,int heigth)
	{
			final JDialog output=new JDialog();
		
		JPanel panel=new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		JLabel lbl0=new JLabel("Editar Registro");
		lbl0.setFont(new Font(Font.SANS_SERIF,Font.BOLD,33));
		JLabel lbl1=new JLabel("Autor");
		JLabel lbl2=new JLabel("Categoria");
		JLabel lbl3=new JLabel("Direccion:(http)");
		JLabel lbl4=new JLabel("Popularidad");
		JLabel lbl5=new JLabel("Tiempo");			
		
		final JTextField txt1=new JTextField(18);
	    final JTextField txt2=new JTextField(18);
	    final JTextField txt3=new JTextField(18);
		final JTextField txt4=new JTextField(18);
		final JTextField txt5=new JTextField(18);
		
		//busca registro y pinta en cuadros de texto
		Url url=operationfile.busc(null,null,list_urls.getSelectedValue().toString());
		if(url!=null)
		{
			txt1.setText(url.getAutor());
			txt2.setText(url.getCategoria());
			txt3.setText(url.getDireccion());
			txt4.setText(url.getPopularidad());
			txt5.setText(url.getTiempo());				
		}	
		else System.err.println("url no encontrado");
		
		JButton button1=new JButton("Guardar Cambios");
		button1.addActionListener(new ActionListener(){
			
		public void actionPerformed(ActionEvent e)
		{   
						
		  //se vuelve a pintar con los registros editados
		       String aut=txt1.getText().trim();
		       String cat=txt2.getText().trim();
	           String dir=txt3.getText().trim();
		       String pop=txt4.getText().trim();	   
		       String tie=txt5.getText().trim();
           
              Url regedit=new Url();
              regedit.setAutor(aut);
		      regedit.setCategoria(cat);
		      regedit.setDireccion(dir);
		      regedit.setPopularidad(pop);
		      regedit.setTiempo(tie);
              operationfile.updateRegister(regedit,null,null,(String)list_urls.getSelectedValue());
              readwrite.update();
              
              if(readwrite.getEstado().equals("ok"))
              {
              	listUrlModel.set( list_urls.getSelectedIndex(),regedit.getDireccion());
              	output.setVisible(false);
			    output.dispose();
              }	
              
		   
		}	 
			});
		JButton button2=new JButton("Cancelar");
		button2.addActionListener(new ActionListener(){
			
		public void actionPerformed(ActionEvent e)
		{	
			output.setVisible(false);
			output.dispose();
		}
			});
		
		
		//a panel
		GridBagConstraints cons=new GridBagConstraints();
		cons.insets=new Insets(1,1,1,1);
		cons.gridx=0;
		cons.gridy=0;
		cons.gridwidth=2;
		cons.gridheight=1;
		cons.anchor=GridBagConstraints.CENTER;
		panel.add(lbl0,cons);
		
		cons.gridx=0;
		cons.gridy=1;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(lbl1,cons);
		
		cons.gridx=0;
		cons.gridy=2;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(lbl2,cons);
		
		cons.gridx=0;
		cons.gridy=3;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(lbl3,cons);
		
		cons.gridx=0;
		cons.gridy=4;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(lbl4,cons);
		
		cons.gridx=0;
		cons.gridy=5;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(lbl5,cons);	
			
		cons.gridx=1;
		cons.gridy=1;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(txt1,cons);
		
		cons.gridx=1;
		cons.gridy=2;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(txt2,cons);
		
		cons.gridx=1;
		cons.gridy=3;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(txt3,cons);
		
		cons.gridx=1;
		cons.gridy=4;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(txt4,cons);
		
		cons.gridx=1;
		cons.gridy=5;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(txt5,cons);
		
		cons.gridx=0;
		cons.gridy=6;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(button1,cons);
		
		cons.gridx=1;
		cons.gridy=6;
		cons.gridwidth=1;
		cons.gridheight=1;
		panel.add(button2,cons);
			

		output.setTitle("Edit Register");							
		output.setSize(width,heigth);
		output.getContentPane().setLayout(new BorderLayout());
		output.getContentPane().add(panel,BorderLayout.CENTER);
		output.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		output.setVisible(true);
		output.setModal(false);
				
		return output;
		
	}
	
	public JPopupMenu createJPopupMenuList(int x,int y)
	{
		JPopupMenu jpopupmenu=new JPopupMenu("popup");
		JMenuItem menuitem=new JMenuItem("Visualizar en editor");
		jpopupmenu.add(menuitem);
		menuitem.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e)
			{
			    new Thread(new Runnable(){
			    	
			    	public void run(){
			    		
			    	    Desktop desktop=Desktop.getDesktop();
			            String file=(String)list_urls.getSelectedValue();
			         try
			         {   				
				        txteditor_result.setPage(new URL(file));	
			          }
		           	 catch(Exception ex){
			         System.err.println(ex.getMessage());
			         return;
				     }	
			    		
			    		}
			    	} ).start();
		      
			}	
			
			});
		jpopupmenu.setLocation(x,y);
		jpopupmenu.setVisible(true);
		return jpopupmenu;
	}			
	
	
	public void action_addurl(){
		
		JDialog registernew=null;
		//registernew=createDialogAddUrl(400,350);
  registernew=createDialogAddUrl(400,350);
		
		}
	public void action_bus(){
		
		String busc=txt_busc.getText().trim();
		
		List<Url>listUrl=operationfile.consult(busc);
		DefaultListModel modellist2=new DefaultListModel();
		if(listUrl!=null)
		{  
			for(Url url:listUrl)
			{	
			 modellist2.addElement(url.getDireccion());
			}
			 list_urls.setModel(modellist2);
		}
		else
			System.err.println("resultados no encontrados");	
		
		}
	public void action_removeUrl(){
		
		
		}
	public void action_removeAllUrl(){}
	public void action_updateUrl(){
			JDialog registernew=null;
		//registernew=createDialogAddUrl(400,350);
registernew=createDialogEditUrl(400,350);
		}
		
		
	private class ListenerKey extends KeyAdapter
	{
		public ListenerKey()
		{
			super();
		}
	
		public void keyPressed(KeyEvent e)
		{		
			if(txt_busc.getText().length()==0)
			{
				list_urls.setModel(listUrlModel);
			}
			else	
		     action_bus();			
		}
	
	}
	
	private class ListenerMouse extends MouseAdapter
	{
		
		public void mousePressed(MouseEvent e)
		{
			evaluarPopupTrigger(e);
		}
		
		public void mouseReleased(MouseEvent e)
		{
		  evaluarPopupTrigger(e);
		}
		
		private void evaluarPopupTrigger(MouseEvent e)
		{
			System.out.println("ancho de indice seleccionado="+list_urls.getAnchorSelectionIndex());
			if(e.isPopupTrigger() && list_urls.locationToIndex(new Point(e.getX(),e.getY()))!=-1  )
			{
				JPopupMenu popup=createJPopupMenuList(300,20);
				popup.show(e.getComponent(),e.getX(),e.getY());
			}		
		}				
		
		public void mouseClicked(MouseEvent e)
		{
			if(e.getClickCount()==2 && !e.isMetaDown())
			{
			 new Thread(new Runnable(){
			 	
			 	public void run()
			 	{
			 	 action2Click();
			 	}	
			 	
			 	}).start();
			}			
		}
		
		private void action2Click()
		{
			Desktop desktop=Desktop.getDesktop();
			String file=(String)list_urls.getSelectedValue();
			try
			{   				
				desktop.browse(new URI(file));	
			}
			catch(Exception ex){
			System.err.println(ex.getMessage());
			return;
				}	
		}	
	}	
	
	private void cargarRegistros()
	{
		List<Url> listUrl=null;
		listUrl=operationfile.getListUrl();
		for(Url url:listUrl)
		{
			listUrlModel.addElement(url.getDireccion());
		}
	    
	    //se selecciona el primer elementos si es que hay
	    if(listUrl.size()>0)
	    list_urls.setSelectedIndex(0);
	    else
	    {
	    	button_removeUrl.setEnabled(false);
	    	button_removeallUrl.setEnabled(false);
	    	button_updateUrl.setEnabled(false);
	    	button_addurl.setEnabled(true);
	    }		
	}	
	
	public void keyPressed(KeyEvent e){}
	 public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
					
	
	public static void main(String[]args)
	{
		MiAplicativo obj=new MiAplicativo();
		obj.addInContainers();
	}		
	
		
}