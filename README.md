# Bandit Game Architect
## Description

This game involves a player P and a casino C. The casino C offers s slots (where s is determined at game day) one of which offers a 0.6 probability to win and s-1 slots having a 0.47 probability to win. C chooses which slot is the winning slot and may change the winning slot k times (also determined at game day but below s/3) over 100 * s pulls.

P begins with 100*s tokens. Before each pull, P choose a slot and bets from one to three tokens. If P wins, then P's wealth increases by that same number of tokens. If P loses, then P's wealth decreases by that same number of tokens. C is told what P's wealth is after each roll.
