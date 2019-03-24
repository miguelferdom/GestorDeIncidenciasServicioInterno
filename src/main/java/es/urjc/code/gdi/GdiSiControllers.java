package es.urjc.code.gdi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GdiSiControllers {
	 
	@Autowired
	 public JavaMailSender emailSender;
	 
	@PostMapping("/mailer")
	public void NotificarAUsuario (@RequestBody DatosIncidenciaCorreo datos) {
		
		// Mensaje usado para comprobar en un primer momento la conectividad entre 
		// la aplicación de gestión de incidencias y el servicio interno
		/*
		System.out.println ("incidencia: " + datos.getIdIncidencia() + "\n" + 
							"Titulo: " + datos.getTitulo() + "\n" +
							"Descripcion: " + datos.getDescripcion() + "\n" +
							"Solucion: " + datos.getSolucion() + "\n" +
							"Email: " + datos.getUserEmail());
		*/
		
		String asunto = "La incidencia " + datos.getIdIncidencia() + ": " + datos.getTitulo() + " ha sido solucionada";
		String cuerpo = "Buenos días, el servicio de gestión de incidencias le informa que la siguiente incidencia ha sido solucionada: \n" + 
						"\n" +
						"incidencia: " + datos.getIdIncidencia() + "\n" + 
						"Titulo: " + datos.getTitulo() + "\n" +
						"Descripcion: " + datos.getDescripcion() + "\n" +
						"Solucion: " + datos.getSolucion() + "\n" +
						"\n" +
						"Si está de acuerdo con la solución, por favor conéctese a GDI y cierre la incidencia para dar el problema por resuelto definitivamente, en caso contrario reabra la incidencia e indique el motivo en un nuevo comentario.";
		
		//https://www.baeldung.com/spring-email
		
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(datos.getUserEmail()); 
        message.setSubject(asunto); 
        message.setText(cuerpo);
        emailSender.send(message);
	}
}
