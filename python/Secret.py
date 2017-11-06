
def get_secret():
    with open('secret.txt', 'r') as file:
        data = file.read().replace('\n', '')
    return data
