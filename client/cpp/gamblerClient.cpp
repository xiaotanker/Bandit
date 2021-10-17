#include <iostream>
#include "connection.h"
#include "json.hpp"
using json = nlohmann::json;

std::string pwd;
std::string clientName = "cppGamblerClient";
bool gameOver;
bool isStart;
bool gamblerTurn;
int currentWealth;
int currentSlot;
int currentRound;
int totalSlot;//s


void getStatus(){
    std::string ret = sendGet("http://localhost:8080/gambler/status?pwd="+ pwd);
    std::cout<<ret<<std::endl;
    json j = json::parse(ret);
    totalSlot = j["totalSlot"];
    gamblerTurn = j["gamblerTurn"];
    currentSlot = j["currentSlot"];
    isStart = j["start"];
    gameOver =j["gameOver"];
    currentRound = j["currentRound"];
    currentWealth = j["deposit"];
}

void sendMove(int slot,int bet){
    assert(slot<=totalSlot);
    assert(bet>=1&&bet<=3);
    std::string ret = sendPost("http://localhost:8080/gambler/sendMove?pwd="+pwd+"&slot="+ std::to_string(slot)+"&bet="+std::to_string(bet));
    std::cout<<ret<<std::endl;
    json j = json::parse(ret);
    totalSlot = j["totalSlot"];
    gamblerTurn = j["gamblerTurn"];
    currentSlot = j["currentSlot"];
    isStart = j["start"];
    gameOver =j["gameOver"];
    currentRound = j["currentRound"];
    currentWealth = j["deposit"];

}
int main() {
    initCurl();
    pwd=sendPost("http://localhost:8080/gambler/join?name="+ clientName);
    getStatus();
    while(!isStart){
        getStatus();
    }

    while(!gameOver){
        if(gamblerTurn){
            //TODO: change the strategy
           sendMove(1,std::min(3,currentWealth));
        }
        getStatus();
    }
    cleanCurl();
    return 0;
}
