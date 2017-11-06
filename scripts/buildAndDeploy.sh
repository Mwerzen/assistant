sudo service assistant-core stop
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
rm -r /assistant/python/*.py
cp *.py /assistant/python/.
cp secret.txt /assistant/python/.
chmod 755 /assistant/python/*
nohup python3 /assistant/python/Controller.py >> /tmp/python.out 2>&1&