sudo pkill -f assistant.jar
cd ~/projects/assistant
git pull
cd kotlin
mvn clean install
rm /assistant/core/assistant.jar
cp target/assistant*.jar /assistant/core/assistant.jar
chmod 744 /assistant/core/assistant.jar
sudo service assistant-core start

cd ~/projects/assistant/python
rm -r /assistant/python/.
cp *.py /assistant/python/.
cp secret.txt /assistant/python/.
nohup /assistant/python/Controller.py >> /tmp/python.out 2>&1&