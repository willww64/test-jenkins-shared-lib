def call(body) {

    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    pipeline {
        agent none
        stages {
            stage("echo parameters") {
                steps {
                    sh "env | sort"
                    echo "${agentLabel}"
                    echo "${pipelineParams.osConfiguration}"
                    echo "${pipelineParams.osConfiguration.OS_VERSION}"
                    echo "${pipelineParams.osConfiguration.DIR_TYPE}"
                }
            }
            stage("Prepare Build Environment") {
                steps {
                    prepareBuildEnvironment()
                    helloWorld(name: "prepareBuildEnvironment")
                    helloWorldExternal()
                }
            }
            stage("Source Code Checkout") {
                steps {
                    echo 'scc'
                }
            }
            stage("SonarQube Scan") {
                when {
                    branch 'master'
                }
                steps {
                    echo 'scan'
                }
            }
            stage("Build Application") {
                steps {
                    echo 'build'
                }
            }
            stage("Publish Artifacts") {
                steps {
                    publishArtifacts(name: "publishArtifacts")
                }
            }
            stage("Deploy Application") {
                steps {
                    deployApplication(name: "deployApplication")
                }
            }
            //stage("Long Running Stage") {
            //    agent { label "${agentLabel}" }
            //    steps {
            //        script {
            //            hook = registerWebhook()
            //        }
            //    }
            //}
            //stage("Waiting for Webhook") {
            //    steps {
            //        echo "Waiting for POST to ${hook.getURL()}"
            //        script {
            //            data = waitForWebhook hook
            //        }
            //        echo "Webhook called with data: ${data}"
            //    }
            //}         
        }
        //post {
        //    always {
        //        sendNotification()
        //    }
        //}
        // post {
        //     always {
        //       addSidebarLink()
        //     }
        // }
    }
}
