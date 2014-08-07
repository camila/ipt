package best.dog;

import javax.jws.WebService;

@WebService
public interface Milhagem {
    String calculaMilhagem(String cpf);
}

