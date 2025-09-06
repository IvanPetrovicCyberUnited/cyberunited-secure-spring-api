.PHONY: securityAll qualityAll

securityAll:
	./gradlew --no-daemon dependencyCheckAnalyze cyclonedxBom

qualityAll:
	./gradlew --no-daemon check spotbugsMain spotbugsTest pmdMain pmdTest checkstyleMain checkstyleTest
