def call(body) {

    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    pipeline {
        // [Pipeline] End of Pipeline
        // ERROR: Attempted to execute a step that requires a node context while ‘agent none’ was specified. Be sure to specify your own ‘node { ... }’ blocks when using ‘agent none’.
        // Finished: FAILURE
        // agent none
        agent any
        stages {
            stage("echo parameters") {
                steps {
                    sh "env | sort"
                    echo "${pipelineParams.osConfiguration}"
                    echo "${pipelineParams.osConfiguration.OS_VERSION}"
                    echo "${pipelineParams.osConfiguration.DIR_TYPE}"
                }
            }
            stage("Prepare Build Environment") {
                steps {
                    echo 'prepareBuildEnvironment()'
                    echo 'helloWorld(name: "prepareBuildEnvironment")'
                    echo 'helloWorldExternal()'
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
                    echo 'publishArtifacts(name: "publishArtifacts")'
                }
            }
            stage("Deploy Application") {
                steps {
                    echo 'deployApplication(name: "deployApplication")'
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
