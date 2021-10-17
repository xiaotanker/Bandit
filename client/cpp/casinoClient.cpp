#include <iostream>
#include "connection.h"
#include "json.hpp"
using json = nlohmann::json;

std::string pwd;
std::string clientName = "cppCasinoClient";
int currentRound;
int totalSlot;
int winningSlot;
int switchLeft;
int currentWealth;
bool isStart =false;
bool casinoTurn=true;
bool gameOver=false;
bool changedSlot=false;

void getStatus(){
    std::string ret = sendGet("http://localhost:8080/casino/status?pwd="+ pwd);
    std::cout<<ret<<std::endl;
    json j = json::parse(ret);
    currentWealth = j["deposit"];
    totalSlot = j["totalSlot"];
    winningSlot = j["winningSlot"];
    switchLeft = j["switchLeft"];
    casinoTurn = j["casinoTurn"];
    isStart = j["start"];
    gameOver =j["gameOver"];
    currentRound = j["currentRound"];
    changedSlot = j["changedSlot"];
}

void sendMove(int newWinningSLot){
    std::string ret = sendPost("http://localhost:8080/casino/sendMove?pwd="+pwd+"&winningSlot="+ std::to_string(newWinningSLot));
    std::cout<<ret<<std::endl;
    json j = json::parse(ret);
    totalSlot = j["totalSlot"];
    winningSlot = j["winningSlot"];
    switchLeft = j["switchLeft"];
    casinoTurn = j["casinoTurn"];
    isStart = j["start"];
    gameOver =j["gameOver"];
    currentRound = j["currentRound"];
    changedSlot = j["changedSlot"];
}
int main() {
    initCurl();
    pwd=sendPost("http://localhost:8080/casino/join?name="+ clientName);
    getStatus();
    while(!isStart){
        getStatus();
    }
    
    while(!gameOver){
        if(casinoTurn){
            //TODO: change the strategy
            if(switchLeft) {
                sendMove((currentRound - 1) % 100 + 1);
            }
            else{
                sendMove(winningSlot);
            }
        }
        getStatus();
    }
    cleanCurl();
    return 0;
}
