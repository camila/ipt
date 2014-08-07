package demo.web.service;

import static demo.models.User.Type.ADMIN;
import demo.models.User;

/**
 * Controle de acesso comuns aos serviços
 */
public abstract class AbstractService {

    protected void authorize(User admin) {
        admin.setType(ADMIN);
    }

}
