task-web
========

Exemplo de sistema de e-commerce para a aula de Sistemas Web (1Q - 2014)

#### Heroku:

O sistema foi disponibilizado para testes na nuvem (heroku.com) e pode ser acessado em:

http://task-web.herokuapp.com/ 

Devido à restrição de uso de banco de dados do Heroku somente a clientes pagos, a base de dados de produtos é mantida em arquivo CSV.

#### Tecnologia utilizada:

* Java 1.7 
* Spring MVC - Framework MVC Java (via http://projects.spring.io/spring-boot)
* Thymeleaf - Alternativa ao JSP com integração ao Spring MVC
* Tomcat 7 - Servidor de aplicação Java
* Gradle 1.9 - Sistema auxiliar para compilação, deploy e gerenciamento de dependências

#### Procedimento para execução:

1. Instalar a JDK (java.com) e o Gradle (www.gradle.org). O gradle baixará o Tomcat automaticamente (diretório `build` gerado após a compilação).
2. Baixar o zip com o código-fonte do projeto em: https://github.com/camila/task-web/archive/master.zip
3. Descompatar o zip e abrir o diretório "task-web".
4. Abrir um prompt de comando e executar o comando `gradle`. Isso irá compilar, rodar os testes e gerar o WAR.
5. No mesmo prompt, executar o comando `java -jar target/*.war`. Isso iniciará o tomcat com o WAR gerado.

#### Documentação técnica: 

* Projeto baseado em https://github.com/spring-projects/spring-test-htmlunit
* Ver javadoc (`gradle javadoc`)

#### Casos de Uso

##### Listar Produtos
1. Usuario entra na loja
2. Usuário visualiza a lista de produtos da loja

![Listar Produtos](doc/diagrams/Listar Produtos.png?raw=true)

---

##### Ver Detalhes do Produto
1. Usuário visualiza a lista de produtos da loja
2. Usuário seleciona um produto da loja
3. Usuário visualiza os detalhe do produto

![Ver Detalhes do Produto](doc/diagrams/Ver Detalhes do Produto.png?raw=true)

---

##### Incluir Item no Carrinho
1. Usuário visualiza a lista de produtos da loja
2. Usuário seleciona um produto da loja
3. Usuário visualiza os detalhes do produto
4. Usuário seleciona incluir o produto no carrinho
5. Carrinho inclui o produto na lista de produtos do usuário

![Incluir Item no Carrinho](doc/diagrams/Incluir Item no Carrinho.png?raw=true)

---

##### Remover Item do Carrinho
1. Usuário visualiza o carrinho
2. Usuário seleciona um produto para remover
3. Carrinho remove o produto da lista de produtos do usuário

![Remover Item do Carrinho](doc/diagrams/Remover Item do Carrinho.png?raw=true)
---

##### Finalizar Compra
1. Usuário visualiza o carrinho
2. Usuário seleciona finalizar a compra
3. Carrinho cadastra produtos na conta do usuário

![Finalizar Compra](doc/diagrams/Finalizar Compra.png?raw=true)
---
