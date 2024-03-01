# rinhaquarkus

## API backend submetida ao desafio "rinha de backend 2024 q1"

### Tecnologias e recursos utilizados
- **Java 17 nativo**
- **Quarkus 3.7.4** clássico (não reativo)
- **nginx** como load balancer
- **PostgreSQL**
- **Panache/Hibernate** com pessimistic locking
- **Jackson** para serializar/deserializar os JSON dos endpoints REST
- **Podman** para construir os containers com a imagem nativa do quarkus
- http://quay.io para publicar as imagens

### Subir a aplicação (é necessário ter podman-compose instalado)
```shell script
./up.sh
```
#### acessar http://localhost:9999

