@Library('jenkins-shared-libraries') _
def ENV_LOC=[:]
pipeline {
    parameters {
        choice(name: 'PLATFORM_FILTER', choices: ['all', 'windows-java-samples', 'windows-arm-java-samples', 'mac-arm-java-samples', 'rocky9-java-samples', 'rocky9-arm-java-samples'], description: 'Run on specific platform')
        booleanParam defaultValue: false, description: 'Completely clean the workspace before building, including the Maven package cache', name: 'CLEAN_WORKSPACE'
        booleanParam defaultValue: false, description: 'Run clean-samples', name: 'DISTCLEAN'
    }
    options{
        buildDiscarder logRotator(artifactDaysToKeepStr: '4', artifactNumToKeepStr: '10', daysToKeepStr: '7', numToKeepStr: '10')
        disableConcurrentBuilds()
        timeout(time: 4, unit: "HOURS")
    }
    agent none
    triggers {
        // The job will be triggered only for the develop branch at 7am every day.
        parameterizedCron(env.BRANCH_NAME == "develop-21" ? "0 7 * * *" : "")
    }
    stages {
        stage('Matrix stage') {
            matrix {
                agent {
                    label "${NODE}"
                }
                when { anyOf {
                    expression { params.PLATFORM_FILTER == 'all' }
                    expression { params.PLATFORM_FILTER == env.NODE }
                } }
                axes {
                    axis {
                        name 'NODE'
                        values 'windows-java-samples', 'windows-arm-java-samples', 'mac-arm-java-samples', 'rocky9-java-samples', 'rocky9-arm-java-samples'
                    }
                }
                environment {
                    APDFL_KEY = credentials('apdfl-rlm-key')
                    // Job-local Maven repository; the node-wide ~/.m2 is shared with
                    // deploy jobs that install unreleased artifacts into it.
                    MAVEN_OPTS = "-Dmaven.repo.local=${WORKSPACE}/.m2repo"
                }
                stages {
                    stage('Axis'){
                        steps {
                            printPlatformNameInStep()
                        }
                    }
                    stage('Clean/reset Git checkout for release') {
                        when {
                            expression {
                                params.CLEAN_WORKSPACE
                            }
                        }
                        steps {
                            echo "Clean ${NODE}"
                            // Removes the package caches only on manually-triggered builds
                            cleanPackageCaches()
                            script {
                                // Ensure that the checkout is clean and any changes
                                // to .gitattributes and .gitignore have been taken
                                // into effect. The Maven package cache is excluded:
                                // cleanPackageCaches owns its removal.
                                if (isUnix()) {
                                    sh """
                                          git rm -f -q -r .
                                          git reset --hard HEAD
                                          git clean -fdx -e .m2repo
                                    """
                                } else {
                                    bat """
                                          git rm -q -r .
                                          git reset --hard HEAD
                                          git clean -fdx -e .m2repo
                                    """
                                }
                            }
                        }
                    }
                    stage('Set-Up Environment') {
                        steps {
                            echo "Set-Up Environment ${NODE}"
                            script {
                                if (isUnix()) {
                                    sh 'chmod +x mkenv.py'
                                    sh './mkenv.py --verbose'
                                    ENV_LOC[NODE] = sh (
                                        script: './mkenv.py --env-name',
                                        returnStdout: true
                                    ).trim()
                                } else {
                                    // Invoke through the Python Launcher (py) explicitly rather than
                                    // relying on the .py file association, which on some Windows hosts
                                    // does not forward arguments (%*). Without the argument, mkenv.py
                                    // runs a full environment setup and prints pip output, corrupting
                                    // the value captured below.
                                    bat 'py mkenv.py --verbose'
                                    ENV_LOC[NODE] = bat (
                                        // The @ prevents Windows from echoing the command itself into the stdout,
                                        // which would corrupt the value of the returned data.
                                        script: '@py mkenv.py --env-name',
                                        returnStdout: true
                                    ).trim()
                                }
                            }
                        }
                    }
                    stage('Clean Samples') {
                        steps {
                            echo "Clean ${NODE}"
                            script {
                                if (isUnix()) {
                                    sh """. ${ENV_LOC[NODE]}/bin/activate
                                          invoke clean-samples
                                    """
                                } else {
                                    bat """CALL ${ENV_LOC[NODE]}\\Scripts\\activate
                                          invoke clean-samples
                                    """
                                }
                            }
                        }
                    }
                    stage('Build Samples') {
                        steps {
                            echo "Build ${NODE}"
                            script {
                                if (isUnix()) {
                                    sh """. ${ENV_LOC[NODE]}/bin/activate
                                          invoke build-samples
                                    """
                                } else {
                                    bat """CALL ${ENV_LOC[NODE]}\\Scripts\\activate
                                          invoke build-samples
                                    """
                                }
                            }
                        }
                    }
                    stage('Run Samples') {
                        steps {
                            echo "Running samples on ${NODE}"
                            script {
                                if (isUnix()) {
                                    sh """. ${ENV_LOC[NODE]}/bin/activate
                                          invoke run-samples
                                    """
                                } else {
                                    bat """CALL ${ENV_LOC[NODE]}\\Scripts\\activate
                                          invoke run-samples
                                    """
                                }
                            }
                        }
                    }
                    stage('Clean Samples After Run') {
                        steps {
                            echo "Clean ${NODE}"
                            script {
                                if (isUnix()) {
                                    sh """. ${ENV_LOC[NODE]}/bin/activate
                                          invoke clean-samples
                                    """
                                } else {
                                    bat """CALL ${ENV_LOC[NODE]}\\Scripts\\activate
                                          invoke clean-samples
                                    """
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
