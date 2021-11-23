# Desafio Técnico Sicredi

## API para controle de votação em pautas

### Endpoints Disponíveis
<a href="#pauta">desafio/api/v1/pauta</a><br/>
<a href="#iniciarVotacao">desafio/api/v1/pauta/iniciar/{pauta}/{duração}</a><br/>
<a href="#votar">desafio/api/v1/voto</a><br/>

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
[Git](https://git-scm.com), [Docker](https://www.docker.com/products/docker-desktop), [Java](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)
[IntelliJ IDEA](https://www.jetbrains.com/pt-br/idea/download/).

### 🎲 Rodando o Back End (servidor)
## Clone este repositório
$ git clone <https://github.com/hbiavatti/DesafioTecnico.git>
####Abra o projeto com a IDE IntelliJ IDEA e espere carregar as dependências maven.
####Acesse a aba terminal e rode o comando docker-compose up, após rodar o comando com sucesso inicie a aplicação java através da classe DesafioTecnicoApplication.java
####Para ver a documentação da API acesse http://localhost:8080/desafio/swagger-ui.html.

### 🛠 Tecnologias

As seguintes ferramentas foram usadas na construção do projeto:

- [Java](https://aws.amazon.com/pt/corretto/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Quartz](http://www.quartz-scheduler.org/)
- [MySQL](https://www.mysql.com/)
- [Docker](https://www.docker.com/products/docker-desktop)
- [ZooKeeper](https://zookeeper.apache.org/)
- [Kafka](https://kafka.apache.org/)
- [Maven](https://maven.apache.org/)