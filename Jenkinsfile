node{
  stage('SCM') { 
    git branch: 'main', credentialsId: 'thanh1072004-github', url: 'https://github.com/thanh1072004/Nhom2_QuanLySuKien_Project1.git' 
  } 
  stage('SonarQube Analysis') { 
   def scannerHome = tool 'SonarQube Scanner'; 
    withSonarQubeEnv() { 
      sh "${scannerHome}/bin/sonar-scanner -Dsonar.java.binaries=.  -Dsonar.projectKey=Nhom2_QuanLySuKien_Project1 -Dsonar.login=qa_75bdabce5e692bcbc419a95ccdf549c6001a2914" 
    } 
  } 
}
