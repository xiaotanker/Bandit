import json
import requests

URL = "http://localhost:8080"
name = "pythonGamblerClient"


def sendPost(path):
    r = requests.post(URL + path)
    return r.text


def sendGet(path):
    r = requests.get(URL + path)
    return r.text


def getStatus(pwd):
    ret = sendGet("/gambler/status?pwd=" + pwd)
    return json.loads(ret)


def sendMove(pwd, slot, bet):
    ret = sendPost("/gambler/sendMove?pwd=" + pwd + "&slot=" + str(slot) + "&bet=" + str(bet))
    return json.loads(ret)


if __name__ == '__main__':
    pwd = sendPost("/gambler/join?name=" + name)
    status = getStatus(pwd)
    print(status)
    while not status["start"]:
        status = getStatus(pwd)

    while not status["gameOver"]:
        if status["gamblerTurn"]:
            # TODO: set current slot and bet
            sendMove(pwd, 2, min(3, status["deposit"]))
        status = getStatus(pwd)
