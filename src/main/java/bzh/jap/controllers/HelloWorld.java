package bzh.jap.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Classe pour tester le serveur [A supprimer en prod]

@RestController
public class HelloWorld {
	
	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}
}