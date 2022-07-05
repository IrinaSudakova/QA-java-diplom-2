pipeline {
    agent any
    tools {
            maven 'Maven 3.8.6'
            jdk 'Java 9'
    }
    stages {
        stage ('checkout repo') {
            steps {
                git branch: 'develop2',
                credentialsId: '228fc8ca-2074-4584-9309-ab2faba991d5',
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