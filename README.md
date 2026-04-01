# Java Spring Boot Maven Project for CI/CD Education

Ce projet est une application Spring Boot simple construite avec Maven, conçue pour enseigner aux étudiants comment configurer des pipelines CI/CD pour construire des fichiers JAR, créer des images Docker et les pousser vers des registres en utilisant Jenkins, GitLab CI et Azure DevOps.

## Fonctionnalités

- API REST Spring Boot avec un endpoint Hello
- Maven pour la gestion des dépendances et la construction
- Support Docker pour la conteneurisation

## Prérequis

- JDK 11+
- Maven 3.6+
- Docker

## Développement local

1. Cloner le dépôt :

```bash
git clone https://github.com/Formation-DevOps-Level-1/java-spring-maven.git
cd java-spring-maven
```

2. Construire le projet :

```bash
mvn clean install
```

3. Lancer l'application :

```bash
mvn spring-boot:run
```

L'application démarrera sur http://localhost:8080

## Endpoints API

- GET /status - Retourne le statut de l'application ("ok")
- GET /temp - Retourne la température ("17.2 C")

## Test de l'API

Pour tester l'API, vous pouvez utiliser Postman ou Bruno (un client API open-source similaire à Postman).

1. Lancez l'application localement avec `mvn spring-boot:run`.

2. Ouvrez Postman ou Bruno.

3. Créez une nouvelle requête GET pour chaque endpoint :

   - URL : `http://localhost:8080/status`
     - Méthode : GET
     - Réponse attendue : "ok"

   - URL : `http://localhost:8080/temp`
     - Méthode : GET
     - Réponse attendue : "17.2 C"

4. Envoyez la requête et vérifiez que la réponse correspond à celle attendue.

## Construction du JAR

```bash
mvn clean package
```

## Docker

### Construire l'image

```bash
docker build -t java-spring-maven .
```

### Lancer le conteneur

```bash
docker run -p 8080:8080 java-spring-maven
```

## Pipelines CI/CD

Ce dépôt inclut des exemples pour configurer des pipelines dans :

### Jenkins

Créer un job pipeline Jenkins avec le script suivant :

```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t my-registry/java-spring-maven:$BUILD_NUMBER .'
            }
        }
        stage('Push Docker Image') {
            steps {
                sh 'docker push my-registry/java-spring-maven:$BUILD_NUMBER'
            }
        }
    }
}
```

### GitLab CI

Ajouter un fichier `.gitlab-ci.yml` :

```yaml
stages:
  - test
  - build
  - docker

test:
  stage: test
  script:
    - mvn test

build:
  stage: build
  script:
    - mvn clean package

docker_build:
  stage: docker
  script:
    - docker build -t $CI_REGISTRY_IMAGE:$CI_COMMIT_REF_SLUG .
    - docker push $CI_REGISTRY_IMAGE:$CI_COMMIT_REF_SLUG
```

### Azure DevOps

Créer un pipeline avec le YAML suivant :

```yaml
trigger:
- main

pool:
  vmImage: 'ubuntu-latest'

steps:
- task: Maven@3
  inputs:
    mavenPomFile: 'pom.xml'
    goals: 'test'

- task: Maven@3
  inputs:
    mavenPomFile: 'pom.xml'
    goals: 'clean package'

- task: Docker@2
  inputs:
    command: 'buildAndPush'
    repository: 'java-spring-maven'
    dockerfile: 'Dockerfile'
    containerRegistry: 'my-registry'
    tags: '$(Build.BuildId)'
```

## Exercices pour les étudiants

### Étape 1: Créer un Dockerfile multi-stage

Modifiez le Dockerfile existant pour utiliser le multi-stage building afin d'optimiser la taille de l'image finale.

**Objectif :** Réduire la taille de l'image en séparant l'étape de construction (avec Maven) de l'étape d'exécution (avec seulement le JRE).

**Indice :** Utilisez une image Maven pour la construction, copiez le JAR dans une image JRE légère.

### Étape 2: Ajouter une étape de test dans les pipelines

Ajoutez une étape de test dans les exemples de pipelines ci-dessus pour exécuter les tests unitaires avant la construction du JAR.

**Jenkins :**

Ajoutez cette étape avant 'Build' :

```groovy
stage('Test') {
    steps {
        sh 'mvn test'
    }
}
```

**GitLab CI :**

Ajoutez un job 'test' dans le stage 'test' :

```yaml
test:
  stage: test
  script:
    - mvn test
```

**Azure DevOps :**

Ajoutez cette tâche avant la tâche Maven 'clean package' :

```yaml
- task: Maven@3
  inputs:
    mavenPomFile: 'pom.xml'
    goals: 'test'
```

## Contribution

N'hésitez pas à contribuer en ajoutant plus d'exemples de pipelines ou en améliorant le code.