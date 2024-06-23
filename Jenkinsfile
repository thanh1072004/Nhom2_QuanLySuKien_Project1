node{
  stage('SCM') { 
    git branch: 'main', credentialsId: '13122004', url: 'https://github.com/thanh1072004/Nhom2_QuanLySuKien_Project1.git' 
  } 
  stage('SonarQube Analysis') { 
   def scannerHome = tool 'SonarQube Scanner'; 
    withSonarQubeEnv() { 
      sh "${scannerHome}/bin/sonar-scanner -Dsonar.java.binaries=.  -Dsonar.projectKey=Nhom2_QuanLySuKien_Project1 -Dsonar.login=sqa_5decdb34eaab4423dd256861409e13dc89b0a535" 
    } 
  } 
}
