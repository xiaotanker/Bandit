//
// Created by Tianbo Jiang on 10/17/21.
//

#ifndef CPP_CONNECTION_H
#define CPP_CONNECTION_H
#include <iostream>
#include <curl/curl.h>
CURL *postcurl;
CURL *getcurl;
CURLcode res;

void initCurl(){
    /* In windows, this will init the winsock stuff */
    curl_global_init(CURL_GLOBAL_ALL);


    /* get a curl handle */
    postcurl = curl_easy_init();
    getcurl = curl_easy_init();
}
static size_t WriteCallback(void *contents, size_t size, size_t nmemb, void *userp)
{
    ((std::string*)userp)->append((char*)contents, size * nmemb);
    return size * nmemb;
}
void cleanCurl(){
    if(postcurl) {
        curl_easy_cleanup(postcurl);
    }
    if(getcurl){
        curl_easy_cleanup(getcurl);
    }
    curl_global_cleanup();
}
std::string sendPost(std::string url){
    std::string readBuffer;
    if(postcurl) {
        /* First set the URL that is about to receive our POST. This URL can
           just as well be a https:// URL if that is what should receive the
           data. */
        curl_easy_setopt(postcurl, CURLOPT_URL, url.c_str());
        /* Now specify the POST data */
        curl_easy_setopt(postcurl, CURLOPT_POSTFIELDS, "");

        curl_easy_setopt(postcurl, CURLOPT_WRITEFUNCTION, WriteCallback);
        curl_easy_setopt(postcurl, CURLOPT_WRITEDATA, &readBuffer);

        /* Perform the request, res will get the return code */
        res = curl_easy_perform(postcurl);
        /* Check for errors */
        if (res != CURLE_OK)
            fprintf(stderr, "curl_easy_perform() failed: %s\n",
                    curl_easy_strerror(res));
    }
    return readBuffer;
}

std::string sendGet(std::string url){
    std::string readBuffer;
    if(getcurl) {
        curl_easy_setopt(getcurl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(getcurl, CURLOPT_NOPROGRESS, 1L);
        curl_easy_setopt(getcurl, CURLOPT_MAXREDIRS, 50L);
        curl_easy_setopt(getcurl, CURLOPT_TCP_KEEPALIVE, 1L);

        std::string response_string;
        std::string header_string;
        curl_easy_setopt(getcurl, CURLOPT_WRITEFUNCTION, WriteCallback);
        curl_easy_setopt(getcurl, CURLOPT_WRITEDATA, &readBuffer);
        curl_easy_setopt(getcurl, CURLOPT_HEADERDATA, &header_string);

        curl_easy_perform(getcurl);
        std::cout << response_string;
    }
    return readBuffer;
}
#endif //CPP_CONNECTION_H
