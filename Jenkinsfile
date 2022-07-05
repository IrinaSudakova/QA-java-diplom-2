pipeline {
    agent any
    tools {
            maven 'Maven 3.8.6'
            jdk 'Java 17'
    }
    stages {
        stage ('checkout repo') {
            steps {
                git branch: 'develop2',
                credentialsId: '7984c6bc-1d41-4e25-b196-4962ffd46069',
                url: 'https://github.com/IrinaSudakova/QA-java-diplom-2.git'
            }
        }

        stage ('build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }

        stage ('run api tests') {
            steps {
                sh 'mvn test'
            }
        }
    }
    post {
        always {
            script {
                allure ([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }
}