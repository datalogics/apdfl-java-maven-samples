@Library('jenkins-shared-libraries') _
def ENV_LOC=[:]
pipeline {
    parameters {
        choice(name: 'PLATFORM_FILTER', choices: ['all', 'windows-java-samples', 'mac-arm-java-samples', 'linux-java-samples'], description: 'Run on specific platform')
        booleanParam defaultValue: false, description: 'Completely clean the workspace before building, including the Conan cache', name: 'CLEAN_WORKSPACE'
        booleanParam defaultValue: false, description: 'Run clean-samples', name: 'DISTCLEAN'
    }
    options{
        buildDiscarder logRotator(artifactDaysToKeepStr: '4', artifactNumToKeepStr: '10', daysToKeepStr: '7', numToKeepStr: '10')
        disableConcurrentBuilds()
        timeout(time: 4, unit: "HOURS")
    }
    agent none
    triggers {
        // From the doc: @midnight actually means some time between 12:00 AM and 2:59 AM.
        // This gives us automatic spreading out of jobs, so they don't cause load spikes.
        cron('@midnight')
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
                        values 'windows-java-samples', 'mac-arm-java-samples', 'linux-java-samples'
                    }
                }
                environment {
                    CONAN_USER_HOME = "${WORKSPACE}"
                    CONAN_NON_INTERACTIVE = '1'
                    CONAN_PRINT_RUN_COMMANDS = '1'
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
                            script {
                                // Ensure that the checkout is clean and any changes
                                // to .gitattributes and .gitignore have been taken
                                // into effect
                                if (isUnix()) {
                                    sh """
                                          git rm -f -q -r .
                                          git reset --hard HEAD
                                          git clean -fdx
                                    """
                                } else {
                                    // On Windows, 'git clean' can't handle long paths in .conan,
                                    // so remove that first.
                                    bat """
                                          if exist ${WORKSPACE}\\.conan\\ rmdir/s/q ${WORKSPACE}\\.conan
                                          git rm -q -r .
                                          git reset --hard HEAD
                                          git clean -fdx
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