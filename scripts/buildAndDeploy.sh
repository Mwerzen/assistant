cd ~/projects/assistant
git pull
cd kotlin
mvn clean install
rm /assistant/core/assistant.jar
cp target/assistant*.jar /assistant/core/assistant.jar
chmod 777 /assistant/core/assistant.jar

