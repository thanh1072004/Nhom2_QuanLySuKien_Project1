node{
  stage('SCM') { 
    git branch: 'main', credentialsId: 'thanh1072004-github', url: 'https://github.com/thanh1072004/Nhom2_QuanLySuKien_Project1.git' 
  } 
  stage('SonarQube Analysis') { 
   def scannerHome = tool 'SonarQube Scanner'; 
    withSonarQubeEnv() { 
      sh "${scannerHome}/bin/sonar-scanner -Dsonar.java.binaries=.  -Dsonar.projectKey=sonar.projectKey=Nhom2_QuanLySuKien_Project -Dsonar.login=sqa_1f524ef881d910f4f75746aff1fb06772253475d" 
    } 
  } 
}
