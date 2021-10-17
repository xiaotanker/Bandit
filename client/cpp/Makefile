all: casinoClient gamblerClient

casinoClient: casinoClient.cpp connection.h
	g++ -std=c++11 -lcurl casinoClient.cpp -o casinoClient -g
gamblerClient: gamblerClient.cpp connection.h
	g++ -std=c++11 -lcurl gamblerClient.cpp -o gamblerClient -g
clean:
	rm -rf *Client