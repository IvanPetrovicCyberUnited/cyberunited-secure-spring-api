.PHONY: securityAll qualityAll

securityAll:
	./gradlew --no-daemon dependencyCheckAnalyze cyclonedxBom

qualityAll:
	./gradlew --no-daemon test jacocoTestReport spotbugsMain pmdMain checkstyleMain pitest
