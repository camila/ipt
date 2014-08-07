package best.dog;

import javax.jws.WebService;

@WebService
public interface Estoque {
	
	String calculaEstoque(String codItem);
	
	String consultaEstoque(String codItem);

}
