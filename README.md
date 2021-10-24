# Bandit Game Architect
## Description

This game involves a player P and a casino C. The casino C offers s slots (where s is determined at game day) one of which offers a 0.6 probability to win and s-1 slots having a 0.47 probability to win. C chooses which slot is the winning slot and may change the winning slot k times (also determined at game day but below s/3) over 100 * s pulls.

P begins with 100*s tokens. Before each pull, P choose a slot and bets from one to three tokens. If P wins, then P's wealth increases by that same number of tokens. If P loses, then P's wealth decreases by that same number of tokens. C is told what P's wealth is after each roll.

When C switches, P is not told even that a switch has happened.

If P's wealth ever reaches 0 or if the 100*s pulls are done, the game stops.

### game description update at 10/23

The player may stop playing at any time before 100* s pulls and keep the tokens P still has at that time.

player(gambler) may stop playing by sendMove with {bet} = 0.

## build and start the server
require java version 11 later, maven 3
```bash
  git clone http://github.com/xiaotanker/Bandit.git
  cd server
  mvn clean package
  java -jar ./target/bandit-server-1.0-SNAPSHOT.jar --bandit.totalSlot=20 --bandit.maxSwitch=8
```
--bandit.totalSlot stands for s, and --bandit.maxSwitch stands for k
this will start a server at localhost and port 8080
## REST apis for clients to play the game

### joining the game
|                  |                               |
|------------------|-------------------------------|
|request type:     | POST                          |
|path:             |  /casino/join?name={name}     |
|description:      |  joinning the game as the casino with name {name}|
|response status:  |  200 on success               |
|response contents:|  pwd a string for authentication|


|                  |                               |
|------------------|-------------------------------|
|request type:     | POST                          |
|path:             |  /gambler/join?name={name}     |
|description:      |  joinning the game as the gambler(player) with name {name}|
|response status:  |  200 on success               |
|response contents:|  pwd a string for authentication|

### geting the status


|                  |                               |
|------------------|-------------------------------|
|request type:     | GET                          |
|path:             |  /casino/status?pwd={pwd}     |
|description:      |  get the current status for casino|
|response status:  |  200 on success               |
|response contents:| current status, json string, example displayed down here|
```json
{ 
  "gameOver":false,
  "start":true,
  "casinoTurn":false,
  "deposit":1730,
  "currentRound":1999,
  "totalSlot":20,
  "maxSwitch":8,
  "switchLeft":0,
  "changedSlot":false,
  "winningSlot":9,
  "normalSlotRate":47,
  "winningSlotRate":60
}

```

|                  |                               |
|------------------|-------------------------------|
|request type:     | GET                          |
|path:             |  /gambler/status?pwd={pwd}     |
|description:      |  get the current status for gambler(player)|
|response status:  |  200 on success               |
|response contents:|  current status, json string, example displayed down here|
```json
{
  "gameOver":false,
  "start":true,
  "gamblerTurn":true,
  "deposit":1730,
  "currentSlot":10,
  "currentRound":1999,
  "totalSlot":20,
  "maxSwitch":8,
  "normalSlotRate":47,
  "winningSlotRate":60
}

```

### sending the  move 

|                  |                               |
|------------------|-------------------------------|
|request type:     | POST                          |
|path:             |  /casino/sendMove?pwd={pwd}&winningSlot={winningSlot}     |
|description:      |  set current winning slot to {winningSlot}|
|response status:  |  200 on success               |
|response contents:|  current status, json string, same as get status|


|                  |                               |
|------------------|-------------------------------|
|request type:     | POST                          |
|path:             |  /gambler/sendMove?pwd={pwd}&slot={slot}&bet={bet}     |
|description:      |  gambler go to slot {slot} and bets for {bet}, stop playing the game by setting {bet}=0|
|response status:  |  200 on success               |
|response contents:|  current status, json string, same as get status|

## contact us
feel free to contact us via email tj2099@nyu.edu & cz2139@nyu.edu
