# D&D Troops

### Disclaimer
This is a School project for Java classes.

### Build Status - Travis

[![Build Status](https://travis-ci.org/DDTroopsJava/dd-troops.svg?branch=master)](https://travis-ci.org/DDTroopsJava/dd-troops)

### Documentation

You can find documentation on [WIKI](https://github.com/DDTroopsJava/dd-troops/wiki).

### Presentation

[OverLeaf](https://www.overleaf.com/7746943zqhpjfvgxchv)
[PDF](https://www.dropbox.com/s/pyqksdrrhodthks/dungeons-dragons-pa165%20.pdf?dl=0)

### MVC 

* RUN: ``mvn clean install && cd ddtroops-mvc && mvn tomcat7:run``
* admin: ``chuck@example.com/heslo``
* user: ``superman@example.com/heslo``
* URL: ``http://localhost:8080/pa165``

### REST

* RUN: ``mvn clean install && cd ddtroops-rest && mvn tomcat7:run``
* Endpoint ````
* Entity **Hero**

#### Create Hero:
```bash
curl -X POST -H "Content-Type: application/json" localhost:8080/pa165/rest/heroes --data '{"name":"Stalone","experience":60}'

```

#### List all:
```bash
curl localhost:8080/pa165/rest/heroes
```

#### Get by Id:
```bash
curl localhost:8080/pa165/rest/heroes/{id} # where id is hero id
```

#### Delete:
```bash
curl -X DELETE localhost:8080/pa165/rest/heroes/{id} # where id is hero id
```
