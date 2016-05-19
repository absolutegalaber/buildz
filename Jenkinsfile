import groovy.json.JsonSlurper

node('master') {
    //checkout the branch passed as Build Parameter 'THE_BRANCH'
    checkout scm
    sh("git clean -dfx")

    withEnv(["PATH=CHECK:${tool 'jdk-8u66'}/bin:${tool 'maven-3.3.9'}/bin:${env.PATH}"]) {
        dir('buildz') {
            stage 'BUILD'
            sh("mvn package spring-boot:repackage")

            stage 'DISTRIBUTE'
            String nextVersionData = requestNextVersion()
            //parse the json into a build Number
            Long REAL_BUILD_NUMBER = extractNextVersion(nextVersionData)
            sh("docker build -t dockerregistry.sg.de.pri.o2.com/piranha/buildz-tool:${REAL_BUILD_NUMBER} .")
            sh("docker push dockerregistry.sg.de.pri.o2.com/piranha/buildz-tool:${REAL_BUILD_NUMBER}")

            stage 'PUBLISH'
            String buildData = createBuildData(REAL_BUILD_NUMBER)
            Long buildId = extractBuildId(buildData)

            sh("git rev-parse HEAD >> current_rev.txt")
            String gitCommit = readFile('current_rev.txt')
            String toStore = """
[
    {"key":"commit", "value":"${gitCommit}"},
    {"key":"build_url", "value":"${env.BUILD_URL}"},
    {"key":"docker_image", "value":"dockerregistry.sg.de.pri.o2.com/piranha/buildz-tool:${REAL_BUILD_NUMBER}"}
]
"""
            writeFile file: 'toStore.json', text: toStore
            sh("curl -X POST -H \"Content-Type: application/json\" --data @toStore.json http://builds-vpint05.vpint.o2online.de/v1/builds/addLabels/${buildId}")
        }
    }
}

String requestNextVersion() {
    sh "curl http://builds-vpint05.vpint.o2online.de/v1/buildNumbers/next/buildz-tool/master -o nextVersion.json"
    //read the returned json into a string
    return readFile('nextVersion.json')
}

@NonCPS
Long extractNextVersion(String nextVersionData) {
    def buildCount = new JsonSlurper().parseText(nextVersionData)
    return buildCount.count
}

String createBuildData(Long buildNumber) {
    sh "curl http://builds-vpint05.vpint.o2online.de/v1/builds/create/buildz-tool/master/${buildNumber} -o thisBuild.json"
    return readFile('thisBuild.json')
}

@NonCPS
Long extractBuildId(String buildData) {
    def theBuild = new JsonSlurper().parseText(buildData)
    return theBuild.id
}