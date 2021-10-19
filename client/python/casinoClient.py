import json
import requests
URL = "http://localhost:8080"
name = "pythonCasinoClient"


def sendPost(path):
    r = requests.post(URL+path)
    return r.text


def sendGet(path):
    r = requests.get(URL+path)
    return r.text


def getStatus(pwd):
    ret = sendGet("/casino/status?pwd="+pwd)
    return json.loads(ret)


def sendMove(pwd, winningSlot):
    ret = sendPost("/casino/sendMove?pwd="+pwd+"&winningSlot="+str(winningSlot))
    return json.loads(ret)


if __name__ == '__main__':
    pwd = sendPost("/casino/join?name="+name)

    status = getStatus(pwd)
    while not status["start"]:
        status = getStatus(pwd)

    #TODO: set the firstWiningSlot
    status = sendMove(pwd, 1)
    while not status["gameOver"]:
        if status["casinoTurn"]:
            #TODO: set winnigSlot
            sendMove(pwd, 1)
        status = getStatus(pwd)


