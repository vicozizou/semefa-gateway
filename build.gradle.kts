import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("idea")
    id("org.springframework.boot") version "2.5.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.asciidoctor.convert") version "1.5.8"
    id("no.nils.wsdl2java") version "0.12"
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.spring") version "1.5.20"
}

group = "com.saludaunclic.semefa"
version = "1.0"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
//    integrationTestCompile.extendsFrom testCompile
//    integrationTestRuntime.extendsFrom testRuntime
}

repositories {
    mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")
val snippetsDir = extra["snippetsDir"]

dependencies {
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("javax.el:javax.el-api:3.0.1-b06")
    implementation("org.hibernate.validator:hibernate-validator:7.0.1.Final")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation(files("libs/com.ibm.mq.commonservices.jar"))
    implementation(files("libs/com.ibm.mq.jmqi.jar"))
    implementation(files("libs/com.ibm.mq.headers.jar"))
    implementation(files("libs/com.ibm.mq.pcf.jar"))
    implementation(files("libs/com.ibm.mq.jar"))
    implementation(files("libs/connector.jar"))
    implementation(files("libs/jr-afiliacion-susalud-1.0.0.jar"))
    runtimeOnly("mysql:mysql-connector-java")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.apache.httpcomponents:httpclient")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

sourceSets {
    create("integrationTest") {
        withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
            kotlin.srcDir("src/integrationTest/kotlin")
            resources.srcDir("src/integrationTest/resources")
            compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
            runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
        }
    }
}

extra["cxfVersion"] = "3.3.2"
extra["cxfPluginVersion"] = "3.2.2"

wsdl2java {
    wsdlDir = file("$projectDir/src/main/wsdl")
    wsdlsToGenerate = listOf(
        listOf("$wsdlDir/firstwsdl.wsdl"),
        listOf("-xjc", "-b", "bindingfile.xml", "$wsdlDir/secondwsdl.wsdl")
    )
}

tasks.withType<Test> {
    useJUnitPlatform()
}

/*tasks.integrationTest<Test> {
    description = "Runs the integration tests"
    group = "verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    mustRunAfter(tasks["test"])
    useJUnitPlatform()
}*/

task<Test>("integrationTest") {
    description = "Runs the integration tests"
    group = "verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    mustRunAfter(tasks["test"])
    useJUnitPlatform()
}

task<Exec>("dockerInitDeps") {
    commandLine("./scripts/docker-init-deps.sh")
}

task<Exec>("dockerStopDeps") {
    commandLine("./scripts/docker-stop-deps.sh")
}

task<Exec>("dockerRestartDeps") {
    commandLine("./scripts/docker-stop-deps.sh")
    finalizedBy("dockerInitDeps")
}
